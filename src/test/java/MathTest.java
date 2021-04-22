import java.time.LocalDateTime;
import java.util.Date;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * MathTest
 *
 * @author mfh 2021/3/11 17:39
 * @version 1.0.0
 **/
public class MathTest {

    public static void main(String[] args) throws InterruptedException {
        Long startTime = System.currentTimeMillis();
        Thread.sleep(1000);
        Long endTime = System.currentTimeMillis();
        System.out.println(endTime);
        System.out.println(startTime);
        System.out.println(endTime-startTime);
    }

    public static void sendEnd () {
        ThreadFactory nameThreadFactory = r -> {
            Thread t = new Thread();
            t.setName("11111");
            return t;
        };
        ScheduledExecutorService scheduledExecutorService = new ScheduledThreadPoolExecutor(1, nameThreadFactory);
        scheduledExecutorService.schedule(nameThreadFactory.newThread(() -> System.out.println("sendEnd"+ LocalDateTime.now())), 6, TimeUnit.SECONDS);
    }
}
