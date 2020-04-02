package Commands;

public class Exit extends AbstractCommand {

    public Exit() {
        super("exit");
    }
    @Override
    public void commit(String[] args) {
        System.out.println("Выход из программы...");
        System.exit(0);
    }

    public String help() {
        return super.help(" : вывести справку по доступным командам");
    }
}
