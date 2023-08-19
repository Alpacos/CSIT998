package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Utils.UIfEmpty;
import com.example.myapplication.db.DBHelper;

/**
 * 登录页
 */
public class LoginActivity extends Activity {

    private TextView btn_register;
    private Button btn_login;
    private CheckBox cb_remember;
    private EditText et_password;
    private EditText et_username;
    private SQLiteDatabase db;
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initViews();    //


        getSp();    //获取SharedPreferences

        /**
         * 跳转至注册界面
         */
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        /**
         * 登录按钮的点击事件
         */
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取控件属性值
                String username = et_username.getText().toString().trim();
                String password = et_password.getText().toString().trim();
                if (UIfEmpty.ifEmpty(username, password)) {
                    Toast.makeText(LoginActivity.this, "信息未输入完整", Toast.LENGTH_SHORT).show();
                } else {
                    //将数据查询，看看数据库有没有存在当前用户
                    Cursor cursor = db.rawQuery("select username from user where username = ? and password = ?",
                            new String[]{username, password});
                    if (cursor.getCount() == 1) {
//                        SharedPreferences.Editor editor = sp.edit();
//                        if (cb_remember.isChecked()) {
//                            editor.putString("username", username);
//                            editor.putString("password", password);
//                            editor.putBoolean("IFCHECK", true);
//                        } else {
//                            editor.putBoolean("IFCHECK", false);
//                        }
//                        editor.commit();
                        startActivity(new Intent(LoginActivity.this,MainActivity.class));
                        Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(LoginActivity.this, "账号或密码错误", Toast.LENGTH_SHORT).show();
                    }
                    cursor.close();
                }
            }
        });
    }

    private void initViews() {
        DBHelper helper = new DBHelper(this);
        db = helper.getWritableDatabase();
        sp = getSharedPreferences("USERINFO", MODE_PRIVATE);

        et_username = findViewById(R.id.et_usn);
        et_password = findViewById(R.id.et_pwd);
//        cb_remember = findViewById(R.id.login_checkBox);
        btn_login = findViewById(R.id.btn_login);
        btn_register = findViewById(R.id.tv_register);

    }

    /**
     * 判断是否记住密码
     */
    private void getSp() {
        if (sp.getBoolean("IFCHECK", false)) {
            String username = sp.getString("username", "");
            String password = sp.getString("password", "");
            et_username.setText(username);
            et_password.setText(password);
            cb_remember.setChecked(true);
        }
    }


}
