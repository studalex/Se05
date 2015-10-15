import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.NoSuchFileException;
import java.nio.file.NotDirectoryException;

import static java.lang.System.out;

public class FileManager {
    private File file;

    FileManager() {
        file = new File(System.getProperty("user.dir"));
    }

    public void upDirectory() throws Exception {
        try {
            file = new File(file.getParent());
        } catch (NullPointerException nullPointerException) {
            showCurrentDirectory();
            throw new NullPointerException("Подниматься некуда. Это корневая директория!");
        } catch (Exception exception) {
            throw new Exception(exception);
        }
    }

    public void downDirectory(String dir) throws Exception {
        try {
            file = new File(file.getPath() + "//" + dir);
            if (!file.isDirectory()) {
                file = new File(file.getParent());
                throw new NotDirectoryException(file.toString());
            } else {
                showCurrentDirectory();
            }
        } catch (NullPointerException nullPointerException) {
            throw new NullPointerException();
        } catch (Exception exception) {
            throw new Exception(exception);
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

    public void openFile(String fileName) throws Exception {
        try {
            TextEditor.showFile(new File(file + "//" + fileName));
        } catch (FileNotFoundException fileNotFoundException) {
            throw new FileNotFoundException();
        } catch (IOException ioException) {
            throw new IOException(ioException);
        } catch (Exception exception) {
            throw new Exception();
        }
    }

    public void createFile(String fileName, String text) throws Exception {
        File workFile = new File(file + "//" + fileName);
        if (workFile.isFile()) throw new FileAlreadyExistsException(workFile.toString());
        else {
            try {
                TextEditor.createFile(workFile, text);
            } catch (IOException ioException) {
                throw new IOException(ioException);
            } catch (Exception exception) {
                throw new Exception(exception);
            }
        }
    }

    public void writeInFile(String fileName, String text, boolean deleteOldText) throws Exception {
        File workFile = new File(file + "//" + fileName);
        if (!workFile.isFile()) throw new FileNotFoundException("Файл не найден!");
        try {
            TextEditor.writeInFile(workFile, text, deleteOldText);
        } catch (IOException ioException) {
            throw new IOException(ioException);
        } catch (Exception exception) {
            throw new Exception(exception);
        }
    }
}
