package driver;

import app.AppUtilities;
import custom.exceptions.EntryNotFoundException;
import custom.exceptions.InvalidCredentialsException;
import data.Data;
import user.ride.Ride;
import java.util.HashMap;
import java.util.LinkedList;

public class DriverController {

    static Data data = Data.getInstance();
    public static void signUpDriver(String contactNo, String userName,String vehicleType) {
        LinkedList<Driver> requestList = data.getRequestList();
        requestList.add(new Driver(userName,contactNo,vehicleType));
    }
    public static Driver authenticateDriver(String contactNo, String password) throws InvalidCredentialsException, EntryNotFoundException {
        HashMap<String, Driver> driversList = data.getDriversList();

        if(driversList.containsKey(contactNo)){
            if(driversList.get(contactNo).getPassword().equals(AppUtilities.encrypt(password))){
                return driversList.get(contactNo);
            }
            else {
                throw new InvalidCredentialsException("Please enter correct username/password");
            }
        }
        else{
            throw new EntryNotFoundException("No entry found");
        }
    }
    public static void modifyDriverName(Driver driver, String value) {
        driver.setDriverName(value);
        updateDriverInList(driver);
    }
    public static void modifyDriverVehicleType(Driver driver, String value) {
        driver.setVehicleType(value);
        updateDriverInList(driver);
    }
    public static void modifyDriverHistory(Driver driver) {
        LinkedList<Ride> driverRideHistory = (driver.getRidesHistory()==null) ? new LinkedList<>() : driver.getRidesHistory();
        driverRideHistory.add(driver.getCurrentRide());
        driver.setRidesHistory(driverRideHistory);
        updateDriverInList(driver);
    }
    public static void modifyDriverNumber(Driver driver, String value) {
        HashMap<String,Driver> driversList = data.getDriversList();
        driversList.remove(driver.getContactNo());
        driver.setContactNo(value);
        driversList.put(driver.getContactNo(),driver);
        data.setDriversList(driversList);
    }
    public static void modifyDriverPassword(Driver driver, String password, String confirmedPassword) throws InvalidCredentialsException{
        if(password.equals(confirmedPassword)){
            driver.setPassword(AppUtilities.encrypt(password));
            updateDriverInList(driver);
        }
        else{
            throw new InvalidCredentialsException("Please enter your password correctly");
        }
    }
    public static void removeDriver(Driver driver, String password) throws InvalidCredentialsException{
        if(driver.getPassword().equals(AppUtilities.encrypt(password))){
            HashMap<String,Driver> driversList = data.getDriversList();
            driversList.remove(driver.getContactNo());
            data.setDriversList(driversList);
        }
        else {
            throw new InvalidCredentialsException("Please enter correct password to delete your account");
        }
    }
    public static void updateDriverInList(Driver driver){
        HashMap<String, Driver> driversList = data.getDriversList();
        driversList.put(driver.getContactNo(),driver);
        data.setDriversList(driversList);
    }

}
