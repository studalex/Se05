import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.NotDirectoryException;
import java.util.Scanner;

import static java.lang.System.*;

public class ConsoleReader {
    private FileManager fileManager;

    ConsoleReader() {
        fileManager = new FileManager();
    }

    public void Console() {
        String[] command;
        String enterText = "";
        helper();
        try (Scanner scanner = new Scanner(in)) {
            do {
                try {
                    enterText = scanner.nextLine();
                } catch (Exception e) {
                    out.println("Произошла ошибка!");
                    out.println(e.getMessage());
                }
                command = enterText.split(" ");
                switch (command[0]) {
                    case "ls": {
                        fileManager.showFilesInThisDirectory();
                        break;
                    }
                    case "up": {
                        up();
                        break;
                    }
                    case "cd": {
                        cd(command[1]);
                        break;
                    }
                    case "openr": {
                        openr(command[1]);
                        break;
                    }
                    case "openw": {
                        openw(command);
                        break;
                    }
                    case "openrw": {
                        openrw(command);
                        break;
                    }
                    case "create": {
                        create(command);
                        break;
                    }
                    case "help": {
                        helper();
                        break;
                    }
                    case "exit": {
                        break;
                    }
                    default:
                        out.println("Команда не найдена!");
                }
            } while (!enterText.equals("exit"));
        }
    }

    private void up() {
        try {
            fileManager.upDirectory();
            fileManager.showCurrentDirectory();
        } catch (NullPointerException nullPointerException) {
            out.println(nullPointerException.getMessage());
            helpLs();
            helpCd();
            helpHelp();
        } catch (Exception e) {
            out.println(e.getMessage());
            helper();
        }
    }

    private void cd(String s) {
        try {
            if (s == null) {
                helper();
            } else {
                fileManager.downDirectory(s);
            }
        } catch (NotDirectoryException notDirException) {
            out.println("Такой директории не существует!");
            helpLs();
            helpCd();
            helpHelp();
        } catch (NullPointerException nullPException) {
            out.println(nullPException.getMessage());
            helper();
        } catch (Exception e) {
            out.println(e.getMessage());
            helper();
        }
    }

    private void openr(String fileName) {
        try {
            fileManager.openFile(fileName);
        } catch (FileNotFoundException fNotFound) {
            out.println("Такого файла не существует!");
            helpLs();
            helpCreate();
            helpOpenW();
            helpOpenR();
            helpOpenRewrite();
        } catch (IOException ioException) {
            out.println("Ошибка чтения!");
            helper();
        } catch (Exception e) {
            out.println(e.getMessage());
            helper();
        }
    }

    private void openw(String[] command) {
        try {
            fileManager.writeInFile(command[1], TextForWrite(command), true);
            out.println("Запись добавлена");
        } catch (FileNotFoundException fNotFound) {
            out.println(fNotFound.getMessage());
            helpLs();
            helpCreate();
        } catch (IOException ioException) {
            out.println("Ошибка чтения!");
            helper();
        } catch (Exception e) {
            out.println(e.getMessage());
            helper();
        }
    }

    private void openrw(String[] command) {
        try {
            fileManager.writeInFile(command[1], TextForWrite(command), false);
            out.println("Запись пересоздана");
        } catch (FileNotFoundException fNotFound) {
            out.println(fNotFound.getMessage());
            helpLs();
            helpCreate();
        } catch (IOException ioException) {
            out.println("Ошибка записи!");
            helper();
        } catch (Exception exception) {
            out.println(exception.getMessage());
            helper();
        }
    }

    private void create(String[] command) {
        try {
            fileManager.createFile(command[1], TextForWrite(command));
            out.println("Файл с записью создана");
        } catch (FileAlreadyExistsException fAlreadyExist) {
            out.println("Файл " + fAlreadyExist.getMessage() + " уже существует!");
            helpLs();
            helpOpenR();
            helpOpenRewrite();
            helpOpenW();
        } catch (IOException ioException) {
            out.println("Ошибка чтения!");
            helper();
        } catch (Exception e) {
            out.println(e.getMessage());
            helper();
        }
    }

    private String TextForWrite(String... command) {
        StringBuilder textForWrite = new StringBuilder();
        for (int i = 2; i < command.length; i++) {
            textForWrite.append(command[i] + " ");
        }
        return textForWrite.toString();
    }

    private void helpLs() {
        out.println("Для просмотра файлов в текущей директории введите ls");
    }

    private void helpUp() {
        out.println("Чтобы подняться вверх по директории введите up");
    }

    private void helpCd() {
        out.println("Чтобы зайти в папку введите cd ИмяПапки");
    }

    private void helpOpenR() {
        out.println("Чтобы открыть файл для чтения введите openr ИмяФайла.расширение");
    }

    private void helpOpenW() {
        out.println("Чтобы открыть файл для дозаписи введите openw ИмяФайла.расширение текст для дозаписи");
    }

    private void helpOpenRewrite() {
        out.println("Чтобы открыть файл для перезаписи введите openrw ИмяФайла.расширение текст для перезаписи");
    }

    private void helpCreate() {
        out.println("Чтобы создать файл введите create ИмяФайла.расширение или create ИмяПапки");
    }

    private void helpExit() {
        out.println("Чтобы выйти из программы введите exit");
    }

    private void helpHelp() {
        out.println("Чтобы увидеть подсказку введите help");
    }

    private void helper() {
        helpLs();
        helpCd();
        helpCreate();
        helpUp();
        helpOpenR();
        helpOpenRewrite();
        helpOpenW();
        helpExit();
    }
}
