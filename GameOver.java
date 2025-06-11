/**
 * The GameOver screen displayed when the player loses or completes the game.
 * <p>
 * This screen shows a game over image and provides a button to return to the main menu.
 * The screen is not resizable and has fixed dimensions of 1000x700 pixels.
 * 
 * <h2>Features:</h2>
 * <ul>
 *   <li>Displays a full-screen game over image</li>
 *   <li>Provides a "Return to Menu" button</li>
 *   <li>Automatically handles transition back to main menu</li>
 * </ul>
 *
 * @version final
 * @author Your Name
 */
 
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameOver extends JFrame implements ActionListener {

    /** The button to return to the main menu. */
    private JButton returnMenu;

    /**
     * Constructs the GameOver screen.
     * <p>
     * Initializes the window with the game over image and a return button.
     * The window is set to be non-resizable with dimensions 1000x700 pixels.
     */
     
    public GameOver() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setSize(1000, 700);

        ImageIcon loadingIcon = new ImageIcon(getClass().getResource("gameover.png"));
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
     * When the return button is clicked, this method hides the GameOver screen
     * and shows the main menu screen.
     *
     * @param e The ActionEvent that occurred
     */
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == returnMenu) {
            setVisible(false);
            new MainMenu().setVisible(true);
        }
    }
}
