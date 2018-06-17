package model;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class ChartModel extends AbstractTableModel {

    private ArrayList<Account> accounts;
    private String[] colNames= {"Rank", "Username","Point","Time"};

    public ChartModel(){

    }

    @Override
    public String getColumnName(int column){
        return colNames[column];
    }

    public void setData(ArrayList<Account> accounts){
        this.accounts = accounts;
    }
    @Override
    public int getRowCount() {
        return accounts.size();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Account account = accounts.get(rowIndex);

        switch (columnIndex){
            case 0:
                return rowIndex;
            case 1:
                return account.getUsername();
            case 2:
                return account.getPoint();
            case 3:
                return account.getTime();
        }
        return null;
    }
}
