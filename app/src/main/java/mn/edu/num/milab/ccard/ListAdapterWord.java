package mn.edu.num.milab.ccard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import mn.edu.num.milab.ccard.database.Category;
import mn.edu.num.milab.ccard.database.Word;

/**
 * Created by zoloo on 6/2/17.
 */
public class ListAdapterWord extends ArrayAdapter<Word> {
    List<Word> data;
    Context context;
    private int lastPosition = -1;
    public ListAdapterWord(Context context, List<Word> data){
        super(context, R.layout.list_item_horizontal_layout, data);
        this.data = data;
        this.context = context;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Word word = data.get(position);
        ViewHolder viewHolder;
        final View result;
        if(convertView==null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.list_item_category, parent, false);
            viewHolder.imageTitle = (TextView) convertView.findViewById(R.id.mng);
            viewHolder.image = (ImageView) convertView.findViewById(R.id.image_sentence);
            result = convertView;
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        Animation animation = AnimationUtils.loadAnimation(context, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        result.startAnimation(animation);
        lastPosition = position;

        viewHolder.imageTitle.setText(word.getMng());
        viewHolder.image.setImageBitmap(word.getBitmap());
        return convertView;
    }

    @Override
    public Word getItem(int position) {
        return data.get(position);
    }

    static class ViewHolder {
        TextView imageTitle;
        ImageView image;
    }
}
