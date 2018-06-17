package model;

public class Image {
    private String imageName;
    private String imageAnswer;

    public Image(String name, String answer){
        this.imageName = name;
        this.imageAnswer  = answer;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getImageAnswer() {
        return imageAnswer;
    }

    public void setImageAnswer(String imageAnswer) {
        this.imageAnswer = imageAnswer;
    }
}
