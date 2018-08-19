package arami265.github.io.flashcards;

import android.app.LauncherActivity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by arami265
 *
 * This class provides functionality for
 * reading and writing to the local
 * database.
 */

public class dbAdapter {

    dbHelper helper;
    private List<FieldListItem> fieldListItems;
    private List<String> fieldListStrings;
    private List<ClassListItem> classListItems;
    private classDetailItem currentClassDetailItem;
    private List<DeckListItem> deckListItems;
    private List<cardItem> cardItems;

    public dbAdapter(Context context)
    {
        helper = new dbHelper(context);
    }

    //Change this to long
    public long insertFieldName(String name)
    {
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put("FNAME", name);
        long id = db.insert("FIELD_TABLE", null, cv);
        return id;
    }

    public long insertClass(String name, String field, String time, String room, String instructor, String description)
    {
        SQLiteDatabase db = helper.getWritableDatabase();

        //Get FID
        String[] columns = {"FNAME", "FID"};
        Cursor cursor = db.query("FIELD_TABLE", columns, "FNAME=?", new String[] { field }, null, null, null);


        //fieldListStrings = new ArrayList<String>();
        //StringBuffer buffer = new StringBuffer();

        int fid=-1;

        if( cursor != null && cursor.moveToFirst() )
        {
            fid = cursor.getInt(cursor.getColumnIndex("FID"));
            cursor.close();
        }
        else
            return -1;

        /*while(cursor.moveToNext())
        {

            int index1 = cursor.getColumnIndex("FID");

            fid = cursor.getInt(index1);
            //buffer.append(fid + " " + fname + "\n");
            //String temp = fid + " " + fname;
        }*/


        ContentValues cv = new ContentValues();
        cv.put("FID", fid);
        cv.put("CLNAME", name);
        cv.put("CLDESCRIPTION", description);
        cv.put("CLROOM", room);
        cv.put("CLTIME", time);
        cv.put("CLINSTRUCTOR", instructor);




        long id = db.insert("COURSE_TABLE", null, cv);
        return id;
    }

    public void deleteClass(int clid)
    {
        SQLiteDatabase db = helper.getWritableDatabase();

        String[] columns = {"DID"};
        //Cursor cursor = db.query("DECK_TABLE", columns, null, null, null, null, null);
        String tempStr = String.valueOf(clid);
        Cursor cursor = db.query("DECK_TABLE", columns, "CLID=?", new String[] {tempStr} , null, null, null);


        try {
            while (cursor.moveToNext()) {

                int index = cursor.getColumnIndex("DID");

                int did = cursor.getInt(index);
                deleteDeck(did);
            }
        }
        finally {
            cursor.close();
        }

        db.delete("COURSE_TABLE", "CLID=?", new String[]{String.valueOf(clid)});
    }

    public long insertDeck(String name, String description, int currentClid)
    {
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put("DNAME", name);
        cv.put("DDESCRIPTION", description);
        cv.put("CLID", currentClid);


        long id = db.insert("DECK_TABLE", null, cv);
        return id;
    }

    public void deleteDeck(int currentDid)
    {
        SQLiteDatabase db = helper.getWritableDatabase();

        //db.execSQL("DELETE FROM COURSE_TABLE WHERE CLNAME = "+className);
        //db.delete("COURSE_TABLE", "CLNAME" + "=" + className, null);

        String[] columns = {"CID"};
        //Cursor cursor = db.query("DECK_TABLE", columns, null, null, null, null, null);
        String tempStr = String.valueOf(currentDid);
        Cursor cursor = db.query("CARD_TABLE", columns, "DID=?", new String[] {tempStr} , null, null, null);

        try {
            while (cursor.moveToNext()) {

                int index = cursor.getColumnIndex("CID");
                int cid = cursor.getInt(index);

                db.delete("CARD_TABLE", "CID=?", new String[]{String.valueOf(cid)});
            }
        }
        finally {
            cursor.close();
        }

        db.delete("DECK_TABLE", "DID=?", new String[]{String.valueOf(currentDid)});
    }

    public long insertCard(int did, String ctitle, String ctext)
    {
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put("DID", did);
        if(ctitle != "")
            cv.put("CTITLE", ctitle);
        cv.put("CTEXT", ctext);
        cv.put("COMP", -1);


        long id = db.insert("CARD_TABLE", null, cv);
        return id;
    }

    public void deleteCard(int cid)
    {
        SQLiteDatabase db = helper.getWritableDatabase();

        //db.execSQL("DELETE FROM COURSE_TABLE WHERE CLNAME = "+className);
        //db.delete("COURSE_TABLE", "CLNAME" + "=" + className, null);
        db.delete("CARD_TABLE", "CID=?", new String[]{String.valueOf(cid)});
    }

