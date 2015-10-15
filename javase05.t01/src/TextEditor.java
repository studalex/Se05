import java.io.*;
import java.nio.file.FileAlreadyExistsException;

import static java.lang.System.*;

public class TextEditor {

    private static String LoadFile(File file) throws Exception {
        StringBuilder textInFile = new StringBuilder();
        try (FileReader fileReader = new FileReader(file)) {
            int content;
            while ((content = fileReader.read()) != -1) {
                textInFile.append((char) content);
            }
            return textInFile.toString();
        } catch (FileNotFoundException fileNotFound) {
            throw new FileNotFoundException();
        } catch (IOException ioException) {
            throw new IOException(ioException);
        } catch (Exception exception) {
            throw new Exception(exception);
        }
    }

    public static void showFile(File file) throws Exception {
        try {
            out.println(LoadFile(file));
        } catch (FileNotFoundException fileNotFoundException) {
            throw new FileNotFoundException();
        } catch (IOException ioException) {
            throw new IOException(ioException);
        } catch (Exception exception) {
            throw new Exception();
        }
    }

    public static void writeInFile(File file, String text, boolean delete) throws Exception {
        try (FileWriter fileWriter = new FileWriter(file, delete)) {
            fileWriter.write(text);
        } catch (FileNotFoundException fileNotFoundException) {
            throw new FileNotFoundException();
        } catch (IOException ioException) {
            throw new IOException(ioException);
        } catch (Exception exception) {
            throw new Exception(exception);
        }
    }

    public static void createFile(File file, String text) throws Exception {
        try (FileWriter fileWriter = new FileWriter(file, false)) {
            fileWriter.write(text);
        } catch (FileAlreadyExistsException fileAlreadyExistsException) {
            throw new FileAlreadyExistsException(file.toString());
        } catch (IOException ioException) {
            throw new IOException(ioException);
        } catch (Exception exception) {
            throw new Exception(exception);
        }
    }
}
