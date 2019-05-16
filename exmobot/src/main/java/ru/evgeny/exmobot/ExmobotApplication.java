package ru.evgeny.exmobot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import ru.evgeny.exmobot.bots.SimpleBot;
import ru.evgeny.exmobot.configuration.Configuration;
import ru.evgeny.exmobot.configuration.LoadingSettingData;

@SpringBootApplication
public class ExmobotApplication {


    private static LoadingSettingData configuration;

    @Autowired
    public ExmobotApplication(LoadingSettingData conf) {
        configuration = conf;
    }

	public static void main(String[] args) {
		SpringApplication.run(ExmobotApplication.class, args);
        System.out.println(configuration.getPathToLogFile());

        SimpleBot bot = new SimpleBot();
        bot.setIncludeСommission(true);


	}





}
