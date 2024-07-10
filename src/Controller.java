import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller {

    Collection collection;
    Gui gui;
    //int next;

    public Controller(Collection c, Gui g){
        collection = c;
        gui = g;
    }

    void inputSwitch() {
        collection.introInstru();
        gui.setOutputField("<html>" + collection.codeOutput.replaceAll("\n", "<br/>") + "</html>");
        gui.button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                collection.setUserInput(gui.getInputField());
                collection.choices();
                gui.setOutputField("<html>" + collection.codeOutput.replaceAll("\n", "<br/>") + "</html>");
            }
        });
        gui.exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

}
