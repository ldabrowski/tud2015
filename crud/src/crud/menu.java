package crud;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Scanner;
 
public class menu {
	

    public static int menu(){
    	
    	
    	
    	
        System.out.println();
        System.out.println("     ****************************************");
        System.out.println("     *                 MENU                 *");
        System.out.println("     ****************************************");
        System.out.println("     1. Tworzenie tabeli");
        System.out.println("     2. Insert");
        System.out.println("     3. Select");
        System.out.println("     4. Update");
        System.out.println("     5. Delete");
        System.out.println("     0. Koniec");
 
        Scanner in = new Scanner(System.in);
        int w = in.nextInt();
 
        return w;
    }

	public static void main(String[] args) throws IOException {
        Scanner in = new Scanner(System.in);
        
      
 
        int wybor = menu();
 
        while(wybor!=0){
        	
            switch(wybor){
            case 1:
            	
            	create.main(null);
            	
                break;
            
                case 2:
                	
                	insert.main(null);
                	
                    break;
 
                case 3:
                
                	select.main(null);
                	
                    break;
 
                case 4:
                	
                   update.main(null);
                    
                    break;
                    
                case 5:
                	
                    delete.main(null);
                    
                    break;
 
            }
 
            System.out.println("\nWciœnij Enter, aby kontynuowaæ...");
            System.in.read();
 
            wybor = menu();
        }
 
 
        System.out.println("     ****************************************");
        System.out.println("\n     Koniec programu\n\n");
    }
	}
