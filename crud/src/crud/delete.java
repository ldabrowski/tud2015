package crud;
import java.sql.*;

public class delete
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
      String sql = "DELETE from Rakieta where idRakieta=3;";
      stmt.executeUpdate(sql);
      c.commit();

      ResultSet rs = stmt.executeQuery( "SELECT * FROM Rakieta;" );
      while ( rs.next() ) {
    	  int idRakieta = rs.getInt("idRakieta");
          String  marka = rs.getString("marka");
          String model  = rs.getString("model");
          int  cena_zakupu = rs.getInt("cena_zakupu");
          System.out.println( "Id = " + idRakieta );
          System.out.println( "Marka = " + marka );
          System.out.println( "Model = " + model );
          System.out.println( "Cena zakupu = " + cena_zakupu );
          System.out.println();
      }
      rs.close();
      stmt.close();
      c.close();
    } catch ( Exception e ) {
      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
      System.exit(0);
    }
    System.out.println("Ta promocja to jednak blad, wiec usunmy ta pozycje");
  }

}
