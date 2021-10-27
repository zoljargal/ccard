package mn.edu.num.milab.ccard;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import mn.edu.num.milab.ccard.database.Word;

/**
 * Created by zoloo on 6/5/17.
 */
public class GridViewAdapterWord extends ArrayAdapter<Word> {
    private Context context;
    private int layoutResourceId;
    private ArrayList<Word> data;

    public GridViewAdapterWord(Context context, int layoutResourceId, ArrayList<Word> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder = null;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new ViewHolder();
            holder.imageTitle = (TextView) row.findViewById(R.id.text);
            holder.image = (ImageView) row.findViewById(R.id.image);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }

        Word item = data.get(position);
        holder.imageTitle.setText(item.getText());
        holder.image.setImageBitmap(item.getBitmap());
        return row;
    }

    public ArrayList<Word> getData() {
        return data;
    }

    static class ViewHolder {
        TextView imageTitle;
        ImageView image;
    }

}