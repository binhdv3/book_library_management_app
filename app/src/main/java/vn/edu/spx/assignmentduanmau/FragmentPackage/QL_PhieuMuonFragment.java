package vn.edu.spx.assignmentduanmau.FragmentPackage;

import static java.time.LocalDateTime.now;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import vn.edu.spx.assignmentduanmau.Adrapter.LoaiSachSpinnerAdrapter;
import vn.edu.spx.assignmentduanmau.Adrapter.PhieuMuonAdrapter;
import vn.edu.spx.assignmentduanmau.Adrapter.SachAdrapter;
import vn.edu.spx.assignmentduanmau.Adrapter.SachSpinnerAdrapter;
import vn.edu.spx.assignmentduanmau.Adrapter.ThanhVienSpinnerAdrapter;
import vn.edu.spx.assignmentduanmau.DAO.LoaiSachDao;
import vn.edu.spx.assignmentduanmau.DAO.PhieuMuonDao;
import vn.edu.spx.assignmentduanmau.DAO.SachDao;
import vn.edu.spx.assignmentduanmau.DAO.ThanhVienDao;
import vn.edu.spx.assignmentduanmau.Object.LoaiSach;
import vn.edu.spx.assignmentduanmau.Object.PhieuMuon;
import vn.edu.spx.assignmentduanmau.Object.Sach;
import vn.edu.spx.assignmentduanmau.Object.ThanhVien;
import vn.edu.spx.assignmentduanmau.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link QL_PhieuMuonFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QL_PhieuMuonFragment extends Fragment {

    ListView lvPM;
    FloatingActionButton fabOpenDialogPM;
    ArrayList<Sach> listS;
    ArrayList<ThanhVien> listTV;
    ArrayList<PhieuMuon> listPM;
    Dialog dialog;
    EditText edMaPM;
    TextView tvNgay, tvTienThue;
    CheckBox chkTrasach;
    Button btnSave;
    Spinner spinnerSach,spinnerThanhVien;
    static PhieuMuonDao phieuMuonDao;
    PhieuMuon phieuMuon;
    PhieuMuonAdrapter phieuMuonAdrapter;
    SachSpinnerAdrapter sachSpinnerAdrapter;
    ThanhVienSpinnerAdrapter thanhVienSpinnerAdrapter;
    SachDao sachDao;
    Sach sach;
    ThanhVien thanhVien;
    ThanhVienDao thanhVienDao;
    int maS,tienthue, postionTV, postionS, maTV;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
    String ngay;

    public QL_PhieuMuonFragment() {
        // Required empty public constructor
    }

    public static QL_PhieuMuonFragment newInstance() {
        QL_PhieuMuonFragment fragment = new QL_PhieuMuonFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_q_l__phieu_muon, container, false);
        lvPM = v.findViewById(R.id.lv_phieumuon);
        fabOpenDialogPM = v.findViewById(R.id.fab_opendialog_pm);
        phieuMuonDao = new PhieuMuonDao(getActivity());
        phieuMuon = new PhieuMuon();
        //----set listView----
        capnhatLv();
        //---code---
        fabOpenDialogPM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                themPM();
            }
        });

        lvPM.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                phieuMuon = listPM.get(position);
                dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.dialog_phieumuon);
                edMaPM = dialog.findViewById(R.id.ed_maPM);
                tvNgay = dialog.findViewById(R.id.tv_ngaythue);
                tvTienThue = dialog.findViewById(R.id.tv_tienthue);
                chkTrasach = dialog.findViewById(R.id.chk_trasach);
                btnSave = dialog.findViewById(R.id.btn_save_pm);
                spinnerSach = dialog.findViewById(R.id.sp_sach);
                spinnerThanhVien = dialog.findViewById(R.id.sp_thanhvien);
                //------
                listTV = new ArrayList<ThanhVien>();
                thanhVienDao = new ThanhVienDao(getActivity());
                listTV = (ArrayList<ThanhVien>) thanhVienDao.getAll();
                thanhVienSpinnerAdrapter = new ThanhVienSpinnerAdrapter(getActivity(),listTV);
                spinnerThanhVien.setAdapter(thanhVienSpinnerAdrapter);
                //---
                spinnerThanhVien.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        maTV = listTV.get(position).getMaTV();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                //------
                listS = new ArrayList<Sach>();
                sachDao = new SachDao(getActivity());
                listS = (ArrayList<Sach>) sachDao.getAll();
                sachSpinnerAdrapter = new SachSpinnerAdrapter(getActivity(),listS);
                spinnerSach.setAdapter(sachSpinnerAdrapter);
                //---
                spinnerSach.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        maS = listS.get(position).getMaSach();
                        tienthue = listS.get(position).getGiaThue();
                        tvTienThue.setText("Tiền thuê: "+tienthue);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                edMaPM.setEnabled(false);
                edMaPM.setText(String.valueOf(phieuMuon.getMaPM()));
                for(int i=0; i<listTV.size();i++) {
                    if (phieuMuon.getMaTV() == listTV.get(i).getMaTV()) {
                        postionTV = i;
                    }
                }
                spinnerThanhVien.setSelection(postionTV);
                ///--------------------
                for(int i=0; i<listS.size();i++) {
                    if (phieuMuon.getMaSach() == listS.get(i).getMaSach()) {
                        postionS = i;
                    }
                }
                spinnerThanhVien.setSelection(postionS);
                tvNgay.setText("Ngày thuê: "+simpleDateFormat.format(phieuMuon.getNgay()));
                if(phieuMuon.getTraSach() == 1){
                    chkTrasach.setChecked(true);
                }else {
                    chkTrasach.setChecked(false);
                }

                btnSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        phieuMuon = new PhieuMuon();
                        phieuMuon.setMaPM(Integer.parseInt(edMaPM.getText().toString()));
                        phieuMuon.setMaTV(maTV);
                        phieuMuon.setMaSach(maS);
                        phieuMuon.setNgay(new Date());
                        phieuMuon.setTienThue(tienthue);
                        if(chkTrasach.isChecked()){
                            phieuMuon.setTraSach(1);
                        }else {
                            phieuMuon.setTraSach(0);
                        }
                        if (phieuMuonDao.update(phieuMuon)>0){
                            Toast.makeText(getActivity(), "Cap nhat thanh cong", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(getActivity(), "Cap nhat that bai", Toast.LENGTH_SHORT).show();
                        }
                        capnhatLv();
                        dialog.dismiss();
                    }
                });
                dialog.show();
                return false;
            }
        });
        return v;
    }


    protected void themPM(){
        dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.dialog_phieumuon);
        edMaPM = dialog.findViewById(R.id.ed_maPM);
        tvNgay = dialog.findViewById(R.id.tv_ngaythue);
        tvTienThue = dialog.findViewById(R.id.tv_tienthue);
        chkTrasach = dialog.findViewById(R.id.chk_trasach);
        btnSave = dialog.findViewById(R.id.btn_save_pm);
        spinnerSach = dialog.findViewById(R.id.sp_sach);
        spinnerThanhVien = dialog.findViewById(R.id.sp_thanhvien);
        //------
        listTV = new ArrayList<ThanhVien>();
        thanhVienDao = new ThanhVienDao(getActivity());
        listTV = (ArrayList<ThanhVien>) thanhVienDao.getAll();
        thanhVienSpinnerAdrapter = new ThanhVienSpinnerAdrapter(getActivity(),listTV);
        spinnerThanhVien.setAdapter(thanhVienSpinnerAdrapter);
        //---
        spinnerThanhVien.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                maTV = listTV.get(position).getMaTV();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //------
        listS = new ArrayList<Sach>();
        sachDao = new SachDao(getContext());
        listS = (ArrayList<Sach>) sachDao.getAll();
        sachSpinnerAdrapter = new SachSpinnerAdrapter(getContext(),listS);
        spinnerSach.setAdapter(sachSpinnerAdrapter);
        //---
        spinnerSach.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                maS = listS.get(position).getMaSach();
                tienthue = listS.get(position).getGiaThue();
                tvTienThue.setText("Tiền thuê: "+tienthue);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        edMaPM.setEnabled(false);

        //----------------save-----------
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phieuMuon = new PhieuMuon();
                phieuMuon.setMaTV(maTV);
                phieuMuon.setMaSach(maS);
                phieuMuon.setNgay(new Date());
                phieuMuon.setTienThue(tienthue);

                if(chkTrasach.isChecked()==true){
                    phieuMuon.setTraSach(1);
                }else {
                    phieuMuon.setTraSach(0);
                }
                //----
                if(phieuMuonDao.insert(phieuMuon)>0){
                    Toast.makeText(getActivity(), "Them thanh cong", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getActivity(), "Them that bai", Toast.LENGTH_SHORT).show();
                }
                listPM.add(phieuMuon);
                capnhatLv();
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void capnhatLv() {
        listPM = (ArrayList<PhieuMuon>) phieuMuonDao.getAll();
        phieuMuonAdrapter = new PhieuMuonAdrapter(getActivity(),this,listPM);
        lvPM.setAdapter(phieuMuonAdrapter);
    }

    public void deletePM(final String id){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Delete");
        builder.setMessage("Có xoá không");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                phieuMuonDao.delete(id);
                capnhatLv();
                Toast.makeText(getContext(), "Xoa thanh cong", Toast.LENGTH_SHORT).show();
                dialog.cancel();
            }
        });
        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

}