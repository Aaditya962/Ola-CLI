package user.ride;

import java.io.Serial;
import java.io.Serializable;

public class Ride implements Serializable {


    @Serial
    private static final long serialVersionUID = 7583439273440139775L;
    private final String userId;
    private String driverId;
    public enum StatusValues{
        BOOKED,ASSIGNED,PICKED,ARRIVED,CANCELLED
    }
    private StatusValues status;
    private final String vehicleType;
    private final String pickupLocation;
    private final String dropLocation;
    private int cost;
    private String startTime;
    private String endTime;

    public Ride(String userId,String pickupLocation,String dropLocation, String vehicleType){
        this.userId = userId;
        this.pickupLocation = pickupLocation;
        this.dropLocation = dropLocation;
        this.vehicleType = vehicleType;
        this.status = StatusValues.BOOKED;
    }
    public Ride(Ride ride) {
        this.userId = ride.getUserId();
        this.driverId = ride.getDriverId();
        this.cost = ride.cost;
        this.pickupLocation = ride.getPickupLocation();
        this.dropLocation = ride.getDropLocation();
        this.vehicleType = ride.getVehicleType();
        this.status = ride.getStatus();
    }

    public String getUserId() {
        return userId;
    }
    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverID) {
        this.driverId = driverID;
    }

    public void setStatus(StatusValues status){
        this.status = status;
    }
    public StatusValues getStatus(){
        return status;
    }
    public String getVehicleType() {
        return vehicleType;
    }

    public String getPickupLocation() {
        return pickupLocation;
    }
    public String getDropLocation() {
        return dropLocation;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
