package com.example.game_2048;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.preference.Preference;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Random;

public class dataGame {
    private SharedPreferences.Editor editor;
    private boolean flag = false;
    private boolean gioiHanDiem = false;
    private int BackupPoint;
    private int BackupLandmark;
    private boolean rorareState;
    private int diem;
    private int diemMax;
    private static dataGame dataGame;
    private ArrayList<Integer> arraySo = new ArrayList<>();
    private ArrayList<Integer> arraySoLuiBuoc = new ArrayList<>();
    private int[][] mangHaiChieu = new int[4][4];
    private int[][] mangHaiChieuLuiBuoc = new int[4][4];
    private int[] mangMau;
    private Random r = new Random();
    private PreviousData previousData;
    private Gson gson = new Gson();
    private SharedPreferences sharedPreferences;
    private boolean startPreviousGame = false;

    static {
        dataGame = new dataGame();
    }

    public SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }

    public boolean isStartPreviousGame() {
        return startPreviousGame;
    }

    public void setStartPreviousGame(boolean startPreviousGame) {
        this.startPreviousGame = startPreviousGame;
    }

    public void setSharedPreferences(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
        editor = sharedPreferences.edit();
    }

    public void saveData() {
        previousData = new PreviousData(gioiHanDiem, BackupPoint, BackupLandmark, rorareState, diem, diemMax, arraySo, arraySoLuiBuoc, mangHaiChieu,
                mangHaiChieuLuiBuoc);
        String json = gson.toJson(previousData);
        System.out.println(json);
        editor.putBoolean("flag", flag);
        editor.putString("json", (json));
        editor.commit();
    }


    public void readPreviousData() {
        if (startPreviousGame) {
            System.out.println(sharedPreferences.getBoolean("flag", false));
            String json = sharedPreferences.getString("json", "null");
            this.previousData = gson.fromJson(json, PreviousData.class);

            this.arraySo = previousData.getArraySo();
            this.mangHaiChieu = previousData.getMangHaiChieu();
            this.gioiHanDiem = previousData.isGioiHanDiem();
            this.arraySoLuiBuoc = previousData.getArraySoLuiBuoc();
            this.BackupPoint = previousData.getBackupPoint();
            this.BackupLandmark = previousData.getBackupLandmark();
            this.diem = previousData.getDiem();
            this.diemMax = previousData.getDiemMax();
            this.rorareState = previousData.isRorareState();
            this.gioiHanDiem = previousData.isGioiHanDiem();

            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    System.out.print(previousData.getMangHaiChieu()[i][j] + "");
                }
            }
            this.startPreviousGame = false;
        } else ;
    }

    public void setGioiHanDiem(boolean gioiHanDiem) {
        this.gioiHanDiem = gioiHanDiem;
    }

    public void setEditor(SharedPreferences.Editor editor) {
        this.editor = editor;
    }

    public static dataGame getDataGame() {
        return dataGame;
    }

    public int getBackupPoint() {
        return BackupLandmark;
    }

    public void intt(Context context) {
        BackupLandmark = 2 * 5;
        BackupPoint = 1;// điểm ban đầu có thể quay lại
        flag = true;
        diem = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                mangHaiChieu[i][j] = 0;
                arraySo.add(0);// thêm 16 giá trị vào mảng( gtri ban đầu)
            }
        }
        TypedArray ta = context.getResources().obtainTypedArray(R.array.mauNenCuaSo);//lưu màu nền
        mangMau = new int[ta.length()];
        for (int i = 0; i < ta.length(); i++) {
            mangMau[i] = ta.getColor(i, 0);
        }
        ta.recycle();
        taoSo();
        chuyenDoi();
    }

    public ArrayList<Integer> getArraySo() {
        return arraySo;
    }

    public int colorr(int so) {
        if (so == 0)
            return Color.WHITE;
        else {
            int a = (int) (Math.log(so) / Math.log(2));
            return mangMau[a - 1];
        }
    }

    public void SaveData(boolean rorareState) {
        this.rorareState = rorareState;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                mangHaiChieuLuiBuoc[i][j] = mangHaiChieu[i][j];
            }
        }
    }

    public void Delete2048() {
        if (gioiHanDiem) {
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    if (mangHaiChieu[i][j] == 64) {
                        mangHaiChieu[i][j] = 0;
                    }
                }
            }
        } else ;
    }

    public void backupData() {
        if (diem >= BackupLandmark) {
            if (this.rorareState == true) {
                for (int i = 0; i < 4; i++) {
                    for (int j = 0; j < 4; j++) {
                        mangHaiChieu[i][j] = mangHaiChieuLuiBuoc[i][j];
                    }
                }
                BackupLandmark = diem;
                this.diem = diem / 2;
                chuyenDoi();
                SaveData(false);
            }
        } else ;
    }

    public void taoSo() {
        int so0 = 0;// số các số 0 trong ma trận
//        for (int i = 0; i < 16; i++) {
//            if (arraySo.get(i)%(32)==1) {
//                BackupPoint++;
//            }
//        }
        for (int i = 0; i < 16; i++) {
            if (arraySo.get(i) == 0) {
                so0++;
            }
        }
        int soOTao;
        if (so0 > 1) {
            soOTao = r.nextInt(2) + 1;//tạo ngẫu nhiên 1 hay 2 số
        } else {
            if (so0 == 1) {
                soOTao = 1;
            } else {
                soOTao = 0;
            }
        }
        while (soOTao != 0) {
            int i = r.nextInt(4), j = r.nextInt(4);
            if (mangHaiChieu[i][j] == 0) {
                mangHaiChieu[i][j] = (new Random().nextInt(2) + 1) * 2;//taọ số bất kì từ 2 4
//                mangHaiChieu[i][j] = 1024;
//                mangHaiChieu[i][j] = 32768;
                diem += mangHaiChieu[i][j];
                System.out.println("this is a message!");
                soOTao--;
            }
        }
    }

    public void chuyenDoi() {
        readPreviousData();
        arraySo.clear();
        Delete2048();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                arraySo.add(mangHaiChieu[i][j]);
            }
        }
        saveData();
        readPreviousData();
    }

    public void vuotPhai() {
        SaveData(true);
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                int so = mangHaiChieu[i][j];
                if (so == 0)
                    continue;
                else {
                    for (int k = j + 1; k < 4; k++) {
                        int sox = mangHaiChieu[i][k];
                        if (sox == 0) continue;
                        else {
                            if (sox == so) {
                                mangHaiChieu[i][j] = so * 2;
                                diem += mangHaiChieu[i][j];
                                mangHaiChieu[i][k] = 0;
                                break;
                            } else {
                                break;
                            }
                        }
                    }
                }
            }
        }
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                int so = mangHaiChieu[i][j];
                if (so == 0) {
                    for (int k = j + 1; k < 4; k++) {
                        int so1 = mangHaiChieu[i][k];
                        if (so1 == 0) {
                            continue;
                        } else {
                            mangHaiChieu[i][j] = mangHaiChieu[i][k];
                            mangHaiChieu[i][k] = 0;
                            break;
                        }
                    }
                }
            }
        }
        taoSo();
        chuyenDoi();
    }

    public void vuotTrai() {
        SaveData(true);
        for (int i = 3; i >= 0; i--) {
            for (int j = 3; j >= 0; j--) {
                int so = mangHaiChieu[i][j];
                if (so == 0)
                    continue;
                else {
                    for (int k = j - 1; k >= 0; k--) {
                        int sox = mangHaiChieu[i][k];
                        if (sox == 0) continue;
                        else {
                            if (sox == so) {
                                mangHaiChieu[i][j] = so * 2;
                                mangHaiChieu[i][k] = 0;
                                diem += mangHaiChieu[i][j];
                                break;
                            } else {
                                break;
                            }
                        }
                    }
                }
            }
        }
        for (int i = 3; i >= 0; i--) {
            for (int j = 3; j >= 0; j--) {
                int so = mangHaiChieu[i][j];
                if (so == 0) {
                    for (int k = j - 1; k >= 0; k--) {
                        int so1 = mangHaiChieu[i][k];
                        if (so1 == 0) {
                            continue;
                        } else {
                            mangHaiChieu[i][j] = mangHaiChieu[i][k];
                            mangHaiChieu[i][k] = 0;
                            break;
                        }
                    }
                }
            }
        }
        taoSo();
        chuyenDoi();
    }

    public void vuotXuong() {
        SaveData(true);
        for (int i = 3; i >= 0; i--) {
            for (int j = 3; j >= 0; j--) {
                int so = mangHaiChieu[j][i];
                if (so == 0)
                    continue;
                else {
                    for (int k = j - 1; k >= 0; k--) {
                        int sox = mangHaiChieu[k][i];
                        if (sox == 0) continue;
                        else {
                            if (sox == so) {
                                mangHaiChieu[j][i] = so * 2;
                                mangHaiChieu[k][i] = 0;
                                diem += mangHaiChieu[i][j];
                                break;
                            } else {
                                break;
                            }
                        }
                    }
                }
            }
        }
        for (int i = 3; i >= 0; i--) {
            for (int j = 3; j >= 0; j--) {
                int so = mangHaiChieu[j][i];
                if (so == 0) {
                    for (int k = j - 1; k >= 0; k--) {
                        int so1 = mangHaiChieu[k][i];
                        if (so1 == 0) {
                            continue;
                        } else {
                            mangHaiChieu[j][i] = mangHaiChieu[k][i];
                            mangHaiChieu[k][i] = 0;
                            break;
                        }
                    }
                }
            }
        }
        taoSo();
        chuyenDoi();
    }

    public void vuotLen() {
        SaveData(true);
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                int so = mangHaiChieu[j][i];
                if (so == 0)
                    continue;
                else {
                    for (int k = j + 1; k < 4; k++) {
                        int sox = mangHaiChieu[k][i];
                        if (sox == 0) continue;
                        else {
                            if (sox == so) {
                                mangHaiChieu[j][i] = so * 2;
                                mangHaiChieu[k][i] = 0;
                                diem += mangHaiChieu[i][j];
                                break;
                            } else {
                                break;
                            }
                        }
                    }
                }
            }
        }
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                int so = mangHaiChieu[j][i];
                if (so == 0) {
                    for (int k = j + 1; k < 4; k++) {
                        int so1 = mangHaiChieu[k][i];
                        if (so1 == 0) {
                            continue;
                        } else {
                            mangHaiChieu[j][i] = mangHaiChieu[k][i];
                            mangHaiChieu[k][i] = 0;
                            break;
                        }
                    }
                }
            }
        }
        taoSo();
        chuyenDoi();
    }

    public int getDiem() {
        return diem;
    }

    public boolean KiemTraChienThang() {
        System.out.println("checkwon");
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
//                System.out.println(mangHaiChieu[i][j]+"");
                if (mangHaiChieu[i][j] == 65536)
                    return true;
            }
        }
        return false;
    }

    public boolean kiemTra() {
        int so0 = 0;
        for (int i = 0; i < 16; i++) {
            if (arraySo.get(i) == 0) {
                so0++;
            }
        }
        if (so0 != 0) return true;
        System.out.println("so 0: " + so0);
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 3; j++) {
                int so = mangHaiChieu[i][j];
                for (int k = j + 1; k < 4; k++) {
                    int sox = mangHaiChieu[i][k];
                    if (sox == 0) continue;
                    else {
                        if (sox == so) {
                            System.out.println("i:" + i);
                            System.out.println("j:" + j);
                            System.out.println("k:" + k);
                            System.out.println("sox:" + sox);
                            System.out.println("so:" + so);
                            System.out.println("tag1");
                            return true;
                        } else {
                            break;
                        }
                    }
                }

            }
        }
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 3; j++) {
                int so = mangHaiChieu[i][j];
                for (int k = i + 1; k < 4; k++) {
                    int sox = mangHaiChieu[k][j];
                    if (sox == 0) continue;
                    else {
                        if (sox == so) {
                            System.out.println("tag2");
                            return true;
                        } else {
                            break;
                        }
                    }
                }

            }
        }
        flag = false;
        return false;
    }

    public void setDiemMax(int diemMax) {
        this.diemMax = diemMax;
    }

    public int getDiemMax() {
        return diemMax;
    }
}