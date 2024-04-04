package dev.overlord.entities;

public class RiddlesAPI {
    private String riddle;
    private String answer;

    public RiddlesAPI(String riddle, String answer) {
        this.riddle = riddle;
        this.answer = answer;
    }

    public RiddlesAPI() {
    }

    public String getRiddle() {
        return riddle;
    }

    public void setRiddle(String riddle) {
        this.riddle = riddle;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

}
