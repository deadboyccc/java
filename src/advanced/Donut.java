import java.util.Arrays; // For initializing arrays
import java.util.concurrent.TimeUnit; // For Thread.sleep
import java.io.IOException; // For potential system command errors

public class Donut {

  // --- Donut Parameters ---
  // R1: The radius of the torus tube (the 'thickness' of the donut).
  private static final float R1 = 1.0f;
  // R2: The distance from the center of the donut's hole to the center of its
  // tube (the 'major radius').
  private static final float R2 = 2.0f;

  // --- Screen & Projection Parameters ---
  // Adjust SCREEN_WIDTH and SCREEN_HEIGHT to best fit your terminal window size.
  // A wider terminal allows for a larger or wider donut.
  private static final int SCREEN_WIDTH = 80;
  private static final int SCREEN_HEIGHT = 22; // Using a slightly smaller height than common 24 to avoid scroll issues
                                               // on some terminals.

  // K_PROJECTION: A scaling factor that determines the visual size of the donut
  // on the screen.
  // Increase this value to make the donut appear larger; decrease it to make it
  // smaller.
  private static final float K_PROJECTION = 40.0f; // A good starting value for an 80x22 terminal.

  // K_DEPTH: Represents the simulated distance from the viewer to the center of
  // the donut.
  // Higher values make the donut appear further away, affecting perspective.
  // Calculated as R1 + R2 (the closest point on the donut to the origin) plus an
  // offset.
  private static final float K_DEPTH = R1 + R2 + 5.0f;

  // CHARACTERS: A string of characters used for shading, ordered from
  // darkest/least dense
  // to brightest/most dense. The more characters, the finer the shading gradient.
  // This sequence gives a good visual progression from empty space to solid.
  private static final String CHARACTERS = " .',:;c!jJt+*sSaA#@$%"; // A set of 20 characters for detailed shading.

  // --- Rotation Angles ---
  // A: Current rotation angle (in radians) around the X-axis.
  // B: Current rotation angle (in radians) around the Z-axis (as in the classic
  // implementation
  // which simulates a top-down rotation).
  private static float A = 0.0f;
  private static float B = 0.0f;

