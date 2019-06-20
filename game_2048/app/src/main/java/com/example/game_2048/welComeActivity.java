package com.example.game_2048;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import com.google.gson.Gson;

public class welComeActivity extends AppCompatActivity {
    private Button btnStartGame;
    private Button btnStartPreviousGame;
    private CheckBox cbGioiHanDiem;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private PreviousData previousData;
    private Gson gson = new Gson();
    private boolean flag;// đang chơi thì thoát ra flag=true
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wel_come);
        sharedPreferences = getSharedPreferences("diemMax", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        btnStartGame = findViewById(R.id.btnStartGame);
        cbGioiHanDiem = findViewById(R.id.checkBoxGioiHanDiem);
        btnStartPreviousGame  = findViewById(R.id.btnStartPreviousGame);
        flag = sharedPreferences.getBoolean("flag",false);
        System.out.println("khóa flag" + flag);
        if (flag){
            btnStartPreviousGame.setEnabled(true);// khi true thì có thể click
        }
        else{
            btnStartPreviousGame.setEnabled(false);
        }
        btnStartPreviousGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(welComeActivity.this, MainActivity.class);
                intent.putExtra("StartPreviousGame",true);
                startActivity(intent);
            }
        });
        btnStartGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(welComeActivity.this, MainActivity.class);
                intent.putExtra("gioiHanDiem",cbGioiHanDiem.isChecked());
                startActivity(intent);
            }
        });
    }
}