    public List<cardItem> getCards(int currentDid)
    {
        SQLiteDatabase db = helper.getWritableDatabase();

        String[] columns = {"CID", "DID", "CTITLE", "CTEXT", "COMP" };
        //Cursor cursor = db.query("DECK_TABLE", columns, null, null, null, null, null);
        String tempStr = String.valueOf(currentDid);
        Cursor cursor = db.query("CARD_TABLE", columns, "DID=?", new String[] {tempStr} , null, null, null);


        cardItems = new ArrayList<>();
        //StringBuffer buffer = new StringBuffer();
        while(cursor.moveToNext())
        {

            int index1 = cursor.getColumnIndex("CTITLE");
            int index2 = cursor.getColumnIndex("CTEXT");
            int index3 = cursor.getColumnIndex("COMP");
            int index4 = cursor.getColumnIndex("CID");

            String ctitle = cursor.getString(index1);
            String ctext = cursor.getString(index2);
            float comp = cursor.getFloat(index3);
            int cid = cursor.getInt(index4);
            //buffer.append(fid + " " + fname + "\n");
            //String temp = fid + " " + fname;

            cardItem tempItem = new cardItem(cid, ctitle, ctext, comp);

            cardItems.add(tempItem);
        }

        //return buffer.toString();
        cursor.close();
        return cardItems;
    }

    public List<FieldListItem> getFieldNames()
    {
        SQLiteDatabase db = helper.getWritableDatabase();

        String[] columns = {"FID", "FNAME"};
        Cursor cursor = db.query("FIELD_TABLE", columns, null, null, null, null, null);


        fieldListItems = new ArrayList<>();
        //StringBuffer buffer = new StringBuffer();
        while(cursor.moveToNext())
        {

            int index1 = cursor.getColumnIndex("FID");
            int index2 = cursor.getColumnIndex("FNAME");

            int fid = cursor.getInt(index1);
            String fname = cursor.getString(index2);
            //buffer.append(fid + " " + fname + "\n");
            //String temp = fid + " " + fname;

            FieldListItem tempItem = new FieldListItem(fname);

            fieldListItems.add(tempItem);
        }

        //return buffer.toString();
        cursor.close();
        return fieldListItems;
    }

    public List<String> getFieldNamesAsStrings()
    {
        SQLiteDatabase db = helper.getWritableDatabase();

        String[] columns = {"FID", "FNAME"};
        Cursor cursor = db.query("FIELD_TABLE", columns, null, null, null, null, null);


        fieldListStrings = new ArrayList<String>();
        //StringBuffer buffer = new StringBuffer();
        while(cursor.moveToNext())
        {

            int index1 = cursor.getColumnIndex("FID");
            int index2 = cursor.getColumnIndex("FNAME");

            int fid = cursor.getInt(index1);
            String fname = cursor.getString(index2);
            //buffer.append(fid + " " + fname + "\n");
            //String temp = fid + " " + fname;

            fieldListStrings.add(fname);
        }

        //return buffer.toString();
        cursor.close();
        return fieldListStrings;
    }

    public List<ClassListItem> getClasses()
    {
        SQLiteDatabase db = helper.getWritableDatabase();

        String[] columns = {"CLID", "CLNAME"};
        Cursor cursor = db.query("COURSE_TABLE", columns, null, null, null, null, null);


        classListItems = new ArrayList<>();
        //StringBuffer buffer = new StringBuffer();
        while(cursor.moveToNext())
        {

            int index1 = cursor.getColumnIndex("CLNAME");
            int index2 = cursor.getColumnIndex("CLID");

            String clname = cursor.getString(index1);
            int clid = cursor.getInt(index2);
            //buffer.append(fid + " " + fname + "\n");
            //String temp = fid + " " + fname;

            ClassListItem tempItem = new ClassListItem(clname, clid);

            classListItems.add(tempItem);
        }

        //return buffer.toString();
        cursor.close();
        return classListItems;
    }

