package vn.edu.spx.assignmentduanmau.Adrapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import vn.edu.spx.assignmentduanmau.DAO.LoaiSachDao;
import vn.edu.spx.assignmentduanmau.DAO.PhieuMuonDao;
import vn.edu.spx.assignmentduanmau.DAO.SachDao;
import vn.edu.spx.assignmentduanmau.DAO.ThanhVienDao;
import vn.edu.spx.assignmentduanmau.FragmentPackage.QL_LoaiSachFragment;
import vn.edu.spx.assignmentduanmau.FragmentPackage.QL_PhieuMuonFragment;
import vn.edu.spx.assignmentduanmau.Object.LoaiSach;
import vn.edu.spx.assignmentduanmau.Object.PhieuMuon;
import vn.edu.spx.assignmentduanmau.Object.Sach;
import vn.edu.spx.assignmentduanmau.Object.ThanhVien;
import vn.edu.spx.assignmentduanmau.R;

public class PhieuMuonAdrapter extends ArrayAdapter<PhieuMuon> {

    private Context context;
    QL_PhieuMuonFragment fragment;
    private ArrayList<PhieuMuon> lists;
    ArrayList<Sach> listSach;
    ArrayList<ThanhVien> listTV;
    TextView tvMaPM, tvmaS,tvmaTV,tvTienthue,tvNgay,tvTrasach,tvDelPM;
    SachDao sachDao;
    ThanhVienDao thanhVienDao;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    PhieuMuonDao phieuMuonDao;

    public PhieuMuonAdrapter(@NonNull Context context,QL_PhieuMuonFragment fragment, @NonNull ArrayList<PhieuMuon> lists) {
        super(context, 0, lists);
        this.context = context;
        this.lists = lists;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if(v == null){
            LayoutInflater inflater = (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.item_phieumuon,null);
        }
        final PhieuMuon phieuMuon = lists.get(position);
        thanhVienDao = new ThanhVienDao(context);
        sachDao = new SachDao(context);

        ThanhVien thanhVien = thanhVienDao.getId(String.valueOf(phieuMuon.getMaTV()));
        Sach sach = sachDao.getId(String.valueOf(phieuMuon.getMaSach()));
        phieuMuonDao = new PhieuMuonDao(v.getContext());

        tvMaPM = v.findViewById(R.id.tv_maPM);
        tvmaS =v.findViewById(R.id.tv_maS_pm);
        tvmaTV = v.findViewById(R.id.tv_maTV_pm);
        tvTienthue = v.findViewById(R.id.tv_tienthue_pm);
        tvNgay =v.findViewById(R.id.tv_ngaythue_pm);
        tvTrasach = v.findViewById(R.id.tv_trasach_pm);

        if(phieuMuon != null){ //nếu đối tượng k rỗng

            tvMaPM.setText("Mã phiếu mượn: "+phieuMuon.getMaPM());
            tvmaS.setText("Tên sách: "+sach.getTenSach());
            tvmaTV.setText("Tên thành viên: "+thanhVien.getHoTen());
            tvTienthue.setText("Tiền thuê "+phieuMuon.getTienThue());
            tvNgay.setText("Ngày: "+simpleDateFormat.format(phieuMuon.getNgay()));
            if(phieuMuon.getTraSach() == 1){
                tvTrasach.setText("Đã trả sách");
                tvTrasach.setTextColor(Color.GREEN);
            }else if(phieuMuon.getTraSach() == 0) {
                tvTrasach.setText("chưa trả sách");
                tvTrasach.setTextColor(Color.RED);
            }
        }

        tvDelPM = v.findViewById(R.id.tv_deletePM);
        tvDelPM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment.deletePM(String.valueOf(phieuMuon.getMaPM()));
            }
        });
        return v;
    }
}
