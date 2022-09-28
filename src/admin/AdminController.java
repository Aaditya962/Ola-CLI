package admin;

import app.AppUtilities;
import custom.exceptions.InvalidCredentialsException;
import data.Data;
import driver.Driver;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Properties;
import java.util.StringTokenizer;

public class AdminController {

    static Data data = Data.getInstance();

    public static void authenticateAdmin(String userName,String password) throws InvalidCredentialsException {

        Properties adminProperties = data.getAdminDetails();
        if ((!userName.equals(adminProperties.getProperty("Name"))) || (!password.equals(adminProperties.getProperty("Password")))) {
            throw new InvalidCredentialsException("Please enter valid username/Password");
        }
    }
    static public void authorizeList(String acceptedRequests) {

        StringTokenizer requests = new StringTokenizer(acceptedRequests," ");
        LinkedList<Driver> requestList = data.getRequestList();
        HashMap<String, Driver> driversList = data.getDriversList();
        while (requests.hasMoreTokens()) {

            String driverName = requests.nextToken();
            for (Driver driver : requestList) {

                if (driver.getDriverName().equals(driverName)) {
                    driver.setPassword(AppUtilities.generatePassword(driver.getDriverName() ,driver.getVehicleType()));
                    driversList.put(driver.getContactNo(), driver);
                }
            }
        }
        data.setList("requestList", null);
    }
}
