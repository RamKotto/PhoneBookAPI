package utils;

import constants.PropertiesEnum;
import lombok.extern.slf4j.Slf4j;
import java.io.FileInputStream;
import java.util.Properties;
import static constants.PropertiesEnum.CONFIG;

@Slf4j
public class PropertyManager {

    public static String propHandler(PropertiesEnum file, String key) {
        FileInputStream props = null;
        Properties property = new Properties();
        try {
            if (file.equals(CONFIG)) {
                props = new FileInputStream("src\\main\\resources\\config.properties");
            }
            property.load(props);
        } catch (Exception e) {
            log.info("Properties were not found...");
            e.printStackTrace();
        }
        return property.getProperty(key);
    }
}
