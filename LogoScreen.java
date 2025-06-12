/**
 * Displays the initial logo splash screen when the game launches.
 * <p>
 * This screen shows the game's logo on a non-resizable window that automatically
 * appears when instantiated. The window dimensions are fixed at 1000x700 pixels
 * to maintain consistent presentation across all game screens.
 *
 * <h2>Features:</h2>
 * <ul>
 *   <li>Displays full-screen logo image</li>
 *   <li>Non-resizable window</li>
 *   <li>Automatically visible on instantiation</li>
 *   <li>Properly closes application when window is closed</li>
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

public class LogoScreen extends JFrame {
   /** The label component that holds and displays the logo image */
    JLabel label;

     /**
     * Constructs and displays the logo splash screen.
     * <p>
     * Initializes the window with:
     * <ul>
     *   <li>Fixed size of 1000x700 pixels</li>
     *   <li>Non-resizable property</li>
     *   <li>Default close operation (EXIT_ON_CLOSE)</li>
     *   <li>Logo image loaded from "csassociation.png" resource</li>
     * </ul>
     * The screen becomes visible immediately upon construction.
     */
     
    public LogoScreen() {

        setResizable(false);
        setSize(1000, 700);

        ImageIcon logoIcon = new ImageIcon(getClass().getResource("csassociation.png"));
        Image scaledLogo = logoIcon.getImage().getScaledInstance(1000, 700, Image.SCALE_SMOOTH);
        label = new JLabel(new ImageIcon(scaledLogo));
        add(label);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        
    }
}
