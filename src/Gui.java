import javax.swing.*;

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

    public Gui(){
        setContentPane(guiPanel);
        setTitle("Rise Against Catalog");
        setSize(700,600);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void setOutputField(String output) {
        this.outputField.setText("<html>" + output + "</html>");
        //progress = false;
    }

    public String getInputField(){
        return inputField.getText();
    }

    public static void main(String[] args) {
        Gui gui = new Gui();
    }
}
