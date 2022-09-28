package user;

import app.AppUtilities;
import custom.exceptions.InvalidCancellationException;
import custom.exceptions.InvalidCredentialsException;
import user.ride.Ride;
import user.ride.RideController;
import java.util.LinkedList;

public class UserView {

    private enum userChoice
    {
        BOOK(1),ACCOUNT(2), RIDE(0),HISTORY(3),EXIT(4);
        private final int value;

        userChoice(int value){

            this.value = value;
        }
    }
    public static void displayUserInterface(User user) {
        System.out.println("Welcome " + user.getUserName());
        int userInterfaceChoice;
        do {

            System.out.println("\n1.Book a ride ");
            System.out.println("2.Account Management");
            System.out.println("3.Show rides History");
            System.out.println("4.Logout");
            if(user.getCurrentRide() != null){
                System.out.println("0.About Current Ride");
            }
            userInterfaceChoice = AppUtilities.getInteger();

            if (userInterfaceChoice == userChoice.BOOK.value) {

                if(user.getCurrentRide()!=null){

                    System.out.println("You cant book a ride,while your previous ride is active");
                }
                else {

                    final int AUTO = 1;
                    final int BIKE = 2;
                    final int MINI = 3;
                    final int PRIME = 4;
                    System.out.println("Enter pickup location");
                    String pickupPoint = AppUtilities.getString();
                    System.out.println("Enter drop location");
                    String dropPoint = AppUtilities.getString();
                    System.out.println("1.Auto    -    100");
                    System.out.println("2.Bike    -    70");
                    System.out.println("3.Mini    -    150");
                    System.out.println("4.Prime   -    250");
                    int vehicleChoice = AppUtilities.getInteger();
                    Ride ride;
                    ride = switch (vehicleChoice) {

                        case AUTO -> new Ride(user.getContactNo(), pickupPoint, dropPoint, "Auto");
                        case BIKE -> new Ride(user.getContactNo(), pickupPoint, dropPoint, "Bike");
                        case MINI -> new Ride(user.getContactNo(), pickupPoint, dropPoint,"Mini");
                        case PRIME -> new Ride(user.getContactNo(), pickupPoint, dropPoint,"Prime");
                        default -> throw new IllegalStateException("Unexpected value: " + vehicleChoice);
                    };
                    RideController.addRideToList(ride);
                    user.setCurrentRide(ride);
                    System.out.println("You ride has been booked !!!\n" +
                                       "Searching for drivers.....");
                    }
                } else if (userInterfaceChoice == userChoice.ACCOUNT.value) {

                final int MODIFY = 1;
                final int DISPLAY = 2;
                final int REMOVE = 3;
                final int BACK = 4;
                final int DELETE = Integer.MAX_VALUE;
                int userAccountChoice;
                do {
                    System.out.println("\n1.Modify Account details");
                    System.out.println("2.Display Account details");
                    System.out.println("3.Delete Account");
                    System.out.println("4.Go back");
                    userAccountChoice = AppUtilities.getInteger();
                    switch (userAccountChoice) {
                        case MODIFY -> {
                            try {
                                System.out.println("Enter the field to modify(name/number/password)");
                                String field = AppUtilities.getString().toUpperCase();
                                System.out.println("Enter the modified field");
                                String value = AppUtilities.getString();
                                switch (field) {
                                    case "NAME" -> UserController.modifyUserName(user, value);
                                    case "NUMBER" -> UserController.modifyUserNumber(user, value);
                                    case "PASSWORD" -> {
                                        System.out.println("Confirm password : ");
                                        String confirmedValue = AppUtilities.getString();
                                        UserController.modifyUserPassword(user, value, confirmedValue);
                                    }
                                }
                            } catch (InvalidCredentialsException exception) {
                                System.out.println(exception);
                            }
                            System.out.println("Account modified successfully");
                        }
                        case DISPLAY -> UserView.displayUser(user);
                        case REMOVE -> {
                            try {
                                System.out.println("Enter password");
                                String password = AppUtilities.getString();
                                UserController.removeUser(user, password);
                                System.out.println("Account deleted successfully");
                                userAccountChoice = DELETE;
                            } catch (InvalidCredentialsException exception) {
                                System.out.println(exception);
                            }
                        }
                    }
                } while ((userAccountChoice != BACK) && (userAccountChoice != DELETE) );
                if (userAccountChoice == DELETE){
                    userInterfaceChoice = userChoice.EXIT.value;
                }
            }
            else if (userInterfaceChoice == userChoice.HISTORY.value){
                System.out.println("1.Show history ");
                System.out.println("2.Clear history ");
                System.out.println("3.Go back ");
                int choice = AppUtilities.getInteger();
                if(choice == 1) {
                    try{
                    LinkedList<Ride> ridesList = user.getRidesHistory();
                    for (Ride ride : ridesList) {
                        displayRide(ride);
                    }
                    }catch (NullPointerException exception) {
                        System.out.println("No past rides Exists");
                    }
                }
                else if(choice == 2){
                    user.setRidesHistory(null);
                    UserController.updateUserInList(user);
                    System.out.println("Rides history emptied");
                }
                else{

                    System.out.println(" Redirecting back to the main menu ");
                }
            }
            else if (userInterfaceChoice == userChoice.RIDE.value) {

                if(user.getCurrentRide() == null){

                    System.out.println("Book a ride to show the details ");
                }
                else {

                    final int STATUS = 1, DETAILS = 2, CANCEL = 3, BACK = 4;
                    int rideInterfaceChoice;
                    do {

                        System.out.println("1. Ride Status ");
                        System.out.println("2. Ride details ");
                        System.out.println("3. Cancel Ride ");
                        System.out.println("4. Back ");
                        rideInterfaceChoice = AppUtilities.getInteger();
                        switch (rideInterfaceChoice) {

                            case STATUS -> {

                                if (user.getCurrentRide().getStatus() == Ride.StatusValues.ARRIVED){
                                    System.out.println("You have arrived at your destination");
                                } else if (user.getCurrentRide().getStatus() == Ride.StatusValues.PICKED) {
                                    System.out.println("Driver has picked you at your location");
                                } else if (user.getCurrentRide().getStatus() == Ride.StatusValues.ASSIGNED) {
                                    System.out.println("Driver is coming");
                                } else {
                                    System.out.println("Searching for drivers");
                                }
                            }
                            case DETAILS -> displayRideDetails(user.getCurrentRide());

                            case CANCEL -> {
                                try {

                                    RideController.cancelRideByUser(user.getCurrentRide());
                                    System.out.println("Your ride has been cancelled");
                                    rideInterfaceChoice = BACK;
                                } catch (InvalidCancellationException exception) {

                                    System.out.println(exception);
                                }
                            }
                        }

                    } while (rideInterfaceChoice != BACK);
                }
            }
        } while (userInterfaceChoice != userChoice.EXIT.value);
        System.out.println(" Redirecting to the dashboard ");
    }
    public static void displayUser(User user){

        System.out.println("Name : " + user.getUserName() + " Contact No : "+ user.getContactNo() );
    }
    public static void displayRideDetails(Ride ride){

        System.out.print("UserId : " + ride.getUserId());
        if(ride.getStatus() == Ride.StatusValues.ASSIGNED) {

            System.out.println(" Driver Id : " + ride.getDriverId());
        }
        System.out.println(" PickUp : " + ride.getPickupLocation() +
                " Drop : " + ride.getDropLocation() +
                " VehicleType : " + ride.getVehicleType() +
                " Cost : " + ride.getCost());
    }
    public static void displayRide(Ride ride){

        System.out.print("UserId : " + ride.getUserId());
        if(ride.getStatus() == Ride.StatusValues.ASSIGNED) {

            System.out.print(" DriverId : " + ride.getDriverId());
        }
        System.out.print(" PickUp : " + ride.getPickupLocation() +
                " Drop : " + ride.getDropLocation() +
                " VehicleType : " + ride.getVehicleType() +
                " Cost : " + ride.getCost());
        if(ride.getStatus() == Ride.StatusValues.ARRIVED){

            System.out.println( " Start time : " + ride.getStartTime() + " End Time : " + ride.getEndTime() + " Status : Success");
        }
        else if(ride.getStatus() == Ride.StatusValues.CANCELLED){

            System.out.println(" Status : Cancelled");
        }
        else{
            System.out.println(" Status : Active ");
        }
    }
}
