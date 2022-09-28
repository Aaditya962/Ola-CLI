package user.ride;

import custom.exceptions.EntryNotFoundException;
import custom.exceptions.InvalidCancellationException;
import data.Data;
import driver.Driver;
import driver.DriverController;
import user.User;
import user.UserController;
import java.util.LinkedList;

public class RideController {

    static Data data = Data.getInstance();
    public static void addRideToList(Ride ride) {
        LinkedList<Ride> rideList = data.getRideList();
        rideList.add(ride);
        data.setRideList(rideList);
    }
    public static LinkedList<Ride> getRides(Driver driver, String city){
        LinkedList<Ride> ridesList = data.getRideList();
        LinkedList<Ride> cityRidesList  = new LinkedList<>();
        for(Ride ride : ridesList){
            if((ride.getPickupLocation().equals(city))&&(ride.getStatus()==Ride.StatusValues.BOOKED)&&(ride.getVehicleType().equals(driver.getVehicleType()))){
                cityRidesList.add(ride);
            }
        }
        return cityRidesList;
    }

    public static void assignRide(Driver driver,String city,String userRequest) throws EntryNotFoundException {
        LinkedList<Ride> ridesList = getRides(driver,city);
        for(Ride ride : ridesList){
            if(ride.getUserId().equals(userRequest)){
                ride.setStatus(Ride.StatusValues.ASSIGNED);
                ride.setDriverId(driver.getContactNo());
                driver.setCurrentRide(ride);
                switch (driver.getVehicleType().toUpperCase()){
                    case "AUTO" -> ride.setCost(100);
                    case "BIKE" -> ride.setCost(70);
                    case "MINI" -> ride.setCost(150);
                    case "PRIME" -> ride.setCost(250);
                }
            }
        }
        if(driver.getCurrentRide()==null){
            throw new EntryNotFoundException(" No such  found ");
        }
    }
    public static void cancelRideByUser(Ride ride) throws InvalidCancellationException {

        if(ride.getStatus()== Ride.StatusValues.BOOKED){                           //cancelled before assigning a driver
            ride.setStatus(Ride.StatusValues.CANCELLED);
            User user = data.getUsersList().get(ride.getUserId());
            UserController.modifyUserHistory(user);
            LinkedList<Ride> rideList = data.getRideList();
            rideList.remove(ride);
            data.setRideList(rideList);
            user.setCurrentRide(null);
        }
        else if(ride.getStatus()== Ride.StatusValues.ASSIGNED) {                       //cancelled after assigning the driver
            ride.setStatus(Ride.StatusValues.CANCELLED);
            User user = data.getUsersList().get(ride.getUserId());
            Driver driver = data.getDriversList().get(ride.getDriverId());
            UserController.modifyUserHistory(user);
            DriverController.modifyDriverHistory(driver);
            LinkedList<Ride> rideList = data.getRideList();
            rideList.remove(ride);
            data.setRideList(rideList);
            driver.setCurrentRide(null);
            user.setCurrentRide(null);
        }
        else throw new InvalidCancellationException(" Ride cannot be cancelled ");
    }
    public static void cancelRideByDriver(Ride ride) throws InvalidCancellationException {                              //ride cancelled by driver
        if(ride.getStatus()!= Ride.StatusValues.PICKED) {
            Driver driver = data.getDriversList().get(ride.getDriverId());
            Ride ride1 = new Ride(ride);
            ride1.setStatus(Ride.StatusValues.CANCELLED);
            driver.setCurrentRide(ride1);
            DriverController.modifyDriverHistory(driver);
            LinkedList<Ride> rideList = data.getRideList();
            rideList.remove(ride);
            ride.setStatus(Ride.StatusValues.BOOKED);
            ride.setDriverId(null);
            rideList.add(ride);
            driver.setCurrentRide(null);
            data.setRideList(rideList);
        }
        else throw new InvalidCancellationException(" Ride cannot be cancelled ");
    }

    public static void endRide(Ride ride) {                         //When ride gets completed
        User user = data.getUsersList().get(ride.getUserId());
        Driver driver = data.getDriversList().get(ride.getDriverId());
        DriverController.modifyDriverHistory(driver);
        UserController.modifyUserHistory(user);
        driver.setCity(ride.getDropLocation());
        LinkedList<Ride> rideList = data.getRideList();
        rideList.remove(ride);
        data.setRideList(rideList);
        driver.setCurrentRide(null);
        user.setCurrentRide(null);
    }
}
