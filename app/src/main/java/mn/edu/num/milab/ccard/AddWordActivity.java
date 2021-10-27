package mn.edu.num.milab.ccard;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Random;

import mn.edu.num.milab.ccard.database.Category;
import mn.edu.num.milab.ccard.database.Datasource;
import mn.edu.num.milab.ccard.database.Word;

public class AddWordActivity extends AppCompatActivity {

    Button mButtonGallery;
    Button mButtonCamera;
    Button mButtonSave;
    Button mButtonCancel;

    EditText mEditTextWordMng;
    EditText mEditTextWordEng;
    ImageView mImageAdd;
    Spinner mSpinnerCategory;

    private static int TOAST_DURATION = 1;
    private Datasource datasource;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_word);


        mEditTextWordMng = (EditText) findViewById(R.id.editTextWordMng);
        mEditTextWordEng = (EditText) findViewById(R.id.editTextWordEng);
        mImageAdd = (ImageView) findViewById(R.id.imageAdd);
        mSpinnerCategory = (Spinner) findViewById(R.id.spinner);

        mButtonGallery = (Button) findViewById(R.id.btnGallery);
        mButtonCamera = (Button) findViewById(R.id.btnCamera);
        mButtonSave = (Button) findViewById(R.id.btnSave);
        mButtonCancel = (Button) findViewById(R.id.btnCancel);



        datasource = new Datasource(this);
        datasource.open();
        List<Category> categories = datasource.getAllCategory();

        ArrayAdapter<Category> adapter = new ArrayAdapter<Category>(this,
                R.layout.support_simple_spinner_dropdown_item, categories);


        mSpinnerCategory.setAdapter(adapter);


        mButtonGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhoto , 1);
            }
        });

        mButtonCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(takePicture, 0);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case 0:
                if(resultCode == RESULT_OK){
                    Uri selectedImage = data.getData();
                    mImageAdd.setImageURI(selectedImage);
                }

                break;
            case 1:
                if(resultCode == RESULT_OK){
                    Uri selectedImage = data.getData();
                    Log.i("zoloo",selectedImage.getEncodedPath());
                    mImageAdd.setImageURI(selectedImage);
                }
                break;
        }

        mButtonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mEditTextWordMng.getText().toString().trim().isEmpty()){
                    Toast.makeText(getApplicationContext(), "Үгээ бичнэ үү!\nPlease enter word!", Toast.LENGTH_SHORT).show();
                    return;
                }

                Word word = new Word();

                word.setMng(mEditTextWordMng.getText().toString());
                word.setEng(mEditTextWordEng.getText().toString());
                word.setBuiltIn(0);
                word.setCategoryId((Category)mSpinnerCategory.getSelectedItem());

                String root = Environment.getExternalStorageDirectory().toString();
                File myDir = new File(root + "/ccard_images/");
                if(!myDir.exists())
                    myDir.mkdirs();

                String fname = (new Random()).nextInt(10000) +".jpg";
                File file = new File (myDir, fname);
                if (file.exists ()) file.delete ();

                mImageAdd.buildDrawingCache();
                Bitmap finalBitmap = mImageAdd.getDrawingCache();
                finalBitmap = Bitmap.createScaledBitmap(finalBitmap, 150, 150, false);

                try {
                    FileOutputStream out = new FileOutputStream(file);
                    finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
                    out.flush();
                    out.close();
                    word.setImage(file.getAbsolutePath());
                    Log.i("zoloo", word.getImage());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                datasource.createWord(word);
                AddWordActivity.this.finish();
            }
        });

        mButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddWordActivity.this.finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        datasource.close();
    }
}
