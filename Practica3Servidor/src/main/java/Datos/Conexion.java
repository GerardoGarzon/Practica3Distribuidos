package Datos;

import java.sql.*;

public class Conexion {
     private static final String JDBC_URL = "jdbc:mysql://frwahxxknm9kwy6c.cbetxkdyhwsb.us-east-1.rds.amazonaws.com:3306/nbhlw7ym2dqt5xyy?useSSL=false&useTimezone=true&serverTimezone=UTC&allowPublicKeyRetrieval=true";
     private static final String JDBC_USER = "i3aep0unb41yipvv";
     private static final String JDBC_PASSWORD = "j5wgha7cpo8a0x2q";

     public static Connection getConnection () throws SQLException {
          return DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
     }

     public static void close(ResultSet rs) throws SQLException {
          rs.close();
     }

     public static void close(PreparedStatement smtm) throws SQLException {
          smtm.close();
     }

     public static void close(Connection conn) throws SQLException {
          conn.close();
     }
}
