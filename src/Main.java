public class Main {
    public static void main(String[] args) {
        Gui gui = new Gui();
        Collection collection = new Collection();
        Controller controller = new Controller(collection, gui);
        controller.inputSwitch();
    }
}
