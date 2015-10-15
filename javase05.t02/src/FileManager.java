import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.NotDirectoryException;
import java.util.MissingResourceException;

import static java.lang.System.out;

public class FileManager {
    private File file;

    FileManager() {
        file = new File(System.getProperty("user.dir"));
    }

    public void upDirectory() throws Exception {
        file = new File(file.getParent());
    }

    public void downDirectory(String dir) throws Exception {

        file = new File(file.getPath() + "//" + dir);
        if (!file.isDirectory()) {
            file = new File(file.getParent());
            throw new NotDirectoryException(file.toString());
        } else {
            showCurrentDirectory();
        }
    }


    public void showCurrentDirectory() {
        out.println("Текущая директория: " + file.toString());
    }

    public void showFilesInThisDirectory() {
        out.println("Список файлов в директории: ");
        for (File file : this.file.listFiles()) {
            out.println(file.getName());
        }
    }

    public void openFile(String fileName, String keyWord) throws RuntimeException {
        try {
            String answer = LoadProperties.findKey(file, fileName, keyWord);
            out.println(answer);
        } catch (MissingResourceException mrExc) {
            throw mrExc;
        } catch (NullPointerException npExc) {
            throw npExc;
        } catch (IllegalArgumentException iaExc) {
            throw iaExc;
        }
    }
}
