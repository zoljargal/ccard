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
            letterMap.put('??', "1.png");
            letterMap.put('??', "2.png");
            letterMap.put('??', "3.png");
            letterMap.put('??', "4.png");
            letterMap.put('??', "5.png");
            letterMap.put('??', "6.png");
            letterMap.put('??', "7.png");
            letterMap.put('??', "8.png");
            letterMap.put('??', "9.png");
            letterMap.put('??', "10.png");
            letterMap.put('??', "11.png");
            letterMap.put('??', "12.png");
            letterMap.put('??', "13.png");
            letterMap.put('??', "14.png");
            letterMap.put('??', "15.png");
            letterMap.put('??', "16.png");
            letterMap.put('??', "17.png");
            letterMap.put('??', "18.png");
            letterMap.put('??', "19.png");
            letterMap.put('??', "20.png");
            letterMap.put('??', "21.png");
            letterMap.put('??', "22.png");
            letterMap.put('??', "23.png");
            letterMap.put('??', "24.png");
            letterMap.put('??', "25.png");
            letterMap.put('??', "26.png");
            letterMap.put('??', "27.png");
            letterMap.put('??', "28.png");
            letterMap.put('??', "29.png");
            letterMap.put('??', "30.png");
            letterMap.put('??', "31.png");
            letterMap.put('??', "32.png");
            letterMap.put('??', "33.png");
            letterMap.put('??', "34.png");
            letterMap.put('??', "35.png");
        }

        String imagePath = letterMap.get(c);
        if(imagePath==null)
            return null;
        return Utils.getBitmapFromAsset("letters/"+imagePath, getAssets());

    }
}
