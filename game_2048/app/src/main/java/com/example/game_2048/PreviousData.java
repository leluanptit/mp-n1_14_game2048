package com.example.game_2048;

import java.io.Serializable;
import java.util.ArrayList;

public class PreviousData implements Serializable {
    private boolean gioiHanDiem = false;
    private int BackupPoint;
    private int BackupLandmark;
    private boolean rorareState;
    private int diem;
    private int diemMax;
    private ArrayList<Integer> arraySo = new ArrayList<>();
    private ArrayList<Integer> arraySoLuiBuoc = new ArrayList<>();
    private int[][] mangHaiChieu = new int[4][4];
    private int[][] mangHaiChieuLuiBuoc = new int[4][4];

    public PreviousData(boolean gioiHanDiem, int backupPoint, int backupLandmark, boolean rorareState, int diem,
                        int diemMax, ArrayList<Integer> arraySo, ArrayList<Integer> arraySoLuiBuoc, int[][] mangHaiChieu,
                        int[][] mangHaiChieuLuiBuoc) {
        this.gioiHanDiem = gioiHanDiem;
        BackupPoint = backupPoint;
        BackupLandmark = backupLandmark;
        this.rorareState = rorareState;
        this.diem = diem;
        this.diemMax = diemMax;
        this.arraySo = arraySo;
        this.arraySoLuiBuoc = arraySoLuiBuoc;
        this.mangHaiChieu = mangHaiChieu;
        this.mangHaiChieuLuiBuoc = mangHaiChieuLuiBuoc;
    }

    public boolean isGioiHanDiem() {
        return gioiHanDiem;
    }

    public void setGioiHanDiem(boolean gioiHanDiem) {
        this.gioiHanDiem = gioiHanDiem;
    }

    public int getBackupPoint() {
        return BackupPoint;
    }

    public void setBackupPoint(int backupPoint) {
        BackupPoint = backupPoint;
    }

    public int getBackupLandmark() {
        return BackupLandmark;
    }

    public void setBackupLandmark(int backupLandmark) {
        BackupLandmark = backupLandmark;
    }

    public boolean isRorareState() {
        return rorareState;
    }

    public void setRorareState(boolean rorareState) {
        this.rorareState = rorareState;
    }

    public int getDiem() {
        return diem;
    }

    public void setDiem(int diem) {
        this.diem = diem;
    }

    public int getDiemMax() {
        return diemMax;
    }

    public void setDiemMax(int diemMax) {
        this.diemMax = diemMax;
    }

    public ArrayList<Integer> getArraySo() {
        return arraySo;
    }

    public void setArraySo(ArrayList<Integer> arraySo) {
        this.arraySo = arraySo;
    }

    public ArrayList<Integer> getArraySoLuiBuoc() {
        return arraySoLuiBuoc;
    }

    public void setArraySoLuiBuoc(ArrayList<Integer> arraySoLuiBuoc) {
        this.arraySoLuiBuoc = arraySoLuiBuoc;
    }

    public int[][] getMangHaiChieu() {
        return mangHaiChieu;
    }

    public void setMangHaiChieu(int[][] mangHaiChieu) {
        this.mangHaiChieu = mangHaiChieu;
    }

    public int[][] getMangHaiChieuLuiBuoc() {
        return mangHaiChieuLuiBuoc;
    }

    public void setMangHaiChieuLuiBuoc(int[][] mangHaiChieuLuiBuoc) {
        this.mangHaiChieuLuiBuoc = mangHaiChieuLuiBuoc;
    }

}
