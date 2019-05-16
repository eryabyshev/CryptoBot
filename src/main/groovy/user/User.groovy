package user

import exmo.Exmo



class User {

    def key
    def secret
    String pair
    def balance
    def profit
    def bet

    Exmo exmo;


    User(key, secret, balance, profit){
        this.key = key
        this.secret = secret
        this.pair = pair
        exmo = new Exmo(key, secret)

        if(balanceWrong)
            throw new Exception("Excellent means")

        this.balance = balance
        this.profit = profit
        bet = balance / 10d





    }


    def balanceWrong = { balance ->
        if(exmo.getUserInfo().balances.get(pair.split("_")[1]) >= balance)
            true
        false
    }







}
