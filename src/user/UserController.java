package user;

import app.AppUtilities;
import custom.exceptions.EntryNotFoundException;
import custom.exceptions.InvalidCredentialsException;
import data.Data;
import user.ride.Ride;
import java.util.HashMap;
import java.util.LinkedList;

public class UserController {

    static Data data = Data.getInstance();
    public static void addUser(String contactNo, String userName, String password) {
        User user = new User(contactNo,userName, AppUtilities.encrypt(password));
        updateUserInList(user);
    }
    public static User authenticateUser(String contactNo, String password) throws InvalidCredentialsException, EntryNotFoundException {
        HashMap<String, User> usersList = data.getUsersList();

        if(usersList.containsKey(contactNo)){
            if(usersList.get(contactNo).getPassword().equals(AppUtilities.encrypt(password))){
                return usersList.get(contactNo);
            }
            else {
                throw new InvalidCredentialsException("Please enter correct username/password");
            }
        }
        else{
            throw new EntryNotFoundException("No entry found");
        }
    }


    public static void modifyUserName(User user, String value) {
        user.setUserName(value);
        updateUserInList(user);
    }
    public static void modifyUserHistory(User user) {
        LinkedList<Ride> userRideHistory = (user.getRidesHistory()==null) ? new LinkedList<>() : user.getRidesHistory();
        userRideHistory.add(user.getCurrentRide());
        user.setRidesHistory(userRideHistory);
        updateUserInList(user);
    }

    public static void modifyUserNumber(User user, String value) {
        HashMap<String,User> usersList = data.getUsersList();
        usersList.remove(user.getContactNo());
        user.setContactNo(value);
        usersList.put(user.getContactNo(),user);
        data.setUsersList(usersList);
    }
    public static void modifyUserPassword(User user, String password, String confirmedPassword) throws InvalidCredentialsException {
        if(password.equals(confirmedPassword)){
            user.setPassword(AppUtilities.encrypt(password));
            updateUserInList(user);
        }
        else{
            throw new InvalidCredentialsException("Please enter your password correctly");
        }
    }

    public static void removeUser(User user, String password )throws InvalidCredentialsException {
        if(user.getPassword().equals(AppUtilities.encrypt(password))){
            HashMap<String,User> usersList = data.getUsersList();
            usersList.remove(user.getContactNo());
            data.setUsersList(usersList);
        }
        else {
            throw new InvalidCredentialsException("Please enter correct password to delete your account");
        }
    }
    public static void updateUserInList(User user){
        HashMap<String, User> usersList = data.getUsersList();
        usersList.put(user.getContactNo(),user);
        data.setUsersList(usersList);
    }
}
