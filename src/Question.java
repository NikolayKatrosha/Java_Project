public class Question {
    private String question;
    private String[] choices;
    private int correctChoiceIndex;

    public Question(String question, String[] choices, int correctChoiceIndex) {
        this.question = question;
        this.choices = choices;
        this.correctChoiceIndex = correctChoiceIndex;
    }

    public String getQuestion() {
        return question;
    }

    public String[] getChoices() {
        return choices;
    }


    public int getCorrectChoiceIndex() {
        return correctChoiceIndex;
    }
}
