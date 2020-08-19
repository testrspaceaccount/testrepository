package com.axiope.ecat5.utils;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * handling properties class
 */
public class WebTestProperties {

    //log
    private static final Logger logger = Logger.getLogger(WebTestProperties.class);

    //properties
    private static Properties props;

    //load properties
    static {
        String userPropertyFile = System.getProperty("user.name") + ".properties";
        props = new Properties();
        InputStream is = WebTestProperties.class.getClassLoader().getResourceAsStream(userPropertyFile);

        if (is == null) {
            logger.error("Couldn't find user.properties file: " + userPropertyFile);
        } else {
            try {
                props.load(is);
                is.close();
            } catch (IOException e) {
                logger.error("Couldn't load user.properties file: " + userPropertyFile);
                e.printStackTrace();
            }
        }
    }

    /**
     * Get property by name
     * @param property property name
     * @return
     */
    public static String getProperty(String property) {
        return props.getProperty(property);
    }

}
