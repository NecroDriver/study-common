import com.xin.common.utils.collection.ListUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * CommonTest
 *
 * @author mfh 2020/8/2 18:05
 * @version 1.0.0
 **/
public class CommonTest {

    public static void main(String[] args) {
        List<User> list = new ArrayList<>();
        list.add(new User("1", "a"));
        list.add(new User("2", "b"));
        list.add(new User("1", "ac"));
        Map<String, String> map = new HashMap<>();
        list.forEach(user -> map.put(user.getId(), user.getName()));
//        Map<String, String> map = list.stream().collect(Collectors.toMap(User::getId, User::getName));
        System.out.println(map);
//        System.out.println(LocalDateTime.now()+"主任务执行-----");
//        try {
//            Thread.sleep(2000);
//            ExecutorService executorService = Executors.newFixedThreadPool(1);
//            executorService.execute(() -> {
//                System.out.println(LocalDateTime.now()+"异步线程执行-----");
//                try {
//                    Thread.sleep(5000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                System.out.println(LocalDateTime.now()+"异步线程执行结束-----");
//            });
//            executorService.shutdown();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        System.out.println(LocalDateTime.now()+"主任务执行结束-----");

    }
    public static class User {
        String id;
        String name;

        public User(String id, String name) {
            this.id = id;
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
