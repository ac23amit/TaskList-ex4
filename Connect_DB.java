package com.ac23amit.tasklist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by pc on 05/11/13.
 */
public class Connect_DB extends SQLiteOpenHelper
{
    private static Connect_DB ourInstance = null;
    private Context context;
    private ArrayList<ItemDetails> itemsArr = null;
    private static final int DB_VER =1;
    private static final String DB_NAME ="Tasklist_Db";
    private static final String TABLE_TASKLIST ="Tasklist_Tbl";
    //tasklist col names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_ACT_ST = "status";
    private static final String KEY_PRICE = "price";


    private Connect_DB (Context context)
    {
        super(context, DB_NAME, null, DB_VER);
        this.context = context;
        itemsArr = new ArrayList<ItemDetails>();
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
      String CREATE_TABLE_TASKLIST ="CREATE TABLE" + TABLE_TASKLIST + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT ," + KEY_ACT_ST + " TEXT ," + KEY_PRICE + " TEXT" + ")";//
      try
      {
       db.execSQL (CREATE_TABLE_TASKLIST);
      }
          catch (Exception e)
        {
            Toast.makeText(this.context, "cant do", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade (SQLiteDatabase db, int oldVersion, int newVersion )
    {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASKLIST);
        onCreate(db);
    }

    public int getSize ()
    {
        return itemsArr.size();
    }

    public void addItem (ItemDetails ItemP)
    {
        SQLiteDatabase db = null;
        try
        {
            db= this.getWritableDatabase();
            ContentValues values= new ContentValues();
            values.put(KEY_NAME, ItemP.getName());
            values.put(KEY_ACT_ST, ItemP.getbtnText());
            values.put(KEY_PRICE, ItemP.getPrice());
            db.insert(TABLE_TASKLIST, null, values);
            itemsArr.add(ItemP);
        }
        catch (Exception e)
        {
            Toast.makeText(this.context, "Error", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
        finally
        {
            db.close();

        }
    }
    public long getElmID (int position)
    {
        return this.getElm(position).getId();
    }

    public void populateItemsArr ()
    {
        String selectQuery = "SELECT  * FROM " + TABLE_TASKLIST;
        Cursor cursor = null;
        SQLiteDatabase db = this.getWritableDatabase();
        try
        {
            cursor = db.rawQuery(selectQuery, null);
        }
        catch (Exception e)
        {
            Toast.makeText(this.context, "Error", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

        if (cursor.moveToFirst())
        {
            do
            {
                ItemDetails itemDetails = new ItemDetails(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), cursor.getString(3));//
                itemsArr.add(itemDetails);
            }
            while (cursor.moveToNext());
        }
        cursor.close();

    }



    public ArrayList<ItemDetails> getItems ()
    {
        return itemsArr;
    }

    public ItemDetails getElm (int position) { return itemsArr.get(position); }

    public void deleteElm (int position )
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TASKLIST, KEY_ID + " = ?", new String[]{String.valueOf(this.getElmID(position))});
        itemsArr.remove(position);

        db.close();

    }
    public static synchronized Connect_DB getInstance (Context context)
    {
        if (ourInstance == null) ourInstance = new Connect_DB(context);
        return ourInstance;
    }

}

