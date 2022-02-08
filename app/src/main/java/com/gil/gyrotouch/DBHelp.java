package com.gil.gyrotouch;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelp extends SQLiteOpenHelper {

    public DBHelp(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE MEASUREBOX (_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, num INTEGER, mnum INTERGER, mtx REAL, mty REAL, mgx REAL, mgy REAL, mgz REAL, dt REAL);");
        db.execSQL("CREATE TABLE BALANCEBOX (_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, mnum INTERGER, btx REAL, bty REAL, bgx REAL, bgy REAL, bgz REAL, bdt REAL);");
        db.execSQL("CREATE TABLE COSINEBOX (_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, num INTEGER, mnum INTERGER, ctx REAL, cty REAL, cgx REAL, cgy REAL, cgz REAL, cdt REAL);");
        db.execSQL("CREATE TABLE VALIDBOX (_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, valid1min REAL, valid1max REAL, valid2min REAL, valid2max REAL, valid3min REAL, valid3max REAL, valid4min REAL, valid4max REAL);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public String findResult(String id, int num) {
        SQLiteDatabase db = getReadableDatabase();
        String findresult = "";

        Cursor cursor = db.rawQuery("SELECT * FROM MEASUREBOX WHERE name = '" + id + "' AND num = '" + num + "';", null);
        while (cursor.moveToNext()) {
            findresult += cursor.getString(0)
                    +". ID : "
                    + cursor.getString(1)
                    + "|측정수:"
                    + cursor.getInt(2)
                    + "|구간:"
                    + cursor.getInt(3)
                    + "|mtx:"
                    + cursor.getInt(4)
                    + "|mty:"
                    + cursor.getInt(5)
                    + "|mgx:"
                    + cursor.getInt(6)
                    + "|mgy:"
                    + cursor.getInt(7)
                    + "|mgz:"
                    + cursor.getInt(8)
                    + "|dt:"
                    + cursor.getInt(9)
                    + "\n";
        }
        return findresult;
    }

    public Boolean isitok(String id) {
        // 읽기가 가능하게 DB 열기
        SQLiteDatabase db = getReadableDatabase();
        Boolean ok = false;
        // DB에 있는 데이터를 쉽게 처리하기 위해 Cursor를 사용하여 테이블에 있는 모든 데이터 출력
        Cursor cursor = db.rawQuery("SELECT * FROM MEASUREBOX WHERE name='" + id + "';", null);
        cursor.moveToLast();
        if(cursor.getInt(2) >= 5) {
            ok = true;
        }
        return ok;
    }

    public Boolean okid(String id) {
        // 읽기가 가능하게 DB 열기
        SQLiteDatabase db = getReadableDatabase();
        Boolean ok = false;
        // DB에 있는 데이터를 쉽게 처리하기 위해 Cursor를 사용하여 테이블에 있는 모든 데이터 출력
        Cursor cursor = db.rawQuery("SELECT * FROM MEASUREBOX WHERE name='" + id + "';", null);
        int count = cursor.getCount();
        if(count != 0){
            ok = true;
        }
        return ok;
    }

    public int Measurecount(String id){
        int count = 1;
        Cursor cursor;
        SQLiteDatabase db = getWritableDatabase();
        cursor = db.rawQuery("SELECT * FROM MEASUREBOX WHERE name = '" + id + "' AND mnum = '" + 1 + "';", null);
        if(cursor.getCount()==0){
            return 1;
        } else {
            cursor.moveToFirst();
            while (cursor.moveToNext()) {
                count++;
            }
            count++;
            return count;
        }
    }

    public float[] makeBalance(String id, int mnum) {
        // 읽기가 가능하게 DB 열기
        SQLiteDatabase db = getReadableDatabase();
        float []balance = {0, 0, 0, 0, 0, 0};
        float btx = 0;
        float bty = 0;
        float bgx = 0;
        float bgy = 0;
        float bgz = 0;
        float bdt = 0;
        float count = 0;

        // DB에 있는 데이터를 쉽게 처리하기 위해 Cursor를 사용하여 테이블에 있는 모든 데이터 출력
        Cursor cursor = db.rawQuery("SELECT * FROM MEASUREBOX WHERE name = '" + id + "' AND mnum = '" + mnum + "';", null);

        if(cursor.getCount()!=0) {
            cursor.moveToFirst();
            count++;
            btx += cursor.getFloat(4);
            bty += cursor.getFloat(5);
            bgx += cursor.getFloat(6);
            bgy += cursor.getFloat(7);
            bgz += cursor.getFloat(8);
            bdt += cursor.getFloat(9);

            while (cursor.moveToNext()) {
                btx += cursor.getFloat(4);
                bty += cursor.getFloat(5);
                bgx += cursor.getFloat(6);
                bgy += cursor.getFloat(7);
                bgz += cursor.getFloat(8);
                bdt += cursor.getFloat(9);
                count++;
            }
        }
        balance[0] = btx/count;
        balance[1] = bty/count;
        balance[2] = bgx/count;
        balance[3] = bgy/count;
        balance[4] = bgz/count;
        balance[5] = bdt/count;

        return balance;
    }

    public String MeasureResult(String id) {
        // 읽기가 가능하게 DB 열기
        SQLiteDatabase db = getReadableDatabase();
        String measureresult = "";

        // DB에 있는 데이터를 쉽게 처리하기 위해 Cursor를 사용하여 테이블에 있는 모든 데이터 출력
        Cursor cursor = db.rawQuery("SELECT * FROM MEASUREBOX WHERE name = '" + id + "';", null);

        if(cursor.getCount()!=0){
            cursor.moveToLast();
            measureresult +=
                    "ID : "
                    + cursor.getString(1)
                    + " | 측정수 : "
                    + cursor.getInt(2)
                    + " | 구간 : "
                    + cursor.getInt(3) + "\n"
                    + "mtx : "
                    + cursor.getInt(4)
                    + " | mty : "
                    + cursor.getInt(5)
                    + " | mgx : "
                    + cursor.getInt(6)
                    + " | mgy : "
                    + cursor.getInt(7)
                    + " | mgz : "
                    + cursor.getInt(8)
                    + " | dt : "
                    + cursor.getInt(9)
                    + "\n";

            while (cursor.moveToPrevious()) {
                measureresult +=
                        "ID : "
                                + cursor.getString(1)
                                + " | 측정수 : "
                                + cursor.getInt(2)
                                + " | 구간 : "
                                + cursor.getInt(3) + "\n"
                                + "mtx : "
                                + cursor.getInt(4)
                                + " | mty : "
                                + cursor.getInt(5)
                                + " | mgx : "
                                + cursor.getInt(6)
                                + " | mgy : "
                                + cursor.getInt(7)
                                + " | mgz : "
                                + cursor.getInt(8)
                                + " | dt : "
                                + cursor.getInt(9)
                                + "\n";
            }
        }
        return measureresult;
    }

    public void Measureinsert(String id, int num, int when, float[]mea) {
        // 읽고 쓰기가 가능하게 DB 열기
        SQLiteDatabase db = getWritableDatabase();
        // DB에 입력한 값으로 행 추가
        db.execSQL("INSERT INTO MEASUREBOX (name, num, mnum, mtx, mty, mgx, mgy, mgz, dt)  " +
                "Values ('" + id + "', " + num + ", " + when + ", " + mea[0] + ", " + mea[1] + ", " + mea[2] + ", " + mea[3] + ", " + mea[4] + ", '" + mea[5] + "');");
        db.close();
    }

    public float[] getMeasure(String id, int num, int mnum) {
        // 읽기가 가능하게 DB 열기
        SQLiteDatabase db = getReadableDatabase();
        float []gM = {0, 0, 0, 0, 0, 0};
        float btx = 0;
        float bty = 0;
        float bgx = 0;
        float bgy = 0;
        float bgz = 0;
        float bdt = 0;

        // DB에 있는 데이터를 쉽게 처리하기 위해 Cursor를 사용하여 테이블에 있는 모든 데이터 출력
        Cursor cursor = db.rawQuery("SELECT * FROM MEASUREBOX WHERE name = '" + id + "' AND num = '" + num + "' AND mnum = '" + mnum + "';", null);

        while (cursor.moveToNext()){
            btx = cursor.getFloat(4);
            bty = cursor.getFloat(5);
            bgx = cursor.getFloat(6);
            bgy = cursor.getFloat(7);
            bgz = cursor.getFloat(8);
            bdt = cursor.getFloat(9);
        }
        gM[0] = btx;
        gM[1] = bty;
        gM[2] = bgx;
        gM[3] = bgy;
        gM[4] = bgz;
        gM[5] = bdt;

        return gM;
    }

    public float[] getBalance(String id, int mnum) {
        // 읽기가 가능하게 DB 열기
        SQLiteDatabase db = getReadableDatabase();
        float []gM = {0, 0, 0, 0, 0, 0};
        float btx = 0;
        float bty = 0;
        float bgx = 0;
        float bgy = 0;
        float bgz = 0;
        float bdt = 0;

        // DB에 있는 데이터를 쉽게 처리하기 위해 Cursor를 사용하여 테이블에 있는 모든 데이터 출력
        Cursor cursor = db.rawQuery("SELECT * FROM BALANCEBOX WHERE name = '" + id + "' AND mnum = '" + mnum + "';", null);

        while (cursor.moveToNext()){
            btx = cursor.getFloat(3);
            bty = cursor.getFloat(4);
            bgx = cursor.getFloat(5);
            bgy = cursor.getFloat(6);
            bgz = cursor.getFloat(7);
            bdt = cursor.getFloat(8);
        }
        gM[0] = btx;
        gM[1] = bty;
        gM[2] = bgx;
        gM[3] = bgy;
        gM[4] = bgz;
        gM[5] = bdt;

        return gM;
    }


    public float[] getValid(String id) {
        // 읽기가 가능하게 DB 열기
        SQLiteDatabase db = getReadableDatabase();
        float []gM = {0, 0, 0, 0, 0, 0, 0, 0};
        float bt1 = 0;
        float bt2 = 0;
        float btx = 0;
        float bty = 0;
        float bgx = 0;
        float bgy = 0;
        float bgz = 0;
        float bdt = 0;

        // DB에 있는 데이터를 쉽게 처리하기 위해 Cursor를 사용하여 테이블에 있는 모든 데이터 출력
        Cursor cursor = db.rawQuery("SELECT * FROM VALIDBOX WHERE name = '" + id + "';", null);

        while (cursor.moveToNext()){
            bt1 = cursor.getFloat(2);
            bt2 = cursor.getFloat(3);
            btx = cursor.getFloat(4);
            bty = cursor.getFloat(5);
            bgx = cursor.getFloat(6);
            bgy = cursor.getFloat(7);
            bgz = cursor.getFloat(8);
            bdt = cursor.getFloat(9);

        }
        gM[0] = bt1;
        gM[1] = bt2;
        gM[2] = btx;
        gM[3] = bty;
        gM[4] = bgx;
        gM[5] = bgy;
        gM[6] = bgz;
        gM[7] = bdt;

        return gM;
    }

    public void Balanceinsert(String id, int num, float[]bal) {
        // 읽고 쓰기가 가능하게 DB 열기
        SQLiteDatabase db = getWritableDatabase();
        // DB에 입력한 값으로 행 추가

        Cursor cursor = db.rawQuery("SELECT * FROM BALANCEBOX WHERE name='" + id + "' AND mnum = '" + num + "';", null);
        int count = cursor.getCount();
        if(count == 0){
            db.execSQL("INSERT INTO BALANCEBOX (name, mnum, btx, bty, bgx, bgy, bgz, bdt)  " +
                    "Values ('" + id + "', " + num + ", " + bal[0] + ", " + bal[1] + ", " + bal[2] + ", " + bal[3] + ", " + bal[4] + ", '" + bal[5] + "');");
            db.close();
        }else if(count != 0){
            db.execSQL("UPDATE BALANCEBOX SET btx = '"+ bal[0] + "', bty = '"+ bal[1] + "', bgx = '"+ bal[2] + "', bgy = '"+ bal[3] + "', bgz = '"+ bal[4] + "', bdt = '"+ bal[5] + "' WHERE name = '" + id + "' AND mnum = '" + num + "';");
            db.close();
        }
    }

    public void Validinsert(String id, double[][]bal) {
        // 읽고 쓰기가 가능하게 DB 열기
        SQLiteDatabase db = getWritableDatabase();
        // DB에 입력한 값으로 행 추가
        Cursor cursor = db.rawQuery("SELECT * FROM VALIDBOX WHERE name='" + id + "';", null);
        int count = cursor.getCount();
        if(count == 0){
            db.execSQL("INSERT INTO VALIDBOX (name, valid1min, valid1max, valid2min, valid2max, valid3min, valid3max, valid4min, valid4max)  " +
                    "Values ('" + id + "', " + bal[0][0] + ", " + bal[0][1] + ", " + bal[1][0] + ", " + bal[1][1] + ", " + bal[2][0] + ", " + bal[2][1] + ", " + bal[3][0] + ", '" + bal[3][1] + "');");
            db.close();
        }else if(count != 0){
            db.execSQL("UPDATE VALIDBOX SET valid1min = '"+ bal[0][0] + "', valid1max = '"+ bal[0][1] + "', valid2min = '"+ bal[1][0] + "', valid2max = '"+ bal[1][1] + "', valid3min = '"+ bal[2][0] + "', valid3max = '"+ bal[2][1] + "', valid4min = '"+ bal[3][0] + "', valid4max = '"+ bal[3][1] + "' WHERE name = '" + id + "';");
            db.close();
        }
    }

    public String ValidResult(String id) {
        // 읽기가 가능하게 DB 열기
        SQLiteDatabase db = getReadableDatabase();
        String balanceresult = "";

        // DB에 있는 데이터를 쉽게 처리하기 위해 Cursor를 사용하여 테이블에 있는 모든 데이터 출력
        Cursor cursor = db.rawQuery("SELECT * FROM VALIDBOX WHERE name = '" + id + "';", null);
        while (cursor.moveToNext()) {
            balanceresult +=
                    "ID : "
                    + cursor.getString(1) +"\n"
                    + "1구간최소:"
                    + cursor.getDouble(2) +"\n"
                    + "1구간최대:"
                    + cursor.getDouble(3) +"\n"
                    + "2구간최소:"
                    + cursor.getDouble(4) +"\n"
                    + "2구간최대:"
                    + cursor.getDouble(5) +"\n"
                    + "3구간최소:"
                    + cursor.getDouble(6) +"\n"
                    + "3구간최대:"
                    + cursor.getDouble(7) +"\n"
                    + "4구간최소:"
                    + cursor.getDouble(8) +"\n"
                    + "4구간최대:"
                    + cursor.getDouble(9)
                    + "\n";
        }
        return balanceresult;
    }
}
