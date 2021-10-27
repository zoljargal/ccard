package mn.edu.num.milab.ccard.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import mn.edu.num.milab.ccard.R;

/**
 * Created by zoloo on 6/2/17.
 */
public class SqlHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "ccard.db";
    public static final String DATABASE_TABLE_WORD = "word";
    public static final String DATABASE_TABLE_CATEGORY = "category";
    private static final int DATABASE_VERSION = 1;

    private  Context context;
    public SqlHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "create table word " +
                "(id integer primary key autoincrement, " +
                "mng text not null, " +
                "eng text, " +
                "image text, " +
                "sign_image text, " +
                "built_in integer, " +
                "favourite integer, " +
                "category_id integer not null); ";
        sqLiteDatabase.execSQL(sql);
        sql = "create table category (id integer primary key autoincrement, mng text not null, eng text, image text, built_in integer); ";
        sqLiteDatabase.execSQL(sql);

        String strArray[] = context.getResources().getStringArray(R.array.categories);
        for(String c:strArray){
            String props [] = c.split(";");
            sql = "insert into category (id, mng, eng, image, built_in) values ("+props[0]+",'"+ props[1] +"', '"+props[2]+"', '"+props[3]+"', 1 );";
            sqLiteDatabase.execSQL(sql);
        }

        strArray = context.getResources().getStringArray(R.array.words);
        for(String c:strArray){
            String props [] = c.split(";");
            sql = "insert into word (mng, eng, image, sign_image, built_in, category_id) values " +
                    "('"+ props[0] +"', '"+props[1]+"', '"+props[2]+"', '"+props[3]+"', 1, "+props[4]+" );";
            sqLiteDatabase.execSQL(sql);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
