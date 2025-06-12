/**
 * A screen displayed to congratulate the player on completing a level successfully.
 * <p>
 * This screen provides visual feedback for successful level completion and includes
 * a button to return to the main menu. It features:
 * <ul>
 *   <li>A congratulatory background image</li>
 *   <li>A return to menu button</li>
 *   <li>Simple navigation back to the main menu</li>
 * </ul>
 *
 * <h2>Course Info:</h2>
 * ICS4U0, Ms. Krasteva
 *
 * @version final
 * @author Cindy He, Sarah Chong
 */


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GoodWorkScreen extends JFrame implements ActionListener {

/** Button to return to the main menu. */
private JButton returnMenu;

   /**
     * Constructs the GoodWorkScreen with all UI components.
     * <p>
     * Initializes:
     * <ul>
     *   <li>Frame properties (size, resizability)</li>
     *   <li>Background image with congratulatory message</li>
     *   <li>Return to menu button with appropriate positioning</li>
     * </ul>
     */
    public GoodWorkScreen() {
        setResizable(false);
        setSize(1000, 700);
        setVisible(true);
        setLayout(null);

        ImageIcon loadingIcon = new ImageIcon(getClass().getResource("goodWork.png"));
        Image scaledLoading = loadingIcon.getImage().getScaledInstance(1000, 700, Image.SCALE_SMOOTH);
        JLabel label = new JLabel(new ImageIcon(scaledLoading));
        
        returnMenu = new JButton("Return to Menu");
        returnMenu.setBounds(300, 620, 300, 50); //fix position
        returnMenu.addActionListener(this);
        label.add(returnMenu);
        add(label);
        setVisible(true);
        
    }
    
    /**
     * Handles button click events.
     * <p>
     * Currently handles:
     * <ul>
     *   <li>Return to menu button - closes this screen and opens main menu</li>
     * </ul>
     *
     * @param e The ActionEvent containing event information
     */
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == returnMenu) {
            setVisible(false);
            new MainMenu().setVisible(true);
        }
    }
}
