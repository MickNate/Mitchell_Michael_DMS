public class Main {
    /*
     * Main
     * Michael N. Mitchell, CEN-3024C-31950, July 10, 2024
     * The part of the class that contains rest of it. Creates a gui object, collection object, and controller
     * object. And passes the gui and collection into the controller. Then starts the controller class.
     */
    public static void main(String[] args) {
        Gui gui = new Gui();
        Collection collection = new Collection();
        Controller controller = new Controller(collection, gui);
        controller.inputSwitch(); //starts the program
    }
}
