package utilities;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBUtility {

    //In this class, we implement:
    //  Opening the connection
    //  Executing the queries
    //  Closing the connection


    private static Connection connection;
    private static Statement statement;
    private static ResultSet resultSet;

    private DBUtility()
    {

    }

    // 1st : Open the connection
    public static void openConnection(String dbType)
    {
        try
        {
            System.out.println("...Connecting to DB");
            switch(dbType)
            {
                case "oracle" :
                    connection = DriverManager.getConnection(
                            Config.getProperty("oracleUrl"),
                            Config.getProperty("oracleUsername"),
                            Config.getProperty("oraclePassword")
                    );
                    break;

                default :
                    connection = null;
            }
            if(connection != null)
            {
                System.out.println("...Connection Successful");
            }
            else
                {
                    System.out.println("...Connection Null");
                    throw new SQLException();
                }

        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
            System.out.println("...Connection Unsuccessful");
        }

    }

    //2nd : Execute the queries
    public static List<Map<String, Object>> executeQuery(String query)
    {

        List<Map<String, Object>> table = new ArrayList<>();
        try
        {
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            resultSet = statement.executeQuery(query);

            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();

            while(resultSet.next())
            {
                Map<String, Object> row = new HashMap<>();
                for(int i = 1; i <= resultSetMetaData.getColumnCount(); i ++)
                {
                    row.put(resultSetMetaData.getColumnName(i) , resultSet.getObject(i));
                }
                table.add(row);
            }

        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
        }
        return table;
    }

    public static void closeConnection()
    {
        try
        {
            connection.close();
            statement.close();
            resultSet.close();
            System.out.println("...Connection closed successfully");
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
            System.out.println("...Failed to close connection");
        }

    }

    public static void printResult(List<Map<String, Object>> table)
    {
        if(table != null || table.size() != 0)
        {
            int columnCount = table.get(0).size();

            outer:
            for(Map<String, Object> row : table)
            {
                for(int i = 1; i <= columnCount; i++ )
                {
                    try
                    {
                        System.out.printf(" %11s %3s" , row.get(resultSet.getMetaData().getColumnName(i)), "|");
                    }
                    catch(SQLException ex)
                    {
                        ex.printStackTrace();
                        System.out.println("...Unable to print");
                        break outer;
                    }

                }
                System.out.println();
            }
        }
        else
            {
                System.out.println("...Table Empty");
            }

    }

}
