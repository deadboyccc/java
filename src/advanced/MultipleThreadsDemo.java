package advanced;

import java.util.concurrent.TimeUnit;

public class MultipleThreadsDemo {
    public static void main(String[] args) {

    }
}
class StopWatch {
    private TimeUnit timeUnit;

    public StopWatch(TimeUnit timeUnit) {
        this.timeUnit = timeUnit;
    }
    public void countDown(int unitCount){

        String threadName = Thread.currentThread().getName();
        ThreadColor threadColor = ThreadColor.ANSI_RESET;
        try {
            threadColor = ThreadColor.valueOf(threadName);
            
        } catch (Exception e) {
            // TODO: handle exception
            //ignore
        }
        String color = threadColor.color();
    }


}
