package sharedcode.turboeditor.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import sharedcode.turboeditor.R;

/**
 * Created by root on 07/03/17.
 */

public class UserAdapter extends ArrayAdapter<String> {
    private int UserColors[] = {Color.RED, Color.GREEN, Color.CYAN, Color.MAGENTA, Color.YELLOW, Color.BLUE};

    public UserAdapter(Context context, ArrayList<String> users) {
        super(context, 0, users);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        String user = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.display_user_layout, parent, false);
        }

        TextView tvName = (TextView) convertView.findViewById(R.id.tvName);
        View tvHome = convertView.findViewById(R.id.tvHome);

        tvName.setText(user);
        tvHome.setBackgroundColor(UserColors[position]);

        return convertView;
    }

}
