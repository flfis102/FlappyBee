package com.example.firstgame;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class myDbAdapter {
    myDbHelper myhelper;

    public myDbAdapter(Context context) {
        myhelper = new myDbHelper(context);
    }

    public long insertData(String name, String date, String score)
    {
        SQLiteDatabase dbb = myhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(myDbHelper.NAME, name);
        contentValues.put(myDbHelper.DATE, date);
        contentValues.put(myDbHelper.SCORE, score);
        long id = dbb.insert(myDbHelper.TABLE_NAME, null , contentValues);
        return id;
    }


    public void insertFirst()
    {
        SQLiteDatabase dbb = myhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(myDbHelper.UID, 0);
        contentValues.put(myDbHelper.NAME, "First");
        contentValues.put(myDbHelper.DATE, "1.1.1");
        contentValues.put(myDbHelper.SCORE, "0");
        long id = dbb.insert(myDbHelper.TABLE_NAME, null , contentValues);
    }

    public  void delete()
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        db.delete(myDbHelper.TABLE_NAME,null,null);

    }

    public String getName(){
        SQLiteDatabase db = myhelper.getWritableDatabase();
        String[] columns = {myDbHelper.UID,myDbHelper.NAME,myDbHelper.DATE,myDbHelper.SCORE};
        Cursor cursor =db.query(myDbHelper.TABLE_NAME,columns,null,null,null,null,myDbHelper.UID+" DESC");
        StringBuffer buffer= new StringBuffer();
        int count = 0;
        while (cursor.moveToNext() && count < 10)
        {
            int cid =cursor.getInt(cursor.getColumnIndex(myDbHelper.UID));
            if(cid != 0) {
                String name = cursor.getString(cursor.getColumnIndex(myDbHelper.NAME));
                buffer.append(count + 1 + ": " + name + "\n");
                count++;
            }
        }
        return buffer.toString();
    }

    public int getHighscore()
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        String[] columns = {myDbHelper.UID,myDbHelper.NAME,myDbHelper.DATE,myDbHelper.SCORE};
        Cursor cursor =db.query(myDbHelper.TABLE_NAME,columns,null,null,null,null,null);
        StringBuffer buffer= new StringBuffer();
        int highscore = 0;
        while (cursor.moveToNext())
        {
            int cid =cursor.getInt(cursor.getColumnIndex(myDbHelper.UID));
            String score =cursor.getString(cursor.getColumnIndex(myDbHelper.SCORE));
            if(Integer.parseInt(score) > highscore) {
               highscore = Integer.parseInt(score);
            }
        }
        return highscore;
    }

    public String getScore()
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        String[] columns = {myDbHelper.UID,myDbHelper.NAME,myDbHelper.DATE,myDbHelper.SCORE};
        Cursor cursor =db.query(myDbHelper.TABLE_NAME,columns,null,null,null,null,myDbHelper.UID+" DESC");
        StringBuffer buffer= new StringBuffer();
        int count = 0;
        while (cursor.moveToNext() && count < 10)
        {
            int cid = cursor.getInt(cursor.getColumnIndex(myDbHelper.UID));
            if(cid != 0) {
                String score = cursor.getString(cursor.getColumnIndex(myDbHelper.SCORE));
                buffer.append("Score: " + score + "\n");
                count++;
            }
        }
        return buffer.toString();
    }

    public String getScoreById(long id)
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        Cursor cursor = db.query(myDbHelper.TABLE_NAME, new String[] { myDbHelper.UID,
                        myDbHelper.SCORE}, myDbHelper.UID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        StringBuffer buffer= new StringBuffer();
        if (cursor != null) {
            cursor.moveToFirst();
        }
            String score =cursor.getString(cursor.getColumnIndex(myDbHelper.SCORE));
            buffer.append(score);

        return buffer.toString();
    }

    public String getDateById(long id)
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        Cursor cursor = db.query(myDbHelper.TABLE_NAME, new String[] { myDbHelper.UID,
                        myDbHelper.DATE}, myDbHelper.UID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        StringBuffer buffer= new StringBuffer();
        if (cursor != null) {
            cursor.moveToFirst();
        }
        String date =cursor.getString(cursor.getColumnIndex(myDbHelper.DATE));
        buffer.append(date);

        return buffer.toString();
    }

    public String getNameById(long id)
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        Cursor cursor = db.query(myDbHelper.TABLE_NAME, new String[] { myDbHelper.UID,
                        myDbHelper.NAME}, myDbHelper.UID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        StringBuffer buffer= new StringBuffer();
        if (cursor != null) {
            cursor.moveToFirst();
        }
        String name =cursor.getString(cursor.getColumnIndex(myDbHelper.NAME));
        buffer.append(name);

        return buffer.toString();
    }

    public String getDate()
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        String[] columns = {myDbHelper.UID,myDbHelper.NAME,myDbHelper.DATE,myDbHelper.SCORE};
        Cursor cursor =db.query(myDbHelper.TABLE_NAME,columns,null,null,null,null,myDbHelper.UID+" DESC");
        StringBuffer buffer= new StringBuffer();
        int count = 0;
        while (cursor.moveToNext() && count < 10)
        {
            int cid =cursor.getInt(cursor.getColumnIndex(myDbHelper.UID));
            if(cid != 0) {
                String date = cursor.getString(cursor.getColumnIndex(myDbHelper.DATE));
                buffer.append(date + "\n");
                count++;
            }
        }
        return buffer.toString();
    }

    public String getData()
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        String[] columns = {myDbHelper.UID,myDbHelper.NAME,myDbHelper.DATE,myDbHelper.SCORE};
        Cursor cursor =db.query(myDbHelper.TABLE_NAME,columns,null,null,null,null,null);
        StringBuffer buffer= new StringBuffer();
        while (cursor.moveToNext())
        {
            int cid =cursor.getInt(cursor.getColumnIndex(myDbHelper.UID));
            String name =cursor.getString(cursor.getColumnIndex(myDbHelper.NAME));
            String date =cursor.getString(cursor.getColumnIndex(myDbHelper.DATE));
            String score =cursor.getString(cursor.getColumnIndex(myDbHelper.SCORE));
            buffer.append(cid + " " + name + "   " + date + "   " + score +" \n");
        }
        return buffer.toString();
    }

    public long getHighscoreId() {

        SQLiteDatabase db = myhelper.getWritableDatabase();
        String[] columns = {myDbHelper.UID,myDbHelper.NAME,myDbHelper.DATE,myDbHelper.SCORE};
        Cursor cursor =db.query(myDbHelper.TABLE_NAME,columns,null,null,null,null,null);
        StringBuffer buffer= new StringBuffer();
        int highscore = 0;
        long highscoreId = 0;
        while (cursor.moveToNext())
        {
            int cid =cursor.getInt(cursor.getColumnIndex(myDbHelper.UID));
            String score =cursor.getString(cursor.getColumnIndex(myDbHelper.SCORE));
            if(Integer.parseInt(score) > highscore) {
                highscore = Integer.parseInt(score);
                highscoreId = cid;
            }
        }
        return highscoreId;

    }

    static class myDbHelper extends SQLiteOpenHelper {
        private static final String DATABASE_NAME = "myDatabase";    // Database Name
        private static final String TABLE_NAME = "myTable";   // Table Name
        private static final int DATABASE_Version = 1;   // Database Version
        private static final String UID = "_id";     // Column I (Primary Key)
        private static final String NAME = "Name";    //Column II
        private static final String DATE = "Date";    // Column III
        private static final String SCORE = "Score";
        private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME +
                " (" + UID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + NAME + " VARCHAR(255) ," + DATE + " VARCHAR(225), " + SCORE + " VARCHAR(255));";
        private static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
        private Context context;

        public myDbHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_Version);
            this.context = context;
        }

        public void onCreate(SQLiteDatabase db) {

            try {
                db.execSQL(CREATE_TABLE);
            } catch (Exception e) {
                Message.message(context, "" + e);
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            try {
                Message.message(context, "OnUpgrade");
                db.execSQL(DROP_TABLE);
                onCreate(db);
            } catch (Exception e) {
                Message.message(context, "" + e);
            }
        }
    }
}
