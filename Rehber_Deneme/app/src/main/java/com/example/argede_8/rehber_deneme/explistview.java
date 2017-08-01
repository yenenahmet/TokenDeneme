package com.example.argede_8.rehber_deneme;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class explistview extends AppCompatActivity {
    public List<String> list_parent;
    public ExpandListViewAdapter expand_adapter;
    public HashMap<String, List<String>> list_child;
    public ExpandableListView expandlist_view;
    public List<String> gs_list;
    public List<String> fb_list;
    public int last_position = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explistview);
        expandlist_view = (ExpandableListView)findViewById(R.id.expand_listview);

        Hazırla(); // expandablelistview içeriğini hazırlamak için

        // Adapter sınıfımızı oluşturmak için başlıklardan oluşan listimizi ve onlara bağlı olan elemanlarımızı oluşturmak için HaspMap türünü yolluyoruz
        expand_adapter = new ExpandListViewAdapter(explistview.this, list_parent, list_child);
        expandlist_view.setAdapter(expand_adapter);  // oluşturduğumuz adapter sınıfını set ediyoruz
        expandlist_view.setClickable(true);
        expandlist_view.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {

                String child_name = (String)expand_adapter.getChild(groupPosition, childPosition);
                //Toast.makeText(getApplicationContext(),"hey" + child_name, Toast.LENGTH_LONG).show();

                AlertDialog.Builder builder = new AlertDialog.Builder(explistview.this);
                builder.setMessage(child_name)
                        .setTitle("Mobilhanem Expandablelistview")
                        .setCancelable(false)
                        .setPositiveButton("TAMAM", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();


                return false;
            }
        });

    }
    public void Hazırla()
    {
        list_parent = new ArrayList<String>();  // başlıklarımızı listemelek için oluşturduk
        list_child = new HashMap<String, List<String>>(); // başlıklara bağlı elemenları tutmak için oluşturduk

        list_parent.add("GALATASARAY");  // ilk başlığı giriyoruz
        list_parent.add("FENERBAHCE");   // ikinci başlığı giriyoruz

        gs_list = new ArrayList<String>();  // ilk başlık için alt elemanları tanımlıyoruz
        gs_list.add("Muslera");
        gs_list.add("MM");
        gs_list.add("ee");
        fb_list = new ArrayList<String>(); // ikinci başlık için alt elemanları tanımlıyoruz
        fb_list.add("Volkan Demirel");
        fb_list.add("RR");
        fb_list.add("TT");
        list_child.put(list_parent.get(0),gs_list); // ilk başlığımızı ve onların elemanlarını HashMap sınıfında tutuyoruz
        list_child.put(list_parent.get(1), fb_list); // ikinci başlığımızı ve onların elemanlarını HashMap sınıfında tutuyoruz


    }
}
