package mn.edu.num.milab.ccard;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.MenuInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import mn.edu.num.milab.ccard.database.Category;
import mn.edu.num.milab.ccard.database.Datasource;
import mn.edu.num.milab.ccard.database.Word;
import android.widget.SearchView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Datasource datasource;
    private GridView gridViewWord;
    private GridView gridViewSentence;
    private GridViewAdapterWord gridVeiwAdapterSetntence;
    private ListView listViewCategory;
    private ListAdapterCategory listAdapterCategory;
    private long lastCategoryId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                Intent intent = new Intent(MainActivity.this, PlayActivity.class);
                ArrayList<Long> cardIds=new ArrayList<Long>();
                for(Word word : gridVeiwAdapterSetntence.getData()){
                    cardIds.add(word.getId());
                }
                intent.putExtra("play-cards", cardIds   );
                startActivity(intent);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        datasource = new Datasource(this);
        datasource.open();

        List<Category> categories = datasource.getAllCategory();
        listAdapterCategory = new ListAdapterCategory(this, categories);
        listViewCategory = (ListView) findViewById(R.id.categoryListView);
        listViewCategory.setAdapter(listAdapterCategory);

        listViewCategory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Category category = listAdapterCategory.getItem(i);
                setGridViewWord(category.getId());
            }
        });



        gridViewWord = (GridView) findViewById(R.id.gridView);
        gridViewWord.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Word w = (Word) gridViewWord.getAdapter().getItem(i);
                gridVeiwAdapterSetntence.add(w);
                gridVeiwAdapterSetntence.notifyDataSetChanged();
                Utils.setDynamicWidth(gridViewSentence);
            }
        });

        gridViewWord.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                final Word w = (Word) gridViewWord.getAdapter().getItem(i);
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle(R.string.dialog_title_word_delete);
                if(w.isBuiltIn()){
                    builder.setMessage(R.string.dialog_message_cant_delete);
                    builder.setPositiveButton(R.string.action_ok, new Dialog.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }

                    });

                }else{
                    builder.setMessage(R.string.dialog_message_word_delete);
                    builder.setPositiveButton(R.string.action_sure, new Dialog.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if(datasource.deleteWord(w.getId())){
                                GridViewAdapterWord gridViewAdapter=(GridViewAdapterWord)gridViewWord.getAdapter();
                                gridViewAdapter.remove(w);
                                gridViewAdapter.notifyDataSetChanged();
                            }
                            dialog.cancel();
                        }

                    });

                    builder.setNegativeButton(R.string.action_no, new Dialog.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();

                        }

                    });
                }
                builder.show();
                return false;
            }
        });

        gridViewSentence = (GridView) findViewById(R.id.gridViewSentence);
        gridVeiwAdapterSetntence = new GridViewAdapterWord(this, R.layout.grid_item_layout, new ArrayList<Word>());
        gridViewSentence.setAdapter(gridVeiwAdapterSetntence);
        gridViewSentence.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Word w = (Word) gridViewSentence.getAdapter().getItem(i);
                gridVeiwAdapterSetntence.remove(w);
                gridVeiwAdapterSetntence.notifyDataSetChanged();
                Utils.setDynamicWidth(gridViewSentence);
            }
        });

        setGridViewWord(1);
    }

    private void setGridViewWord(long category_id){
        lastCategoryId = category_id;
        setGridViewWord(category_id, null);
    }
    private void setGridViewWord(String query){
        setGridViewWord(-1, query);
    }
    private void setGridViewWord(long category_id, String query){

        ArrayList<Word> words;
        if(query!=null)
            words = datasource.getAllWord(query);
        else{
            words = datasource.getAllWord(category_id);
        }

        for(Word w:words){
            Bitmap bitmap = null;
            try {
                if(w.isBuiltIn())
                    bitmap = Utils.getBitmapFromAsset(w.getImage(), getAssets());
                else
                    bitmap = Utils.getBitmapFromPath(w.getImage());
            } catch (IOException e) {
                e.printStackTrace();
            }
            w.setBitmap(bitmap);
        }
        GridViewAdapterWord gridVeiwAdapter = new GridViewAdapterWord(this, R.layout.grid_item_layout, words);

        gridViewWord.setAdapter(gridVeiwAdapter);
    }



    @Override
    public void onBackPressed() {
        datasource.close();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        // side bar
        MenuInflater inflater = getMenuInflater();
        //inflater.inflate(R.menu.main, menu);
        inflater.inflate(R.menu.menu_search, menu);
        MenuItem item = menu.findItem(R.id.menuSearch);
        SearchView searchView = (SearchView)item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                setGridViewWord(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
//                setGridViewWord(s);
                return false;
            }
        });

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                setGridViewWord(lastCategoryId);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_gallery) {
            Intent intent = new Intent(MainActivity.this, AddWordActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_write_letter) {
            Intent intent = new Intent(MainActivity.this, WriteLetterActivity.class);
            startActivity(intent);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
