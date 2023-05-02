import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Random;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.Clip;

public class Buttons {
    private JButton newGameButton;
    private JButton loadGameButton;
    private JPanel newGamePanel;
    private NewGame newGame;
    private Clip clip;
    public Buttons(JPanel newGamePanel) {
        this.newGamePanel = newGamePanel;
        newGame = new NewGame();
    }

    public JPanel createPanel() {
        JPanel panel = new JPanel(new FlowLayout());

        newGameButton = new JButton("Новая игра");
        loadGameButton = new JButton("Загрузить игру");
        JButton closeButton = new JButton("Закрыть окно");
        JButton playButton = new JButton("Play music");
        loadGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newGame.reset();
                newGame.createPanel();
                newGamePanel.setVisible(true);
            }
        });

        loadGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                NewGame newGame = new NewGame();
                newGamePanel.removeAll();
                newGamePanel.add(newGame.createPanel());
                newGamePanel.revalidate();
                newGamePanel.repaint();
            }
        });

        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newGamePanel.setVisible(true);
            }
        });

        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Создаем массив с названиями файлов песен
                String[] songNames = {"/song1.wav", "/song2.wav", "/song3.wav", "/song4.wav", "/song5.wav", "/song6.wav"};
                // Генерируем случайный индекс песни
                int songIndex = new Random().nextInt(songNames.length);

                // Остановите текущую песню, если она воспроизводится
                if (clip != null && clip.isRunning()) {
                    clip.stop();
                }

                // Загружаем и воспроизводим выбранную песню
                try {
                    clip = AudioSystem.getClip();
                    AudioInputStream inputStream = AudioSystem.getAudioInputStream(getClass().getResourceAsStream(songNames[songIndex]));
                    clip.open(inputStream);
                    clip.start();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        panel.add(playButton);
        panel.add(newGameButton);
        panel.add(loadGameButton);
        panel.add(closeButton);

        return panel;
    }


}
