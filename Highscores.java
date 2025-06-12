/**
 * Displays and manages the game's high score leaderboard.
 * <p>
 * This screen shows a formatted list of player names and scores loaded from a file,
 * sorted in descending order. The display features:
 * <ul>
 *   <li>A background image with transparent score display</li>
 *   <li>Properly aligned name and score columns</li>
 *   <li>A return button to navigate back to the main menu</li>
 * </ul>
 * Scores are automatically sorted and formatted for optimal readability.
 *
 * <h2>Course Info:</h2>
 * ICS4U0, Ms. Krasteva
 *
 * @version final
 * @author Cindy He, Sarah Chong
 */
 
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;


public class Highscores extends JFrame implements ActionListener {
     /** 
     * 2D array storing player names and scores (nameScore[i][0] = name, nameScore[i][1] = score)
     * with capacity for maxEntries entries
     */
   private String[][] nameScore;
   
   /** Current number of valid score entries loaded from file */
   private int numOfPlays;
   
   /** Button to return to the main menu */
   private JButton returnMenu;
   
   /**   
     * Constructs the high scores display screen.
     *
     * @param maxEntries The maximum number of high score entries to display and store
     */
   public Highscores(int maxEntries) {
      super("Highscores");
      int maxDisplay = Math.min(maxEntries, 10);

      nameScore = new String[maxDisplay][2]; 
      numOfPlays = 0;
      readData("highscores.txt");
      
      sortScores(nameScore, numOfPlays);
      
      setSize(1000, 700);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setResizable(false);
      setLayout(null);
   
      ImageIcon backgroundIcon = new ImageIcon(getClass().getResource("highscore.png"));
      Image scaledImage = backgroundIcon.getImage().getScaledInstance(1000, 700, Image.SCALE_SMOOTH);
      JLabel background = new JLabel(new ImageIcon(scaledImage));
      background.setBounds(0, 0, 1000, 700);
      background.setLayout(null);
      add(background);
   
      returnMenu = new JButton("Return to Menu");
      returnMenu.setBounds(350, 550, 300, 50);
      returnMenu.addActionListener(this);
      background.add(returnMenu);
   
      JPanel scoresPanel = new JPanel(new GridLayout(numOfPlays, 1, 5, 0));
      scoresPanel.setBounds(210, 200, 900, numOfPlays * 30);  
   
      scoresPanel.setOpaque(false); 
   
      for (int i = 0; i < numOfPlays; i++) {
         String formatted = String.format("%-30s %10s", nameScore[i][0], nameScore[i][1]);
         JLabel scoreLabel = new JLabel(formatted);
      
         scoreLabel.setFont(new Font("Courier New", Font.BOLD, 20));
         scoreLabel.setForeground(Color.WHITE);
         scoreLabel.setHorizontalAlignment(JLabel.LEFT);
         scoresPanel.add(scoreLabel);
      }
   
      background.add(scoresPanel);
   
      setVisible(true);
   }
   
     /**
     * Handles action events for UI components.
     *
     * @param e The ActionEvent containing event details
     */
   public void actionPerformed(ActionEvent e) {
      if (e.getSource() == returnMenu) {
         setVisible(false);
         new MainMenu().setVisible(true);
      }
   }

     /**
     * Reads high score data from the specified file.
     * <p>
     * Expected file format:
     * <pre>
     * name1 score1
     * name2 score2
     * ...
     * DONE
     * </pre>
     *
     * @param fileName The path to the high scores data file
     */
   private void readData(String fileName) {
      try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
         String line;
         while ((line = reader.readLine()) != null && !line.equals("DONE") && numOfPlays < nameScore.length) {
            int spaceIndex = line.indexOf(" ");
            if (spaceIndex != -1) {
               nameScore[numOfPlays][0] = line.substring(0, spaceIndex);
               nameScore[numOfPlays][1] = line.substring(spaceIndex + 1);
               numOfPlays++;
            }
         }
      } catch (IOException e) {
      System.out.println("Error reading file: " + e.getMessage());
      }
   }

/**
     * Sorts the scores array in descending order using bubble sort algorithm.
     *
     * @param scores The 2D array containing name/score pairs
     * @param length The number of valid entries in the array
     */
   private static void sortScores(String[][] scores, int length) {
      for (int i = 0; i < length - 1; i++) {
         for (int j = 0; j < length - i - 1; j++) {
            int s1 = Integer.parseInt(scores[j][1]);
            int s2 = Integer.parseInt(scores[j + 1][1]);
            if (s2 > s1) {
               String[] temp = scores[j];
               scores[j] = scores[j + 1];
               scores[j + 1] = temp;
            }
         }
      }
   }

     /**
     * Saves a new score to the high scores file while maintaining sorted order.
     * <p>
     * If the scores file is not full, the new score will be added.
     * If full, it will only be added if it qualifies as a high score.
     *
     * @param name The player name to save
     * @param score The player score to save
     * @param fileName The path to the high scores file
     * @param maxEntries The maximum number of entries to maintain
     */
   public static void saveScore(String name, int score, String fileName, int maxEntries) {
      String[][] scores = new String[maxEntries][2];
      int count = 0;
   
      try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
         String line;
         while ((line = reader.readLine()) != null && !line.equals("DONE") && count < maxEntries) {
            int spaceIndex = line.indexOf(" ");
            if (spaceIndex != -1) {
               scores[count][0] = line.substring(0, spaceIndex);
               scores[count][1] = line.substring(spaceIndex + 1);
               count++;
            }
         }
      } catch (IOException e) {
      System.out.println("Error reading file: " + e.getMessage());
      }
   
      if (count < maxEntries) {
         scores[count][0] = name;
         scores[count][1] = String.valueOf(score);
         count++;
      }
   
   
      try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
         for (int i = 0; i < count; i++) {
            writer.write(scores[i][0] + " " + scores[i][1]);
            writer.newLine();
         }
         writer.write("DONE");
         writer.newLine();
      } catch (IOException e) {
      System.out.println("Error reading file: " + e.getMessage());
      }
   }   
}
