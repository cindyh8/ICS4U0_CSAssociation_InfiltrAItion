/**
 * The main menu screen that serves as the central navigation hub for the game.
 * <p>
 * This screen provides access to all major game features through buttons:
 * <ul>
 *   <li>Level 1 and Level 2 gameplay</li>
 *   <li>Game instructions</li>
 *   <li>High scores display</li>
 *   <li>Exit functionality</li>
 * </ul>
 * The menu maintains player name and score statistics across screens.
 *
 * <h2>Features:</h2>
 * <ul>
 *   <li>Background image with button overlay</li>
 *   <li>Five navigation buttons with action listeners</li>
 *   <li>Static player name and score tracking</li>
 *   <li>1000x700 non-resizable window</li>
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

public class MainMenu extends JFrame implements ActionListener {

   // Navigation buttons
   private JButton instructions, level2, highscores, exit, play;
   
    /** Stores the player's name for score tracking */
   public static String playerName = "";
   
   /** Tracks the number of correct guesses across game sessions */
   public static int correctGuesses = 0;

     /**
     * Constructs the main menu interface.
     * <p>
     * Initializes the window with:
     * <ul>
     *   <li>Background image scaled to 1000x700</li>
     *   <li>Five navigation buttons with action listeners</li>
     *   <li>Fixed size and exit-on-close behavior</li>
     * </ul>
     */
   public MainMenu() {
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setSize(1000, 700);
      setResizable(false);
      setLayout(null);
   
      ImageIcon bgIcon = new ImageIcon(getClass().getResource("mainmenu.png"));
      Image scaledBg = bgIcon.getImage().getScaledInstance(1000, 700, Image.SCALE_SMOOTH);
      JLabel background = new JLabel(new ImageIcon(scaledBg));
      background.setBounds(0, 0, 1000, 700);
      background.setLayout(null);
   
      instructions = new JButton("Instructions");
      instructions.setBounds(100, 250, 300, 50);
      instructions.addActionListener(this);
   
      level2 = new JButton("Level 2");
      level2.setBounds(100, 340, 300, 50);
      level2.addActionListener(this);
   
      highscores = new JButton("Highscores");
      highscores.setBounds(100, 430, 300, 50);
      highscores.addActionListener(this);
   
      exit = new JButton("Exit");
      exit.setBounds(100, 520, 300, 50);
      exit.addActionListener(this);
   
      play = new JButton("Level 1");
      play.setBounds(550, 520, 300, 50);
      play.addActionListener(this);
      
   //add components to window
      background.add(instructions);
      background.add(level2);
      background.add(highscores);
      background.add(exit);
      background.add(play);
      add(background);
   }
   
   /**
     * Displays the logo screen before exiting the application.
     * <p>
     * This method runs in a separate thread to:
     * <ol>
     *   <li>Hide the current menu</li>
     *   <li>Show the logo screen for 3 seconds</li>
     *   <li>Terminate the application</li>
     * </ol>
     */
   public void endLogoScreen() {
      Thread t = new Thread(
         new Runnable() {
            public void run() {
               try {
                  setVisible(false);
                  LogoScreen logo = new LogoScreen();
                  logo.setVisible(true);
                  Thread.sleep(3000);
                  System.exit(0);
               } catch (Exception e) {
               System.out.println("Error during exit animation: " + e.getMessage());
               }
            }
         });
      t.start();
   }

     /**
     * Handles button click events for navigation.
     * <p>
     * Depending on which button was clicked:
     * <ul>
     *   <li>Hides the current menu</li>
     *   <li>Opens the corresponding screen</li>
     *   <li>For exit button, initiates shutdown sequence</li>
     * </ul>
     *
     * @param e The ActionEvent containing event details
     */
   public void actionPerformed(ActionEvent e) {
   
      if (e.getSource() == instructions) {
         setVisible(false);
         new InstructionsScreen(); 
      } else if (e.getSource() == level2) {
         setVisible(false);
         new Level2();
      } else if (e.getSource() == highscores) {
         setVisible(false);
         new Highscores(100); 
      } else if (e.getSource() == exit) {
         setVisible(false);
         endLogoScreen();
      } else if (e.getSource() == play) {
         setVisible(false);
         new Level1(); 
      }
   }
   
   /**
     * Main method to launch the game's main menu.
     *
     * @param args Command line arguments (not used)
     */
   public static void main(String[] args) {
      new MainMenu().setVisible(true);
   }
}
