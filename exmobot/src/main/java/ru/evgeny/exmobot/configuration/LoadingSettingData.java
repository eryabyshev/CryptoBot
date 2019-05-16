package ru.evgeny.exmobot.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Properties;

@Service
public class LoadingSettingData {
    private Configuration configuration;

    @Autowired
    LoadingSettingData(Configuration configuration) {
        this.configuration = configuration;
    }

    public String getPathToLogFile() {
        return configuration.getPathToLogFile();
    }

    public String getPathToHistory() {
        return configuration.getPathToHistory();
    }

    public Properties getGmailSettings() {
        return configuration.getSettingOfGmail();
    }
}
