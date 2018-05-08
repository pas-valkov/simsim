package com.example.arsen.menu;

import java.util.ArrayList;

public class Good {
    public int fields_number;
    public String[] info;

    public boolean isChecked; //TODO REDO

    Good () {
        isChecked = false;
    }


//    public Good (int size) {
////        fields_number = DBHelper.Get_good_column_number();
//        fields_number = size;
//        info = new String[fields_number];
//    }

    public Good (ArrayList <String> strings, int size) {
        fields_number = size;
        info = new String[fields_number];
        for (int i = 0; i < fields_number; ++i)
            info[i] = strings.get(i);
    }
}

