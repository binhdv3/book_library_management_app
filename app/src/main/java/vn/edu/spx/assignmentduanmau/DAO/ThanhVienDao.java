package vn.edu.spx.assignmentduanmau.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import vn.edu.spx.assignmentduanmau.DbHelper.DatabaseSQLiteHelper;
import vn.edu.spx.assignmentduanmau.Object.ThanhVien;

public class ThanhVienDao {
    private SQLiteDatabase db;

    public ThanhVienDao(Context context){
        DatabaseSQLiteHelper helper = new DatabaseSQLiteHelper(context);
        db = helper.getWritableDatabase();
    }

    //get all data ---
    public List<ThanhVien> getAll(){
        String sql = "SELECT * FROM tableThanhVien";
        return getData(sql);
    }

    //get data from id ---
    public ThanhVien getId(String id){
        String sql = "SELECT * FROM tableThanhVien WHERE maTV=?";
        List<ThanhVien> list = getData(sql,id);
        return list.get(0);
    }

    //get data tu tham so----
    public List<ThanhVien> getData(String sql, String...selectionArgs){
        List<ThanhVien> list = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql,selectionArgs);
        if (cursor.moveToFirst()){
            while(!cursor.isAfterLast()){
                ThanhVien thanhVien = new ThanhVien();
                thanhVien.setMaTV(cursor.getInt(0));
                thanhVien.setHoTen(cursor.getString(1));
                thanhVien.setNamSinh(cursor.getString(2));
                list.add(thanhVien);
                cursor.moveToNext();
            }
        }
        return list;
    }

    //insert-----
    public long insert(ThanhVien thanhVienObj){
        ContentValues values = new ContentValues();
        values.put("hoTen",thanhVienObj.getHoTen());
        values.put("namSinh",thanhVienObj.getNamSinh());
        return db.insert("tableThanhVien",null, values);
    }

    public int update(ThanhVien thanhVienObj){
        ContentValues values = new ContentValues();
        values.put("hoTen",thanhVienObj.getHoTen());
        values.put("namSinh",thanhVienObj.getNamSinh());
        return db.update("tableThanhVien",values,"maTV=?",new String[]{String.valueOf(thanhVienObj.getMaTV())});
    }

    public int detele(String id){
        return db.delete("tableThanhVien","maTV=?",new String[]{id});
    }
}
