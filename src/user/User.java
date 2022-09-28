package user;

import user.ride.Ride;
import java.io.Serial;
import java.io.Serializable;
import java.util.LinkedList;

public class User implements Serializable {
    @Serial
    private static final long serialVersionUID = 6744338499256500280L;
    private String userName;
    private String password;
    private String contactNo;
    private Ride currentRide;
    private LinkedList<Ride> ridesHistory;

    public User(String contactNo, String userName, String password){
        this.userName = userName;
        this.password = password;
        this.contactNo = contactNo;
        this.setCurrentRide(null);
    }
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }
    public Ride getCurrentRide() {
        return currentRide;
    }

    public void setCurrentRide(Ride ride) {
        currentRide = ride;
    }

    public LinkedList<Ride> getRidesHistory() {
        return ridesHistory;
    }

    public void setRidesHistory(LinkedList<Ride> ridesHistory) {
        this.ridesHistory = ridesHistory;
    }
}
