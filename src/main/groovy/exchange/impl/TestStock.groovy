package exchange.impl

import exchange.Stock
import response.Order

class TestStock extends Stock {


    Map<Long, Order> orders = new HashMap<>()
    static Long orderId = 0;



    TestStock(user) {
        super(user)
    }

    @Override
    int buy() {
        user.balance -= user.bet
        def price =user.exmo.getTicker(user.pair).lastTrade
        def quantity = user.exmo.getTicker(user.pair).lastTrade / 10d
        def amount = quantity * price
        def id = orderId++

        orders.put(id, new Order(id,
                new Date().time,
                "buy", user.pair,
                price,
                quantity,
                amount))
        return id
    }

    @Override
    int sell() {
        return 0
    }

    @Override
    def cancelOrder(Object orderId) {
        return null
    }
}
