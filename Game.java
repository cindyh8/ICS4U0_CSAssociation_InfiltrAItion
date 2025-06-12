/**
 * Main game class that controls the sequence of screens in the application.
 * <p>
 * This class manages the transition between different game screens:
 * <ol>
 *   <li>Logo screen (3 seconds)</li>
 *   <li>Loading screen (3 seconds)</li>
 *   <li>Intro scenes (38 seconds)</li>
 *   <li>Name entry screen</li>
 * </ol>
 * 
 * <h2>Sequence Flow:</h2>
 * Each screen is displayed for a specific duration before automatically
 * transitioning to the next screen.
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

public class Game {

    /**
     * The main entry point for the application.
     * <p>
     * Creates and manages the sequence of game screens in a separate thread
     * to allow for timed transitions between screens.
     *
     * @param args Command line arguments (not used in this application)
     */
     
   public static void main(String[] args) {
      LogoScreen logo = new LogoScreen();
   
      Thread t = new Thread(
         new Runnable() {
               /**
                 * Runs the screen sequence with timed transitions.
                 * <p>
                 * The sequence is:
                 * 1. Logo screen (3 seconds)
                 * 2. Loading screen (3 seconds)
                 * 3. Intro scenes (38 seconds)
                 * 4. Name entry screen
                 */
            public void run() {
               try {
                  //display logo for 3 seconds
                  Thread.sleep(3000);
                  logo.setVisible(false);
                  
                  //show loading screen for 3 seconds
                  LoadingScreen loading = new LoadingScreen();
                  loading.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                  loading.setVisible(true);
               
                  Thread.sleep(3000);
                  loading.setVisible(false);
                  
                  //show intro scenes for 38 seconds
                  IntroScenes intro = new IntroScenes();
                  Thread.sleep(38000);
                  
                  //show entry screen name
                  EnterName name = new EnterName();
                  name.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                  name.setVisible(true);
               
               } catch (Exception e) {
                System.out.println("Thread was interrupted: " + e.getMessage());
               }
            }
         });
   
      t.start();
   }
}

