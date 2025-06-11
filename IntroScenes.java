/**
 * Displays a sequence of introductory scenes for the game.
 * <p>
 * This class shows a series of images in sequence with timed transitions:
 * <ol>
 *   <li>scene1.png (4 seconds)</li>
 *   <li>scene2.png (6 seconds)</li>
 *   <li>scene3.png (4 seconds)</li>
 *   <li>scene4.png (6 seconds)</li>
 *   <li>scene5.png (3 seconds)</li>
 *   <li>scene6.png (6 seconds)</li>
 *   <li>scene7.png (5 seconds)</li>
 *   <li>scene8.png (5 seconds)</li>
 * </ol>
 * The total intro sequence lasts approximately 39 seconds.
 *
 * @version final
 * @author Your Name
 */
 
import javax.swing.*;
import java.awt.*;

public class IntroScenes extends JFrame {

   /** The label used to display each scene image */
   private JLabel sceneLabel;
   
   /**
     * Constructs and displays the intro sequence.
     * <p>
     * Initializes a 1000x700 non-resizable window and starts a thread
     * that manages the timed sequence of scenes. Each scene is displayed
     * for a specific duration before automatically advancing to the next.
     */

   public IntroScenes() {
      setTitle("Intro Scenes");
      setResizable(false);
      setSize(1000, 700);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setLayout(new BorderLayout());
   
      sceneLabel = new JLabel();
      add(sceneLabel, BorderLayout.CENTER);
   
      setVisible(true);
   
      Thread introThread = new Thread(
         new Runnable() {
         /**
             * Runs the intro scene sequence with timed transitions.
             * <p>
             * Displays each scene image for its specified duration
             * before automatically proceeding to the next scene.
             * The sequence runs in a separate thread to avoid freezing
             * the UI during sleep intervals.
             */
             
            public void run() {
               try {
                  showScene("scene1.png");
                  Thread.sleep(4000);
                  showScene("scene2.png");
                  Thread.sleep(6000);
                  showScene("scene3.png");
                  Thread.sleep(4000);
                  showScene("scene4.png");
                  Thread.sleep(6000);
                  showScene("scene5.png");
                  Thread.sleep(3000);
                  showScene("scene6.png");
                  Thread.sleep(6000);
                  showScene("scene7.png");
                  Thread.sleep(5000);
                  showScene("scene8.png");
                  Thread.sleep(5000);
                  setVisible(false);
               
               } catch (InterruptedException e) {
               //silent interruption handling
               }
            }
         });
   
      introThread.start();
   }
   
     /**
     * Displays a specified scene image.
     * <p>
     * Loads and scales the image to fit the 1000x700 window,
     * then displays it in the center of the frame.
     *
     * @param imageName The filename of the image to display (must be in resources)
     */
   private void showScene(String imageName) {
      ImageIcon icon = new ImageIcon(getClass().getResource(imageName));
      Image scaled = icon.getImage().getScaledInstance(1000, 700, Image.SCALE_SMOOTH);
      sceneLabel.setIcon(new ImageIcon(scaled));
   }
   
     /**
     * Main method for testing the intro scenes independently.
     *
     * @param args Command line arguments (not used)
     */
   public static void main(String[] args) {
      new IntroScenes();
   }
}
