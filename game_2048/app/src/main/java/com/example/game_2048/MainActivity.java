package com.example.game_2048;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.nio.file.Files;

public class MainActivity extends AppCompatActivity {
    private boolean gioiHanDiem = false;
    private GridView gdvGamePlay;
    private oSoAdapter oSoAdapter;
    private View.OnTouchListener listener;
    private float x, y;
    TextView txtvDiem, txtvDiemMax, txtBackupPoint;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private TextView txtWonMessage;
    private ImageView imgViewRorateStep;
    private boolean StartPreviousGame = false;

    //    private Layout firstwindow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AnhXa();
        Intent intent = getIntent();
        gioiHanDiem = intent.getBooleanExtra("gioiHanDiem", false);// đọc giá trị intent gửi sang
        StartPreviousGame = intent.getBooleanExtra("StartPreviousGame",false);
        startGame();
        RorateStep();
        txtBackupPoint.setText(dataGame.getDataGame().getBackupPoint() + "");
    }

    public void startGame() {
        if(StartPreviousGame){
            dataGame.getDataGame().setStartPreviousGame(StartPreviousGame);
        }
        sharedPreferences = getSharedPreferences("diemMax", MODE_PRIVATE);
        editor = sharedPreferences.edit();
//        editor.putInt("diemMax", 0);
//        editor.commit();
        if (sharedPreferences.getInt("diemMax", -1) == -1) {
            txtvDiemMax.setText("0");
            dataGame.getDataGame().setDiemMax(0);
        } else {
            txtvDiemMax.setText(sharedPreferences.getInt("diemMax", -1) + "");
            dataGame.getDataGame().setDiemMax(sharedPreferences.getInt("diemMax", -1));
        }
        dataGame.getDataGame().setGioiHanDiem(gioiHanDiem);
        dataGame.getDataGame().setSharedPreferences(sharedPreferences);
        KhoiTao();
        setData();
    }

    private void RorateStep() {
        imgViewRorateStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("check rorate state");
                dataGame.getDataGame().backupData();
                txtvDiem.setText(dataGame.getDataGame().getDiem() + "");
                oSoAdapter.notifyDataSetChanged();
            }
        });
    }

    private void AnhXa() {
        gdvGamePlay = (GridView) findViewById(R.id.grvGamePlay);
        txtvDiem = findViewById(R.id.txtvDiem);
        txtvDiemMax = findViewById(R.id.txtvDiemMax);
        txtWonMessage = findViewById(R.id.txtWonMessage);
        imgViewRorateStep = findViewById(R.id.ImgViewRorate);
        txtBackupPoint = findViewById(R.id.txtvBackupPoint);
    }

    private void KhoiTao() {
        dataGame.getDataGame().intt(MainActivity.this);
        txtvDiem.setText(dataGame.getDataGame().getDiem() + "");
        oSoAdapter = new oSoAdapter(MainActivity.this, 0, dataGame.getDataGame().getArraySo());
        listener = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        x = event.getX();
                        y = event.getY();
                        break;
                    case MotionEvent.ACTION_UP:
                        if (event.getY() == y && event.getX() == x) {// chạm và thả giống nhau

                            break;
                        }
                        if (Math.abs(event.getX() - x) > Math.abs(event.getY() - y)) {
                            if (event.getX() > x) {
                                dataGame.getDataGame().vuotTrai();
                                oSoAdapter.notifyDataSetChanged();
                                if (dataGame.getDataGame().kiemTra() == false) {
                                    System.out.println("end game");
                                }
                                txtvDiem.setText(dataGame.getDataGame().getDiem() + "");
                                txtBackupPoint.setText(dataGame.getDataGame().getBackupPoint() + "");
                                if (dataGame.getDataGame().getDiem() > dataGame.getDataGame().getDiemMax()) {
                                    dataGame.getDataGame().setDiemMax(dataGame.getDataGame().getDiem());
                                    txtvDiemMax.setText(dataGame.getDataGame().getDiemMax() + "");
//                                    editor.putInt("diemMax", -1);
                                    editor.putInt("diemMax", dataGame.getDataGame().getDiem());
                                    editor.commit();
                                }
                            } else {
                                dataGame.getDataGame().vuotPhai();
                                oSoAdapter.notifyDataSetChanged();
                                if (dataGame.getDataGame().kiemTra() == false) {
                                    System.out.println("end game");
                                }
                                txtvDiem.setText(dataGame.getDataGame().getDiem() + "");
                                txtBackupPoint.setText(dataGame.getDataGame().getBackupPoint() + "");
                                if (dataGame.getDataGame().getDiem() > dataGame.getDataGame().getDiemMax()) {
                                    dataGame.getDataGame().setDiemMax(dataGame.getDataGame().getDiem());
                                    txtvDiemMax.setText(dataGame.getDataGame().getDiemMax() + "");
                                    editor.putInt("diemMax", dataGame.getDataGame().getDiem());
                                    editor.commit();
                                }
                            }
                        } else {
                            if (event.getY() > y) {
                                dataGame.getDataGame().vuotXuong();
                                oSoAdapter.notifyDataSetChanged();
                                if (dataGame.getDataGame().kiemTra() == false) {
                                    System.out.println("end game");
                                }
                                txtvDiem.setText(dataGame.getDataGame().getDiem() + "");
                                txtBackupPoint.setText(dataGame.getDataGame().getBackupPoint() + "");
                                if (dataGame.getDataGame().getDiem() > dataGame.getDataGame().getDiemMax()) {
                                    dataGame.getDataGame().setDiemMax(dataGame.getDataGame().getDiem());
                                    txtvDiemMax.setText(dataGame.getDataGame().getDiemMax() + "");
                                    editor.putInt("diemMax", dataGame.getDataGame().getDiem());
                                    editor.commit();
                                }
                            } else {
                                dataGame.getDataGame().vuotLen();
                                oSoAdapter.notifyDataSetChanged();
                                if (dataGame.getDataGame().kiemTra() == false) {
                                    System.out.println("end game");
                                }
                            }
                            txtvDiem.setText(dataGame.getDataGame().getDiem() + "");
                            txtBackupPoint.setText(dataGame.getDataGame().getBackupPoint() + "");
                            if (dataGame.getDataGame().getDiem() > dataGame.getDataGame().getDiemMax()) {
                                dataGame.getDataGame().setDiemMax(dataGame.getDataGame().getDiem());
                                txtvDiemMax.setText(dataGame.getDataGame().getDiemMax() + "");
                                editor.putInt("diemMax", dataGame.getDataGame().getDiem());
                                editor.commit();
                            }
                        }
                        break;
                }
                if (dataGame.getDataGame().KiemTraChienThang()) {
                    System.out.println("end game");
                    txtWonMessage.setText("bạn đã chiến thắng tuyệt đối");
                }
                return true;
            }
        };

    }

    private void setData() {
        gdvGamePlay.setAdapter(oSoAdapter);
        gdvGamePlay.setOnTouchListener(listener);
    }
}
