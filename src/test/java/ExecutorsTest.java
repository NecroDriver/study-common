import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * ExecutorsTest
 *
 * @author mfh 2021/4/25 17:15
 * @version 1.0.0
 **/
public class ExecutorsTest {

    /**
     * scheduleAtFixedRate 每隔delay时间执行一次，执行前会等待上次任务执行结束
     * scheduleWithFixedDelay 在上个任务执行结束后，间隔delay时间，再执行
     * @param args
     */
    public static void main(String[] args) {
        final ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        System.out.println("init - " + LocalDateTime.now());
        scheduledExecutorService.scheduleWithFixedDelay(() -> {
            System.out.println("task - " + LocalDateTime.now());
            try {
                Thread.sleep(6000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, 2, 5, TimeUnit.SECONDS);
    }
}
