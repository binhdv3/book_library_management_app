package vn.edu.spx.assignmentduanmau.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;

import java.util.ArrayList;
import java.util.List;

import vn.edu.spx.assignmentduanmau.DbHelper.DatabaseSQLiteHelper;
import vn.edu.spx.assignmentduanmau.Object.ThuThu;

public class ThuThuDao {
    private SQLiteDatabase db;

    public ThuThuDao(Context context){
        DatabaseSQLiteHelper helper = new DatabaseSQLiteHelper(context);
        db = helper.getWritableDatabase();
    }

    //get all---
    public List<ThuThu> getAll(){
        String sql = "SELECT * FROM tableThuThu";
        return getData(sql);
    }

    //get from id---
    public ThuThu getId(String id){
        String sql = "SELECT * FROM tableThuThu WHERE maTT=?";
        List<ThuThu> list = getData(sql,id);
        return list.get(0);
    }

    //get all data theo tham so---
    private List<ThuThu> getData(String sql,String...selectionsArgs){
        List<ThuThu> list = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql,selectionsArgs);
        if(cursor.moveToFirst()){
            while(!cursor.isAfterLast()){
                ThuThu thuThu = new ThuThu();
                thuThu.setMaTT(cursor.getString(0));
                thuThu.setHoTen(cursor.getString(1));
                thuThu.setMatKhau(cursor.getString(2));
                list.add(thuThu);
                cursor.moveToNext();
            }
        }
        return list;
    }

    public long insert(ThuThu thuThuObj){
        ContentValues values = new ContentValues();
        values.put("maTT",thuThuObj.getMaTT());
        values.put("hoTenTT",thuThuObj.getHoTen());
        values.put("mauKhauTT",thuThuObj.getMatKhau());
        return db.insert("tableThuThu",null,values);
    }

    public int update(ThuThu thuThuObj){
        ContentValues values = new ContentValues();
        values.put("maTT",thuThuObj.getMaTT());
        values.put("hoTenTT",thuThuObj.getHoTen());
        values.put("mauKhauTT",thuThuObj.getMatKhau());
        return db.update("tableThuThu",values,"maTT=?",new String[]{thuThuObj.getMaTT()});
    }

    public int delete(String id){
        return db.delete("tableThuThu","maTT=?",new String[]{id});
    }

    //check login---
    public int checkLogin(String id, String passWork){
        String sql = "SELECT * FROM tableThuThu WHERE maTT=? AND mauKhauTT=?";
        List<ThuThu> listTT = getData(sql,id,passWork);
        if (listTT.size() == 0){ //neu k co user va pass
            return -1;
        }
        return 1;
    }

}
