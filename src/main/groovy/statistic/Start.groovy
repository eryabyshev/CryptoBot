package statistic

class Start {

    static String dirName
    static final def DIR = 'botstatistic'


    static void main(String[] args) {
        if (args.length == 1) {
            dirName = args[0]
            if(!pathChek(dirName)) {
                print dirName
                if(new File(dirName).mkdir())
                    print "dir has been created\n"
                else {
                    print "dir hasn't been created"
                    return
                }

            }
        } else {
            dirCreator()
        }

        Statist.start(dirName + "/")


    }


    static def itWindows() {
        if (System.getProperty('os.name').contains("win"))
            true
        false
    }

    static def pathChek(path) {
        def dir = new File(path)
        dir.exists()
    }

    static def dirCreator() {
        if (itWindows())
            dirName = 'C://botstatistic'
        else{
            def user = System.getProperty("user.name")
            dirName = '/Users/' + user + '/Documents/' + DIR
            new File(dirName).mkdir()
        }

    }


}
