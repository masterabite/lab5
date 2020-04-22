package Commands;

import Monitoring.Assistant;

/**
 * базовый класс для команд
 */
public class Command implements CommandInterface {
    private String name;
    private String help;

    Command(String name, String help) {
        this.name = name;
        this.help = help;
    }

    public String getName() {
        return name;
    }



    public String getHelp() {
        return this.name + ": " + this.help;
    }

    @Override
    public void commit(Assistant assistant, boolean visible) {
        System.out.println("commit...");
    }
}
