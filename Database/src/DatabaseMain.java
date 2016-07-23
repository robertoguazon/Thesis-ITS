import com.westlyf.database.DatabaseConnection;

import java.sql.Connection;

/**
 * Created by robertoguazon on 24/07/2016.
 */
public class DatabaseMain {

    public static void main(String[] args) {
        DatabaseConnection.connect();
        Connection conn = DatabaseConnection.getConnection();
        System.out.println(conn);
    }
}
