package com.example.class22b_and_1_pacmengame_315238915.objects;

import java.util.ArrayList;
import java.util.Collections;

public class MyDB {
    private ArrayList<Record> records;
    private static MyDB myDB;

    public MyDB() {
        this.records =  new ArrayList<>();
    }

    public static MyDB initMyDB() {
        if (myDB == null) {
            myDB = new MyDB();
        }
        return myDB;
    }

    public void addRecord (Record record) {
        records.add(record);
    }

    public Record getSpecificRecord (int position) {
        return records.get(position);
    }

    public ArrayList<Record> getRecords() {
        return records;
    }

    public void  sortByScore () {
        Collections.sort(records);
    }




}
