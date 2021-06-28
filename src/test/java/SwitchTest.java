import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * SwitchTest
 *
 * @author mfh 2021/5/12 15:04
 * @version 1.0.0
 **/
public class SwitchTest {

    public static void main(String[] args) {
        ArrayList<EntityValues> list = new ArrayList<>();
        EntityValues values = new EntityValues("name", "value", "id");
        list.add(values);
        list.add(new EntityValues("name1", "value1", "id1"));
        list.add(new EntityValues("name2", "value2", "id2"));
        for (EntityValues entityValues : list) {
            System.out.println(entityValues.classId);
        }
        for (int i = 0; i < list.size(); i++) {
            list.set(i, new EntityValues("name3", "value3", "id3"));
        }
        for (EntityValues entityValues : list) {
            System.out.println(entityValues.classId);
        }
    }


}

class EntityValues {

    EntityValues(String className, String classValue, String classId) {
        this.className = className;
        this.classValue = classValue;
        this.classId = classId;
    }

    public String className;
    public String classValue;
    public String classId;
}
