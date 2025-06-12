/**
 * The first level of the game, similar to tutorial with some interactive elements. 
 * Tells the player about how things run, how the world works. 
 * <p>
 * This level includes:
 * <ul>
 *   <li>An animated robot scanning sequence</li>
 *   <li>Three interactive sectors with multiple screens</li>
 *   <li>Caesar cipher and phone call actions</li>
 *   <li>An extensive introductory sequence with typing animations</li>
 *   <li>Guide robot hints and transitions</li>
 * </ul>
 * 
 * <h2>Key Features:</h2>
 * <ul>
 *   <li>Layered pane for complex visual effects</li>
 *   <li>Multiple timer-based animations</li>
 *   <li>Keyboard-controlled navigation</li>
 *   <li>Dynamic text display with masking effects</li>
 *   <li>Score tracking for correct/incorrect answers</li>
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
import java.util.ArrayList;

public class Level1 extends JFrame implements KeyListener {

   //robot animation 
   private JLabel bot1;
   private Timer animationTimer;
   private Timer botMoveTimer;
   private boolean isBotMoving = false;
   private boolean isEntryDone = false;
   private int bot1X = -100;
   private final int bot1Y = 150;
   private final int targetX = 400;
   
   //layered pane components 
   private JLayeredPane layeredPane;
   private JLabel sceneLabel;
   
   //sector management
   private int currentSector = 11;
   private final String[] sector1;
   private final String[] sector2;
   private final String[] sector3;
   private boolean sector1open = false;
   private boolean sector2open = false;
   private boolean sector3open = false;
   
   //open and close 
   private boolean caesarCipherOpen = false;
   private boolean phoneOpen = false;
   
   //scoring
   private int correctCount = 0;
   private int incorrectCount = 0;
   
   //ui indicators
   private JPanel openButtonChange;
   private JPanel closeButtonChange;
   
   //intro scene components
   private ArrayList<ImageIcon> introText;
   private int introIndex = 0;
   private boolean introFinished = false;
   private JLabel introTextLabel;
   
   //typing mask animation
   private JPanel typingMask;
   private int maskX=0;
   private Timer typingTimer;
   private JPanel typingMask2;
   private Timer typingTimer2;
   private int maskX2 = 0;
   private boolean animationDone = false;
   private boolean showMask = true;
   
   //guide robot display
   private JLabel guideRobotPointLabel;
   private JLabel caeserPointLabel;
   private JLabel callPointLabel;
   private JLabel comparePointLabel;
   private Timer robotTimer;

     /**
     * Constructs the Level1 game environment.
     * <p>
     * Initializes:
     * <ul>
     *   <li>Main window with fixed size</li>
     *   <li>Layered pane for visual effects</li>
     *   <li>Background images and robot sprite</li>
     *   <li>Intro text sequence with 45 frames</li>
     *   <li>Guide robot hint markers</li>
     *   <li>Interactive sector images</li>
     *   <li>Button state indicators</li>
     * </ul>
     */
   public Level1() {
      setSize(1000, 700);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setResizable(false);
   
      layeredPane = new JLayeredPane();
      layeredPane.setPreferredSize(new Dimension(1000, 700));
      setContentPane(layeredPane);
   
      setFocusable(true);
      requestFocusInWindow();
      addKeyListener(this);
   
      ImageIcon bgIcon = new ImageIcon(getClass().getResource("level1_1.png"));
      Image bgImg = bgIcon.getImage().getScaledInstance(1000, 700, Image.SCALE_SMOOTH);
      JLabel background = new JLabel(new ImageIcon(bgImg));
      background.setBounds(0, 0, 1000, 700);
      layeredPane.add(background, Integer.valueOf(0));
     
      ImageIcon bgIcon1 = new ImageIcon(getClass().getResource("level1_2.png"));
      Image bgImg1 = bgIcon1.getImage().getScaledInstance(1000, 700, Image.SCALE_SMOOTH);
      JLabel background1 = new JLabel(new ImageIcon(bgImg1));
      background1.setBounds(0, 0, 1000, 700);
      layeredPane.add(background1, Integer.valueOf(2));
   
      ImageIcon bot1IconOriginal = new ImageIcon(getClass().getResource("yellowBot.png"));
      Image scaledBot = bot1IconOriginal.getImage().getScaledInstance(250, 200, Image.SCALE_SMOOTH);
      ImageIcon bot1Icon = new ImageIcon(scaledBot);
      bot1 = new JLabel(bot1Icon);
      bot1.setBounds(bot1X, bot1Y, 250, 200);
      layeredPane.add(bot1, Integer.valueOf(1));
     
      //put all introtext into an arraylist
      introText = new ArrayList<>();
      for (int i = 1; i <=45; i++) {
     
         ImageIcon textImages = new ImageIcon(getClass().getResource("text" + i + ".png"));
         Image scaledImg = textImages.getImage().getScaledInstance(900, 600, Image.SCALE_SMOOTH);
         ImageIcon scaledIcon = new ImageIcon(scaledImg);
         introText.add(scaledIcon);
      }
     
      introTextLabel = new JLabel(introText.get(0));
   
      introTextLabel.setBounds(0, 150, 1000, 700);
      layeredPane.add(introTextLabel, Integer.valueOf(6));
     
      //guide robots
      ImageIcon guideIcon = new ImageIcon(getClass().getResource("infoPoint.png"));
      Image guideImage = guideIcon.getImage().getScaledInstance(1000, 700, Image.SCALE_SMOOTH);
      guideRobotPointLabel = new JLabel(new ImageIcon(guideImage));
      guideRobotPointLabel.setBounds(0, 0, 1000, 700);
      guideRobotPointLabel.setVisible(false);
      layeredPane.add(guideRobotPointLabel, Integer.valueOf(10));
   
      ImageIcon guideIcon1 = new ImageIcon(getClass().getResource("caeserPoint.png"));
      Image guideImage1 = guideIcon1.getImage().getScaledInstance(1000, 700, Image.SCALE_SMOOTH);
      caeserPointLabel = new JLabel(new ImageIcon(guideImage1));
      caeserPointLabel.setBounds(0, 0, 1000, 700);
      caeserPointLabel.setVisible(false);
      layeredPane.add(caeserPointLabel, Integer.valueOf(10));
     
      ImageIcon guideIcon2 = new ImageIcon(getClass().getResource("callPoint.png"));
      Image guideImage2 = guideIcon2.getImage().getScaledInstance(1000, 700, Image.SCALE_SMOOTH);
      callPointLabel = new JLabel(new ImageIcon(guideImage2));
      callPointLabel.setBounds(0, 0, 1000, 700);
      callPointLabel.setVisible(false);
      layeredPane.add(callPointLabel, Integer.valueOf(10));
     
      ImageIcon guideIcon3 = new ImageIcon(getClass().getResource("comparePoint.png"));
      Image guideImage3 = guideIcon3.getImage().getScaledInstance(1000, 700, Image.SCALE_SMOOTH);
      comparePointLabel = new JLabel(new ImageIcon(guideImage3));
      comparePointLabel.setBounds(0, 0, 1000, 700);
      comparePointLabel.setVisible(false);
      layeredPane.add(comparePointLabel, Integer.valueOf(10));
   
   
   //typing masks
      typingMask = new JPanel();
      typingMask.setBackground(new Color(175, 219, 225));
      typingMask.setBounds(150, 450, 0, 100);
      layeredPane.add(typingMask, Integer.valueOf(7));
     
      typingMask2 = new JPanel();
      typingMask2.setBackground(new Color(175, 219, 225));
      typingMask2.setBounds(400, 500, 0, 100);
      layeredPane.add(typingMask2, Integer.valueOf(7));
   
      sceneLabel = new JLabel();
      sceneLabel.setBounds(0, 0, 1000, 700);
      layeredPane.add(sceneLabel, Integer.valueOf(4));
   
      setVisible(true);
   
      sector1 = new String[]{"sector1_1.png", "sector1_2.png", "sector1_3.png", "sector1_4.png", "sector1_5.png", "sector1_6.png"};
      sector2 = new String[]{"sector2_1.png", "sector2_2.png", "sector2_3.png", "sector2_4.png", "sector2_5.png", "sector2_6.png"};
      sector3 = new String[]{"sector3_1.png", "sector3_2.png", "sector3_3.png", "sector3_4.png", "sector3_5.png", "sector3_6.png"};
     
      openButtonChange =
         new JPanel() {
            public void paintComponent(Graphics g) {
               super.paintComponent(g);
               g.setColor(new Color(0, 128, 0));
               g.fillOval(0, 0, getWidth(), getHeight());
            }
         };
         
      openButtonChange.setOpaque(false);
      openButtonChange.setBounds(880, 320, 45, 45);
      openButtonChange.setVisible(false);
      layeredPane.add(openButtonChange, Integer.valueOf(5));  
     
      closeButtonChange =
         new JPanel() {
            public void paintComponent(Graphics g) {
               super.paintComponent(g);
               g.setColor(Color.RED);
               g.fillOval(0, 0, getWidth(), getHeight());
            }
         };
         
      closeButtonChange.setOpaque(false);
      closeButtonChange.setBounds(810, 320, 45, 45);
      closeButtonChange.setVisible(false);
      layeredPane.add(closeButtonChange, Integer.valueOf(5));
     
      showIntroText();
   }
   
   
