package crud;
import java.sql.*;

public class create
{
  public static void main( String args[] )
  {
    Connection c = null;
    Statement stmt = null;
    try {
      Class.forName("org.sqlite.JDBC");
      c = DriverManager.getConnection("jdbc:sqlite:test.db");
     // System.out.println("Opened database successfully");

      stmt = c.createStatement();
      String sql = "CREATE TABLE Rakieta " +
                   "(idRakieta INT PRIMARY KEY     NOT NULL," +
                   " marka           VARCHAR(15)    NOT NULL, " + 
                   " model            VARCHAR(30)     NOT NULL, " + 
                   " cena_zakupu        DECIMAL(8,2)); "  +
                   
                   "CREATE TABLE Adres" +
                   "(idAdres	INT	PRIMARY KEY		NOT NULL,"+
                   "ulica			VARCHAR(15)	NOT NULL,"+
                   "miasto VARCHAR(24) NOT NULL ,"+
                   "nr_mieszkania VARCHAR(3) NOT NULL ,"+
                   "nr_domu VARCHAR(3) NOT NULL ,"+
                   "kod_pocztowy CHAR(6) NULL); "+
                   
					"CREATE TABLE Trener" +
					"(idTrener INT PRIMARY KEY NOT NULL ,"+
					"imie VARCHAR(15) NOT NULL ,"+
					"nazwisko VARCHAR(20) NOT NULL ,"+
					"nr_telefonu VARCHAR(16) NULL ,"+
					"pesel CHAR(11) NULL ,"+
					"idAdres INTEGER NOT NULL, "+
					"FOREIGN KEY (idAdres) REFERENCES Adres ( idAdres ) ON UPDATE RESTRICT ON DELETE RESTRICT);"+
					"CREATE INDEX Trener_adres ON Trener (idAdres);"+
					
					"CREATE TABLE Kort "+
					"(idKort INT PRIMARY KEY NOT NULL ,"+
					"nr_kortu CHAR(2) NOT NULL ,"+
					"cena_za_godzine NUMERIC(8,2) NOT NULL) ;"+
					
					
					"CREATE TABLE Rakieta_wypozyczalnia"+
					"(idRakieta_wypozyczalnia INT PRIMARY KEY NOT NULL ,"+
					"marka VARCHAR(15) NOT NULL ,"+
					"model VARCHAR(30) NOT NULL ,"+
					"cena_za_godzine NUMERIC(8,2) NOT NULL) ;"+
					
					
					"CREATE TABLE Klient" +
					"(idKlient INT PRIMARY KEY NOT NULL ,"+
					"imie VARCHAR(15) NOT NULL ,"+
					"nazwisko VARCHAR(20) NOT NULL ,"+
					"data_ur DATE NOT NULL ,"+
					"nr_tel VARCHAR(16) NOT NULL ,"+
					"pesel CHAR(11) NOT NULL ,"+
					"idTrener INTEGER NULL ,"+
					"idAdres INTEGER NOT NULL ,"+
					"FOREIGN KEY (idAdres) REFERENCES Adres ( idAdres ) ON UPDATE RESTRICT ON DELETE RESTRICT, "+
					"FOREIGN KEY (idTrener) REFERENCES Trener ( idTrener ) ON UPDATE RESTRICT ON DELETE RESTRICT);"+
					"CREATE INDEX Klient_trener ON Klient (idTrener);"+
					"CREATE INDEX Klient_adres ON Klient (idAdres);";
					
					
  
      stmt.executeUpdate(sql);
      stmt.close();
      c.close();
    } catch ( Exception e ) {
      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
      System.exit(0);
    }
    System.out.println("Utworzono tabele");
  }

public static void java() {
	   Connection c = null;
	    Statement stmt = null;
	    try {
	      Class.forName("org.sqlite.JDBC");
	      c = DriverManager.getConnection("jdbc:sqlite:test.db");
	     // System.out.println("Opened database successfully");

	      stmt = c.createStatement();
	      String sql = "CREATE TABLE Rakieta " +
                  "(idRakieta INT PRIMARY KEY     NOT NULL," +
                  " marka           VARCHAR(15)    NOT NULL, " + 
                  " model            VARCHAR(30)     NOT NULL, " + 
                  " cena_zakupu        DECIMAL(8,2)); "  +
                  
                  "CREATE TABLE Adres" +
                  "(idAdres	INT	PRIMARY KEY		NOT NULL,"+
                  "ulica			VARCHAR(15)	NOT NULL,"+
                  "miasto VARCHAR(24) NOT NULL ,"+
                  "nr_mieszkania VARCHAR(3) NOT NULL ,"+
                  "nr_domu VARCHAR(3) NOT NULL ,"+
                  "kod_pocztowy CHAR(6) NULL); "+
                  
					"CREATE TABLE Trener" +
					"(idTrener INT PRIMARY KEY NOT NULL ,"+
					"imie VARCHAR(15) NOT NULL ,"+
					"nazwisko VARCHAR(20) NOT NULL ,"+
					"nr_telefonu VARCHAR(16) NULL ,"+
					"pesel CHAR(11) NULL ,"+
					"idAdres INTEGER NOT NULL, "+
					"FOREIGN KEY (idAdres) REFERENCES Adres ( idAdres ) ON UPDATE RESTRICT ON DELETE RESTRICT);"+
					"CREATE INDEX Trener_adres ON Trener (idAdres);"+
					
					"CREATE TABLE Kort "+
					"(idKort INT PRIMARY KEY NOT NULL ,"+
					"nr_kortu CHAR(2) NOT NULL ,"+
					"cena_za_godzine NUMERIC(8,2) NOT NULL) ;"+
					
					
					"CREATE TABLE Rakieta_wypozyczalnia"+
					"(idRakieta_wypozyczalnia INT PRIMARY KEY NOT NULL ,"+
					"marka VARCHAR(15) NOT NULL ,"+
					"model VARCHAR(30) NOT NULL ,"+
					"cena_za_godzine NUMERIC(8,2) NOT NULL) ;"+
					
					
					"CREATE TABLE Klient" +
					"(idKlient INT PRIMARY KEY NOT NULL ,"+
					"imie VARCHAR(15) NOT NULL ,"+
					"nazwisko VARCHAR(20) NOT NULL ,"+
					"data_ur DATE NOT NULL ,"+
					"nr_tel VARCHAR(16) NOT NULL ,"+
					"pesel CHAR(11) NOT NULL ,"+
					"idTrener INTEGER NULL ,"+
					"idAdres INTEGER NOT NULL ,"+
					"FOREIGN KEY (idAdres) REFERENCES Adres ( idAdres ) ON UPDATE RESTRICT ON DELETE RESTRICT, "+
					"FOREIGN KEY (idTrener) REFERENCES Trener ( idTrener ) ON UPDATE RESTRICT ON DELETE RESTRICT);"+
					"CREATE INDEX Klient_trener ON Klient (idTrener);"+
					"CREATE INDEX Klient_adres ON Klient (idAdres);";
	      
	      stmt.executeUpdate(sql);
	      stmt.close();
	      c.close();
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
	    System.out.println("Utworzono tabele");
	
}
}
