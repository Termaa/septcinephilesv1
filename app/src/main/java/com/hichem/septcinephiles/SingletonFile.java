package com.hichem.septcinephiles;


import android.content.Context;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class SingletonFile {
    private FileOutputStream fOut;
    private FileInputStream isr;

    private SingletonFile() {


    }

    private static SingletonFile INSTANCE = null;

    public static SingletonFile getInstance() {
        if (INSTANCE == null)
            return new SingletonFile();

        return INSTANCE;
    }

    public void getOuput(String file, String content,Context context) {
        try {
            fOut = context.openFileOutput(file, Context.MODE_APPEND);
            fOut.write(content.getBytes());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String[] getInput(String file, Context context) {
        String temp="";
        try {
            isr = context.openFileInput(file);
            int c;
            while( (c = isr.read()) != -1){
                temp = temp + Character.toString((char)c);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return temp.split(", ");
    }



}
