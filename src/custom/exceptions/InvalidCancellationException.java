package custom.exceptions;

public class InvalidCancellationException extends Exception{
    public InvalidCancellationException(String message){
        super(message);
    }
}
