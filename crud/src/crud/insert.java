package crud;
import java.sql.*;

public class insert
{
  public static void main( String args[] )
  {
    Connection c = null;
    Statement stmt = null;
    try {
      Class.forName("org.sqlite.JDBC");
      c = DriverManager.getConnection("jdbc:sqlite:test.db");
      c.setAutoCommit(false);

      stmt = c.createStatement();
      String sql = "INSERT INTO Rakieta (idRakieta,marka,model,cena_zakupu) " +
                   "VALUES (1, 'Babolat', 'AeroPro Drive GT', 600);"; 
      stmt.executeUpdate(sql);

      sql = "INSERT INTO Rakieta (idRakieta,marka,model,cena_zakupu) " +
            "VALUES (2, 'Babolat', 'Drive Z-Tour', 500);"; 
      stmt.executeUpdate(sql);

      sql = "INSERT INTO Rakieta (idRakieta,marka,model,cena_zakupu) " +
            "VALUES (3, 'Babolat', 'Contact Tour', 150);"; 
      stmt.executeUpdate(sql);

      stmt.close();
      c.commit();
      c.close();
    } catch ( Exception e ) {
      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
      System.exit(0);
    }
    System.out.println("Rekordy dodane");
  }
}
