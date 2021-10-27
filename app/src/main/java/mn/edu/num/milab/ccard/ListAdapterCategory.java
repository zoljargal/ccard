package mn.edu.num.milab.ccard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import mn.edu.num.milab.ccard.database.Category;

/**
 * Created by zoloo on 6/2/17.
 */
public class ListAdapterCategory extends ArrayAdapter<Category> {
    List<Category> data;
    Context context;
    private int lastPosition = -1;

    // View lookup cache
    private static class ViewHolder {
        TextView txtMng;
    }

    public ListAdapterCategory(Context context, List<Category> data){
        super(context, R.layout.list_item_category, data);
        this.data = data;
        this.context = context;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Category category = data.get(position);
        ViewHolder viewHolder;
        final View result;
        if(convertView==null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.list_item_category, parent, false);
            viewHolder.txtMng = (TextView) convertView.findViewById(R.id.mng);
            result = convertView;
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        Animation animation = AnimationUtils.loadAnimation(context, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        result.startAnimation(animation);
        lastPosition = position;

        viewHolder.txtMng.setText(category.getText());
        return convertView;
    }

    @Override
    public Category getItem(int position) {
        return data.get(position);
    }
}
