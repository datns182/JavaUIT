package gui;

import java.util.EventListener;

public interface InfoPanelListener extends EventListener {
    public void showTimeArea();
    public void startTest();
    public void stopTest();
}
