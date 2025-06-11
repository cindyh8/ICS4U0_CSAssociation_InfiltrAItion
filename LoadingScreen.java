/**
 * Displays a loading screen while game resources are being prepared.
 * <p>
 * This screen shows a static loading image on a non-resizable window.
 * The window dimensions are fixed at 1000x700 pixels to match the game's
 * standard screen size.
 *
 * <h2>Features:</h2>
 * <ul>
 *   <li>Full-screen loading image display</li>
 *   <li>Non-resizable window</li>
 *   <li>Simple FlowLayout for centered content</li>
 * </ul>
 *
 * @version final
 * @author Your Name
 */

import javax.swing.*;
import java.awt.*;

public class LoadingScreen extends JFrame {

     /**
     * Constructs the loading screen.
     * <p>
     * Initializes the window with:
     * <ul>
     *   <li>FlowLayout for simple centered content</li>
     *   <li>Fixed size of 1000x700 pixels</li>
     *   <li>Non-resizable window</li>
     *   <li>Loading image scaled to window dimensions</li>
     * </ul>
     * The loading image is loaded from "loadingscreen.png" resource.
     */
    public LoadingScreen() {
        setLayout(new FlowLayout());
        setResizable(false);
        setSize(1000, 700);

        ImageIcon loadingIcon = new ImageIcon(getClass().getResource("loadingscreen.png"));
        Image scaledLoading = loadingIcon.getImage().getScaledInstance(1000, 700, Image.SCALE_SMOOTH);
        JLabel label = new JLabel(new ImageIcon(scaledLoading));
        add(label);
        
    }
}
