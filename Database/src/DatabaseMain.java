import com.westlyf.database.DatabaseConnection;

import java.sql.Connection;

/**
 * Created by robertoguazon on 24/07/2016.
 */
public class DatabaseMain {

    public static void main(String[] args) {
        test();
    }

    private static void test() {
        Connection user = DatabaseConnection.getUserConnection();
        Connection lesson = DatabaseConnection.getLessonConn();
        System.out.println(user);
        System.out.println(lesson);
    }

}
