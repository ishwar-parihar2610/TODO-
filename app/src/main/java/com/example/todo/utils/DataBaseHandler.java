package com.example.todo.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.example.todo.Modal.ListModel;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHandler extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String NAME = "todoListDatabase";
    private static final String TODO_TABLE = "todo";
    private static final String ID = "id";
    private static final String TASK = "task";
    private static final String DESCRIPTION = "description";
    private static final String  CREATE_TODO_TABLE="CREATE TABLE "+TODO_TABLE + "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + TASK + " TEXT," + DESCRIPTION + " TEXT)";

    private SQLiteDatabase database;

    public DataBaseHandler(Context context) {
        super(context, NAME, null, VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(CREATE_TODO_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        database.execSQL("DROP TABLE IF EXISTS " + TODO_TABLE);
        onCreate(database);
    }

   /* public void openDataBase() {
        database = this.getWritableDatabase();
    }

    public void insertTask(ListModel listModel) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(TASK, listModel.getTask());
        contentValues.put(DESCRIPTION,listModel.getDescription());
        database.insert(TODO_TABLE, null, contentValues);

    }

    public List<ListModel> getAllTask() {
        List<ListModel> taskList=new ArrayList<>();
        Cursor cursor = null;
        database.beginTransaction();
        try {
            cursor = database.query(TODO_TABLE, null, null, null, null, null, null, null);
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    do {
                        ListModel listModel = new ListModel();
                        listModel.setId(cursor.getInt(cursor.getColumnIndex(ID)));
                        listModel.setTask(cursor.getString(cursor.getColumnIndex(TASK)));
                        listModel.setDescription(cursor.getString(cursor.getColumnIndex(DESCRIPTION)));
                        taskList.add(listModel);
                    } while (cursor.moveToNext());
                }else{
                    Log.e("value","iss null ");
                }
            }
        } finally {
            database.endTransaction();
            cursor.close();
        }
        return taskList;
    }

    public void updateTask(int id, String task,String description) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(TASK, task);
        contentValues.put(DESCRIPTION, description);
        database.update(TODO_TABLE, contentValues, ID + "=?", new String[]{String.valueOf(id)});

    }

    public void deleteTask(int id){
    database.delete(TODO_TABLE,ID + "=?",new String[]{String.valueOf(id)});
    }*/

}
