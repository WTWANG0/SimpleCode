package T10_;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Test {
    public static void main(String[] args) {
        String SDF_DATETIME = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat sf = new SimpleDateFormat(SDF_DATETIME);
        long time = 1671366600000L;
        System.out.println(sf.format(new Date(time)));
        System.out.println("--------");
    }
}
