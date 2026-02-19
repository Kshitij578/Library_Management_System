package util;

import java.io.IOException;
import java.util.logging.*;

public class LoggerConfig {

    public static void configure() {

        Logger rootLogger = Logger.getLogger("");
        Handler[] handlers = rootLogger.getHandlers();

        for (Handler handler : handlers) {
            rootLogger.removeHandler(handler);
        }

        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setLevel(Level.ALL);

        consoleHandler.setFormatter(new SimpleFormatter());

        rootLogger.addHandler(consoleHandler);
        rootLogger.setLevel(Level.ALL);
    }
}
