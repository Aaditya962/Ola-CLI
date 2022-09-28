package app;

import admin.AdminController;
import admin.AdminView;
import custom.exceptions.EntryNotFoundException;
import custom.exceptions.InvalidCredentialsException;
import driver.Driver;
import driver.DriverController;
import driver.DriverView;
import user.User;
import user.UserController;
import user.UserView;
import java.util.InputMismatchException;

public class AppView {

    private enum dashBoardChoice{
        LOGIN(1),CREATE(2),EXIT(3);

        private final int value;

        dashBoardChoice(int value){
            this.value = value;
        }
    }
    public static void dashBoard() {
        int dashBoardChoice = Integer.MIN_VALUE;
            do {
                try {
                System.out.println("1.Login");
                System.out.println("2.Create account");
                System.out.println("3.Exit");
                System.out.println("Enter your choice");
                dashBoardChoice = AppUtilities.getInteger();

                if (dashBoardChoice == AppView.dashBoardChoice.LOGIN.value) {
                    int userTypeChoice;
                    final int USER = 1, DRIVER = 2, ADMIN = 3, BACK = 4;
                    do {
                        System.out.println(" Login Page ");
                        System.out.println("Are you a");
                        System.out.println("1.User");
                        System.out.println("2.Driver");
                        System.out.println("3.Admin");
                        System.out.println("4.Back");
                        System.out.println("Enter your choice ");
                        userTypeChoice = AppUtilities.getInteger();

                        switch (userTypeChoice) {

                            case USER -> {
                                System.out.println("UserId : ");
                                String userId = AppUtilities.getString();
                                System.out.println("Password : ");
                                String password = AppUtilities.getString();
                                try {
                                    User user = UserController.authenticateUser(userId, password);
                                    UserView.displayUserInterface(user);
                                } catch (InvalidCredentialsException | EntryNotFoundException exception) {
                                    System.out.println(exception);
                                }
                            }
                            case DRIVER -> {
                                System.out.println("UserId : ");
                                String userId = AppUtilities.getString();
                                System.out.println("Password : ");
                                String password = AppUtilities.getString();
                                try {
                                    Driver driver = DriverController.authenticateDriver(userId, password);
                                    DriverView.displayDriverInterface(driver);
                                } catch (InvalidCredentialsException | EntryNotFoundException exception) {
                                    System.out.println(exception);
                                }
                            }
                            case ADMIN -> {

                                System.out.println("Username : ");
                                String userName = AppUtilities.getString();
                                System.out.println("Password : ");
                                String password = AppUtilities.getString();
                                try {
                                        AdminController.authenticateAdmin(userName, password);
                                        AdminView.showAdminInterface();
                                } catch (InvalidCredentialsException exception) {
                                    System.out.println(exception);
                                }
                            }

                        }
                    } while (userTypeChoice != BACK);
                } else if (dashBoardChoice == AppView.dashBoardChoice.CREATE.value) {
                    int userTypeChoice;
                    final int USER = 1, DRIVER = 2;
                    System.out.println(" Account Creation Page ");
                    System.out.println("Are you a");
                    System.out.println("1.User");
                    System.out.println("2.Driver");
                    System.out.println("3.Back");
                    System.out.println("Enter your choice ");
                    userTypeChoice = AppUtilities.getInteger();

                    switch (userTypeChoice) {

                        case USER -> {

                            System.out.println("Enter the details");
                            System.out.println("Enter your name ");
                            String userName = AppUtilities.getString();
                            System.out.println("Enter your contact number ");
                            String contactNo = AppUtilities.getString();
                            System.out.println("Enter your password");
                            String password = AppUtilities.getString();
                            UserController.addUser(contactNo, userName, password);
                            System.out.println("Thanks for signing in our app");
                        }
                        case DRIVER -> {

                            System.out.println("Enter the details");
                            System.out.println("Enter your name ");
                            String userName = AppUtilities.getString();
                            System.out.println("Enter your contact number ");
                            String contactNo = AppUtilities.getString();
                            System.out.println("Enter your vehicleType (Auto/Bike/Mini/Prime)");
                            String vehicleType = AppUtilities.getString();
                            DriverController.signUpDriver(contactNo, userName, vehicleType);
                            System.out.println("Wait for the authorization from the admin side");
                        }
                    }
                }
            } catch (InputMismatchException exception){
                   System.out.println(exception);
                }
            }while (dashBoardChoice != AppView.dashBoardChoice.EXIT.value);
        System.out.println("Exiting the App");
    }
}