  // --- Animation Loop Function ---
  private static void animateDonut() {
    // Initialize buffers for the current frame to store screen state.
    // output_buffer: A 1D array representing the 2D terminal screen. Each element
    // stores
    // the ASCII character to be displayed at that pixel.
    char[] outputBuffer = new char[SCREEN_WIDTH * SCREEN_HEIGHT];
    Arrays.fill(outputBuffer, ' '); // Initialize with spaces

    // z_buffer: A 1D array (depth buffer) storing the inverse depth (1/z) for each
    // pixel.
    // Used for hidden surface removal: only draw a point if it's closer to the
    // viewer than any other point already rendered at that screen location.
    float[] zBuffer = new float[SCREEN_WIDTH * SCREEN_HEIGHT];
    Arrays.fill(zBuffer, 0.0f); // Initialize with 0.0f

    // Pre-calculate sine and cosine values for the current rotation angles A and B.
    // Doing this once per frame is more efficient than repeatedly calculating
    // inside loops.
    float cosA = (float) Math.cos(A);
    float sinA = (float) Math.sin(A);
    float cosB = (float) Math.cos(B);
    float sinB = (float) Math.sin(B);

    // Iterate through 'theta' and 'phi' to generate points across the torus
    // surface.
    // The torus is parameterized by these two angles:
    // - theta: Angle around the torus tube (minor circle, 0 to 2*pi).
    // Controls the 'vertical' position on the tube's cross-section.
    // - phi: Angle around the main circle of the donut (major circle, 0 to 2*pi).
    // Controls the 'horizontal' position around the donut's circumference.
    final float THETA_INCREMENT = 0.07f; // Step size for theta. Smaller value = more vertical points, smoother donut.
    final float PHI_INCREMENT = 0.02f; // Step size for phi. Smaller value = more horizontal points, smoother donut.

    for (float theta = 0.0f; theta < 2 * Math.PI; theta += THETA_INCREMENT) {
      float cosTheta = (float) Math.cos(theta);
      float sinTheta = (float) Math.sin(theta);

      // Calculate base coordinates (x, y) for a point on the torus before full 3D
      // rotation.
      // These are the local coordinates within the torus's own reference frame.
      float circX = R2 + R1 * cosTheta; // Distance from origin to the center of the tube's cross-section
      float circY = R1 * sinTheta; // Height relative to the major plane of the donut

      for (float phi = 0.0f; phi < 2 * Math.PI; phi += PHI_INCREMENT) {
        float cosPhi = (float) Math.cos(phi);
        float sinPhi = (float) Math.sin(phi);

        // --- 3D Rotation and Translation ---
        // These formulas transform the 3D point from the donut's local coordinates
        // to screen-space 3D coordinates, applying the A and B rotations and
        // translating the donut by K_DEPTH away from the viewer.
        float xPrime = circX * (cosB * cosPhi + sinA * sinB * sinPhi) - circY * cosA * sinB;
        float yPrime = circX * (sinB * cosPhi - sinA * cosB * sinPhi) + circY * cosA * cosB;
        float zPrime = K_DEPTH + cosPhi * circX * sinA + sinTheta * R1 * cosA;

        // --- Luminance (Shading) Calculation ---
        // L represents the light intensity at the current point on the torus surface.
        // It's calculated based on the dot product of the surface normal (direction the
        // surface faces)
        // and an implicit light source vector (assumed to be coming directly from the
        // viewer's eye).
        float L = cosPhi * cosTheta * sinB - cosTheta * sinA * sinPhi - sinTheta * cosA;

        // --- Perspective Projection to 2D Screen ---
        // ooz (one over z_prime) is crucial for perspective projection and depth
        // buffering.
        // Points further away (larger z_prime) will have a smaller ooz, making them
        // appear smaller
        // and effectively giving the illusion of depth.
        float ooz = 1.0f / zPrime;

        // Calculate the 2D screen coordinates (pixel position) where this 3D point
        // projects.
        // SCREEN_WIDTH/2 and SCREEN_HEIGHT/2 center the donut on the screen.
        // K_PROJECTION scales the donut to fit the screen.
        int projX = (int) (SCREEN_WIDTH / 2 + K_PROJECTION * xPrime * ooz);
        int projY = (int) (SCREEN_HEIGHT / 2 + K_PROJECTION * yPrime * ooz);

        // --- Depth Buffering and Character Assignment ---
        // Check if the projected point falls within the visible screen area.
        if (projX >= 0 && projX < SCREEN_WIDTH && projY >= 0 && projY < SCREEN_HEIGHT) {
          // Calculate the 1D array index corresponding to the 2D screen pixel.
          int idx = projX + SCREEN_WIDTH * projY;

          // If the current point is closer to the viewer than any point previously
          // rendered at this specific pixel, then update the z_buffer and output_buffer.
          if (ooz > zBuffer[idx]) {
            zBuffer[idx] = ooz; // Update the depth buffer with the new, closer depth.
            // Map the calculated luminance value (L) to a character from the CHARACTERS
            // string.
            // L typically ranges from -1 (darkest) to 1 (brightest).
            // This formula scales L to an appropriate index (0 to len(CHARACTERS)-1).
            int charIndex = (int) ((L + 1) / 2.0f * (CHARACTERS.length() - 1));
            // Clamp the index to ensure it's within the valid range of CHARACTERS.
            charIndex = Math.max(0, Math.min(CHARACTERS.length() - 1, charIndex));
            outputBuffer[idx] = CHARACTERS.charAt(charIndex);
          }
        }
      }
    }

    // --- Render Frame to Terminal ---
    // "\033[H" is an ANSI escape code that moves the terminal cursor to the home
    // position (top-left).
    // This causes the new frame to overwrite the previous one, creating a smooth
    // animation effect.
    System.out.print("\033[H");
    // Print each row of the output_buffer to the terminal.
    for (int i = 0; i < SCREEN_HEIGHT; ++i) {
      System.out.println(new String(outputBuffer, i * SCREEN_WIDTH, SCREEN_WIDTH));
    }
    // Flush the output buffer to ensure immediate display.
    System.out.flush();

    // --- Update Rotation Angles and Frame Rate Control ---
    // Increment angles A and B for the next frame to simulate continuous rotation.
    A += 0.04f;
    B += 0.02f;

    // Pause for a short duration to control the animation speed (frames per
    // second).
    // 0.05 seconds pause means approximately 20 frames per second (1/0.05 = 20).
    try {
      TimeUnit.MILLISECONDS.sleep(50);
    } catch (InterruptedException e) {
      // Restore interrupt status
      Thread.currentThread().interrupt();
    }
  }

  // --- Main Program Execution ---
  public static void main(String[] args) {
    try {
      // Initial setup: Clear the terminal screen and hide the cursor for cleaner
      // animation.
      // Check OS type to use appropriate clear command.
      String os = System.getProperty("os.name").toLowerCase();
      if (os.contains("win")) {
        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
      } else {
        new ProcessBuilder("clear").inheritIO().start().waitFor();
      }

      System.out.print("\033[?25l"); // ANSI escape code to hide the cursor.
      System.out.flush(); // Ensure cursor is hidden before animation starts.

      // Run the animation indefinitely until interrupted.
      while (true) {
        animateDonut();
      }

    } catch (IOException | InterruptedException e) {
      System.err.println("Error setting up terminal: " + e.getMessage());
      Thread.currentThread().interrupt(); // Restore interrupt status
    } finally {
      // This block will be executed when the program exits (e.g., via Ctrl+C).
      System.out.print("\033[?25h"); // ANSI escape code to show the cursor.
      System.out.println("\nExiting Donut program.");
      System.out.flush(); // Ensure output is visible
    }
  }
}