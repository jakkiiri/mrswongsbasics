// stores the question and answer of a question for a notebook

public class Question {

    String question;
    String answer;
    public Question (String q, String a) {
        question = q;
        answer = a;
    }

    public boolean check(String a) {
        return answer == a;
    }

    public String toString() {
        return question + " " + answer;
    }
    

}