/**
     * Animates the robot's entry and scanning sequence.
     * <p>
     * The animation occurs in three phases:
     * <ol>
     *   <li>Robot moves from left to target position</li>
     *   <li>Green scanning beam expands vertically</li>
     *   <li>Scan complete notification appears</li>
     * </ol>
     */

   private void entry() {
      JLabel greenScan = new JLabel();
      greenScan.setOpaque(true);
      greenScan.setBackground(new Color(57, 255, 20));
      greenScan.setBounds(bot1X, bot1Y, bot1.getWidth(), 0);
      layeredPane.add(greenScan, Integer.valueOf(3));
   
      animationTimer = new Timer(10,
     
         new ActionListener() {
            int phase = 0;
            int scanHeight = 0;
         
            public void actionPerformed(ActionEvent e) {
           
               if (phase == 0) {
                  if (bot1X < targetX) {
                     bot1X += 2;
                     bot1.setLocation(bot1X, bot1Y);
                     greenScan.setLocation(bot1X, bot1Y);
                  }
                  else {
                     phase = 1;
                  }
               }
               else if (phase == 1) {
                  if (scanHeight < bot1.getHeight()) {
                     scanHeight += 4;
                     greenScan.setBounds(bot1X, bot1Y, bot1.getWidth(), scanHeight);
                  }
                  else {
                     phase = 2;
                     layeredPane.remove(greenScan);
                     layeredPane.repaint();
                 
                     ImageIcon scanIcon = new ImageIcon(getClass().getResource("scanComplete.png"));
                     JLabel scanComplete = new JLabel(scanIcon);
                     scanComplete.setBounds(50, 340, scanIcon.getIconWidth(), scanIcon.getIconHeight());
                     layeredPane.add(scanComplete, Integer.valueOf(3));
                     layeredPane.repaint();
                     animationTimer.stop();
                 
                     new Timer(3000,
                     
                        new ActionListener() {
                       
                           public void actionPerformed(ActionEvent e) {
                              ImageIcon newIcon = new ImageIcon(getClass().getResource("yellowBotInfo.png"));
                              JLabel newImage = new JLabel(newIcon);
                              newImage.setBounds(0, 0, newIcon.getIconWidth(), newIcon.getIconHeight());
                              layeredPane.add(newImage, Integer.valueOf(4));
                              layeredPane.repaint();
                           
                              ((Timer) e.getSource()).stop();
                           }
                        }).start();
                  }
               }
            }
         });
   
      animationTimer.start();
   }
   
     /**
     * Handles keyboard input for game control.
     * <p>
     * Key bindings:
     * <ul>
     *   <li>Q/W/E: Toggle sectors 1/2/3</li>
     *   <li>Left/Right: Navigate sector screens</li>
     *   <li>A: Toggle Caesar cipher</li>
     *   <li>C: Toggle phone call</li>
     *   <li>O/P: Show button states</li>
     * </ul>
     *
     * @param e The KeyEvent containing key press information
     */
   public void keyPressed(KeyEvent e) {
      int key = e.getKeyCode();
   
      if (key == KeyEvent.VK_Q) {
         if (sector1open) {
            sceneLabel.setIcon(null);
            sector1open = false;
         }
         else {
            currentSector = 11;
            showSector(sector1[0]);
            sector1open = true;
            sector2open = false;
            sector3open = false;
         }
      }
   
      if (key == KeyEvent.VK_W) {
         if (sector2open) {
            sceneLabel.setIcon(null);
            sector2open = false;
         }
         else {
            currentSector = 21;
            showSector(sector2[0]);
            sector2open = true;
            sector1open = false;
            sector3open = false;
         }
      }
   
      if (key == KeyEvent.VK_E) {
         System.out.println("E pressed");
     
         if (sector3open) {
            sceneLabel.setIcon(null);
            sector3open = false;
         }
         else {
            currentSector = 31;
            showSector(sector3[0]);
            sector3open = true;
            sector1open = false;
            sector2open = false;
         }
      }
   
      if (key == KeyEvent.VK_RIGHT) {
         System.out.println("Right arrow pressed");
     
         if (sector1open) {
            int index = currentSector - 11;
           
            if (index + 1 < sector1.length) {
               currentSector++;
               showSector(sector1[currentSector - 11]);
            }
         }
         else if (sector2open) {
            int index = currentSector - 21;
            if (index + 1 < sector2.length) {
               currentSector++;
               showSector(sector2[currentSector - 21]);
            }
         }
         else if (sector3open) {
            int index = currentSector - 31;
            if (index + 1 < sector3.length) {
               currentSector++;
               showSector(sector3[currentSector - 31]);
            }
         }
      }
   
      if (key == KeyEvent.VK_LEFT) {
         System.out.println("Left arrow pressed");
     
         if (sector1open) {
            int index = currentSector - 11;
            if (index - 1 >= 0) {
               currentSector--;
               showSector(sector1[currentSector - 11]);
            }
         }
         else if (sector2open) {
            int index = currentSector - 21;
            if (index - 1 >= 0) {
               currentSector--;
               showSector(sector2[currentSector - 21]);
            }
         }
         else if (sector3open) {
            int index = currentSector - 31;
            if (index - 1 >= 0) {
               currentSector--;
               showSector(sector3[currentSector - 31]);
            }
         }
      }
     
      if (key == KeyEvent.VK_O) {
         if (isEntryDone && !isBotMoving) {
            isBotMoving = true;
            botMoveTimer = new Timer(10,
               new ActionListener() {
                  public void actionPerformed(ActionEvent e) {
                     bot1X += 2;
                     bot1.setLocation(bot1X, bot1Y);
                  }
               });
            botMoveTimer.start();
         }
     
         openButtonChange.setVisible(true);
      }      
     
      if(key == KeyEvent.VK_P) {
         closeButtonChange.setVisible(true);
      }
     
      if (key == KeyEvent.VK_A) {
         if (caesarCipherOpen) {
            sceneLabel.setIcon(null);
            caesarCipherOpen = false;
         }
         else {
            showSector("caesar_cipher.png");
            caesarCipherOpen = true;
         }
      }
     
      if(key == KeyEvent.VK_C) {
         if (phoneOpen) {
            sceneLabel.setIcon(null);
            phoneOpen = false;
         }
         else {
            showSector("phone.png");
            phoneOpen = true;
         }
      }
   }
   
     /**
     * Handles key release events for button state changes.
     *
     * @param e The KeyEvent containing key release information
     */
   public void keyReleased(KeyEvent e) {
   
      int key = e.getKeyCode();
   
      if (key == KeyEvent.VK_O) {
         openButtonChange.setVisible(false);
      }
     
      if(key == KeyEvent.VK_P) {
         closeButtonChange.setVisible(false);
      }
   }
   
    /**
     * Displays a sector or mini-game image.
     *
     * @param imageName The filename of the image to display
     */
   private void showSector(String imageName) {
      ImageIcon icon = new ImageIcon(getClass().getResource(imageName));
      Image scaledImage = icon.getImage().getScaledInstance(1000, 700, Image.SCALE_SMOOTH);
      ImageIcon scaledIcon = new ImageIcon(scaledImage);
   
      sceneLabel.setIcon(scaledIcon);
      sceneLabel.setBounds(0, 0, 1000, 700);
      layeredPane.setLayer(sceneLabel, Integer.valueOf(5));
      layeredPane.repaint();
   }
   
     /**
     * Manages the introductory text sequence with typing animations.
     */
   private void showIntroText() {
      startTypingMask1();
       
      if (introIndex >= introText.size()) {
         introFinished = true;
         introTextLabel.setVisible(false);
         typingMask.setVisible(false);
         return;
      }
     
      if (introIndex == introText.size()) { //after text45 shown
         typingTimer.stop();
     
         introTextLabel.setVisible(false);
         typingMask.setVisible(false);
         typingMask2.setVisible(false);


      }
   
    //reset mask
      maskX = 0;
      maskX2=0;
      typingMask.setBounds(350, 450, 488, 30);
      typingMask.setVisible(true);
     
      typingMask2.setBounds(350, 480, 400, 27);
      typingMask2.setVisible(true);
   
      animationDone = false;
   
    //check robot entry
      if (introIndex == 8) {
         triggerRobotEntry();
      }
     
   //scans and displays scan
      if (introIndex == 10){
         entry();
      }
     
   //displays first image, of guide robot showing the scan screen
      if (introIndex == 12) { // text13 appears
         introTextLabel.setVisible(false);
         typingMask.setVisible(false);
         typingMask2.setVisible(false);
         //showMask=false;
         showRobotTransition("infoPoint.png",12);  
      }
   
   
      if (introIndex == 13){
         showMask =true;
      }
     
      if (introIndex==19){
         introTextLabel.setVisible(false);
         showMask=false;
         showRobotTransition("comparePoint.png",19);
      }
     
      if (introIndex==24){
         showRobotTransition("caeserPoint.png",24);
      }
      if (introIndex==31){
         showRobotTransition("callPoint.png",31);
      }
     
      if (introIndex==44){
         dispose();
         new level1Over();
      }
   }
   
   /**
     * Animates the first typing mask effect.
     */
   private void startTypingMask1(){
      if (!showMask)
         return;
   
      if (typingTimer != null) typingTimer.stop();
      final int textStartX = 350;
      final int maskWidth = 488;  
   
      maskX = 0; // hide in left start when
      typingMask.setBackground(new Color(175, 219, 225));
      typingMask.setVisible(true);
   
      typingTimer = new Timer(10,
     
         new ActionListener() {
         
            public void actionPerformed(ActionEvent e) {
               maskX += 5;
               
               int newX = textStartX + maskX;
               int newWidth = maskWidth - maskX;
               if (maskX<=maskWidth) {
                  typingMask.setBounds(newX, 450, newWidth, 30);
                  typingMask.setVisible(true);
               }
               else {
                  typingTimer.stop();
                  typingMask.setVisible(false);
                 
                  startTypingMask2();
               }
           
            //stop when completely past the text
               if (maskX >= 710) {
                  typingTimer.stop();
                  animationDone = true;
                  typingMask.setVisible(false);
               }
            }
         });
      typingTimer.start();
   }
   
   /**
     * Animates the second typing mask effect.
     */
   private void startTypingMask2() {
      final int textStartX2 = 350;
      int maskWidth2 = 420;  
   
      maskX2 = 0;
      typingMask2.setBounds(textStartX2, 500, 0, 30);
      typingMask2.setVisible(true);
      typingMask2.setBackground(new Color(175, 219, 225));
   
      typingTimer2 = new Timer(10,
         new ActionListener() {
            public void actionPerformed(ActionEvent e) {
           
               maskX2 += 5;
               int newX = textStartX2 + maskX2;
               int newWidth = maskWidth2 - maskX2;
           
               if (maskX2 <= maskWidth2) {
                  typingMask2.setBounds(newX, 485, newWidth, 20);
               }
               
               else if (showMask){
                 
                  typingTimer2.stop();
                  typingMask2.setVisible(false);
                  animationDone = true;
                 
                  new Timer(2000,
                        new ActionListener() {
                       
                           public void actionPerformed(ActionEvent e) {
                              ((Timer) e.getSource()).stop();
                           
                              introIndex++; // move up text
                              introTextLabel.setIcon(introText.get(introIndex));
                              showIntroText();
                           
                           }
                        }).start();
               }
               else {
                  typingTimer2.stop();
               }
            }
         });
      typingTimer2.start();
   }
   
   /**
     * Triggers the robot's entry animation.
     */
   private void triggerRobotEntry() {
      if (!isBotMoving) {
         isBotMoving = true;
         botMoveTimer = new Timer(10,
            new ActionListener() {
               public void actionPerformed(ActionEvent e) {
                  bot1X += 2;
                  bot1.setLocation(bot1X, bot1Y);
                  if (bot1X >= 400) {
                     botMoveTimer.stop();
                     isEntryDone = true;
                  }
               }
            });
         botMoveTimer.start();
      }
   }
   
   /**
     * Shows a guide robot transition screen.
     *
     * @param robotImagePath The image file for the guide robot
     * @param nextTextIndex The next text index to show after transition
     */
   private void showRobotTransition(String robotImagePath, int nextTextIndex) {
      showMask = false;
      introTextLabel.setVisible(false);
      typingMask.setVisible(false);
   
      guideRobotPointLabel = new JLabel(new ImageIcon(getClass().getResource(robotImagePath)));
      guideRobotPointLabel.setBounds(0, 0, 1000, 700);
      layeredPane.add(guideRobotPointLabel, Integer.valueOf(10));
      guideRobotPointLabel.setVisible(true);
   
      Timer robotDelay = new Timer(5000,
         new ActionListener() {
         
            public void actionPerformed(ActionEvent e) {
           
               guideRobotPointLabel.setVisible(false);
               layeredPane.remove(guideRobotPointLabel);
               layeredPane.repaint();
           
               introTextLabel.setIcon(introText.get(nextTextIndex));
               introTextLabel.setVisible(true);
               typingMask.setVisible(true);
            }
         });
      robotDelay.setRepeats(false);
      robotDelay.start();
     
      showMask=true;
   }

   /**
     * Required by KeyListener interface (unused).
     *
     * @param e The KeyEvent containing key typed information
     */
   public void keyTyped(KeyEvent e) {}

   public static void main(String[] args) {
      SwingUtilities.invokeLater(Level1::new);
   }
}
