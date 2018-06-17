package model;

public class Word {
    private String word;
    private String meaning;
    private String sound;
    private String soundMeaning;
    private String image;

    public String getWord() {
        return word;
    }

    public String getMeaning() {
        return meaning;
    }

    public String getSound() {
        return sound;
    }

    public String getImage() {
        return image;
    }

    public String getSoundMeaning() {
        return soundMeaning;
    }

    public Word(String word, String meaning, String sound, String soundMeaning, String image){
        this.word = word;
        this.meaning = meaning;
        this.sound = sound;
        this.soundMeaning = soundMeaning;
        this.image = image;

    }
}
