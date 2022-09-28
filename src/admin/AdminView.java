package admin;

import app.AppUtilities;
import custom.exceptions.EmptyListException;
import data.Data;
import driver.Driver;
import driver.DriverView;
import user.User;
import user.UserView;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class AdminView {

    static Data data = Data.getInstance();

    public static void showAdminInterface() {
        System.out.println("Welcome admin ");
        int adminInterfaceChoice;
        final int USER = 1, DRIVER = 2, AUTHORIZE = 3,BACK = 4;
        do {
            System.out.println("1.Show users");
            System.out.println("2.Show drivers");
            System.out.println("3.Authorize drivers");
            System.out.println("4.Logout");
            adminInterfaceChoice = AppUtilities.getInteger();
            switch (adminInterfaceChoice) {
                case USER -> displayUsers();
                case DRIVER -> displayDrivers();
                case AUTHORIZE -> {
                    try {
                        AdminView.viewRequestList();
                        System.out.println("Enter the driver's Name from the list to accept");
                        String acceptedRequests = AppUtilities.getString();
                        AdminController.authorizeList(acceptedRequests);
                        System.out.println("The request list is now empty");
                    }
                    catch (EmptyListException exception){
                        System.out.println(exception);
                    }
                }
            }
        } while (adminInterfaceChoice != BACK);
        System.out.println(" Redirecting to the dashboard ");
    }

    public static void displayUsers() {
        HashMap<String, User> usersList = data.getUsersList();
        for (Map.Entry<String, User> userEntry : usersList.entrySet()) {
            UserView.displayUser(userEntry.getValue());
        }
    }
    public static void displayDrivers() {
        HashMap<String, Driver> driversList = data.getDriversList();
        for (Map.Entry<String, Driver> driverEntry : driversList.entrySet()) {
            DriverView.displayDriver(driverEntry.getValue());
        }
    }
    public static void viewRequestList() throws EmptyListException{

        LinkedList<Driver> requestList = data.getRequestList();
        if(requestList == null) {throw new EmptyListException("Request list is empty");}
        else {
            for (Driver driver : requestList) {
                DriverView.displayDriver(driver);
            }
        }
    }
}