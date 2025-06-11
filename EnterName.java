import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Screen for entering player name at the start of the game.
 * <p>
 * This screen allows players to input their name using keyboard:
 * <ul>
 *   <li>Accepts alphanumeric characters (A-Z, 0-9) and spaces</li>
 *   <li>Maximum 15 characters</li>
 *   <li>Backspace to delete characters</li>
 *   <li>Enter to confirm and proceed to main menu</li>
 * </ul>
 * The screen displays on a 1000x700 non-resizable window with background image.
 *
 * @version final
 * @author Your Name
 */
 
public class EnterName extends JFrame implements KeyListener {

   /** Stores the player's name as it's being typed */
   private StringBuilder playerName = new StringBuilder();
   
   /** Displays the player's name as they type it */
   private JLabel nameLabel;
   
   /**
     * Constructs the name entry screen.
     * <p>
     * Initializes the window with:
     * <ul>
     *   <li>Background image scaled to 1000x700</li>
     *   <li>Text display area for the name</li>
     *   <li>Key listener for name input</li>
     * </ul>
     * The window is non-resizable and exits on close.
     */

   public EnterName() {
      
      setSize(1000, 700);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setResizable(false);
      setLayout(null);
      setFocusable(true);
      requestFocusInWindow();
      addKeyListener(this);
   
      ImageIcon enterNameIcon = new ImageIcon(getClass().getResource("enterName.png"));
      Image scaledName = enterNameIcon.getImage().getScaledInstance(1000, 700, Image.SCALE_SMOOTH);
      JLabel label = new JLabel(new ImageIcon(scaledName));
      label.setBounds(0, 0, 1000, 700);
      add(label);
   
      nameLabel = new JLabel();
      nameLabel.setBounds(450, 380, 900, 40);
      nameLabel.setForeground(Color.WHITE);
      nameLabel.setFont(new Font("Courier New", Font.BOLD, 30));
      nameLabel.setOpaque(false);
      label.add(nameLabel);
   }
   
   /**
     * Handles keyboard input for name entry.
     * <p>
     * Processes three types of key events:
     * <ol>
     *   <li>Alphanumeric keys - adds to name (up to 15 chars)</li>
     *   <li>Backspace - deletes last character</li>
     *   <li>Enter - confirms name and proceeds to main menu</li>
     * </ol>
     *
     * @param e The KeyEvent containing key press information
     */
   public void keyPressed(KeyEvent e) {
   
      int key = e.getKeyCode();
   
      if ((key >= KeyEvent.VK_A && key <= KeyEvent.VK_Z) || (key >= KeyEvent.VK_0 && key <= KeyEvent.VK_9) || key == KeyEvent.VK_SPACE) {
         
         if (playerName.length() < 15) {
            char input = (char) key;
            if (key == KeyEvent.VK_SPACE) {
               input = ' ';
            } else {
               input = e.getKeyChar();
            }
            playerName.append(input);
            nameLabel.setText(playerName.toString());
         }
      }
   
      if (key == KeyEvent.VK_BACK_SPACE) {
         if (playerName.length() > 0) {
            playerName.deleteCharAt(playerName.length() - 1);
            nameLabel.setText(playerName.toString());
         }
      }
   
      if (key == KeyEvent.VK_ENTER && playerName.length() > 0) {
         MainMenu.playerName = playerName.toString();
         setVisible(false);
         new MainMenu().setVisible(true);
      }
   }
   
     /**
     * Unused keyReleased event handler (required by KeyListener interface)
     * @param e The KeyEvent
     */ 
   public void keyReleased(KeyEvent e) {}
   
     /**
     * Unused keyTyped event handler (required by KeyListener interface)
     * @param e The KeyEvent
     */
   public void keyTyped(KeyEvent e) {}

     /**
     * Main method for testing the EnterName screen independently.
     *
     * @param args Command line arguments (not used)
     */
   public static void main(String[] args) {
      new EnterName().setVisible(true);
   }
}

