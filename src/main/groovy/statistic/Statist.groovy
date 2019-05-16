package statistic

import exmo.Exmo


import java.text.SimpleDateFormat



static def getPrice(pair){
    def exmo = new Exmo()
    return exmo.getTicker(pair).lastTrade

}
static def writer(pair, path){
    def date = new Date()
    def formater = new SimpleDateFormat("dd_MM_yyyy")
    def file = new File("${path}${pair}_${formater.format(date)}.txt")

    if(file.exists())
        file.append(getPrice(pair) + "\n")
    else
        file.write(getPrice(pair) + "\n")
}




static def start(path){

    def pair = ["BTC_USD", "BTC_RUB", "ETH_USD", "ETH_RUB"]
    while(true){

        pair.each {
            writer(it, path)
            println "new data add in ${it}"

        }

        Thread.sleep(600000)
    }
}





