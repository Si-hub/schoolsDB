package Schools_Database;

import com.github.javafaker.Faker;

import java.sql.*;


public class Schools{
   public static void main(String[] args)
   {
      Connection connection = null;
      try
      {
         // create a database connection
         connection = DriverManager.getConnection("jdbc:sqlite:localSchools.db");
         Statement statement = connection.createStatement();
         String sql = "insert into employees (" + "employees_id, first_name," +
                 " email, phone_number,position) VALUES(?,?,?,?,?)";

         statement.setQueryTimeout(30);  // set timeout to 30 sec.


         statement.executeUpdate("CREATE TABLE IF NOT EXISTS employees(" +
                 "employees_id integer PRIMARY KEY," +
                 "first_name string,"  +
                 "email string," +
                 "phone_number string," +
                 "position string)");

         ResultSet rs = statement.executeQuery("select * from employees");
          PreparedStatement pstmt = connection.prepareStatement(sql);

         // Insert 10 000 records in the database
         int count = 0;
         while(count < 10000){
             //creating faker object
            Faker faker = new Faker();


          String firstName = faker.name().firstName(); // "Sim"
          String email = faker.internet().emailAddress(); // "sim@gmail.com"
          String phoneNumber = faker.phoneNumber().cellPhone(); // "0868353479"
          String position = faker.job().position();// programmer

            pstmt.setString(2, firstName);
            pstmt.setString(3, email);
            pstmt.setString(4, phoneNumber);
            pstmt.setString(5, position);

            count++;

             // read the result set
            /* System.out.println("first_name = " + firstName + " | " +
             "email = " + email + " | " +
             "phone_number = " + phoneNumber + " | " +
             "position = " + position);*/

            pstmt.executeUpdate();
         }
      }
      catch(SQLException e)
      {
         // if the error message is "out of memory",
         // it probably means no database file is found
         System.err.println(e.getMessage());
      }
      finally
      {
         try
         {
            if(connection != null)
               connection.close();
         }
         catch(SQLException e)
         {
            // connection close failed.
            System.err.println(e.getMessage());
         }
      }
   }
}
