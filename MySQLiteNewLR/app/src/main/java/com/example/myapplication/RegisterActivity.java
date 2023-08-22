package com.example.myapplication;

import android.app.Activity;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.myapplication.Utils.UIfEmpty;
import com.example.myapplication.db.DBHelper;

/**
 * 注册页
 */
public class RegisterActivity extends Activity {
    //定义控件
    private Button btn_register;
    private Button btn_back;
    private EditText et_username;
    private EditText et_password;
    private EditText et_repeat;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register); //加载xml
        /**
         * 初始化控件
         */
        initViews();    //主要是绑定   java和xml捆绑

        /**
         * 注册的点击事件
         */
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               register();
            }
        });

        /**
         * 返回的点击事件
         */
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    // 初始化view
    private void initViews() {

        DBHelper helper = new DBHelper(this);
        db = helper.getWritableDatabase();

        btn_register = findViewById(R.id.register_btn_register);
        btn_back = findViewById(R.id.register_btn_back);
        et_username = findViewById(R.id.register_username);
        et_password = findViewById(R.id.register_password);
        et_repeat = findViewById(R.id.register_password_again);
    }


    private void register() {
        //获取控件的属性值
        String userName = et_username.getText().toString().trim();
        String password = et_password.getText().toString().trim();
        String passwordAgain = et_repeat.getText().toString().trim();
        /**
         * 判断是否输入完整
         */
        if (UIfEmpty.ifEmpty(userName, password, passwordAgain)) {
            Toast.makeText(RegisterActivity.this, "信息为输入完整", Toast.LENGTH_SHORT).show();
        } else {
            if(password.equals(passwordAgain)) {
                //将数值， 存入参数表
                ContentValues values = new ContentValues();
                values.put("username", userName);
                values.put("password", password);
                long flag = 0;
                //将数据存储到数据库
                flag = db.insert("user", null, values);
                if(flag == -1) {
                    Toast.makeText(RegisterActivity.this, "账号已被注册", Toast.LENGTH_SHORT).show();
                } else {
                    /**
                     * 返回至登陆界面
                     */
                    Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                    onBackPressed();
                }
            } else {
                Toast.makeText(RegisterActivity.this, "两次输入的密码不一致", Toast.LENGTH_SHORT).show();
            }
        }
    }


}
