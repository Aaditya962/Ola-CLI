import app.AppView;
import data.Data;

public class index {
    public static void main(String[] args) {
        Data data = Data.getInstance();
        AppView.dashBoard();
        data.setList("requestList", data.getRequestList());
        data.setList("User",data.getUsersList());
        data.setList("Driver",data.getDriversList());
    }
}
