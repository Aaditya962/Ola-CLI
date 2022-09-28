import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class test {
    public static void main(String[] args) {

        LocalTime time = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");
        String currentTime = time.format(formatter);
    }
}
