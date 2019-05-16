package exchange

import exmo.Exmo
import user.User

abstract class Stock {

    protected User user

    Stock(user){
        this.user = user
    }



    def getLastPrice(pairName){
        user.getExmo().getTicker(pairName).lastTrade
    }

    abstract int buy()
    abstract int sell()

    abstract def cancelOrder(orderId)



}
