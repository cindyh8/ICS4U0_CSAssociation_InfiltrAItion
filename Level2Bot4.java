/**
 * Level 2 Bot 4 - representing a security threat.
 * <p>
 * This class represents the fourth bot encounter in level 2, where players must
 * analyze and determine if the bot is safe or dangerous using various tools.
 * Players can examine different sectors of the bot, use a Caesar cipher tool,
 * or call the bot via a phone interface to gather information
 *
 * This class represents the fourth bot encounter in Level 2 of the game. It features:
 * <h2>Game Mechanics:</h2>
 * - Press Q/W/E to examine different bot sectors
 * - Use left/right arrows to navigate sector images
 * - Press O to let the bot pass (if safe)
 * - Press R to reject the bot (if dangerous)
 * - Press A to access Caesar cipher tool
 * - Press C to access phone interface (code: 3759)
 * 
 * <h2>Course Info:</h2>
 * ICS4U0, Ms. Krasteva
 *
 * @version 1.0
 * @author Your Name
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Level2Bot4 extends JFrame implements KeyListener { //incorrect bot

//robot animation
   private JLabel bot4;
   private Timer animationTimer;
   private Timer botMoveTimer;
   private boolean isBotMoving = false;
   private int bot4X = -100;
   private final int bot4Y = 150;
   private final int targetX = 400;
   
   //display components 
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
   private boolean bot4IsSafe = false;
   
   //phone interaction
   private JLabel phoneDisplay = new JLabel(""); 
   private StringBuilder phoneInput1 = new StringBuilder();

   /**
     * Constructs the Level2Bot4 game frame and initializes components.
     * <p>
     * Sets up:
     * <ul>
     *   <li>Main window properties</li>
     *   <li>Background and frame layers</li>
     *   <li>Bot character</li>
     *   <li>Sector image resources</li>
     *   <li>Interactive button indicators</li>
     * </ul>
     */
   public Level2Bot4() {
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

   
      ImageIcon bot4IconOriginal = new ImageIcon(getClass().getResource("l2c4.png"));
      Image scaledBotImage = bot4IconOriginal.getImage().getScaledInstance(250, 200, Image.SCALE_SMOOTH);
      ImageIcon bot4Icon = new ImageIcon(scaledBotImage);
      bot4 = new JLabel(bot4Icon);
      bot4.setBounds(bot4X, bot4Y, 200, 200);
      layeredPane.add(bot4, Integer.valueOf(1));
   
      sceneLabel = new JLabel();
      sceneLabel.setBounds(0, 0, 1000, 700);
      layeredPane.add(sceneLabel, Integer.valueOf(4));
      
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
     * Animates the bot's entry and scanning sequence.
     * <p>
     * The animation occurs in three phases:
     * <ol>
     *   <li>Bot moves from left to target position</li>
     *   <li>Green scanning beam expands vertically</li>
     *   <li>Scan complete notification appears</li>
     * </ol>
     * Marks entry as finished when complete.
     */
   private void entry() {
      JLabel greenScan = new JLabel();
      greenScan.setOpaque(true);
      greenScan.setBackground(new Color(57, 255, 20)); 
      greenScan.setBounds(bot4X, bot4Y, bot4.getWidth(), 0);
      layeredPane.add(greenScan, Integer.valueOf(2));
   
      animationTimer = new Timer(10, 
         new ActionListener() {
            int phase = 0;
            int scanHeight = 0;
         
            public void actionPerformed(ActionEvent e) {
               if (phase == 0) {
                  if (bot4X < targetX) {
                     bot4X += 2;
                     bot4.setLocation(bot4X, bot4Y);
                     greenScan.setLocation(bot4X, bot4Y);
                  } else {
                     phase = 1;
                  }
               } else if (phase == 1) {
                  if (scanHeight < bot4.getHeight()) {
                     scanHeight += 4;
                     greenScan.setBounds(bot4X, bot4Y, bot4.getWidth(), scanHeight);
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
                              ImageIcon newIcon = new ImageIcon(getClass().getResource("l2c4_info.png"));
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
     * Handles key press events for game controls.
     * <p>
     * Controls include:
     * <ul>
     *   <li>Q/W/E - Toggle sector views</li>
     *   <li>Left/Right arrows - Navigate sector pages</li>
     *   <li>O/P - Open/close actions with visual feedback</li>
     *   <li>A - Toggle Caesar cipher puzzle</li>
     *   <li>C - Toggle phone </li>
     *   <li>R - Trigger red screen flicker effect</li>
     *   <li>0-9 - Phone number input</li>
     *   <li>Enter - Submit phone input</li>
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
      
         if (bot4IsSafe) {
            MainMenu.correctGuesses++;
            System.out.println("Correct guess! Total: " + MainMenu.correctGuesses);
         } else {
            MainMenu.correctGuesses--;
            System.out.println("Incorrect guess! Total: " + MainMenu.correctGuesses);
             Highscores.saveScore(MainMenu.playerName, MainMenu.correctGuesses, "highscores.txt", 100);
            incorrectGuess();
         }
      
         openButtonChange.setVisible(true);
      
         botMoveTimer = new Timer(10, 
            new ActionListener() {
               public void actionPerformed(ActionEvent e) {
                  bot4X += 2;
                  bot4.setLocation(bot4X, bot4Y);
                  if (bot4X > 1000) {
                     ((Timer) e.getSource()).stop();
                     setVisible(false);
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
            if (phoneInput1.toString().equals("3759")) { //for level 2 bot 1
               showSector("callbot1.png");
               Timer timer = new Timer(3000, 
                  new ActionListener() {
                     public void actionPerformed(ActionEvent e) {
                        showSector("callbot4.png");
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
                  
                     if (!bot4IsSafe) {
                        MainMenu.correctGuesses++;
                        System.out.println("Correct guess! Total: " + MainMenu.correctGuesses);
                     } else {
                        MainMenu.correctGuesses--;
                        System.out.println("Incorrect guess! Total: " + MainMenu.correctGuesses);
                     }
                  
                     Timer moveLeft = new Timer(10, 
                        new ActionListener() {
                           public void actionPerformed(ActionEvent e) {
                              bot4X -= 2;
                              bot4.setLocation(bot4X, bot4Y);
                              if (bot4X + bot4.getWidth() < 0) {
                                 ((Timer) e.getSource()).stop();
                                 setVisible(false);
                                 new Level2Bot5(); //change to next lvl
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
     * Handles key release events for button visual feedback.
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
     * Displays the specified image in the scene view.
     *
     * @param imageName The name of the image file to display
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
     * Handles incorrect guess by stopping timers and showing game over screen.
     */
   private void incorrectGuess() {
      if (animationTimer != null) animationTimer.stop();
      if (botMoveTimer != null) botMoveTimer.stop();
      setVisible(false);
      dispose();
      new GameOver();
   }
   
   /**
     * Required by KeyListener interface (unused).
     *
     * @param e The KeyEvent containing key typed information
     */
   public void keyTyped(KeyEvent e) {}

   public static void main(String[] args) {
      SwingUtilities.invokeLater(Level2Bot4::new);
   }
}