    public classDetailItem getClassDetail(int currentClid)
    {
        SQLiteDatabase db = helper.getWritableDatabase();

        String[] columns = {"CLID", "FID", "CLNAME", "CLDESCRIPTION", "CLROOM", "CLTIME", "CLINSTRUCTOR"};

        String tempStr = String.valueOf(currentClid);
        Cursor cursor = db.query("COURSE_TABLE", columns, "CLID=?", new String[] {tempStr}, null, null, null);


        //StringBuffer buffer = new StringBuffer();

            int index1 = cursor.getColumnIndex("CLNAME");
            int index2 = cursor.getColumnIndex("FID");
            int index3 = cursor.getColumnIndex("CLDESCRIPTION");
            int index4 = cursor.getColumnIndex("CLROOM");
            int index5 = cursor.getColumnIndex("CLTIME");
            int index6 = cursor.getColumnIndex("CLINSTRUCTOR");

            String clname = cursor.getString(index1);
            int fid = cursor.getInt(index2);
            String description = cursor.getString(index1);
            String room = cursor.getString(index1);
            String time = cursor.getString(index1);
            String instructor = cursor.getString(index1);
            //buffer.append(fid + " " + fname + "\n");
            //String temp = fid + " " + fname;


        //Get field name
        String[] columns1 = {"FNAME", "FID"};
        Cursor cursor1 = db.query("FIELD_TABLE", columns1, "FID=?", new String[] { Integer.toString(fid) }, null, null, null);


        //fieldListStrings = new ArrayList<String>();
        //StringBuffer buffer = new StringBuffer();

        String field = "";
        if( cursor1 != null && cursor1.moveToFirst() )
        {
            field = cursor1.getString(cursor1.getColumnIndex("FNAME"));
        }
            //return -1;

        currentClassDetailItem = new classDetailItem(clname, field, description, room, time, instructor);

        //return buffer.toString();
        cursor.close();
        cursor1.close();
        return currentClassDetailItem;
    }

    public List<DeckListItem> getDecks(int currentClid)
    {
        SQLiteDatabase db = helper.getWritableDatabase();

        String[] columns = {"DNAME", "DID", "CLID" };
        //Cursor cursor = db.query("DECK_TABLE", columns, null, null, null, null, null);
        String tempStr = String.valueOf(currentClid);
        Cursor cursor = db.query("DECK_TABLE", columns, "CLID=?", new String[] {tempStr} , null, null, null);


        deckListItems = new ArrayList<>();
        //StringBuffer buffer = new StringBuffer();
        while(cursor.moveToNext())
        {

            int index1 = cursor.getColumnIndex("DNAME");
            int index2 = cursor.getColumnIndex("CLID");
            int index3 = cursor.getColumnIndex("DID");

            String dname = cursor.getString(index1);
            int clid = cursor.getInt(index2);
            int did = cursor.getInt(index3);
            //buffer.append(fid + " " + fname + "\n");
            //String temp = fid + " " + fname;

            DeckListItem tempItem = new DeckListItem(dname, did, clid);

            deckListItems.add(tempItem);
        }

        //return buffer.toString();
        cursor.close();
        return deckListItems;
    }

    public boolean isDeckEmpty(int did)
    {
        SQLiteDatabase db = helper.getWritableDatabase();

        String[] columns = {"DID"};
        //Cursor cursor = db.query("DECK_TABLE", columns, null, null, null, null, null);
        String tempStr = String.valueOf(did);
        Cursor cursor = db.query("CARD_TABLE", columns, "DID=?", new String[] {tempStr} , null, null, null);

        if (cursor.getCount() == 0)
        {
            cursor.close();
            return true;
        }
        else
        {
            cursor.close();
            return false;
        }
    }

    public int getDeckSize(int did)
    {
        SQLiteDatabase db = helper.getWritableDatabase();

        String[] columns = {"DID"};
        //Cursor cursor = db.query("DECK_TABLE", columns, null, null, null, null, null);
        String tempStr = String.valueOf(did);
        Cursor cursor = db.query("CARD_TABLE", columns, "DID=?", new String[] {tempStr} , null, null, null);
        int deckSize = cursor.getCount();

        cursor.close();

        return deckSize;
    }

    public float getDeckComp(int did)
    {
        SQLiteDatabase db = helper.getWritableDatabase();

        String[] columns = {"DID", "COMP"};
        //Cursor cursor = db.query("DECK_TABLE", columns, null, null, null, null, null);
        String tempStr = String.valueOf(did);
        Cursor cursor = db.query("CARD_TABLE", columns, "DID=?", new String[] {tempStr} , null, null, null);

        ArrayList<Float> compList = new ArrayList<Float>();

        while(cursor.moveToNext())
        {
            int index1 = cursor.getColumnIndex("COMP");

            String comp = cursor.getString(index1);
            //buffer.append(fid + " " + fname + "\n");
            //String temp = fid + " " + fname;

            compList.add(Float.parseFloat(comp));
        }

        float compSum = 0;

        for(int i=0; i< compList.size(); i++)
        {
            compSum += compList.get(i);
        }

        float compAvg = compSum / compList.size();

        cursor.close();

        return compAvg;
    }

