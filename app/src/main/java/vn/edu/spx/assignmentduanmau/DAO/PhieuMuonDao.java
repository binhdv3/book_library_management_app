package vn.edu.spx.assignmentduanmau.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import vn.edu.spx.assignmentduanmau.DbHelper.DatabaseSQLiteHelper;
import vn.edu.spx.assignmentduanmau.Object.PhieuMuon;
import vn.edu.spx.assignmentduanmau.Object.ThuThu;

public class PhieuMuonDao {

    private SQLiteDatabase db;
    SimpleDateFormat spf = new SimpleDateFormat("yyyy-MM-dd");

    public PhieuMuonDao(Context context){
        DatabaseSQLiteHelper helper = new DatabaseSQLiteHelper(context);
        db = helper.getWritableDatabase();
    }

    public List<PhieuMuon> getAll(){
        String sql  = "SELECT * FROM tablePhieuMuon";
        return getData(sql);
    }

    public PhieuMuon getId(String id){
        String sql  = "SELECT * FROM tablePhieuMuon WHERE maPM=?";
        List<PhieuMuon> listPM = getData(sql,id);
        return listPM.get(0);
    }

    @SuppressLint("Range")
    private List<PhieuMuon> getData(String sql, String...selectionArgs){
        List<PhieuMuon> list = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql,selectionArgs);
        if(cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                PhieuMuon phieuMuon = new PhieuMuon();
                phieuMuon.setMaPM(cursor.getInt(0));
                phieuMuon.setMaTV(Integer.parseInt(cursor.getString(cursor.getColumnIndex("maTV"))));
                phieuMuon.setMaSach(Integer.parseInt(cursor.getString(cursor.getColumnIndex("maSach"))));
                phieuMuon.setTienThue(Integer.parseInt(cursor.getString(cursor.getColumnIndex("tienThue"))));
                phieuMuon.setNgay(new Date());
                phieuMuon.setTraSach(Integer.parseInt(cursor.getString(cursor.getColumnIndex("traSach"))));
                list.add(phieuMuon);
                cursor.moveToNext();//chuyen den dong tiep theo
            }
        }
        return list;
    }

    public long insert(PhieuMuon phieuMuonObj){
        ContentValues values = new ContentValues();
        values.put("maTV",phieuMuonObj.getMaTV());
        values.put("maSach",phieuMuonObj.getMaSach());
        values.put("tienThue",phieuMuonObj.getTienThue());
        values.put("ngay",String.valueOf(phieuMuonObj.getNgay()));
        values.put("traSach",phieuMuonObj.getTraSach());
        return db.insert("tablePhieuMuon",null,values);
    }

    public int update(PhieuMuon phieuMuonObj){
        ContentValues values = new ContentValues();
        values.put("maTV",phieuMuonObj.getMaTV());
        values.put("maSach",phieuMuonObj.getMaSach());
        values.put("tienThue",phieuMuonObj.getTienThue());
        values.put("ngay",String.valueOf(phieuMuonObj.getNgay()));
        values.put("traSach",phieuMuonObj.getTraSach());
        return db.update("tablePhieuMuon",values,"maPM=?",new String[]{String.valueOf(phieuMuonObj.getMaPM())});
    }

    public int delete(String id){
        return db.delete("tablePhieuMuon","maPM=?",new String[]{id});
    }


}
