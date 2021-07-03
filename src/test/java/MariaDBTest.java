import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;

public class MariaDBTest {

   @Test
    public void test() throws Exception {
//       Class.forName("org.mariadb.jdbc.Driver"); // 마리아DB
//        Class.forName("com.mysql.jdbc.Driver");
       Class.forName("com.mysql.cj.jdbc.Driver"); // heroku mysql

//       Connection con = DriverManager.getConnection("jdbc:mariadb://127.0.0.1:3306/timeline", "root", "maria1234"); // 마리아 DB

//       Connection con = DriverManager.getConnection("jdbc:mariadb://timeline.cmo8b1bgzk71.ap-northeast-2.rds.amazonaws.com:3306/timeline", "admin", "maria1234"); // 마리아 DB
       Connection con = DriverManager.getConnection("jdbc:mysql://be949bab251ff0:c0683bb9@us-cdbr-east-04.cleardb.com:3306/heroku_5c9afded824fe81", "be949bab251ff0", "c0683bb9"); // mysql DB

       System.out.println(con);

   }
}
