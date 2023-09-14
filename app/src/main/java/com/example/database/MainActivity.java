package com.example.database;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {
EditText ed_name,ed_age;
Switch activeCustomer;
Button btn_add,btn_view;
ListView lv_customerList;

CustomerModel customerModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ed_age=findViewById(R.id.et_age);
        ed_name=findViewById(R.id.et_name);
        activeCustomer=findViewById(R.id.switch0);
        btn_add=findViewById(R.id.btn_add);
        btn_view=findViewById(R.id.btn_ViewAll);
        lv_customerList=findViewById(R.id.lv_list);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                     customerModel=new CustomerModel(1,ed_name.getText().toString(),Integer.parseInt(ed_age.getText().toString()),activeCustomer.isActivated());
                    Toast.makeText(MainActivity.this, customerModel.toString(), Toast.LENGTH_SHORT).show();
                }catch (Exception e){
                    Toast.makeText(MainActivity.this, "error", Toast.LENGTH_SHORT).show();
                }

                DataBaseHelper dataBaseHelper=new DataBaseHelper(MainActivity.this);
                boolean success= dataBaseHelper.addOne(customerModel);
                Toast.makeText(MainActivity.this,  "success", Toast.LENGTH_SHORT).show();
            }
        });

        btn_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    DataBaseHelper dataBaseHelper=new DataBaseHelper( MainActivity.this);
                    List<CustomerModel> everyone = dataBaseHelper.getEveryone();

                    ArrayAdapter arrayAdapter=new ArrayAdapter(MainActivity.this,android.R.layout.simple_list_item_1,everyone);
                    lv_customerList.setAdapter(arrayAdapter);

                }catch (Exception e){
                    Toast.makeText(MainActivity.this, "Not working", Toast.LENGTH_SHORT).show();
                }

            }
        });

        lv_customerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DataBaseHelper dataBaseHelper=new DataBaseHelper(MainActivity.this);
                customerModel= (CustomerModel) parent.getItemAtPosition(position);
                dataBaseHelper.deleteOne(customerModel);

                List<CustomerModel> everyone = dataBaseHelper.getEveryone();

                ArrayAdapter arrayAdapter=new ArrayAdapter(MainActivity.this,android.R.layout.simple_list_item_1,everyone);
                lv_customerList.setAdapter(arrayAdapter);

            }
        });
    }
}