package mn.edu.num.milab.ccard.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zoloo on 6/2/17.
 */
public class Datasource {
    private SQLiteDatabase database;
    private SqlHelper dbHelper;

    public Datasource(Context context){
        dbHelper = new SqlHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Word createWord(Word word){
        ContentValues values = new ContentValues();
        values.put("mng", word.getMng());
        if(word.getEng()!=null)
            values.put("eng", word.getEng());
        values.put("image", word.getImage());
        values.put("built_in", word.isBuiltIn());
        values.put("favourite", word.isFavourite());
        values.put("category_id", word.getCategoryId());

        long wordId = database.insert(SqlHelper.DATABASE_TABLE_WORD, null, values);
        word.setId(wordId);
        return word;
    }

    public Category createCategory(Category category){
        ContentValues values = new ContentValues();
        values.put("mng", category.getMng());
        if(category.getEng()!=null)
            values.put("eng", category.getEng());
        values.put("image", category.getImage());
        values.put("built_in", category.isBuiltin());

        long wordId = database.insert(SqlHelper.DATABASE_TABLE_CATEGORY, null, values);
        category.setId(wordId);
        return category;
    }

    public List<Category> getAllCategory(){
        List<Category> categories = new ArrayList<>();
        Cursor cursor = database.query(SqlHelper.DATABASE_TABLE_CATEGORY, new String[]{"id", "mng", "eng", "image", "built_in"}
                ,null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            categories.add(cursorToCategory(cursor));

            cursor.moveToNext();
        }
        cursor.close();
        return categories;
    }


    public ArrayList<Word> getAllWord(long category_id){
        ArrayList<Word> words = new ArrayList<>();
        Cursor cursor = database.query(SqlHelper.DATABASE_TABLE_WORD,
                new String[]{"id", "mng", "eng", "image", "sign_image", "built_in", "favourite", "category_id"},
                "category_id="+category_id, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            words.add(cursorToWord(cursor));
            cursor.moveToNext();
        }
        cursor.close();
        return words;
    }


    public ArrayList<Word> getAllWord(String query) {
        ArrayList<Word> words = new ArrayList<>();
        Cursor cursor = database.query(SqlHelper.DATABASE_TABLE_WORD,
                new String[]{"id", "mng", "eng", "image", "sign_image", "built_in", "favourite", "category_id"},
                "mng like '"+query+"%' OR eng like '"+query+"%'", null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            words.add(cursorToWord(cursor));
            cursor.moveToNext();
        }
        cursor.close();
        return words;
    }

    public Word getWord(long id){
        Cursor cursor = database.query(SqlHelper.DATABASE_TABLE_WORD,
                new String[]{"id", "mng", "eng", "image", "sign_image", "built_in", "favourite", "category_id"},
                "id="+id, null, null, null, null);

        cursor.moveToFirst();
        Word w = cursorToWord(cursor);
        cursor.close();
        return w;

    }

    public boolean deleteWord(long id){
        return database.delete(SqlHelper.DATABASE_TABLE_WORD, "built_in=0 and id=?", new String[]{String.valueOf(id)})>0;
    }
    private Word cursorToWord(Cursor cursor) {
        Word w=new Word();
        w.setId(cursor.getLong(0));
        w.setMng(cursor.getString(1));
        w.setEng(cursor.getString(2));
        w.setImage(cursor.getString(3));
        w.setSignImage(cursor.getString(4));
        w.setBuiltIn(cursor.getInt(5));
        w.setFavourite(cursor.getInt(6));
        w.setCategoryId(cursor.getLong(7));
        return w;
    }

    private Category cursorToCategory(Cursor cursor){
        Category category = new Category();
        category.setId(cursor.getLong(0));
        category.setMng(cursor.getString(1));
        category.setEng(cursor.getString(2));
        category.setImage(cursor.getString(3));
        category.setBuiltIn(cursor.getInt(4));
        return category;
    }

}
