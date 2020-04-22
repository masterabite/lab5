package Commands;

import Monitoring.Assistant;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Выполняет команды из скрипта
 */
public class ExecuteScriptCommand extends Command {

    /**
     * Хранит стек всех используемых сканнеров для вложенных скриптов
     */
    private ArrayList<Scanner> scanners = new ArrayList<>();

    /**
     * Хранит стек всех используемых имен скриптов
     */
    private ArrayList<String> filesName = new ArrayList<>();

    public ExecuteScriptCommand() {
        super("execute_script", ": считать и исполнить скрипт из указанного файла. В скрипте содержатся " +
                "команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.");
    }

    /**
     * функция проверяет находится ли данный файл в стеке (для предотвращения циклов)
     * @param name имя файла
     * @return возвращает true если такой файл используется
     */
    public boolean findFileName(String name) {
        for (String curStr: this.filesName) {
            if (curStr.equals(name)) {
                return true;
            }
        }
        return false;
    }

    /**
     * процедура которая вызывается при прочитывании скрипта, удаляя из стека сканер и имя скрипта
     * @param assistant наш ассистент
     */
    public void scriptRead(Assistant assistant) {
        System.out.println("Скрипт " + filesName.get(filesName.size() - 1) + " считан!");
        filesName.remove(filesName.size() - 1);
        scanners.remove(scanners.size() - 1);
        if (scanners.size() != 0) {
            assistant.getReader().setConsoleScan(scanners.get(scanners.size() - 1));
        } else {
            assistant.getReader().setConsoleScan(new Scanner(System.in));
        }
    }

    @Override
    public void commit(Assistant assistant, boolean visible) {
        String fileName = assistant.getReader().readLine("Введите имя файла: ", this.scanners.size() > 0);
        try {
            if (!findFileName(fileName)) {
                File file = new File(fileName);
                filesName.add(fileName);
                scanners.add(new Scanner(file));
                System.out.println("Начинаю считывать скрипт " + fileName);
                assistant.getReader().setConsoleScan(scanners.get(scanners.size() - 1));
                while (assistant.getReader().getConsoleScan().hasNext()) {
                    String stringCommand = assistant.getReader().readLine("Пытаюсь считать команду: ", true);
                    assistant.commitCommand(stringCommand, true);
                }
                scriptRead(assistant);
            } else {
                System.out.println("этот скрипт уже считывается!");
            }
        } catch (NoSuchElementException e) {
            System.out.println("Скрипт " + fileName + " закончился!");
        } catch (FileNotFoundException e) {
            System.out.println("Скрипт " + fileName + " не найден!");
        }
    }
}

