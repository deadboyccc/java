// LiveLock demo and possible fixes                 
package advanced;

import java.util.Arrays;
import java.util.concurrent.Executors;

record Participant(String name, String searchingFor, Maze maze, int[] startingPosition) {
    public Participant {
        maze.moveLocation(startingPosition[0], startingPosition[1], name);
    }
}

class ParticipantThread extends Thread {

    private final Participant participant;

    public ParticipantThread(Participant participant) {
        super(participant.name());
        this.participant = participant;
    }

    @Override
    public void run() {
        int[] lastSpot = participant.startingPosition();
        Maze maze = participant.maze();

        while (true) {
            int[] newSpot = maze.getNextLocation(lastSpot);
            try {
                Thread.sleep(participant.name().equals("Grace") ? 2900 : 500);
                if (maze.searchCell(participant.searchingFor(), newSpot, lastSpot)) {
                    System.out.printf("%s found %s at %s!%n",
                            participant.name(),
                            participant.searchingFor(),
                            Arrays.toString(newSpot));
                    break;
                }
                synchronized (maze) {
                    maze.moveLocation(newSpot[0], newSpot[1], participant.name());
                }
                lastSpot = new int[] { newSpot[0], newSpot[1] };
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
                return;
            }
            System.out.println(participant.name() + " searching " + participant.maze());
        }
    }

}

public class MazeRunner {
    public static void main(String[] args) {
        Maze maze = new Maze();
        Participant adam = new Participant("Adam", "Grace", maze, new int[] { 3, 3 });
        Participant grace = new Participant("Grace", "Adam", maze, new int[] { 1, 1 });
        System.out.println(maze);
        try (var executor = Executors.newCachedThreadPool()) {
            var adamResult = executor.submit(new ParticipantThread(adam));
            var graceResult = executor.submit(new ParticipantThread(grace));
            while (!adamResult.isDone() && !graceResult.isDone()) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

            }
            if (adamResult.isDone()) {
                graceResult.cancel(true);
            } else if (graceResult.isDone()) {
                adamResult.cancel(true);
            }
        }

    }

}
