package driver;

import app.AppUtilities;
import custom.exceptions.*;
import user.ride.Ride;
import user.ride.RideController;
import java.util.LinkedList;
import static user.UserView.displayRide;

public class DriverView {

    private enum driverChoice
    {
        RIDES(1),ACCOUNT(2), DETAILS(0),HISTORY(3),EXIT(4);
        private final int value;

        driverChoice(int value){

            this.value = value;
        }
    }
    public static void displayDriverInterface(Driver driver) {
        System.out.println("Welcome " + driver.getDriverName());
        int driverInterfaceChoice;
        String city;
        do {

            System.out.println("1.Show rides request ");
            System.out.println("2.Account Management");
            System.out.println("3.Show rides History");
            System.out.println("4.Logout");
            if(driver.getCurrentRide()!=null){
                System.out.println("0.About Current Ride ");
            }
            driverInterfaceChoice = AppUtilities.getInteger();

            if (driverInterfaceChoice == driverChoice.RIDES.value) {
                if(driver.getCurrentRide()==null) {
                    if (driver.getCity() == null) {
                        System.out.println("Enter your location");
                        city = AppUtilities.getString();
                    } else {
                        city = driver.getCity();
                    }
                    displayRidesToDriver(driver, city);
                    System.out.println("Select a ride (userid) ");
                    String userRequest = AppUtilities.getString();
                    try {
                        RideController.assignRide(driver, city, userRequest);
                        System.out.println("Ride assigned");
                    } catch (EntryNotFoundException exception) {
                        System.out.println(exception);
                    }
                }
                else{
                    System.out.println("Complete the ongoing ride to select other rides");
                }
            } else if (driverInterfaceChoice == driverChoice.ACCOUNT.value) {

                final int MODIFY = 1;
                final int DISPLAY = 2;
                final int REMOVE = 3;
                final int BACK = 4;
                final int DELETE = 5;
                int driverAccountChoice;
                do {
                    System.out.println("1.Modify Account details");
                    System.out.println("2.Display Account details");
                    System.out.println("3.Delete Account");
                    System.out.println("4.Go back");
                    driverAccountChoice = AppUtilities.getInteger();
                    switch (driverAccountChoice) {
                        case MODIFY -> {
                            try {
                                System.out.println("Enter the field to modify(name/number/password/vehicle)");
                                String field = AppUtilities.getString().toUpperCase();
                                System.out.println("Enter the modified field");
                                String value = AppUtilities.getString();
                                switch (field) {
                                    case "NAME" -> DriverController.modifyDriverName(driver, value);
                                    case "NUMBER" -> DriverController.modifyDriverNumber(driver, value);
                                    case "PASSWORD" -> {
                                        System.out.println("Confirm password : ");
                                        String confirmedValue = AppUtilities.getString();
                                        DriverController.modifyDriverPassword(driver, value, confirmedValue);
                                    }
                                    case "VEHICLE" -> DriverController.modifyDriverVehicleType(driver, value);
                                }
                            } catch (InvalidCredentialsException exception) {
                                System.out.println(exception);
                            }
                            System.out.println("Account modified successfully");
                        }
                        case DISPLAY -> DriverView.displayDriver(driver);
                        case REMOVE -> {
                            try {
                                System.out.println("Enter password");
                                String password = AppUtilities.getString();
                                DriverController.removeDriver(driver, password);
                                System.out.println("Account deleted successfully");
                                driverAccountChoice = DELETE;
                            } catch (InvalidCredentialsException exception) {
                                System.out.println(exception);
                            }
                        }
                    }
                } while ((driverAccountChoice != BACK) && (driverInterfaceChoice != DELETE));
                if (driverAccountChoice == DELETE){
                    driverInterfaceChoice = driverChoice.EXIT.value;
                }
            }
                else if (driverInterfaceChoice == driverChoice.HISTORY.value){
                    System.out.println("1.Show history ");
                    System.out.println("2.Clear history ");
                    int choice = AppUtilities.getInteger();
                    if(choice == 1) {
                        try {
                            LinkedList<Ride> ridesList = driver.getRidesHistory();
                            for (Ride ride : ridesList) {
                                displayRide(ride);
                            }
                        }catch (NullPointerException exception){
                            System.out.println(exception);
                        }
                    }
                    else if(choice == 2){
                        driver.setRidesHistory(null);
                        DriverController.modifyDriverHistory(driver);
                        System.out.println("Ride History emptied");
                    }
                } else if (driverInterfaceChoice == driverChoice.DETAILS.value) {

                if(driver.getCurrentRide()==null){
                    System.out.println("Select a ride to show the details ");
                }
                else {
                    final int STATUS = 1, DETAILS = 2, CANCEL = 3, BACK = 4;
                    int rideInterfaceChoice;
                    do {
                        System.out.println("1. Update Ride Status ");
                        System.out.println("2. Ride details ");
                        System.out.println("3. Cancel Ride ");
                        System.out.println("4. Back ");
                        rideInterfaceChoice = AppUtilities.getInteger();
                        switch (rideInterfaceChoice) {
                            case STATUS -> {
                                if(driver.getCurrentRide().getStatus()!= Ride.StatusValues.PICKED) {
                                    System.out.println("Picked the user ???");
                                    char status = AppUtilities.getCharacter();
                                    if(status == 'y')
                                        driver.getCurrentRide().setStatus(Ride.StatusValues.PICKED);
                                    driver.getCurrentRide().setStartTime(AppUtilities.getTime());
                                }
                                if((driver.getCurrentRide().getStatus()!= Ride.StatusValues.ARRIVED)&&(driver.getCurrentRide().getStatus()== Ride.StatusValues.PICKED)){
                                    System.out.println("Reached the destination ??");
                                    char status = AppUtilities.getCharacter();
                                    if(status == 'y'){
                                        driver.getCurrentRide().setStatus(Ride.StatusValues.ARRIVED);
                                        driver.getCurrentRide().setEndTime(AppUtilities.getTime());
                                        RideController.endRide(driver.getCurrentRide());
                                        rideInterfaceChoice = BACK;
                                    }
                                    else{
                                        System.out.println("Drop the passenger at his destination");
                                    }
                                }
                                else {
                                    System.out.println("Pickup the user !!!");
                                }
                            }
                            case DETAILS -> displayRideDetails(driver.getCurrentRide());

                            case CANCEL -> {
                                try {
                                    RideController.cancelRideByDriver(driver.getCurrentRide());
                                    System.out.println("Your ride has been cancelled");
                                    rideInterfaceChoice = BACK;
                                }
                                catch (InvalidCancellationException exception){
                                    System.out.println(exception);
                                }
                            }
                        }

                    } while (rideInterfaceChoice != BACK);
                }
            }
        } while (driverInterfaceChoice != driverChoice.EXIT.value);
        System.out.println(" Redirecting to the dashboard ");
    }
    public static void displayDriver(Driver driver){
        System.out.println("Name : " + driver.getDriverName() +
                " Contact No : "+ driver.getContactNo() + " Vehicle Type: " + driver.getVehicleType() + " Location : " + driver.getCity() );
    }
    public static void displayRidesToDriver(Driver driver,String city){
        LinkedList<Ride> ridesList = RideController.getRides(driver,city);
        for(Ride ride : ridesList){
            System.out.println(" UserId : " + ride.getUserId() + " Pickup: " + ride.getPickupLocation() + " Drop: " + ride.getDropLocation());
        }
    }
    public static void displayRideDetails(Ride ride){
        System.out.println("UserId : " + ride.getUserId() +
                " DriverId : " +ride.getDriverId() +
                " PickUp : " + ride.getPickupLocation() +
                " Drop : " + ride.getDropLocation() +
                " VehicleType : " + ride.getVehicleType() +
                " Cost : " + ride.getCost()
        );
    }
}
