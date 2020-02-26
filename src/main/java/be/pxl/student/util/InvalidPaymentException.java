package be.pxl.student.util;

public class InvalidPaymentException extends Exception{ // extends exception betekent dat het een checked exception is dus die moet uitgevoerd worden
    public InvalidPaymentException(String message){
        super(message);
    }
}
