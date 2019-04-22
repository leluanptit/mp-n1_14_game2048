package com.example.game_2048;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.preference.Preference;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class dataGame {
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

    static {
        dataGame = new dataGame();
    }

    public static dataGame getDataGame() {
        return dataGame;
    }

    public int getBackupPoint() {
        return BackupLandmark;
    }

    public void intt(Context context) {
        BackupLandmark = 2 * 5;
        BackupPoint = 1;
        diem = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                mangHaiChieu[i][j] = 0;
                arraySo.add(0);
            }
        }
        TypedArray ta = context.getResources().obtainTypedArray(R.array.mauNenCuaSo);
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
        }else;
    }

    public void taoSo() {
        int so0 = 0;
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
            soOTao = r.nextInt(2) + 1;
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
                mangHaiChieu[i][j] = (new Random().nextInt(2) + 1) * 2;
//                mangHaiChieu[i][j] = 32768;
                diem += mangHaiChieu[i][j];
                System.out.println("this is a message!");
                soOTao--;
            }
        }
    }

    public void chuyenDoi() {
        arraySo.clear();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                arraySo.add(mangHaiChieu[i][j]);
            }
        }

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
        return false;
    }

    public void setDiemMax(int diemMax) {
        this.diemMax = diemMax;
    }

    public int getDiemMax() {
        return diemMax;
    }
}