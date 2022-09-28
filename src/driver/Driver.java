package driver;

import user.ride.Ride;

import java.io.Serial;
import java.io.Serializable;
import java.util.LinkedList;

public class Driver implements Serializable {
    @Serial
    private static final long serialVersionUID = -2916216703927593948L;
    private String driverName;
    private String password;
    private String contactNo;
    private String vehicleType;
    private String city;
    private Ride currentRide;
    private LinkedList<Ride> ridesHistory;

    public Driver(String driverName, String contactNo,String vehicleType){
        this.driverName = driverName;
        this.contactNo = contactNo;
        this.vehicleType = vehicleType;
    }
    public String getDriverName() {
        return driverName;
    }
    public void setDriverName(String driverName) {
        this.driverName = driverName;
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
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public Ride getCurrentRide() {
        return currentRide;
    }

    public void setCurrentRide(Ride ride) {
        this.currentRide = ride;
    }

    public LinkedList<Ride> getRidesHistory() {
        return ridesHistory;
    }

    public void setRidesHistory(LinkedList<Ride> ridesHistory) {
        this.ridesHistory = ridesHistory;
    }
}
