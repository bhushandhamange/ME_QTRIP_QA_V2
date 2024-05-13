package qtriptest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Log4jConfig {

    public static final Logger logger = LogManager.getLogger(Log4jConfig.class);

    static {
        try {
            // Load log4j properties from the file
            System.setProperty("log4j.configurationFile", "src/test/resources/log4j2.properties");
            logger.info("Log4j initialized successfully.");
        } catch (Exception e) {
            System.err.println("Error initializing Log4j: " + e.getMessage());
        }
    }
}

