import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class Game {

   public static void main(String[] args) {
      LogoScreen logo = new LogoScreen();
   
      Thread t = new Thread(
         new Runnable() {
            public void run() {
               try {
                  Thread.sleep(3000);
                  logo.setVisible(false);
               
                  LoadingScreen loading = new LoadingScreen();
                  loading.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                  loading.setVisible(true);
               
                  Thread.sleep(3000);
                  loading.setVisible(false);
               
                  IntroScenes intro = new IntroScenes();
                  Thread.sleep(38000);
                  
                  EnterName name = new EnterName();
                  name.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                  name.setVisible(true);
               
               } catch (Exception e) {
               }
            }
         });
   
      t.start();
   }
}

