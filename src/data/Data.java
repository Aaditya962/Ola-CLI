package data;

import driver.Driver;
import user.User;
import user.ride.Ride;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Properties;

public class Data {
    private static Data data;
    static Properties adminDetails;
    private static HashMap<String, User> usersList;
    private static HashMap<String, Driver> driversList;

    private static LinkedList<Driver> requestList;

    private static LinkedList<Ride> rideList;
    private Data() {
        this.getAdminDetailsFromFile();
        this.getUsersListFromFile();
        this.getDriversListFromFile();
        this.getRequestListFromFile();
        rideList = new LinkedList<>();
    }

    public static Data getInstance(){
        if (data == null){
            data = new Data();
        }
        return data;
    }
    public void getAdminDetailsFromFile(){
        adminDetails = FileOperations.getProperty("Admin");
    }
    @SuppressWarnings("unchecked")     //
    public void getRequestListFromFile(){
        requestList = (isFileEmpty("requestList")) ? new LinkedList<>() : (LinkedList<Driver>) FileOperations.getList("requestList");
    }
    @SuppressWarnings("unchecked")
    public void getUsersListFromFile(){
        usersList =(isFileEmpty("UsersList")) ? new HashMap<>() : (HashMap<String, User>) FileOperations.getList("UsersList");
    }
    @SuppressWarnings("unchecked")
    public void getDriversListFromFile(){
        driversList =(isFileEmpty("DriversList")) ? new HashMap<>() : (HashMap<String, Driver>) FileOperations.getList("DriversList");
    }
    //getters
    public Properties getAdminDetails(){

        return adminDetails;
    }
    public HashMap<String, User> getUsersList(){

        return usersList;
    }
    public HashMap<String, Driver> getDriversList() {

        return driversList;
    }
    public LinkedList<Driver> getRequestList() {

        return requestList;
    }
    public void setUsersList(HashMap<String, User> usersList) {
        Data.usersList = usersList;
    }

    public void setDriversList(HashMap<String, Driver> driversList) {
        Data.driversList = driversList;
    }
    @SuppressWarnings("unchecked")

    //setters
    public void setList(String UserType,Object list){
        switch (UserType) {
            case "requestList" -> {
                requestList = (LinkedList<Driver>) list;
                FileOperations.writeToFile("requestList", requestList);
            }
            case "User" -> {
                usersList = (HashMap<String, User>) list;
                FileOperations.writeToFile("UsersList", usersList);
            }
            case "Driver" -> {
                driversList = (HashMap<String, Driver>) list;
                FileOperations.writeToFile("DriversList", driversList);
            }
        }
    }

    public LinkedList<Ride> getRideList() {
        return rideList;
    }

    public void setRideList(LinkedList<Ride> rideList) {
        Data.rideList = rideList;
    }

    public boolean isFileEmpty(String fileName){
        try {
            return FileOperations.isFileEmpty(fileName);
        }
        catch (IOException exception){
            System.out.println(exception);
            return false;
        }
    }
}
