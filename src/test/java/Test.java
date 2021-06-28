import java.math.BigInteger;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Test
 *
 * @author mfh 2021/1/15 14:36
 * @version 1.0.0
 **/
public class Test {
    public static void main(String[] args) throws UnknownHostException, ParseException {
//        List<Test.CourseSystemCategory> list = new ArrayList<>();
//        list.add(Test.CourseSystemCategory.LaborWork);
//        System.out.println(list.get(1));
//        System.out.println(list.contains(Test.CourseSystemCategory.LaborWork));

//        int pages = (3999-1)/1000 + 1;
//        System.out.println(pages);

        Date now = new Date();
        System.out.println(now.getTime());
        System.out.println(System.currentTimeMillis());
        System.out.println(now);
        Date date = new Date(now.getTime() + 1000 * 60);
        System.out.println(date);
        System.out.println(date.getTime());
    }

    public static BigInteger getNanos(Instant instant) {
        return BigInteger.valueOf(instant.getEpochSecond())
                .multiply(BigInteger.valueOf(1000_000_000L))
                .add(BigInteger.valueOf(instant.getNano()));
    }

    /**
     * 课程的系统分类【故乡云总平台分类，任何分平台无此权限】
     */
    public enum CourseSystemCategory {
        /**
         * 思政课
         */
        Political("思政课程", 0),
        /**
         * 基础课
         */
        General("公共基础", 1),
        /**
         * 专业课
         */
        Professional("专业基础", 2),
        /**
         * 计划课
         */
        Plan("专业计划", 3),
        /**
         * 拓展课
         */
        Extension("专业拓展", 4),
        /**
         * 人工智能
         */
        ArtificialIntelligence("人工智能", 5),
        /**
         * 智能制造
         */
        IntelligentManufacture("智能制造", 6),
        /**
         * 现代学徒
         */
        ModernApprentice("现代学徒", 7),
        /**
         * 新型学徒
         */
        NewApprentice("新型学徒", 8),
        /**
         * 证书课
         */
        Certificate("技能证书", 9),
        /**
         * 校本培训
         */
        SchoolBasedTraining("校本培训", 10),
        /**
         * 企业培训
         */
        CorporateTraining("企业培训", 11),
        /**
         * 劳动课
         */
        LaborWork("劳动教育", 12),
        /**
         * 其他
         */
        Other("其他", 13),
        ;

        private final String name;

        private final Integer index;

        CourseSystemCategory(String name, Integer index) {
            this.name = name;
            this.index = index;
        }

        public String getName() {
            return this.name;
        }

        public Integer getIndex() {
            return index;
        }

        public static boolean exist(String name) {
            for (CourseSystemCategory bizAction : CourseSystemCategory.values()) {
                if (bizAction.name().equals(name)) {
                    return true;
                }
            }
            return false;
        }
    }
}
