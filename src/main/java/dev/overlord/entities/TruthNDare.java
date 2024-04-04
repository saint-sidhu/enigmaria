package dev.overlord.entities;

public class TruthNDare {
    private String id;
    private  String type;
    private String rating;
    private String question;
    private Translations translations;

    public TruthNDare() {
    }

    public TruthNDare(String id, String type, String rating, String question, Translations translations) {
        this.id = id;
        this.type = type;
        this.rating = rating;
        this.question = question;
        this.translations = translations;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Translations getTranslations() {
        return translations;
    }

    public void setTranslations(Translations translations) {
        this.translations = translations;
    }

}
