package advanced;

public class CustomThread extends Thread {
    @Override
    public void run() {
        // TODO Auto-generated method stub
        // super.run();
        for (int i = 0; i <= 5; i++) {
            System.out.print(" 1 ");
            try {

                Thread.sleep(1000);
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }

        }
    }

}
