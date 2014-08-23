package database;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class for getting the configuration options for the work with the database.
 *
 * @author Tihomir Radosavljević
 * @version 1.0
 */
public class DataBaseUtils {

    /**
     * Properties for handling the database.
     */
    private final Properties properties;

    /**
     * Loading properties.
     */
    private DataBaseUtils() {
        properties = new Properties();
        try {
            properties.load(new FileInputStream("db.properties"));

        } catch (IOException ex) {
            System.err.println("Error: " + ex.getMessage());
        }
    }

    public static DataBaseUtils getInstance() {
        return DataBaseUtilsHolder.INSTANCE;
    }

    private static class DataBaseUtilsHolder {

        private static final DataBaseUtils INSTANCE = new DataBaseUtils();
    }

    /**
     * Getting the URL for the current DBMS.
     *
     * @return URL for the database
     */
    public String getURL() {
        return properties.getProperty("url" + properties.getProperty("currentDataBase"));
    }

    /**
     * Getting the driver for the current DBMS.
     *
     * @return name of the driver file
     */
    public String getDriver() {
        return properties.getProperty("driver" + properties.getProperty("currentDataBase"));
    }

    /**
     * Getting the username for the current DBMS.
     *
     * @return username
     */
    public String getUser() {
        return properties.getProperty("user" + properties.getProperty("currentDataBase"));
    }

    /**
     * Getting the password for the current DBMS.
     *
     * @return password
     */
    public String getPassword() {
        return properties.getProperty("password" + properties.getProperty("currentDataBase"));
    }

    /**
     * Getting the URL for the specific DBMS.
     *
     * @param dbms name of the DBMS
     * @return
     */
    public String getURL(String dbms) {
        return properties.getProperty("url" + dbms);
    }

    /**
     * Getting the driver for the specific DBMS.
     *
     * @param dbms name of the DBMS
     * @return
     */
    public String getDriver(String dbms) {
        return properties.getProperty("driver" + dbms);
    }

    /**
     * Getting the username for the specific DBMS.
     *
     * @param dbms name of the DBMS
     * @return
     */
    public String getUser(String dbms) {
        return properties.getProperty("user" + dbms);
    }

    /**
     * Getting the password for the specific DBMS.
     *
     * @param dbms name of DBMS
     * @return
     */
    public String getPassword(String dbms) {
        return properties.getProperty("password" + dbms);
    }

    /**
     * Getting the name of the current DBMS.
     *
     * @return
     */
    public String getCurrentDBMS() {
        return properties.getProperty("currentDataBase");
    }

    /**
     * Getting the names of all DBMS loaded in the properties file.
     *
     * @return
     */
    public List<String> getDBManagementSystems() {
        Set<String> set = properties.stringPropertyNames();
        List<String> list = new ArrayList<>();
        String db = null;
        for (String string : set) {
            if (string.startsWith("url")) {
                list.add(string.substring(3));
            }
        }
        return list;
    }

    /**
     * Setting the URL for the specific DBMS.
     *
     * @param dbms name of the DBMS
     * @param url URL path
     */
    public void setURL(String dbms, String url) {
        properties.setProperty("url" + dbms, url);
    }

    /**
     * Setting the driver for the specific DBMS.
     *
     * @param dbms name of the DBMS
     * @param driver name of the driver
     */
    public void setDriver(String dbms, String driver) {
        properties.setProperty("driver" + dbms, driver);
    }

    /**
     * Setting the username for the specific DBMS.
     *
     * @param dbms name of the DBMS
     * @param user username
     */
    public void setUser(String dbms, String user) {
        properties.setProperty("user" + dbms, user);
    }

    /**
     * Setting the password for the specific DBMS.
     *
     * @param dbms name of the DBMS
     * @param password
     */
    public void setPassword(String dbms, String password) {
        properties.setProperty("password" + dbms, password);
    }

    /**
     * Setting what the DBMS will be in use.
     *
     * @param dbms name of the DBMS
     */
    public void setCurrentDBMS(String dbms) {
        properties.setProperty("currentDataBase", dbms);
    }

    /**
     * Save changes in the properties file.
     */
    public void saveProperies() {
        OutputStream out = null;
        try {
            out = new FileOutputStream(new File("db.properties"));
            properties.store(out, "Changed by user");
        } catch (Exception ex) {
            Logger.getLogger(DataBaseUtils.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(DataBaseUtils.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
