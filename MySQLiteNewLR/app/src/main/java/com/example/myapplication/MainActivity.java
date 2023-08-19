package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.myapplication.bean.Person;
import com.example.myapplication.db.MySQLiteHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText et_name, et_age, et_tall;

    Button btn_add, btn_del, btn_update, btn_query;

    ListView lv_data;

    String name, age, tall;

    ArrayList<Person> people;

    MySQLiteHelper dbHelper;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化数据库
        dbHelper=new MySQLiteHelper(this, "my_sqlite", null, 1);
        db = dbHelper.getWritableDatabase();
        people = new ArrayList<Person>();
        //绑定
        et_name = findViewById(R.id.et_name);
        et_age = findViewById(R.id.et_age);
        et_tall = findViewById(R.id.et_tall);
        btn_add = findViewById(R.id.btn_add);
        btn_del = findViewById(R.id.btn_del);
        btn_update = findViewById(R.id.btn_update);
        btn_query = findViewById(R.id.btn_query);

        lv_data = findViewById(R.id.lv_data);
        //监听
        btn_add.setOnClickListener(this);
        btn_del.setOnClickListener(this);
        btn_update.setOnClickListener(this);
        btn_query.setOnClickListener(this);

    }
    //获取数据
    public void getWidgetsData() {
        name = et_name.getText().toString();
        age = et_age.getText().toString();
        tall = et_tall.getText().toString();
    }
    //增
    public void add() {
        Toast.makeText(this, "新增", Toast.LENGTH_SHORT).show();
        getWidgetsData();

        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("age", Integer.valueOf(age));
        values.put("tall", Integer.valueOf(tall));

        db.insert("Person", null, values);
        Toast.makeText(this, "添加成功", Toast.LENGTH_SHORT).show();
    }
    //删
    public void del() {
        Toast.makeText(this, "修改", Toast.LENGTH_SHORT).show();
        getWidgetsData();
        db.delete("Person", "name=?", new String[]{name});
        Toast.makeText(this, "删除成功", Toast.LENGTH_SHORT).show();
    }
    //改
    public void update() {
        Toast.makeText(this, "更新", Toast.LENGTH_SHORT).show();
        getWidgetsData();
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("age", Integer.valueOf(age));
        values.put("tall", Integer.valueOf(tall));

        db.update("Person", values, "name=?", new String[]{name});
        Toast.makeText(this, "更新成功", Toast.LENGTH_SHORT).show();

    }
    //查
    public void query() {
        Toast.makeText(this, "查询", Toast.LENGTH_SHORT).show();
        Cursor cursor = db.query("Person", null, null,null,null,null,null);
        people.clear();
        while (cursor.moveToNext()){
            int queryId = cursor.getInt(0);
            String queryName =cursor.getString(1);
            int queryAge = cursor.getInt(2);
            int queryTall= cursor.getInt(3);
            Person person =  new Person(queryId, queryName, queryAge, queryTall);
            people.add(person);
        }
//        ListAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, people);// people.toArray());
        List<Map<String, Object>> mOrderData = getAllStus();
        String[] from = {"id", "name", "age", "tall"};
        int[] to ={R.id.tv_id, R.id.tv_name, R.id.tv_age, R.id.tv_tall};
        SimpleAdapter adapter = new SimpleAdapter(MainActivity.this, mOrderData, R.layout.item_list, from, to);// people.toArray());
        lv_data.setAdapter(adapter);
        Toast.makeText(this, "查询成功", Toast.LENGTH_SHORT).show();
    }

    //查找所有学员信息
    @SuppressLint("Range")
    public ArrayList<Map<String, Object>> getAllStus() {
        ArrayList<Map<String, Object>> listStus = new ArrayList<Map<String, Object>>();
        Cursor cursor = db.query("Person", null, null, null, null, null, null);

        int resultCounts = cursor.getCount();  //记录数
        if (resultCounts == 0) {
            return null;
        } else {
            while (cursor.moveToNext()) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("id", cursor.getInt(cursor.getColumnIndex("id")));
                map.put("name", cursor.getString(cursor.getColumnIndex("name")));
                map.put("age", cursor.getInt(cursor.getColumnIndex("age")));
                map.put("tall", cursor.getInt(cursor.getColumnIndex("tall")));

                listStus.add(map);
            }
            return listStus;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_add: {
                add();
            }
            break;
            case R.id.btn_del: {
                del();
            }
            break;
            case R.id.btn_update: {
                update();
            }
            break;
            case R.id.btn_query: {
                query();
            }
            break;
        }
    }
}