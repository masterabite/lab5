package Commands;

public abstract class AbstractCommand implements Command{
    String name;

    public AbstractCommand(String name) {
        this.name = name;
    }

    public String help(String arg) {
        return this.name + arg;
    }
}
