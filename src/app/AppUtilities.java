package app;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class AppUtilities {
    public static String encrypt(String password) {
        StringBuilder encryptedPassword = new StringBuilder();
        StringBuilder builder = new StringBuilder(password);
        builder.reverse();
        String string = builder.toString();
        for (char ch : string.toCharArray()) {
            if (ch == 'z') ch = 'a';
            else if (ch == 'Z') ch = 'A';
            else if (ch == '9') ch = '0';
            else ch += 1;
            encryptedPassword.append(ch);
        }
        return encryptedPassword.toString();
    }
    public static String generatePassword(String parameter1,String parameter2) {
        String password = parameter1 + parameter2;
        return AppUtilities.encrypt(password);
    }
    public static String getString(){
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }
    public static Integer getInteger(){
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }
    public static Character getCharacter(){
        Scanner scanner = new Scanner(System.in);
        return scanner.next().charAt(0);
    }
    public static String getTime(){
        LocalTime time = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");
        return time.format(formatter);
    }
}
