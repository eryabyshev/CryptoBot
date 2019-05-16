package statistic

import exmo.Exmo


import java.text.SimpleDateFormat



def getPrice(pair){
    def exmo = new Exmo()
    return exmo.getTicker(pair).lastTrade

}


def writer(pair){
    def date = new Date()
    def formater = new SimpleDateFormat("dd_MM_yyyy")
    def file = new File("result/${pair}_${formater.format(date)}.txt")

    if(file.exists())
        file.append(getPrice(pair) + "\n")
    else
        file.write(getPrice(pair) + "\n")
}



def pair = ["BTC_USD", "BTC_RUB", "ETH_USD", "ETH_RUB"]


while(true){

    pair.each {
        writer(it)
        println "new data add in ${it}"

    }

    Thread.sleep(600000)

}





