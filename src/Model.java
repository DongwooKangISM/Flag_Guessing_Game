import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Model {

    // boolean checkAnswer()
    // call question class, filehelper class?
    // void start()
    // count score
    // getNextQuestion

    private int score = 0;
    private int numCorrect = 0;
    private int numAttempted = 0;
    private int questionCount = 0;
    List<Question> questions;

    public Model() {
        //read the questions into my question array
        List<String> questionStrings = new FileHelper().getFileContent();
        questions = new ArrayList<>(questionStrings.size());

        for (String i: questionStrings) {
            String[] questionArray = i.split(",");
            questions.add(new Question(questionArray[0],questionArray[1]));
        }
    }

    public static void main(String[] args) {
        //for code testing
        Model model = new Model();
        model.newQuiz();
        System.out.println("hi");
        System.out.println(model.getNextQuestion().country);
    }

    public void newQuiz() {
        numCorrect = 0;
        numAttempted = 0;
        questionCount = 0;
        Collections.shuffle(questions);

        // shuffle the question array

    }

    public Question getNextQuestion() {
        return questions.get(questionCount++);

    }

    public List<String> getCountries() {
        List<String> countries = new ArrayList<>(questions.size());
        questions.forEach((question) -> {
            countries.add(question.country);
        });
        Collections.sort(countries);
        return countries;
    }

    public String checkAnswer(String answer) {
        numAttempted++;
        String correct = questions.get(questionCount - 1).country;
        if(answer.equalsIgnoreCase(correct)) {
            numCorrect ++;
        }

        return correct;

    }

    public int getNumCorrect() {
        return numCorrect;
    }

    public int getNumAttempted() {
        return numAttempted;
    }

    /*private int getNumCorrect() {

    }

    private int getNumattempted() {

    }

    public int getScore() {
        return score;
    }*/


}
