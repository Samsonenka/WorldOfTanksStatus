package com.example.worldoftanksstatus.logics;

import android.util.Log;

import java.text.DecimalFormat;

public class WinPercentage {

    public String getWinPercentage(String wins, String battles){

        double winsInt = Double.parseDouble(wins);
        double battlesInt = Double.parseDouble(battles);

        return new DecimalFormat("#0.00").format(winsInt / battlesInt * 100);
    }
}
