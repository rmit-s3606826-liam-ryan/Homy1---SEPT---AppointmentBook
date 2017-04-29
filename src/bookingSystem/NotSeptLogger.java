package bookingSystem;


import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class NotSeptLogger
{
    private static FileHandler logToFile;
    private static SimpleFormatter formatTextHumanReadable;
    
    static public void setup() throws IOException
    {
        Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
        logger.setLevel(Level.INFO);
        logToFile = new FileHandler("src/bookingSystem/Logging.txt");

        formatTextHumanReadable = new SimpleFormatter();
        logToFile.setFormatter(formatTextHumanReadable);
        logger.addHandler(logToFile);

       
    }
}
