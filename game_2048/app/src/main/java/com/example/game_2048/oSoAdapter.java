package com.example.game_2048;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

public class oSoAdapter extends ArrayAdapter {
    private Context ct;
    private ArrayList<Integer> arr;

    public oSoAdapter(Context context, int resource, List objects) {
        super(context, resource, objects);
        this.ct = context;
        arr = new ArrayList<>(objects);
    }

    @Override
    public void notifyDataSetChanged() {
        arr = dataGame.getDataGame().getArraySo();
        super.notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) ct.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_ovuong, null);
        }
        if (arr.size() > 0) {
            oVuong o = (oVuong) convertView.findViewById(R.id.txvOVuong);
            o.setTextt(arr.get(position));
        }
        return convertView;
    }
}
