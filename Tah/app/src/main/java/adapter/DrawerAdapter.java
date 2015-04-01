package adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.revealing.tah.R;

import java.util.ArrayList;

import util.CircularButton;

/**
 * Created by adityamathur on 24/03/15.
 */
public class DrawerAdapter extends BaseAdapter {
Context context;
public static int id=0;
 ArrayList<String> arrayList;


    public DrawerAdapter(Context context,ArrayList<String> arrayList) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View singleRow = convertView;

        if (singleRow == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            singleRow = inflater.inflate(R.layout.drawer_row, parent, false);

        }
        TextView name= (TextView) singleRow.findViewById(R.id.nameoflist);
        RelativeLayout rowback= (RelativeLayout) singleRow.findViewById(R.id.rowbackground);

        name.setText(arrayList.get(position).toString());

        if (position == id) {
            rowback.setBackgroundColor(Color.parseColor("#e0e0e0"));
           // mButtonPw.setButtonColor(Color.parseColor("#259b24"));
            // set your color
        }else{
            rowback.setBackgroundColor(Color.parseColor("#ffffff"));
           // mButtonPw.setButtonColor(Color.parseColor("#757575"));
        }


        return singleRow;
    }
}
