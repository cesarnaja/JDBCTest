package test;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DatabaseTest {

    public static void main(String[]args)
    {
        //url ec2-3-14-126-109.us-east-2.compute.amazonaws.com:1521
        //username hr
        //password hr

        String oracleURL = "jdbc:oracle:thin:@ec2-3-14-126-109.us-east-2.compute.amazonaws.com:1521:xe";
        String oracleUsername = "hr";
        String oraclePassword = "hr";

        // How do we store all the data we get from the data base?

        //We need to store it in a Key & Value format
        /*
                           MAP1
               | first_name | last_name |
               |   Ellen    |     Abel  |

                           MAP2
               | first_name | last_name |
               |   Sundar   |   Andre  |

                           MAP3
               | first_name | last_name |
               |   Mike     |  Wasauski |

         */

        // *** Each map is a row in the table ***

/*      Map<String , Object> map1 = new HashMap<>();
        map1.put("first_name" , "Ellen");
        map1.put("last_name" , "Abel");

        Map<String , Object> map2 = new HashMap<>();
        map2.put("first_name" , "Sundar");
        map2.put("last_name" , "Andre");

        Map<String, Object> map3 = new HashMap<>();
        map3.put("first_name" , "Mike");
        map3.put("last_name" , "Wasauski");

        //Now we store all these maps in an arraylist
        List<Map<String, Object>> table = new ArrayList<>();
        table.add(map1);
        table.add(map2);
        table.add(map3);

        for(Map<String, Object> map : table)
        {
            System.out.println(map.get("first_name")  + " " + map.get("last_name"));
        }*/

        try
        {
            System.out.println("...Connecting to DB");
            Connection connection = DriverManager.getConnection(oracleURL, oracleUsername, oraclePassword);
            System.out.println("...Connection successful!");

            Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            ResultSet resultSet = statement.executeQuery("select * from employees");

            // After this resultSet.next() method,
            // the resultSet will be pointing to the first row of data in the table.
            resultSet.next();

            //Now we will attempt to retrieve the data in the first row
            //second column, it should return 'Steven'
            //System.out.println("\nFirst Name: " + resultSet.getObject(2));

            //Now we use a loop to print out all the first names
            /*while(resultSet.next())
            {
                System.out.println(resultSet.getObject(2));
            }*/

            //Now we will try to print out the whole table
            //While getting 'x' number of columns dynamically

            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();

/*            while(resultSet.next())
            {
                for(int i = 1 ; i <= resultSetMetaData.getColumnCount(); i ++)
                {
                    System.out.printf("%12s %2s " , resultSet.getObject(i), "|");
                }
                System.out.println();
            }*/

            //How do we get the column name so we can store our data?
            //Lets try to store everything into the maps and then list
            //DYNAMICALLY
            Map<String, Object> map;
            List<Map<String, Object>> table = new ArrayList<>();

            while(resultSet.next())
            {
                map = new HashMap<>();
                for(int i = 1; i < resultSetMetaData.getColumnCount(); i ++)
                {
                    map.put(resultSetMetaData.getColumnName(i) , resultSet.getObject(i));
                }
                table.add(map);
            }

            //Now we print out everything
            for(Map<String, Object> row : table)
            {
                for(int i = 1; i < resultSetMetaData.getColumnCount(); i ++)
                {
                    System.out.printf("%10s %5s" ,row.get(resultSetMetaData.getColumnName(i)), "|");
                }
                System.out.println();
            }

        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
            System.out.println("Failed to open connection");
        }





    }
}
