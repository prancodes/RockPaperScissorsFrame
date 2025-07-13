package GamePkg;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.sound.sampled.*;
import java.net.*;

public class RockPaperScissorsGame extends JFrame {
    private JPanel menuPanel;
    private JButton playButton;
    private JButton optionsButton;
    private boolean isMusicOn=false;
    private Clip clip;
    private JPanel gamePanel;
    private JTextArea outputArea;

    public RockPaperScissorsGame() {
        setTitle("Rock Paper Scissors");
        // Load icon
        URL iconUrl = getClass().getResource("/assets/favicon.png");
        if (iconUrl != null) {
            Image icon = new ImageIcon(iconUrl).getImage();
            setIconImage(icon);
        } else {
            System.err.println("Warning: /assets/icon.png not found on classpath!");
        }
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(360, 460));

        menuPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                URL bgUrl = getClass().getResource("/assets/background.png");
                if (bgUrl == null) {
                    System.err.println("Could not find /assets/background.png on the classpath!");
                } else {
                    ImageIcon backgroundImage = new ImageIcon(bgUrl);
                    g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);
                }
                g.setColor(new Color(255, 165, 0)); // Light orange color
                int rectangleWidth = 282;
                int rectangleHeight = 100;
                int rectangleX = 30;
                int rectangleY = 40;
                g.fillRect(rectangleX, rectangleY, rectangleWidth, rectangleHeight);
                
                g.setColor(Color.WHITE);
                g.setFont(new Font("Arial", Font.BOLD, 28));
                String text = "Rock Paper Scissor";
                int textX = 38;
                int textY = 100;
                g.drawString(text, textX, textY);
            }
        };
        menuPanel.setLayout(null);
        setContentPane(menuPanel);

        playButton = new JButton("Play");
        playButton.setFont(new Font("Arial", Font.BOLD, 30));
        playButton.setBounds(90, 200, 160, 50);
        playButton.setBackground(Color.GREEN);
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setContentPane(gamePanel);
                validate();
                repaint();
            }
        });
        menuPanel.add(playButton);

        optionsButton = new JButton("Options");
        optionsButton.setFont(new Font("Arial", Font.BOLD, 30)); 
        optionsButton.setBackground(Color.GREEN); 
        optionsButton.setBounds(90, 270, 160, 50);
        optionsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                isMusicOn = !isMusicOn;
                if (isMusicOn) {
                    
                    playMusic();
                    JOptionPane.showMessageDialog(RockPaperScissorsGame.this, "Music On");
                } else {
                    
                    stopMusic();
                    JOptionPane.showMessageDialog(RockPaperScissorsGame.this, "Music Off");
                }
            }
        });
        menuPanel.add(optionsButton);


        pack();
        setLocationRelativeTo(null); 
        setVisible(true);

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ This is our Game panel ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

        gamePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                URL bgUrl = getClass().getResource("/assets/background.png");
                if (bgUrl == null) {
                    System.err.println("Could not find /assets/background.png on the classpath!");
                } else {
                    ImageIcon backgroundImage = new ImageIcon(bgUrl);
                    g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);
                }
                g.setColor(Color.RED);
                int rectangleWidth = 282;
                int rectangleHeight = 100;
                int rectangleX = 30;
                int rectangleY = 40;
                g.fillRect(rectangleX, rectangleY, rectangleWidth, rectangleHeight);
                
                g.setColor(Color.WHITE);
                g.setFont(new Font("Arial", Font.BOLD, 26));
                String text = "Defeat the Computer";
                int textX = 40;
                int textY = 100;
                g.drawString(text, textX, textY);
            }
        };

        JButton rockButton = new JButton("Rock");
        rockButton.setFont(new Font("Arial", Font.BOLD, 18));
        rockButton.setBounds(22, 350, 100, 50);
        rockButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playGame("Rock");
            }
        });
        gamePanel.add(rockButton);
        
        JButton paperButton = new JButton("Paper");
        paperButton.setFont(new Font("Arial", Font.BOLD, 18));
        paperButton.setBounds(122, 350, 100, 50);
        paperButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playGame("Paper");
            }
        });
        gamePanel.add(paperButton);
        
        JButton scissorButton = new JButton("Scissor");
        scissorButton.setFont(new Font("Arial", Font.BOLD, 18));
        scissorButton.setBounds(222, 350, 100, 50);
        scissorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playGame("Scissor");
            }
        });
        gamePanel.add(scissorButton);

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ This is our Output Textarea box ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    outputArea = new JTextArea();
    outputArea.setFont(new Font("Arial", Font.BOLD, 22));
    outputArea.setBounds(22, 198, 300, 105);
    outputArea.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    outputArea.setEditable(false);
    outputArea.setLineWrap(true);
    outputArea.setWrapStyleWord(true);
    outputArea.setAlignmentX(Component.CENTER_ALIGNMENT);
    gamePanel.add(outputArea);
    
    // ----------------------------------------------------------------------------------------

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        gamePanel.setLayout(null);        
    }

    private void playGame(String userChoice) {
    String[] choices = {"Rock", "Paper", "Scissor"};
    Random random = new Random();
    int computerIndex = random.nextInt(3);
    String computerChoice = choices[computerIndex];

    String resultMessage = "You chose : "+ userChoice + "\nComputer chose : " + computerChoice + "\n\n";
    if (userChoice.equals(computerChoice)) {
        resultMessage += "                 It's a tie!";
    } 
    
    else if ((userChoice.equals("Rock") && computerChoice.equals("Scissor")) ||
               (userChoice.equals("Paper") && computerChoice.equals("Rock")) ||
               (userChoice.equals("Scissor") && computerChoice.equals("Paper"))) {
        resultMessage += "                You win!";


        Timer timer = new Timer(600, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object[] options = {"Play again", "Exit"};
                int choice = JOptionPane.showOptionDialog(null, "You defeated the computer!", "Game Over",
                        JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
                if (choice == 0) {
                    outputArea.setText(""); // Clear previous text
                } else {
                    System.exit(0);
                }
            }
        });
        timer.setRepeats(false); // Execute ActionListener only once
        timer.start();
    } else {
        resultMessage += "                You lose!";
    }

    outputArea.setText(resultMessage);
}

private void playMusic() {
    try {
        URL musicUrl = getClass().getResource("/assets/music.wav");
        if (musicUrl == null) {
            System.err.println("Could not find /assets/music.wav on the classpath!");
            return;
        }
        try (AudioInputStream audioIn = AudioSystem.getAudioInputStream(musicUrl)) {
            clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.loop(Clip.LOOP_CONTINUOUSLY);

            // volume control:
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);

            float minDb = gainControl.getMinimum();
            float maxDb = gainControl.getMaximum();

            float desiredDb = 0.0f;
            float safeDb = Math.max(minDb, Math.min(desiredDb, maxDb));

            gainControl.setValue(safeDb);
            clip.start();
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
}

    private void stopMusic() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
            clip.close();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new RockPaperScissorsGame();
            }
        });
    }
}
