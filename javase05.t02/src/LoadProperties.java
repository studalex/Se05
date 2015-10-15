import com.sun.org.apache.xml.internal.serializer.Encodings;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;

public class LoadProperties {
    static private Map<File, ResourceBundle> propertiesFile = new HashMap<>();

    public static String findKey(File path, String file, String keyWord) throws RuntimeException {
        ResourceBundle rb = null;
        File fileProperties = new File(path + "//" + file + ".properties");
        if (!fileProperties.isFile()) throw new NullPointerException("Нет файла!");

        if (!propertiesFile.containsKey(fileProperties)) {
            try {
                String[] langCountry = file.split("_");
                Locale current = new Locale(langCountry[1], langCountry[2]);
                URL[] url = {path.toURI().toURL()};
                ClassLoader loader = new URLClassLoader(url);
                rb = ResourceBundle.getBundle(file, current, loader);
                propertiesFile.put(fileProperties, rb);
            } catch (Exception e) {
            }
        }

        String answer = "";
        try {
            answer = new String(propertiesFile.get(fileProperties).getString(keyWord)); // .getBytes("ISO-8859-1")
        } catch (MissingResourceException mrExc) {
            throw new MissingResourceException("Не найдено ключевое слово", rb.getClass().toString(), "");
        } catch (IllegalArgumentException iaExc) {
            throw new IllegalArgumentException();
        } catch (NullPointerException npExc) {
            throw new NullPointerException();
        }
        try {
            return new String(answer.getBytes("ISO-8859-1"));
        } catch (UnsupportedEncodingException exc) {
            return answer;
        }
    }
}
