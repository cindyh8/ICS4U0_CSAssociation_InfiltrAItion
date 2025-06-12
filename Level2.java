/**
 * The second level of the game featuring advanced robot interactions and security challenges.
 * <p>
 * This level includes:
 * <ul>
 *   <li>Animated robot scanning sequence with security checks</li>
 *   <li>Three interactive sectors with multiple screens</li>
 *   <li>Caesar cipher and phone call mini-games</li>
 *   <li>Security flickering effect for danger detection</li>
 *   <li>Score tracking based on correct security decisions</li>
 * </ul>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *   <li>Layered pane for complex visual effects</li>
 *   <li>Multiple timer-based animations</li>
 *   <li>Keyboard-controlled navigation and interactions</li>
 *   <li>Phone number input system</li>
 *   <li>Dynamic score updating</li>
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

public class Level2 extends JFrame implements KeyListener {

   //robot animation
   private JLabel bot1;
   private Timer animationTimer;
   private Timer botMoveTimer;
   private boolean isBotMoving = false;
   private int bot1X = -100;
   private final int bot1Y = 150;
   private final int targetX = 400;
   
   //layered pane
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
   private boolean caesarCipherOpen = false;
   private boolean phoneOpen = false;
   
   //ui indicators
   private JPanel openButtonChange;
   private JPanel closeButtonChange;
   
   //game state
   private boolean entryFinished = false;
   private boolean isFlickering = true;
   private boolean bot1IsSafe = true;
   
   //phone input system
   private JLabel phoneDisplay = new JLabel(""); 
   private StringBuilder phoneInput1 = new StringBuilder();


/**
     * Constructs the Level2 game environment.
     * <p>
     * Initializes:
     * <ul>
     *   <li>Main window with fixed size</li>
     *   <li>Layered pane for visual effects</li>
     *   <li>Background images and robot sprite</li>
     *   <li>Phone display for number input</li>
     *   <li>Interactive sector images</li>
     *   <li>Button state indicators</li>
     *   <li>Automatic entry sequence</li>
     * </ul>
     */
   public Level2() {
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
      
      ImageIcon bgFrame = new ImageIcon(getClass().getResource("level1_2.png"));
      Image bgImgFrame = bgFrame.getImage().getScaledInstance(1000, 700, Image.SCALE_SMOOTH);
      JLabel frame = new JLabel(new ImageIcon(bgImgFrame));
      frame.setBounds(0, 0, 1000, 700);
      layeredPane.add(frame, Integer.valueOf(2));
   
      ImageIcon bot1IconOriginal = new ImageIcon(getClass().getResource("l2c1.png"));
      Image scaledNoobImage = bot1IconOriginal.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
      ImageIcon bot1Icon = new ImageIcon(scaledNoobImage);
      bot1 = new JLabel(bot1Icon);
      bot1.setBounds(bot1X, bot1Y, 200, 200);
      layeredPane.add(bot1, Integer.valueOf(1));
   
      sceneLabel = new JLabel();
      sceneLabel.setBounds(0, 0, 1000, 700);
      layeredPane.add(sceneLabel, Integer.valueOf(5));
      
      phoneDisplay.setFont(new Font("Courier New", Font.BOLD, 24));
      phoneDisplay.setForeground(new Color(10, 32, 52));
      phoneDisplay.setBounds(670, 420, 200, 30); 
      layeredPane.add(phoneDisplay, Integer.valueOf(12)); 
   
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
   
      
      entry();
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
     * Marks entry as finished when complete.
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
                  } else {
                     phase = 1;
                  }
               } else if (phase == 1) {
                  if (scanHeight < bot1.getHeight()) {
                     scanHeight += 4;
                     greenScan.setBounds(bot1X, bot1Y, bot1.getWidth(), scanHeight);
                  } else {
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
                              ImageIcon newIcon = new ImageIcon(getClass().getResource("l2c1_info.png"));
                              JLabel newImage = new JLabel(newIcon);
                              newImage.setBounds(0, 0, newIcon.getIconWidth(), newIcon.getIconHeight());
                              layeredPane.add(newImage, Integer.valueOf(4));
                              layeredPane.repaint();
                           
                              ((Timer) e.getSource()).stop();
                              entryFinished = true;
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
     *   <li>C: Toggle phone call interface</li>
     *   <li>0-9: Enter phone numbers</li>
     *   <li>Enter: Submit phone number</li>
     *   <li>O: Safe robot interaction</li>
     *   <li>R: Danger response (with flicker effect)</li>
     *   <li>P: Show close button state</li>
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
         } else {
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
         } else {
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
         } else {
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
         } else if (sector2open) {
            int index = currentSector - 21;
            if (index + 1 < sector2.length) {
               currentSector++;
               showSector(sector2[currentSector - 21]);
            }
         } else if (sector3open) {
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
         } else if (sector2open) {
            int index = currentSector - 21;
            if (index - 1 >= 0) {
               currentSector--;
               showSector(sector2[currentSector - 21]);
            }
         } else if (sector3open) {
            int index = currentSector - 31;
            if (index - 1 >= 0) {
               currentSector--;
               showSector(sector3[currentSector - 31]);
            }
         }
      }
      
      if (key == KeyEvent.VK_O && entryFinished && !isBotMoving) {
         isBotMoving = true;
      
         if (bot1IsSafe) {
            MainMenu.correctGuesses++;
            System.out.println("Correct guess! Total: " + MainMenu.correctGuesses);
         } else {
            MainMenu.correctGuesses--;
            System.out.println("Incorrect guess! Total: " + MainMenu.correctGuesses);
         }
      
         openButtonChange.setVisible(true);
      
         botMoveTimer = new Timer(10, 
            new ActionListener() {
               public void actionPerformed(ActionEvent e) {
                  bot1X += 2;
                  bot1.setLocation(bot1X, bot1Y);
                  if (bot1X > 1000) {
                     ((Timer) e.getSource()).stop();
                     setVisible(false);
                     new Level2Bot2();
                  }
               }
            });
         botMoveTimer.start();
      }
     
      
      if(key == KeyEvent.VK_P) {
      
         closeButtonChange.setVisible(true);
      
      }
      
      if (key == KeyEvent.VK_A) {
         if (caesarCipherOpen) {
            sceneLabel.setIcon(null);
            caesarCipherOpen = false;
         } else {
            showSector("caesar_cipher.png");
            caesarCipherOpen = true;
         }
      }
   
      if (key == KeyEvent.VK_C) {
         if (phoneOpen) {
            sceneLabel.setIcon(null);
            phoneOpen = false;
            phoneInput1.setLength(0);
            phoneDisplay.setText("");
         } else {
            showSector("phone.png");
            phoneOpen = true;
         }
      } 
          
      if (phoneOpen) {
         if (key >= KeyEvent.VK_0 && key <= KeyEvent.VK_9) {
            int number = key - KeyEvent.VK_0;
            phoneInput1.append(number);
            phoneDisplay.setText(phoneInput1.toString());
         }   
         
         if (key == KeyEvent.VK_ENTER) {
            if (phoneInput1.toString().equals("1728")) { // for level 2 bot 1
            
               showSector("callbot1.png");
               Timer timer = new Timer(3000, 
                  new ActionListener() {
                     public void actionPerformed(ActionEvent e) {
                        showSector("callbot1_2.png");
                     }
                  });
               timer.setRepeats(false);
               timer.start();
            
            } else {
               showSector("call_invalidNumber.png");
            }
         
            phoneInput1.setLength(0);
         }
      }  
          
      if (key == KeyEvent.VK_R && entryFinished && !isBotMoving && isFlickering) {
         isBotMoving = true;
      
         Timer flickerTimer = new Timer(500, 
            new ActionListener() {
               int count = 0;
               boolean show = false;
            
               public void actionPerformed(ActionEvent e) {
                  if (show) {
                     sceneLabel.setIcon(null);
                  } else {
                     showSector("redScreen.png");
                  }
                  show = !show;
                  count++;
               
                  if (count >= 6) {
                     ((Timer) e.getSource()).stop();
                     sceneLabel.setIcon(null);
                     isFlickering = false;
                  
                     if (!bot1IsSafe) {
                        MainMenu.correctGuesses++;
                        System.out.println("Correct guess! Total: " + MainMenu.correctGuesses);
                     } else {
                        MainMenu.correctGuesses--;
                        System.out.println("Incorrect guess! Total: " + MainMenu.correctGuesses);
                     }
                  
                     Timer moveLeft = new Timer(10, 
                        new ActionListener() {
                           public void actionPerformed(ActionEvent e) {
                              bot1X -= 2;
                              bot1.setLocation(bot1X, bot1Y);
                              if (bot1X + bot1.getWidth() < 0) {
                                 ((Timer) e.getSource()).stop();
                                 setVisible(false);
                                 new Level2Bot2();
                              }
                           }
                        });
                     moveLeft.start();
                  }
               }
            });
         flickerTimer.start();
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
      layeredPane.setLayer(sceneLabel, Integer.valueOf(10));
      layeredPane.repaint();
   }
  
  
   /**
     * Required by KeyListener interface (unused).
     *
     * @param e The KeyEvent containing key typed information
     */
   public void keyTyped(KeyEvent e) {}
   
   public static void main(String[] args) {
      SwingUtilities.invokeLater(Level2::new);
   }
}