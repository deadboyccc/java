import org.junit.Test;
import static org.junit.Assert.*;

public class testDemoJunitTest {

  @Test
  public void testAddOneWithZero() {
    int result = invokeAddOne(0);
    assertEquals(1, result);
  }

  @Test
  public void testAddOneWithPositiveNumber() {
    int result = invokeAddOne(5);
    assertEquals(6, result);
  }

  @Test
  public void testAddOneWithNegativeNumber() {
    int result = invokeAddOne(-3);
    assertEquals(-2, result);
  }

  private int invokeAddOne(int x) {
    try {
      java.lang.reflect.Method method = testDemoJunit.class.getDeclaredMethod("addOne", int.class);
      method.setAccessible(true);
      return (int) method.invoke(null, x);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}