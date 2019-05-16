package ru.evgeny.exmobot.exception;

public class CurrencyException extends Exception {

    public CurrencyException(String pair) {
        super(pair + "isn\'t supported");
    }

}
