package test;

import utilities.Config;
import utilities.DBUtility;

import java.util.List;
import java.util.Map;

public class DBTestWithDBUtil {

    public static void main(String[]args)
    {
        DBUtility.openConnection(Config.getProperty("dbType"));

        List<Map<String, Object>> table = DBUtility.executeQuery("select * from employees");

        DBUtility.printResult(table);

        DBUtility.closeConnection();
    }
}
