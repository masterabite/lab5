package Commands;

import Monitoring.Assistant;

/**
 * Интерфейс команд
 */
public interface CommandInterface {

    /**
     * выполнение команды
     * @param assistant наш ассистент
     * @param visible производится ли считывание из скрипта
     */
    void commit(Assistant assistant, boolean visible);
}
