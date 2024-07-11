import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller {
    /*
     * Controller
     * Michael N. Mitchell, CEN-3024C-31950, July 10, 2024
     * A class that is designed to pass the input of the gui into the logic (Controller class) and
     * then return the results of the logic to the gui's output display.
     */
    Collection collection;
    Gui gui;

    public Controller(Collection c, Gui g){
        /*
         * Controller Constructor
         * Assigns the gui and collection objects to the controller.
         * Arguments:
         *          Collection object
         *          Gui object
         * Returns:
         *          None
         */
        collection = c;
        gui = g;
    }

    void inputSwitch() {
        /*
         * Controller inputSwitch
         * Passes the input on the gui to the logic and then
         * passes the results of the logic to the output display of the gui.
         * Arguments:
         *          None
         * Returns:
         *          None
         */
        collection.introInstru();
        gui.setOutputField("<html>" + collection.codeOutput.replaceAll("\n", "<br/>") + "</html>");
        gui.button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
                    // when button is pressed, will trigger the passing of gui to logic and back to gui
            {
                collection.setUserInput(gui.getInputField());
                collection.choices();
                gui.setOutputField("<html>" + collection.codeOutput.replaceAll("\n", "<br/>") + "</html>");
            }
        });
        gui.exitButton.addActionListener(new ActionListener()
            // when button is pressed, will end the program
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

}
