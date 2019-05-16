package ru.evgeny.exmobot.bots;

import exmoException.AuthenticatedApiException;
import exmoException.ExmoException;
import org.json.simple.parser.ParseException;
import ru.evgeny.exmobot.exception.CurrencyException;
import ru.evgeny.exmobot.message.MessageType;

public interface IBot<T> extends Runnable {

    void sendMessageToUser(MessageType type, String...args);

    void setTargetPriceInProcent() throws ExmoException, ParseException;

    long buy() throws ExmoException, AuthenticatedApiException, ParseException;

    long sell() throws ExmoException, ParseException, AuthenticatedApiException;

    void getStory();

    void saveAction(T action);

    void setBotBalance(double balance);

    void setPair(String pair) throws CurrencyException, ParseException;

}
