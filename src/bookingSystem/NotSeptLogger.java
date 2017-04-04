package bookingSystem;

import java.io.IOException;
import java.util.logging.*;
import java.util.Properties;
import java.io.FileInputStream;


public class NotSeptLogger
{
    static private FileHandler logToFile;
    static private SimpleFormatter formatTextHumanReadable;
    
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
