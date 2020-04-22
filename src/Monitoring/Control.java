package Monitoring;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * класс для работы с форматом CSV
 */
public class Control {

    /**
     * Формат ввода/вывода для поля creationDate класса Movie
     */
    private static DateTimeFormatter zonedDateTimeFormat = DateTimeFormatter.ofPattern("z dd/MM/yyyy HH:mm:ss");

    /**
     * Формат ввода/вывода для поля birthday класса Movie
     */
    private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    /**
     * зарезервированные символы формата CSV
     */
    private static String reservedChars = ",\n";

    public static DateTimeFormatter getZoneDateTimeFormat() {
        return zonedDateTimeFormat;
    }
    public static DateTimeFormatter getDateTimeFormatter() {
        return dateTimeFormatter;
    }

    /**
     * Функция проверяет что строка string содержит зарезервированные символы
     * @param string проверяемая строка
     * @return содержит ли строка зарезервированные символы
     */
    public static boolean isReserved(String string) {
        for (int i = 0; i < reservedChars.length(); ++i) {
            if (string.indexOf(reservedChars.charAt(i)) != -1) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param c проверяемый символ
     * @return является ли символ зарезервированным
     */
    public static boolean isReserved(char c) {
        return reservedChars.indexOf(c) != -1;
    }

    /**
     * Функция обрамляет строку string, если в ней содержаться зарезервированные символы
     * @param string обрабатываемая строка
     * @return обработанная строка
     */
    public static String processed(String string) {
        if (isReserved(string)) {
            return "\"" + string + "\"";
        } else {
            return string;
        }
    }

    /**
     * функция приводит объект к строке, если объект null то он приводится к строке "UNKNOW"
     * @param obj объект, приводимый к строке
     * @return соответствующая строка
     */
    public static String objToString(Object obj) {
        if (obj == null) {
            return "UNKNOW";
        } else {
            return obj.toString();
        }
    }

    /**
     * Объект приводиться к строке соответствующей формату CSV
     * @param obj объект, приводимый к формату CSV
     * @return сторку формата CSV
     */
    public static String objToCSV(Object obj) {

        return processed(objToString(obj)) + ',';
    }

    /**
     * Объект класса ZoneDateTime приводиться к строке соответствующей формату ввода/вывода CSV
     * @param date дата, приводимая к формату CSV
     * @return сторку формата CSV
     */
    public static String zoneDateTimeToCSV(ZonedDateTime date) {
        if (date == null) {
            return "UNKNOW,";
        } else {
            return  date.format(zonedDateTimeFormat)+',';
        }
    }

    /**
     * Объект класса localDateTime приводиться к строке соответствующей формату ввода/вывода CSV
     * @param date дата, приводимая к формату CSV
     * @return сторку формата CSV
     */
    public static String localDateTimeToCSV(LocalDateTime date) {
        if (date == null) {
            return "UNKNOW,";
        } else {
            return dateTimeFormatter.format(date)+',';
        }
    }

    /**
     * Функция обрабатывает строку в конец строки таблицы CSV формата
     * @param string строка приводимая к кону строки таблицы
     * @return строка соответствующая конце строки CSV таблицы
     */
    public static String makeCSVLine(String string) {
        String result = string.substring(0, string.length()-1);
        result += '\n';
        return result;
    }

    /**
     * Функция считывает строку из таблицы в CSV формате, и переводит в массив строк содержащий элементы строки
     * @param string начальная строка
     * @param scan сканнер для дополнительного считывания (на случай, если элемент таблицы содержит перевод строки)
     * @return массив строк, содержащий элементы строки таблицы в строков представлении
     */
    public static ArrayList<String> parseLineFromCSV(String string, Scanner scan) {
        ArrayList<String> result = new ArrayList<>();
        boolean priority = false;
        StringBuilder currentString = new StringBuilder();
        StringBuilder stringBuilder = new StringBuilder(string);
        for (int i = 0; i < stringBuilder.length(); ++i) {
            char currentChar = stringBuilder.charAt(i);
            if (currentChar == '\"') {
                priority = !priority;
            } else if (priority || !isReserved(currentChar)) {
                currentString.append(currentChar);
                if (priority && i + 1 == stringBuilder.length()) {
                    stringBuilder.append("\n").append(scan.nextLine());
                }
            } else {
                result.add(currentString.toString());
                currentString = new StringBuilder();
            }
        }
        if (!currentString.toString().equals("")) {
            result.add(currentString.toString());
        }
        return result;
    }
}
