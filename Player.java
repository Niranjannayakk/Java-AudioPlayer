import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.Color;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

public class Player extends JFrame implements ActionListener {
    JFrame playerframe;
    JLabel playDisplay;
    JLabel filename;
    String songName; 
    JButton previousButton;
    JButton nextButton; 
    JButton pauseButton;
    JButton playButton;
    JButton fileButton;
    int pausedPosition;
    int currentPosition;
    Boolean isPause = false;
    File chooosenFile;
    AudioInputStream audioFile;
    Clip c;


    ImageIcon record = new ImageIcon("record.png");
    ImageIcon pImageIcon = new ImageIcon("pause.png");
    ImageIcon nextIcon = new ImageIcon("next.png");
    ImageIcon prImageIcon = new ImageIcon("previous.png");
    ImageIcon play = new ImageIcon("play.png");
    

    public Player(){
        this.setSize(450,500);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.getContentPane().setBackground(Color.black); 
        this.setVisible(true);
        this.setLayout(null);
        this.setResizable(false);

        playDisplay = new JLabel();
        playDisplay.setBounds(10, 10, 300, 250);
        playDisplay.setIcon(record);
        
        filename = new JLabel("Select a file to play");
        filename.setBounds(10, 260, 300, 40);
        filename.setForeground(Color.white);

        previousButton = new JButton(prImageIcon);
        nextButton =new JButton(nextIcon);
        pauseButton =new JButton(pImageIcon);
        playButton =new JButton("PLAY");
        fileButton = new JButton("Select a file to play");

        previousButton.setBounds(10, 400, 50, 40);
        previousButton.setBackground(Color.BLACK);
        previousButton.setFocusable(false);
        previousButton.setBorderPainted(false);

        nextButton.setBounds(180, 400, 50, 40);
        nextButton.setBackground(Color.BLACK);
        nextButton.setFocusable(false);
        nextButton.setBorderPainted(false);

        pauseButton.setBounds(95, 400, 50,40);
        pauseButton.setBackground(Color.BLACK);
        pauseButton.setFocusable(false);
        pauseButton.setBorderPainted(false);
        
        playButton.setBounds(10, 350, 220, 40);

        fileButton.setBounds(10, 300, 220, 40);
        fileButton.setFocusable(false);

        previousButton.addActionListener(this);
        pauseButton.addActionListener(this);
        nextButton.addActionListener(this);
        playButton.addActionListener(this);
        fileButton.addActionListener(this);

        this.add(previousButton);
        this.add(nextButton);
        this.add(pauseButton);
        this.add(playButton);
        this.add(playDisplay);
        this.add(fileButton);
        this.add(filename);
    
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == fileButton){
            JFileChooser fileChooser = new JFileChooser("c:\\Users\\User\\Music");
            int choice = fileChooser.showOpenDialog(null);
            
            if(choice == JFileChooser.APPROVE_OPTION){
                chooosenFile = new File(fileChooser.getSelectedFile().getAbsolutePath());
                songName = chooosenFile.getName();
                songName = songName.substring(0, songName.lastIndexOf("."));
                filename.setText("Playing...."+songName);
                try {
                    audioFile = AudioSystem.getAudioInputStream(chooosenFile);
                    c = AudioSystem.getClip();
                } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e1) {
                    e1.printStackTrace();
                }

            }
        }
        if(e.getSource()== playButton && chooosenFile != null){
            if(isPause == false){
            try {
                c.open(audioFile);
                c.start();
            } catch (LineUnavailableException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
             else{
                c.setFramePosition(pausedPosition);
                c.start();
                isPause = false;
                pauseButton.setIcon(pImageIcon);

            }

        }

        if (e.getSource() == previousButton) {
            currentPosition = c.getFramePosition();
            c.setFramePosition(currentPosition - 441000); 
        } 
        
        if (e.getSource() == nextButton) {
            currentPosition = c.getFramePosition();
            c.setFramePosition(currentPosition + 441000); 
        }
        
        if (e.getSource() == pauseButton) {
            pausedPosition = c.getFramePosition();
            c.stop();
            pauseButton.setIcon(play); 
            isPause = true;
        }
    }
   

}
