package vn.edu.spx.assignmentduanmau.DbHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseSQLiteHelper extends SQLiteOpenHelper {

    static final  String dbName = "PNlib";
    static final int dbVersion = 1;

    public  DatabaseSQLiteHelper(Context context){
        super(context,dbName,null,dbVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //create table thuthu ---
        String tableThuThu = " CREATE TABLE tableThuThu(" +
                "maTT TEXT PRIMARY KEY," +
                "hoTenTT TEXT NOT NULL," +
                "mauKhauTT TEXT NOT NULL)";
        db.execSQL(tableThuThu);

        // create table thanh vien ---
        String tableThanhVien = "CREATE TABLE tableThanhVien(" +
                "maTV INTEGER PRIMARY KEY AUTOINCREMENT," +
                "hoTen TEXT NOT NULL," +
                "namSinh TEXT NOT NULL)";
        db.execSQL(tableThanhVien);

        // create table loai sach---
        String tableLoaiSach = "CREATE TABLE tableLoaiSach(" +
                "maLoai INTEGER PRIMARY KEY AUTOINCREMENT," +
                "tenLoai TEXT NOT NULl)";
        db.execSQL(tableLoaiSach);

        // create table Sach ---
        String tableSach = "CREATE TABLE tableSach(" +
                "maSach INTEGER PRIMARY KEY AUTOINCREMENT," +
                "tenSach TEXT NOT NULL," +
                "giaThue INTEGER NOT NULL," +
                "maLoai INTEGER REFERENCES tableLoaiSach(maLoai))";
        db.execSQL(tableSach);

        // create table Phieu Muon
        String tablePhieuMuon = "CREATE TABLE tablePhieuMuon(" +
                "maPM INTEGER PRIMARY KEY AUTOINCREMENT," +
                "maTT TEXT REFERENCES tableThuThu(maTT)," +
                "maTV INTEGER REFERENCES tableThanhVien(maTV)," +
                "maSach INTEGER REFERENCES tableSach(maSach)," +
                "tienThue INTEGER NOT NULL," +
                "ngay DATE NOT NULL," +
                "traSach INTEGER NOT NULL)";
        db.execSQL(tablePhieuMuon);
        //insert
        String insertThuThu = "insert into tableThuThu values('admin','Dao Van Binh','binh123')";
        db.execSQL(insertThuThu);
        //insert colum table sach
        String insertClSoTrang = "ALTER TABLE tableSach ADD COLUMN soTrang INTEGER";
        db.execSQL(insertClSoTrang);
        //insert colum nhacc table loai sach
        String insertNhacc = "ALTER TABLE tableLoaiSach ADD COLUMN nhaCC TEXT";
        db.execSQL(insertNhacc);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String dropTableThuThu = " drop table if exists tableThuThu";
        db.execSQL(dropTableThuThu);
        String dropTableThanhVien = " drop table if exists tableThanhVien";
        db.execSQL(dropTableThanhVien);
        String dropTableLoaiSach = " drop table if exists tableLoaiSach";
        db.execSQL(dropTableLoaiSach);
        String dropTableSach = " drop table if exists tableSach";
        db.execSQL(dropTableSach);
        String dropTablePhieuMuon = " drop table if exists tablePhieuMuon";
        db.execSQL(dropTablePhieuMuon);
        onCreate(db);
    }
}
