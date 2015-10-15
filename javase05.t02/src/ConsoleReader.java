import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.NotDirectoryException;
import java.util.MissingResourceException;
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
                    case "open": {
                        try {
                            open(command[1], command[2]);
                        }
                        catch (ArrayIndexOutOfBoundsException e) {
                        out.println("Не забудьте ключевое слово для поиска!");
                        }
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

    private void open(String fileName, String keyWord) {
        try {
            fileManager.openFile(fileName, keyWord);
        } catch (MissingResourceException mrExc) {
            out.println(mrExc.getMessage());
        } catch (IllegalArgumentException iaExc) {
            iaExc.printStackTrace();
        } catch (NullPointerException npExc) {
            out.println(npExc.getMessage());
            helpLs();
        }
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

    private void helpOpen() {
        out.println("Чтобы найти в propertoes файле значение введите");
        out.println("open ИмяФайла Язык Страна Ключ");
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
        helpUp();
        helpOpen();
        helpExit();
    }
}
