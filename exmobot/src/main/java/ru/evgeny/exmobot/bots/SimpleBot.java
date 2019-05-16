package ru.evgeny.exmobot.bots;

import com.google.gson.Gson;
import exmo.Exmo;
import exmoException.AuthenticatedApiException;
import exmoException.ExmoException;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import response.Order;
import ru.evgeny.exmobot.configuration.LoadingSettingData;
import ru.evgeny.exmobot.exception.CurrencyException;
import ru.evgeny.exmobot.exception.LastOrderException;
import ru.evgeny.exmobot.message.Message;
import ru.evgeny.exmobot.message.MessageType;
import ru.evgeny.exmobot.news.reporter.email.EMailSender;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import static ru.evgeny.exmobot.constants.Сommission.TRANSACTION_FEE;

public class SimpleBot implements IBot {


    @Getter
    private double botBalance;
    @Getter
    private String pair;
    @Getter
    private double targetPrice;
    @Getter
    @Setter
    private double percent;
    @Getter
    @Setter
    private boolean isIncludeСommission;
    @Getter
    @Setter
    private String emailLogin;
    @Getter
    @Setter
    private String emailPassword;

    private Exmo exmo;
    @Getter
    private long lastOrder;
    @Getter
    private boolean isRun;
    @Getter
    private LoadingSettingData settingData;

    private EMailSender eMailSender;


    public SimpleBot setPrivateSetting(String key, String secret) {
        exmo.setKey(key);
        exmo.setSecret(secret);
        return this;
    }


    @Override
    public void sendMessageToUser(MessageType type, String... args) {
        eMailSender.setUserName(emailLogin)
                .setPassword(emailPassword)
                .sendMessage(type.name(), args[0]);
    }

    @Override
    public void setTargetPriceInProcent() throws ExmoException, ParseException {
        Order order = null;
        try {
            order = getLastOrder();
        }catch (LastOrderException e){
            stopWithError();
            return;
        }
        if(isIncludeСommission)
            targetPrice = addСommissionToTargetPrice(percentToTargetPrice(order.getPrice(), percent));
        else
            targetPrice = percentToTargetPrice(order.getPrice(), percent);
    }

    private double percentToTargetPrice(double price, double plusPercent) {
        return price + ((price / 100) * plusPercent);
    }

    private double addСommissionToTargetPrice(double now) {
        return now + TRANSACTION_FEE * 2;
    }

    private Order getLastOrder() throws ExmoException, ParseException, LastOrderException {
        Object[] toArray = exmo.userOpenOrders().stream().filter(a -> a.getOrderId() == lastOrder).toArray();
        if(toArray.length == 0)
            throw new LastOrderException(lastOrder);
        return (Order) toArray[0];
    }


    @Override
    public long buy() throws ExmoException, AuthenticatedApiException, ParseException {
        lastOrder = exmo.marketBuyTotal(pair, botBalance);
        try {
            Order order = getLastOrder();
            saveAction(order);
            sendMessageToUser(MessageType.BUY, formMessage(order, "bought"));
        } catch (LastOrderException e) {
            stopWithError();
        }
        return lastOrder;
    }

    @Override
    public long sell() throws ExmoException, ParseException, AuthenticatedApiException {
        setTargetPriceInProcent();
        try {
            lastOrder = exmo.sell(pair, getLastOrder().getQuantity(), targetPrice);
        } catch (LastOrderException e) {
            stopWithError();
        }
        return lastOrder;
    }

    @Override
    public void getStory() {

    }

    @Override
    public void saveAction(Object action) {
        if(action instanceof Order) {
            Order order = (Order) action;
            Gson gson = new Gson();
            try {
                Files.write(Paths.get(settingData.getPathToHistory()), gson.toJson(order).getBytes(), StandardOpenOption.APPEND);
            }catch (IOException e) {
                sendMessageToUser(MessageType.ERROR, "Can't save action");
            }
        }
    }



    @Override
    public void setBotBalance(double balance) {
        botBalance = balance;
    }

    @Override
    public void setPair(String pair) throws CurrencyException, ParseException {
        if(pair != null) return;

        if(exmo.getCurrency().contains(pair))
            this.pair = pair;
        else
            throw new CurrencyException(pair);

    }

    @Override
    public void run() {

    }

    private void stopWithError() {
        isRun = false;
        sendMessageToUser(MessageType.ERROR, "Can't found number of last order, bot will be stop");
    }

    private String formMessage(Order order, String action) {
        return String.format("The order with id %s has %s. \n" +
                "Pair: %s\n" +
                "Price: %s\n" +
                "Amount: %s", action, order.getOrderId(), order.getPair(), order.getPrice(), order.getAmount());
    }
}
