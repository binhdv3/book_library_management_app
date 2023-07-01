package vn.edu.spx.assignmentduanmau.DAO;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import vn.edu.spx.assignmentduanmau.DbHelper.DatabaseSQLiteHelper;
import vn.edu.spx.assignmentduanmau.Object.Sach;
import vn.edu.spx.assignmentduanmau.Object.Top;

public class ThongKeDao {
    private SQLiteDatabase db;
    private Context context;

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public ThongKeDao(Context context){
        this.context = context;
        DatabaseSQLiteHelper helper = new DatabaseSQLiteHelper(context);
        db = helper.getWritableDatabase();
    }

    //thong ke top10---
    @SuppressLint("Range")
    public List<Top> getTop(){
        String sqlTop = "SELECT maSach, count(maSach) as soLuong FROM tablePhieuMuon GROUP BY maSach ORDER BY soLuong DESC LIMIT 10";
        List<Top> listTop = new ArrayList<>();
        SachDao sachDao = new SachDao(context);
        Cursor cursor = db.rawQuery(sqlTop,null);
        while (cursor.moveToNext()){
            Top top = new Top();
            Sach sach = sachDao.getId(String.valueOf(cursor.getInt(0)));
            top.setTenSach(sach.getTenSach());
            top.setSoLuong(Integer.parseInt(cursor.getString(cursor.getColumnIndex("soLuong"))));
            listTop.add(top);
        }
        return listTop;
    }

    //thong ke doanh thu----
    @SuppressLint("Range")
    public int getDanhThu(String tuNgay, String denNgay){
        String sqlDoanthu = "SELECT SUM(tienThue) as doanhThu FROM tablePhieuMuon WHERE ngay BETWEEN ? AND ?";
        List<Integer> list = new ArrayList<>();
        Cursor cursor = db.rawQuery(sqlDoanthu,new String[]{tuNgay,denNgay});
        while(cursor.moveToNext()) {
            try{
                list.add(Integer.parseInt(cursor.getString(cursor.getColumnIndex("doanhThu"))));
            }catch (Exception e){
                list.add(0);
            }
        }
        return list.get(0);
    }

}
