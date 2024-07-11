import javax.swing.*;

public class Gui extends JFrame{
    /*
     * GUI
     * Michael N. Mitchell, CEN-3024C-31950, July 10, 2024
     * Class that works as user's interface. It is where the user is able
     * to enter inputs for the logic and then be displayed the results.
     */
    JButton button1;
    JButton exitButton;
    private JTextField inputField;
    private JLabel inputLabel;
    private JLabel outputLabel;
    private JLabel outputField;
    private JPanel guiPanel;
    Boolean progress = false;
    String output;

    public Gui()
        /*
         * Gui Constructor
         * Creates the Gui window that the user will interact with.
         * Arguments:
         *          None
         * Returns:
         *          None
         */
    {
        setContentPane(guiPanel);
        setTitle("Rise Against Catalog");
        setSize(700,600);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void setOutputField(String output)
        /*
         * Gui setOutputField
         * Takes in the results from the logic and then displays this to the user.
         * The results will be wrapped in html tags so that they display on the jlabel properly.
         * Arguments:
         *          String output
         * Returns:
         *          None
         */
    {
        this.outputField.setText("<html>" + output + "</html>");
    }

    public String getInputField()
        /*
         * Gui getInputField
         * Takes the user's input and turns it into a string.
         * Arguments:
         *          None
         * Returns:
         *          String
         */
    {
        return inputField.getText();
    }

    public static void main(String[] args) {
        Gui gui = new Gui();
    }
}