    public void updateComp(List<cardItem> cardItems)
    {
        SQLiteDatabase db = helper.getWritableDatabase();

        for(int i = 0; i < cardItems.size(); i++)
        {
            ContentValues cv = new ContentValues();
            cv.put("COMP", cardItems.get(i).getComp());
            db.update("CARD_TABLE", cv, "CID="+cardItems.get(i).getCid(), null);
        }

        /*cv.put("DID", did);
        if(ctitle != "")
            cv.put("CTITLE", ctitle);
        cv.put("CTEXT", ctext);
        cv.put("COMP", -1);


        long id = db.insert("CARD_TABLE", null, cv);
        return id;*/
    }

    class dbHelper extends SQLiteOpenHelper
    {
        private static final String DATABASE_NAME="flashDB";
        private static final String TABLE_NAME="FLASHTABLE";
        private static final String UID = "_id";
        private static final String NAME = "Name";
        //private static final String CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+"("+UID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+NAME+" VARCHAR(255));";

        private static final String CREATE_FIELD_TABLE = "CREATE TABLE FIELD_TABLE(FID INTEGER PRIMARY KEY AUTOINCREMENT, FNAME VARCHAR(255), FDESCRIPTION VARCHAR(255));";
        private static final String CREATE_COURSE_TABLE = "CREATE TABLE COURSE_TABLE(CLID INTEGER PRIMARY KEY AUTOINCREMENT, FID INTEGER NOT NULL, CLNAME VARCHAR(255) NOT NULL, CLDESCRIPTION VARCHAR(255), CLROOM VARCHAR(255), CLTIME VARCHAR(255), CLINSTRUCTOR VARCHAR(255));";
        private static final String CREATE_DECK_TABLE = "CREATE TABLE DECK_TABLE(DID INTEGER PRIMARY KEY AUTOINCREMENT, CLID INTEGER NOT NULL, DNAME VARCHAR(255) NOT NULL, DDESCRIPTION VARCHAR(255));";
        private static final String CREATE_CARD_TABLE = "CREATE TABLE CARD_TABLE(CID INTEGER PRIMARY KEY AUTOINCREMENT, DID INTEGER NOT NULL, CTITLE VARCHAR(255), CTEXT VARCHAR(255) NOT NULL, COMP REAL);";

        private Context context;

        private static final int DATABASE_VERSION=15;

        public dbHelper(Context context)
        {

            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            this.context = context;
            //Message.message(context, "Constructor called.");
        }

        @Override
        public void onCreate(SQLiteDatabase db)
        {
            try {
                //Message.message(context, "onCreate called.");
                db.execSQL(CREATE_FIELD_TABLE);
                db.execSQL(CREATE_COURSE_TABLE);
                db.execSQL(CREATE_DECK_TABLE);
                db.execSQL(CREATE_CARD_TABLE);

                String [] fieldNames = {"Biology", "Chemistry", "Earth sciences", "Space sciences", "Physics", "Math", "Statistics", "Computer Science", "Psychology", "Sociology", "Political science", "Economics", "Law", "Anthropology", "Medicine", "Performing arts", "Visual arts", "History", "Language", "Philosophy", "Theology"};
                for(String fieldName : fieldNames)
                {
                    ContentValues cv = new ContentValues();
                    cv.put("FNAME", fieldName);
                    long id = db.insert("FIELD_TABLE", null, cv);
                }
            }catch(SQLException e) {
                Message.message(context, ""+e);
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {
            try {
                Message.message(context, "onUpgrade called.");
                db.execSQL("DROP TABLE IF EXISTS COURSE_TABLE;");
                db.execSQL("DROP TABLE IF EXISTS DECK_TABLE;");
                db.execSQL("DROP TABLE IF EXISTS CARD_TABLE;");
                db.execSQL("DROP TABLE IF EXISTS FIELD_TABLE;");
                db.execSQL(CREATE_COURSE_TABLE);
                db.execSQL(CREATE_DECK_TABLE);
                db.execSQL(CREATE_CARD_TABLE);
                db.execSQL(CREATE_FIELD_TABLE);

                String [] fieldNames = {"Biology", "Chemistry", "Earth sciences", "Space sciences", "Physics", "Math", "Statistics", "Computer Science", "Psychology", "Sociology", "Political science", "Economics", "Law", "Anthropology", "Medicine", "Performing arts", "Visual arts", "History", "Language", "Philosophy", "Theology"};
                for(String fieldName : fieldNames)
                {
                    ContentValues cv = new ContentValues();
                    cv.put("FNAME", fieldName);
                    long id = db.insert("FIELD_TABLE", null, cv);
                }

            }catch(SQLException e){
                Message.message(context, ""+e);
            }
        }
    }
}
