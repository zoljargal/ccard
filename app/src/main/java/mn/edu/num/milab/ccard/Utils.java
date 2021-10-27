package mn.edu.num.milab.ccard;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListAdapter;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by zoloo on 6/5/17.
 */
public class Utils {

    public enum LANG{
        MNG,
        ENG,
        MNG_ENG
    };

    public static void setDynamicWidth(GridView gridView) {
        ListAdapter gridViewAdapter = gridView.getAdapter();
        if (gridViewAdapter == null) {
            return;
        }
        int totalWidth;
        int items = gridViewAdapter.getCount();
        gridView.setNumColumns(items);
        View listItem = gridViewAdapter.getView(0, null, gridView);
        listItem.measure(0, 0);
        totalWidth = listItem.getMeasuredWidth();
        totalWidth = totalWidth*items;
        ViewGroup.LayoutParams params = gridView.getLayoutParams();
        params.width = totalWidth;
        gridView.setLayoutParams(params);
    }

    /**
     * Retrieves Bitmap from Asset
     * @param strName
     * @return
     * @throws IOException
     */
    public static Bitmap getBitmapFromAsset(String strName, AssetManager assetManager) throws IOException {
        InputStream istr = assetManager.open(strName);
        Bitmap bitmap = BitmapFactory.decodeStream(istr);
        istr.close();
        return bitmap;
    }

    public static Bitmap getBitmapFromPath(String imagePath) {
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        Bitmap bitmap = BitmapFactory.decodeFile(imagePath,bmOptions);
        return bitmap;
    }

    public static LANG currentLang=LANG.MNG_ENG;
    public static LANG getLang() {
        return currentLang;
    }

    public static String capitalize(final String line) {
        return Character.toUpperCase(line.charAt(0)) + line.substring(1);
    }
}
