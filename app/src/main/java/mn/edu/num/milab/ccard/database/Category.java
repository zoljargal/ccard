package mn.edu.num.milab.ccard.database;

import mn.edu.num.milab.ccard.Utils;

/**
 * Created by zoloo on 6/2/17.
 */
public class Category {
    private long id;
    private String mng;
    private String eng;
    private String image;
    private int builtIn;

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
        this.mng = mng;
    }

    public String getEng() {
        return eng;
    }

    public void setEng(String eng) {
        this.eng = eng;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isBuiltin() {
        return builtIn!=0;
    }
    public void setBuiltIn(int builtIn){ this.builtIn = builtIn; }

    @Override
    public String toString() {
        if(Utils.getLang()==Utils.LANG.ENG){
            return this.eng;
        }
        return mng;
    }

    public String getText() {
        if(Utils.currentLang== Utils.LANG.MNG_ENG)
            return getMng()+" / "+getEng();
        else if(Utils.currentLang== Utils.LANG.ENG)
            return getEng();
        else
            return getMng();

    }
}
