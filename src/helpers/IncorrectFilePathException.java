package helpers;

/**
 * This Exception return error for incorrect file path
 */
class IncorrectFilePathException extends Exception {
    IncorrectFilePathException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
}