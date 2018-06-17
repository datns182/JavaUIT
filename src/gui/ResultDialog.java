package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ResultDialog extends JDialog {

    private JLabel lblTestName;
    private JLabel lblPoint;
    private JLabel lblTime;
    private JLabel lblTiltle;

    private JButton btnOK;
    private ResultDialogListener resultDialogListener;

    public ResultDialog(JFrame parent) {
        super(parent, "Result");
        lblTestName = new JLabel();
        lblTiltle = new JLabel("Congratulation");

        btnOK = new JButton("OK");

        lblTiltle.setHorizontalAlignment(JLabel.CENTER);
        lblTiltle.setVerticalAlignment(JLabel.CENTER);
        //lblTiltle.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));

        lblTestName.setHorizontalAlignment(JLabel.CENTER);
        lblTestName.setVerticalAlignment(JLabel.CENTER);
        lblTestName.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));

        lblPoint = new JLabel();
        lblTime = new JLabel();

        setSize(300, 230);
        setLocationRelativeTo(parent);
        setUndecorated(true);
        setLayout();

        btnOK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(resultDialogListener!=null)
                    resultDialogListener.setResult();
            }
        });
    }

    private void setLayout() {
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new GridBagLayout());
        infoPanel.setBorder(BorderFactory.createEmptyBorder(0,20,0,20));
        GridBagConstraints gc = new GridBagConstraints();

        gc.gridy = 0;


        gc.weightx = 1;
        gc.weighty = 1;

        gc.gridx = 0;
        gc.fill = GridBagConstraints.NONE;
        gc.anchor = GridBagConstraints.LINE_START;
        infoPanel.add(new Label("Point: "),gc);

        gc.gridx =1 ;
        gc.anchor = GridBagConstraints.LINE_END;
        infoPanel.add(lblPoint, gc);

        gc.gridy ++;

        gc.weightx = 1;
        gc.weighty = 1;

        gc.gridx = 0;
        gc.fill = GridBagConstraints.NONE;
        gc.anchor = GridBagConstraints.LINE_START;
        infoPanel.add(new Label("Time: "),gc);

        gc.gridx =1 ;
        gc.anchor = GridBagConstraints.LINE_END;
        infoPanel.add(lblTime, gc);

        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BorderLayout());
        titlePanel.add(lblTiltle, BorderLayout.PAGE_START);
        titlePanel.add(lblTestName, BorderLayout.CENTER);

        setLayout(new BorderLayout());
        getRootPane().setBorder(BorderFactory.createLineBorder(Color.BLACK));
        add(infoPanel, BorderLayout.CENTER);
        add(titlePanel, BorderLayout.PAGE_START);
        add(btnOK, BorderLayout.PAGE_END);

    }

    public void showResult(int point, int time){
        lblPoint.setText(point+"p");
        lblTime.setText(time+"s");
    }

    public void setResultDialogListener(ResultDialogListener listener){
        this.resultDialogListener = listener;
    }

    public void setTestName(String title){
        lblTestName.setText(title);
    }


}
