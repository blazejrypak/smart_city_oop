package helpers;

public class IncorrectFilePathException extends Exception {
    public IncorrectFilePathException(String errorMessage, Throwable err){
        super(errorMessage, err);
    }
}