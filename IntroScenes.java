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

   private JLabel sceneLabel;

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
               }
            }
         });
   
      introThread.start();
   }

   private void showScene(String imageName) {
      ImageIcon icon = new ImageIcon(getClass().getResource(imageName));
      Image scaled = icon.getImage().getScaledInstance(1000, 700, Image.SCALE_SMOOTH);
      sceneLabel.setIcon(new ImageIcon(scaled));
   }

   public static void main(String[] args) {
      new IntroScenes();
   }
}
