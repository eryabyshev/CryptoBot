package ru.evgeny.exmobot.exception;

public class LastOrderException extends Exception {
    public LastOrderException(long orderId) {
        super(orderId + "can not be founnd");
    }
}
