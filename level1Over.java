/**
 * The completion screen displayed after finishing Level 1.
 * <p>
 * This screen congratulates the player for completing the first level
 * and provides a button to return to the main menu. The window is
 * fixed at 1000x700 pixels and cannot be resized.
 *
 * <h2>Features:</h2>
 * <ul>
 *   <li>Completion message with background image</li>
 *   <li>"Return to Menu" button</li>
 *   <li>Automatic transition back to main menu</li>
 *   <li>Non-resizable window</li>
 * </ul>
 *
 * <h2>Course Info:</h2>
 * ICS4U0, Ms. Krasteva
 *
 * @version final
 * @author Your Name
 */
 
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class level1Over extends JFrame implements ActionListener {

/** Button to return to the main menu */
    private JButton returnMenu;

/**
     * Constructs the Level 1 completion screen.
     * <p>
     * Initializes the window with:
     * <ul>
     *   <li>Fixed size of 1000x700 pixels</li>
     *   <li>Exit-on-close behavior</li>
     *   <li>Non-resizable property</li>
     *   <li>Completion background image</li>
     *   <li>Centered return button</li>
     * </ul>
     */
    public level1Over() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setSize(1000, 700);

        ImageIcon loadingIcon = new ImageIcon(getClass().getResource("l1Completed.png"));
        Image scaledLoading = loadingIcon.getImage().getScaledInstance(1000, 700, Image.SCALE_SMOOTH);
        JLabel label = new JLabel(new ImageIcon(scaledLoading));
        label.setBounds(0, 0, 1000, 700);
        label.setLayout(null);

        returnMenu = new JButton("Return to Menu");
        returnMenu.setBounds(350, 550, 300, 50);
        returnMenu.addActionListener(this);
        label.add(returnMenu);
        add(label);
        setVisible(true);
    }
      
     /**
     * Handles button click events.
     * <p>
     * When the return button is clicked:
     * <ol>
     *   <li>Hides the current completion screen</li>
     *   <li>Creates and shows a new MainMenu instance</li>
     * </ol>
     *
     * @param e The ActionEvent containing event details
     */
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == returnMenu) {
            setVisible(false);
            new MainMenu().setVisible(true);
        }
    }
}
