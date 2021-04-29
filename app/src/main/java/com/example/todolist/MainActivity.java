package com.example.todolist;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText item;
    Button btnAdd;
    ListView listView;
    ArrayList<String> itemList = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        item= findViewById(R.id.editText);
        btnAdd = findViewById(R.id.Button);
        listView = findViewById(R.id.listView);

        //Main activity - gelende ilk once kontrol etmek lazimdir ki onceden bir veri kayd olunub ya yox?
        //Eger kayd edilipse ilk once bu kayitli verileri alib ve arrayListe eklemek daha sonra
        // ListView burdaki arraylistden alar verileri

        itemList = FileHelper.readData( this);
        //FileHelper dosyaya veri yazmaq ucun olusturdugumuz siniff, read-ise sinif icinde yazdigimiz metoddur,
        // bu kod setiri dosyada veri varsa onu alib  arrayliste ekleyecek daha sonra adapterle arrayliste artiracagim

        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,android.R.id.text1,itemList);
        listView.setAdapter(arrayAdapter);
        //Onceden veri var idise adaptere yazdiq bura qeder devam edek



        //ListView-a yeni bir veri ekleme

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String itemNAme = item.getText().toString();//edite-yazi yazaq

                itemList.add(itemNAme);
                //add olandan sora itemEdit-i temizlemek lazimdi
                item.setText("");

                FileHelper.writeData(itemList,getApplicationContext());
                arrayAdapter.notifyDataSetChanged();

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                alert.setTitle("Delete");
                alert.setMessage("Do you want to delete this item from list?");
                alert.setCancelable(false); //bu kod bashqa yerden basilarsa alert-i baglayacaq
                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        itemList.remove(position);//bu kod kullanicinin toxundugu itemi- silecek
                        //daha sonra listview-u yenilemek lazimdir
                        arrayAdapter.notifyDataSetChanged();
                        FileHelper.writeData(itemList,getApplicationContext());

                    }
                });

                AlertDialog alertDialog = alert.create();
                alertDialog.show();
                 }
        });



    }
}