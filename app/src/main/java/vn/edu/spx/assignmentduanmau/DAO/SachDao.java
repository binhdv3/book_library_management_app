package vn.edu.spx.assignmentduanmau.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import vn.edu.spx.assignmentduanmau.DbHelper.DatabaseSQLiteHelper;
import vn.edu.spx.assignmentduanmau.Object.LoaiSach;
import vn.edu.spx.assignmentduanmau.Object.Sach;

public class SachDao {

    private SQLiteDatabase db;

    public SachDao(Context context){
        DatabaseSQLiteHelper helper = new DatabaseSQLiteHelper(context);
        db = helper.getWritableDatabase();
    }

    public List<Sach> getAll(){
        String sql = "SELECT * FROM tableSach";
        return getData(sql);
    }

    public Sach getId(String id){
        String sql = "SELECT * FROM tableSach WHERE maSach=?";
        List<Sach> list = getData(sql,id);
        return list.get(0);
    }

    //get data theo tham so
    private List<Sach> getData(String sql, String...selectionsArgs){
        List<Sach> list = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, selectionsArgs);
        if(cursor.moveToFirst()){
            while (!cursor.isAfterLast()){
                Sach sach = new Sach();
                sach.setMaSach(cursor.getInt(0));
                sach.setTenSach(cursor.getString(1));
                sach.setGiaThue(cursor.getInt(2));
                sach.setMaLoai(cursor.getInt(3));
                sach.setSoTrang(cursor.getInt(4));
                list.add(sach);
                cursor.moveToNext();
            }
        }
        return list;
    }

    public long insert(Sach sachObj){
        ContentValues values = new ContentValues();
        values.put("tenSach",sachObj.getTenSach());
        values.put("giaThue",sachObj.getGiaThue());
        values.put("maLoai",sachObj.getMaLoai());
        values.put("soTrang",sachObj.getSoTrang());
        return db.insert("tableSach",null, values);
    }

    public int update(Sach sachObj){
        ContentValues values = new ContentValues();
        values.put("tenSach",sachObj.getTenSach());
        values.put("giaThue",sachObj.getGiaThue());
        values.put("maLoai",sachObj.getMaLoai());
        values.put("soTrang",sachObj.getSoTrang());
        return db.update("tableSach",values,"maSach=?",new String[]{String.valueOf(sachObj.getMaSach())});
    }

    public int delete(String id){
        return db.delete("tableSach","maSach=?", new String[]{id});
    }

    //getsearch----
    public ArrayList<Sach> getSearchSoTrang(String number){
        ArrayList<Sach> list = new ArrayList<>();
        String sql = "select * from tableSach where soTrang > ?";
        list = (ArrayList<Sach>) getData(sql,number);
        return list;
    }

}
