import javax.swing.*;

/**
 * GUI
 * Michael N. Mitchell, CEN-3024C-31950, July 10, 2024
 * Class that works as user's interface. It is where the user is able
 * to enter inputs for the logic and then be displayed the results.
 */
public class Gui extends JFrame{

    JButton button1;
    JButton exitButton;
    private JTextField inputField;
    private JLabel inputLabel;
    private JLabel outputLabel;
    private JLabel outputField;
    private JPanel guiPanel;
    Boolean progress = false;
    String output;

    /**
     * Gui Constructor
     * Creates the Gui window that the user will interact with.
     */
    public Gui()
    {
        setContentPane(guiPanel);
        setTitle("Rise Against Catalog");
        setSize(700,600);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

    /**
     * Gui setOutputField
     * Takes in the results from the logic and then displays this to the user.
     * The results will be wrapped in html tags so that they display on the jlabel properly.
     * @param output  takes in a String
     */
    public void setOutputField(String output)
    {
        this.outputField.setText("<html>" + output + "</html>");
    }

    /**
     * Gui getInputField
     * Takes the user's input and turns it into a string.
     * @return String returned from getInputField
     */
    public String getInputField()
    {
        return inputField.getText();
    }

    /**
     * main
     * @param args String passed into main
     */
    public static void main(String[] args) {
        Gui gui = new Gui();
    }
}
