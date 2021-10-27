package mn.edu.num.milab.ccard;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.LinearLayout;

import org.apmem.tools.layouts.FlowLayout;

import java.io.IOException;
import java.util.HashMap;

import mn.edu.num.milab.ccard.letter.WordViewItem;

public class WriteLetterActivity extends AppCompatActivity {
    private EditText mWriteText;
    private FlowLayout mWordList;
    private

    HashMap<Character, String> letterMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_letter);

        mWordList = (FlowLayout) findViewById(R.id.sign_word_list);

        mWriteText = (EditText) findViewById(R.id.write_text);
        mWriteText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                mWordList.removeAllViews();
                for(int i=0; i<editable.length(); i++){
                    WordViewItem item = new WordViewItem(WriteLetterActivity.this);
                    try {
                        item.setLetterImage(getLetterSign(editable.charAt(i)));
                        mWordList.addView(item);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        });



    }

    private Bitmap getLetterSign(Character c) throws IOException {
        c = Character.toLowerCase(c);
        if(letterMap==null){
            letterMap = new HashMap<>();
            letterMap.put('а', "1.png");
            letterMap.put('б', "2.png");
            letterMap.put('в', "3.png");
            letterMap.put('г', "4.png");
            letterMap.put('д', "5.png");
            letterMap.put('е', "6.png");
            letterMap.put('ё', "7.png");
            letterMap.put('ж', "8.png");
            letterMap.put('з', "9.png");
            letterMap.put('и', "10.png");
            letterMap.put('й', "11.png");
            letterMap.put('к', "12.png");
            letterMap.put('л', "13.png");
            letterMap.put('м', "14.png");
            letterMap.put('н', "15.png");
            letterMap.put('о', "16.png");
            letterMap.put('ө', "17.png");
            letterMap.put('п', "18.png");
            letterMap.put('р', "19.png");
            letterMap.put('с', "20.png");
            letterMap.put('т', "21.png");
            letterMap.put('у', "22.png");
            letterMap.put('ү', "23.png");
            letterMap.put('ф', "24.png");
            letterMap.put('х', "25.png");
            letterMap.put('ц', "26.png");
            letterMap.put('ч', "27.png");
            letterMap.put('ш', "28.png");
            letterMap.put('щ', "29.png");
            letterMap.put('ъ', "30.png");
            letterMap.put('ы', "31.png");
            letterMap.put('ь', "32.png");
            letterMap.put('э', "33.png");
            letterMap.put('ю', "34.png");
            letterMap.put('я', "35.png");
        }

        String imagePath = letterMap.get(c);
        if(imagePath==null)
            return null;
        return Utils.getBitmapFromAsset("letters/"+imagePath, getAssets());

    }
}
