import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Locale;

public class MariaDBTest {

   @Test
    public void test() throws Exception {

      System.out.println(System.getProperty("os.name").toLowerCase(Locale.ROOT));
//       Class.forName("org.mariadb.jdbc.Driver"); // 마리아DB
//        Class.forName("com.mysql.jdbc.Driver");
       Class.forName("com.mysql.cj.jdbc.Driver"); // heroku mysql

//       Connection con = DriverManager.getConnection("jdbc:mariadb://127.0.0.1:3306/timeline", "root", "maria1234"); // 마리아 DB

//       Connection con = DriverManager.getConnection("jdbc:mariadb://timeline.cmo8b1bgzk71.ap-northeast-2.rds.amazonaws.com:3306/timeline", "admin", "maria1234"); // 마리아 DB
       Connection con = DriverManager.getConnection("jdbc:mysql://mfli1shnwhgt4evj:ohn9ca3mz3wdtbw3@kfgk8u2ogtoylkq9.cbetxkdyhwsb.us-east-1.rds.amazonaws.com:3306/qoeq2gfiz0laftgd", "mfli1shnwhgt4evj", "ohn9ca3mz3wdtbw3"); // heroku jawsDB : mysql DB

       System.out.println(con);
   }
}
