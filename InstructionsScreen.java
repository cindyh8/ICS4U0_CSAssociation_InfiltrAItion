/**
 * Displays the game instructions to the player.
 * <p>
 * This screen shows the game instructions with an image background and provides
 * a button to return to the main menu. The window is fixed at 1000x700 pixels
 * and cannot be resized to maintain consistent presentation.
 *
 * <h2>Features:</h2>
 * <ul>
 *   <li>Full-screen instructions image</li>
 *   <li>Return to Menu button</li>
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

public class InstructionsScreen extends JFrame implements ActionListener {

/** The button to return to the main menu */
private JButton returnMenu;

/**
     * Constructs and displays the instructions screen.
     * <p>
     * Initializes the window with:
     * <ul>
     *   <li>Fixed size of 1000x700 pixels</li>
     *   <li>Non-resizable property</li>
     *   <li>Instructions image background</li>
     *   <li>Return button positioned at bottom center</li>
     * </ul>
     * The screen becomes visible immediately upon construction.
     */
    public InstructionsScreen() {
        setResizable(false);
        setSize(1000, 700);
        setVisible(true);

        ImageIcon loadingIcon = new ImageIcon(getClass().getResource("instructions.png"));
        Image scaledLoading = loadingIcon.getImage().getScaledInstance(1000, 700, Image.SCALE_SMOOTH);
        JLabel label = new JLabel(new ImageIcon(scaledLoading));
        label.setLayout(null);
        
        returnMenu = new JButton("Return to Menu");
        returnMenu.setBounds(330, 620, 300, 50);
        returnMenu.addActionListener(this);
        label.add(returnMenu);
        add(label);
        setVisible(true);
        
    }
    
    /**
     * Handles button click events.
     * <p>
     * When the return button is clicked, this method:
     * <ol>
     *   <li>Hides the current instructions screen</li>
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
