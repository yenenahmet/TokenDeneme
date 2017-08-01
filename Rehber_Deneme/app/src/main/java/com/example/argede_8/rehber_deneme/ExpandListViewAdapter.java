package com.example.argede_8.rehber_deneme;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;

/**
 * Created by ARGEDE_8 on 5/12/2017.
 */

public class ExpandListViewAdapter extends BaseExpandableListAdapter {
    public   String[] numbers = new String[] {
            "A", "B", "C"};
    public   String[] numbers1 = new String[] {
            "D", "E", "F"};
    public   String[] numbers2 = new String[] {
            "G", "J", "K"};
    public List<String> list_parent;
    public HashMap<String, List<String>> list_child;
    public Context context;
    public TextView txt;
    public TextView txt_child;
    public LayoutInflater inflater;
    @Override
    public int getGroupCount() {

        return list_parent.size();
    }

    public ExpandListViewAdapter(Context context, List<String> list_parent,HashMap<String, List<String>> list_child)
    {
        this.context = context;
        this.list_parent = list_parent;
        this.list_child = list_child;
    }

    @Override
    public int getChildrenCount(int groupPosition) {

        return list_child.get(list_parent.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {

        return list_parent.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return list_child.get(list_parent.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {

        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {

        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }


    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View view, ViewGroup parent) {
        String title_name = (String)getGroup(groupPosition);

        if(view == null)
        {
            inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.listview_header,null);
        }

        txt = (TextView)view.findViewById(R.id.txt_parent);
        txt.setText(title_name);

        return view;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition,
                             boolean isLastChild, View view, ViewGroup parent) {
        // kaçıncı pozisyonda ise başlığımızın elemanı onun ismini alarak string e atıyoruz
        String txt_child_name = (String)getChild(groupPosition, childPosition);
        if(view==null)
        {
            inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.listview_child, null);
            // fonksiyon adından da anlaşılacağı gibi parent a bağlı elemanlarının layoutunu inflate ediyoruz böylece o görüntüye ulaşmış oluyoruz
        }

        // listview_child ulaştığımıza göre içindeki bileşeni de kullanabiliyoruz daha sonradan view olarak return ediyoruz
        GridView gridView = (GridView)  view.findViewById(R.id.gridView1);
        ArrayAdapter<String> adapter = null;
        if(childPosition ==0){
          adapter = new ArrayAdapter<String>(context,
                    android.R.layout.simple_list_item_1, numbers);
        }else if( childPosition == 1){
            adapter = new ArrayAdapter<String>(context,
                    android.R.layout.simple_list_item_1, numbers1);
        }else if(childPosition == 2){
            adapter = new ArrayAdapter<String>(context,
                    android.R.layout.simple_list_item_1, numbers2);
        }


        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {


                Toast.makeText(context,
                        ((TextView) v).getText(), Toast.LENGTH_SHORT).show();
                ((TextView)v).setBackgroundColor(Color.RED);
            }
        });
        return view;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {

        return true;  // expandablelistview de tıklama yapabilmek için true olmak zorunda
    }

}
