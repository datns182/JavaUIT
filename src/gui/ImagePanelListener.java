package gui;

import java.util.EventListener;

public interface ImagePanelListener extends EventListener{
    public void showLetter(String letter);
    public void clearLetter();
    public void showHint();
}
