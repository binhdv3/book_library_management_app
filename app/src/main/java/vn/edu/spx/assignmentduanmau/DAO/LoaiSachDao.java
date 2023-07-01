package vn.edu.spx.assignmentduanmau.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import vn.edu.spx.assignmentduanmau.DbHelper.DatabaseSQLiteHelper;
import vn.edu.spx.assignmentduanmau.Object.LoaiSach;

public class LoaiSachDao {
    private SQLiteDatabase db;

    public LoaiSachDao(Context context){
        DatabaseSQLiteHelper helper = new DatabaseSQLiteHelper(context);
        db = helper.getWritableDatabase();
    }

    public List<LoaiSach> getAll(){
        String sql = "SELECT * FROM tableLoaiSach";
        return getData(sql);
    }

    public LoaiSach getId(String id){
        String sql = "SELECT * FROM tableLoaiSach WHERE maLoai=?";
        List<LoaiSach> list = getData(sql,id);
        return list.get(0);
    }

    //get data theo tham so
    private List<LoaiSach> getData(String sql, String...selectionsArgs){
        List<LoaiSach> list = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, selectionsArgs);
        if(cursor.moveToFirst()){
            while (!cursor.isAfterLast()){
                LoaiSach loaiSach = new LoaiSach();
                loaiSach.setMaLoai(cursor.getInt(0));
                loaiSach.setTenLoai(cursor.getString(1));
                loaiSach.setNhaCC(cursor.getString(2));
                list.add(loaiSach);
                cursor.moveToNext();
            }
        }
        return list;
    }

    public long insert(LoaiSach loaiSachObj){
        ContentValues values = new ContentValues();
        values.put("tenLoai",loaiSachObj.getTenLoai());
        values.put("nhaCC",loaiSachObj.getNhaCC());
        return db.insert("tableLoaiSach",null, values);
    }

    public int update(LoaiSach loaiSachObj){
        ContentValues values = new ContentValues();
        values.put("tenLoai",loaiSachObj.getTenLoai());
        values.put("nhaCC",loaiSachObj.getNhaCC());
        return db.update("tableLoaiSach",values,"maLoai=?",new String[]{String.valueOf(loaiSachObj.getMaLoai())});
    }

    public int delete(String id){
        return db.delete("tableLoaiSach","maLoai=?", new String[]{id});
    }

    public ArrayList<LoaiSach> getSearchNameNhacc(String name){
        ArrayList<LoaiSach> list = new ArrayList<>();
        String sql = "select * from tableLoaiSach where nhaCC=?";
        list = (ArrayList<LoaiSach>) getData(sql,name);
        return list;
    }

}
