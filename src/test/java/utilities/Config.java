package utilities;

import java.io.FileInputStream;
import java.util.Properties;

public class Config {

    private static Properties property = new Properties();

    static
    {
        String path = "/Users/experience/Desktop/JDBCTest/configurations.properties";
        try
        {
            FileInputStream file = new FileInputStream(path);
            property.load(file);
            file.close();
        }
        catch (Exception ex)
        {
            System.out.println(ex.getMessage());
            System.out.println("File was not found!");
        }
    }
    public static String getProperty(String keyword)
    {
        return property.getProperty(keyword);
    }
}
