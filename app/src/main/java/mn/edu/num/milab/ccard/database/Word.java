package mn.edu.num.milab.ccard.database;

import android.graphics.Bitmap;

import mn.edu.num.milab.ccard.Utils;

/**
 * Created by zoloo on 6/2/17.
 */
public class Word {
    private long id;
    private String mng;
    private String eng;
    private String image;
    private String signImage;
    private int builtIn;
    private int favourite;
    private long categoryId;

    private Bitmap bitmap;
    private Category category;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMng() {
        return mng;
    }

    public void setMng(String mng) {
        if(mng!=null)
            mng = Utils.capitalize(mng.toLowerCase().trim());
        this.mng = mng;
    }

    public String getEng() {
        return eng;
    }

    public void setEng(String eng) {
        if(eng!=null)
            eng = Utils.capitalize(eng.toLowerCase().trim());

        this.eng = eng;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isBuiltIn() {
        return builtIn!=0;
    }

    public boolean isFavourite() {
        return favourite!=0;
    }

    public void setFavourite(int favourite) {
        this.favourite = favourite;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    public void setBuiltIn(int builtIn) {
        this.builtIn = builtIn;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public void setCategoryId(Category selectedItem) {
        this.categoryId = selectedItem.getId();
        this.category = selectedItem;
    }

    public String getSignImage() {
        return signImage;
    }

    public void setSignImage(String signImage) {
        this.signImage = signImage;
    }

    public String getText() {
        if(Utils.currentLang== Utils.LANG.MNG_ENG)
            return getMng()+"\n"+getEng();
        else if(Utils.currentLang== Utils.LANG.ENG)
            return getEng();
        else
            return getMng();

    }
}
