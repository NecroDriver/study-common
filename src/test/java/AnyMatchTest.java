import java.util.ArrayList;
import java.util.List;

/**
 * AnyMatchTest
 *
 * @author mfh 2021/5/11 14:28
 * @version 1.0.0
 **/
public class AnyMatchTest {

    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        list.add(4);
        list.add(2);
        list.add(41);
        list.add(1);
        list.add(15);
        list.add(99);
        final boolean b = list.stream().anyMatch(i -> {
            System.out.println(i);
            return i > 100;
        });
        System.out.println(b);
    };
}
