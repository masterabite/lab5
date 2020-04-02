package Commands;

public class Info extends AbstractCommand {

    public Info() {
        super("info");
    }

    @Override
    public void commit(String[] args) {
    }

    public String help() {
        return super.help(" : вывести в стандартный поток вывода " +
                "информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)");
    }
}
