package vn.edu.spx.assignmentduanmau.FragmentPackage;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import vn.edu.spx.assignmentduanmau.Adrapter.LoaiSachSpinnerAdrapter;
import vn.edu.spx.assignmentduanmau.Adrapter.SachAdrapter;
import vn.edu.spx.assignmentduanmau.Adrapter.ThanhVienAdrapter;
import vn.edu.spx.assignmentduanmau.DAO.LoaiSachDao;
import vn.edu.spx.assignmentduanmau.DAO.SachDao;
import vn.edu.spx.assignmentduanmau.DAO.ThanhVienDao;
import vn.edu.spx.assignmentduanmau.Object.LoaiSach;
import vn.edu.spx.assignmentduanmau.Object.Sach;
import vn.edu.spx.assignmentduanmau.Object.ThanhVien;
import vn.edu.spx.assignmentduanmau.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link QL_SachFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QL_SachFragment extends Fragment {

    ListView lvSach;
    FloatingActionButton fabOpenDialogS;
    ArrayList<Sach> listS;
    ArrayList<LoaiSach> listLS;
    Dialog dialog;
    EditText edMaS, edTenS, edGiathue, edSoTrang, edSearchSoTrang;
    Button btnSave;
    Spinner spinnerLoaiSach;
    static SachDao sachDao;
    SachAdrapter sachAdrapter;
    Sach sach;
    LoaiSachSpinnerAdrapter loaiSachSpinnerAdrapter;
    LoaiSachDao loaiSachDao;
    LoaiSach loaiSach;
    int maLS,pos;
    public QL_SachFragment() {
        // Required empty public constructor
    }
    public static QL_SachFragment newInstance() {
        QL_SachFragment fragment = new QL_SachFragment();
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
        View v = inflater.inflate(R.layout.fragment_q_l__sach, container, false);
        lvSach = v.findViewById(R.id.lv_sach);
        fabOpenDialogS = v.findViewById(R.id.fab_opendialog_s);
        sachDao = new SachDao(getActivity());
        sach = new Sach();
        //----set listView----
        capnhatLv();
        //---code---
        fabOpenDialogS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                themTV();
            }
        });

        lvSach.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                sach = listS.get(position);
                dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.dialog_them_sach);
                edMaS = dialog.findViewById(R.id.ed_maS);
                edTenS = dialog.findViewById(R.id.ed_tenS);
                edGiathue = dialog.findViewById(R.id.ed_giathueS);
                edSoTrang = dialog.findViewById(R.id.ed_soTrang);
                btnSave = dialog.findViewById(R.id.btn_save_s);
                spinnerLoaiSach = dialog.findViewById(R.id.sp_loaisach);
                //---
                edMaS.setText(sach.getMaSach()+"");
                //------
                listLS = new ArrayList<LoaiSach>();
                loaiSachDao = new LoaiSachDao(getActivity());
                listLS = (ArrayList<LoaiSach>) loaiSachDao.getAll();
                loaiSachSpinnerAdrapter = new LoaiSachSpinnerAdrapter(getActivity(),listLS);
                spinnerLoaiSach.setAdapter(loaiSachSpinnerAdrapter);
                //---
                spinnerLoaiSach.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        maLS = listLS.get(position).getMaLoai();
                        Toast.makeText(getActivity(), "Chọn: "+listLS.get(position).getTenLoai(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                edTenS.setText(sach.getTenSach());
                edGiathue.setText(""+sach.getGiaThue());
                for(int i=0;i<listLS.size();i++){
                    if(sach.getMaLoai() == listLS.get(i).getMaLoai()){
                        pos = i;
                    }
                }
                edSoTrang.setText(sach.getSoTrang()+"");
                spinnerLoaiSach.setSelection(pos);
                btnSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (sachDao.update(sach)>0){
                            sach.setTenSach(edTenS.getText().toString());
                            sach.setGiaThue(Integer.valueOf(edGiathue.getText().toString()));
                            sach.setMaLoai(maLS);
                            sach.setSoTrang(Integer.parseInt(edSoTrang.getText().toString()));
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

        //-------------SEARCH----------
        edSearchSoTrang = v.findViewById(R.id.ed_search_soTrang);
        edSearchSoTrang.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_SEARCH)//bat su kien khi click vao action search
                {
                    if(!edSearchSoTrang.getText().toString().isEmpty())
                    {
                        listS = sachDao.getSearchSoTrang(edSearchSoTrang.getText().toString().trim());
                        sachAdrapter = new SachAdrapter(getActivity(),QL_SachFragment.this,listS);
                        lvSach.setAdapter(sachAdrapter);
                    }else
                    {
                        capnhatLv();
                    }
                }
                return false;
            }
        });



        return v;
    }

    private int validate(){
        int check = 1;
        if(edTenS.getText().length() ==0 || edGiathue.getText().length()==0 || edSoTrang.getText().length()==0){
            Toast.makeText(getActivity(), "Không được để trống", Toast.LENGTH_SHORT).show();
            check = -1;
        }
        return check;
    }

    protected void themTV(){
        dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.dialog_them_sach);
        edMaS = dialog.findViewById(R.id.ed_maS);
        edTenS = dialog.findViewById(R.id.ed_tenS);
        edGiathue = dialog.findViewById(R.id.ed_giathueS);
        edSoTrang = dialog.findViewById(R.id.ed_soTrang);
        btnSave = dialog.findViewById(R.id.btn_save_s);
        spinnerLoaiSach = dialog.findViewById(R.id.sp_loaisach);
        //------
        listLS = new ArrayList<LoaiSach>();
        loaiSachDao = new LoaiSachDao(getActivity());
        listLS = (ArrayList<LoaiSach>) loaiSachDao.getAll();
        loaiSachSpinnerAdrapter = new LoaiSachSpinnerAdrapter(getActivity(),listLS);
        spinnerLoaiSach.setAdapter(loaiSachSpinnerAdrapter);
        //---
        spinnerLoaiSach.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                maLS = listLS.get(position).getMaLoai();
                Toast.makeText(getActivity(), "Chọn: "+listLS.get(position).getTenLoai(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //kiem tra type 0 insert hay 1 update
        edMaS.setEnabled(false);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate() > 0){
                    sach = new Sach();
                    sach.setTenSach(edTenS.getText().toString());
                    sach.setGiaThue(Integer.valueOf(edGiathue.getText().toString()));
                    sach.setMaLoai(maLS);
                    sach.setSoTrang(Integer.parseInt(edSoTrang.getText().toString()));
                    if(sachDao.insert(sach)>0){
                        Toast.makeText(getActivity(), "Them thanh cong", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(getActivity(), "Them that bai", Toast.LENGTH_SHORT).show();
                    }
                }
                capnhatLv();
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void capnhatLv() {
        listS = (ArrayList<Sach>) sachDao.getAll();
        sachAdrapter = new SachAdrapter(getActivity(),this,listS);
        lvSach.setAdapter(sachAdrapter);
    }

    public void deleteS(final String id){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Delete sách");
        builder.setMessage("Có xoá không");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                sachDao.delete(id);
                capnhatLv();
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