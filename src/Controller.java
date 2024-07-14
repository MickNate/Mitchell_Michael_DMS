import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Controller
 * Michael N. Mitchell, CEN-3024C-31950, July 10, 2024
 * A class that is designed to pass the input of the gui into the logic (Controller class) and
 * then return the results of the logic to the gui's output display.
 */
public class Controller {
    Collection collection; //the collection(logic) to be used
    Gui gui; //the user interface to be used

    /**
     * Controller Constructor
     * Assigns the gui and collection objects to the controller.
     * @param c collection object passed into constructor
     * @param g gui object passed into constructor
     */
    public Controller(Collection c, Gui g){
        collection = c;
        gui = g;
    }

    /**
     * Controller inputSwitch
     * Passes the input on the gui to the logic and then
     * passes the results of the logic to the output display of the gui.
     */
    void inputSwitch() {
        collection.introInstru();
        gui.setOutputField("<html>" + collection.codeOutput.replaceAll("\n", "<br/>") + "</html>");
        gui.button1.addActionListener(new ActionListener() {
            /**
             * GUI 1 Button ActionPerformed
             * When button is pressed, will send the input field from gui to logic
             * then will send the results of the logic to the output field of the gui.
             * @param e the event to be processed
             */
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
            /**
             * GUI Exit Button ActionPerformed
             * Will end the program if the button is pressed
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

}
