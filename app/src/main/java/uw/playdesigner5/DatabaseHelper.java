package uw.playdesigner5;

/**
 *   Database Helper Call used to create , upgrade database , create table and perform CRUD operations
 *
 */

// see http://techlovejump.com/android-sqlite-database-tutorial/

import java.util.ArrayList;
import java.util.List;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Name
    public static String DATABASE_NAME = "play_database";

    // Current version of database
    private static final int DATABASE_VERSION = 1;

    // Name of table
    private static final String TABLE_PLAYS = "table_plays";

    // All Keys used in table
    private static final String KEY_INDEX = "index_num";
    private static final String KEY_PLAYER_ID = "player_id";
    private static final String KEY_POSITION_X = "position_x";
    private static final String KEY_POSITION_Y = "position_y";

    public static String TAG = "tag";

    // Table Create Query
    /*
     * CREATE TABLE postions
     */
    private static final String CREATE_TABLE_PLAYS = "CREATE TABLE "
            + TABLE_PLAYS + " ( "
            + KEY_PLAYER_ID + " INTEGER , "
            + KEY_INDEX + " INTEGER PRIMARY KEY AUTOINCREMENT , "
            + KEY_POSITION_X + " INTEGER , "
            + KEY_POSITION_Y + " INTEGER );";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /*
     * This method is called by system if the database is accessed but not yet
     * created.
     */

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_PLAYS); // create students table
    }


    /*
     * This method is called when any modifications in database are done like
     * version is updated or database schema is changed
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLAYS); // drop table if exists
        onCreate(db);
        //db.execSQL("DROP TABLE IF EXISTS " + CREATE_TABLE_POSITIONS); // drop table if exists
    }

    /*
     *
     * This method is used to add position detail in table
     */

    public long addPositionDetail(PlayModel play) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Creating content values
        ContentValues values = new ContentValues();
        //values.put(KEY_INDEX, play.index);
        values.put(KEY_PLAYER_ID, play.player_id);
        values.put(KEY_POSITION_X, play.position_x);
        values.put(KEY_POSITION_Y, play.position_y);

        // insert row in students table

        long insert = db.insert(TABLE_PLAYS, null, values);

        return insert;
    }

    /*
     * This method is used to update particular student entry
     *
     * @param student
     * @return
     */

    public int updateEntry(PlayModel play) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Creating content values
        ContentValues values = new ContentValues();
        //values.put(KEY_INDEX, play.index);
        values.put(KEY_PLAYER_ID, play.player_id);
        values.put(KEY_POSITION_X, play.position_x);
        values.put(KEY_POSITION_Y, play.position_y);

        // update row in students table base on students.is value

        return db.update(TABLE_PLAYS, values, KEY_INDEX + " = ?",
                new String[] { String.valueOf(play.player_id) });
    }

    /*
     * Used to delete particular student entry
     *
     * @param id
     */

    public void deleteEntry(long id) {

        // delete row in students table based on id
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PLAYS, KEY_INDEX + " = ?",
                new String[] { String.valueOf(id) });
    }

    /*
     * Used to get particular student details
     *
     * @param id
     * @return
     */
    /*
    public PlayModel getStudent(long id) {
        SQLiteDatabase db = this.getReadableDatabase();

        // SELECT * FROM students WHERE id = ?;
        String selectQuery = "SELECT  * FROM " + TABLE_STUDENTS + " WHERE "
                + KEY_ID + " = " + id;
        Log.d(TAG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        StudentsModel students = new StudentsModel();
        students.id = c.getInt(c.getColumnIndex(KEY_ID));
        students.phone_number = c.getString(c.getColumnIndex(KEY_PHONENUMBER));
        students.name = c.getString(c.getColumnIndex(KEY_NAME));

        return students;
    }
    */

    /*
     * Used to get detail of entire database and save in array list of data type
     * StudentsModel
     *
     * @return
     */


    public List<PlayModel> getPositionList() {
        List<PlayModel> positionArrayList = new ArrayList<PlayModel>();

        String selectQuery = "SELECT  * FROM " + TABLE_PLAYS;
        Log.d(TAG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {

                PlayModel play = new PlayModel();
                play.index = c.getInt(c.getColumnIndex(KEY_INDEX));
                play.position_x = c.getInt(c
                        .getColumnIndex(KEY_POSITION_X));
                play.position_y = c.getInt(c
                        .getColumnIndex(KEY_POSITION_Y));
                play.player_id = c.getInt(c.getColumnIndex(KEY_PLAYER_ID));

                // adding to Students list
                positionArrayList.add(play);
            } while (c.moveToNext());
        }

        return positionArrayList;
    }

}
