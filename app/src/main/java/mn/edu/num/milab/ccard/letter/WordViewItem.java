package mn.edu.num.milab.ccard.letter;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import org.apmem.tools.layouts.FlowLayout;

import mn.edu.num.milab.ccard.R;

/**
 * Created by zoloo on 7/6/17.
 */
public class WordViewItem extends RelativeLayout {
    public WordViewItem(Context context) {
        super(context);
        init();
    }

    public WordViewItem(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    public WordViewItem(Context context, AttributeSet attributeSet, int defStyle) {
        super(context, attributeSet, defStyle);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.word_view_item, this);
    }

    public void setLetterImage(Bitmap bitmap){
        ImageView iv = (ImageView) findViewById(R.id.word_view_image);
        iv.setImageBitmap(bitmap);
    }
}
