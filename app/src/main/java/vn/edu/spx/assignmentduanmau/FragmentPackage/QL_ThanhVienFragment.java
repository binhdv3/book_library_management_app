package vn.edu.spx.assignmentduanmau.FragmentPackage;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import vn.edu.spx.assignmentduanmau.Adrapter.ThanhVienAdrapter;
import vn.edu.spx.assignmentduanmau.DAO.ThanhVienDao;
import vn.edu.spx.assignmentduanmau.Object.ThanhVien;
import vn.edu.spx.assignmentduanmau.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link QL_ThanhVienFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QL_ThanhVienFragment extends Fragment {

    ListView listViewThanhVien;
    FloatingActionButton fabOpenDialogTV;
    ArrayList<ThanhVien> listTV;
    Dialog dialog;
    EditText edMaTV, edTenTV, edNamSinh;
    Button btnSave;

    static ThanhVienDao tvDao;
    ThanhVienAdrapter tvAdrapter;
    ThanhVien thanhVien;

    public QL_ThanhVienFragment() {
        // Required empty public constructor
    }

    public static QL_ThanhVienFragment newInstance() {
        QL_ThanhVienFragment fragment = new QL_ThanhVienFragment();
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
        View v = inflater.inflate(R.layout.fragment_q_l__thanh_vien, container, false);
        listViewThanhVien = v.findViewById(R.id.lv_thanhvien);
        fabOpenDialogTV = v.findViewById(R.id.fab_opendialog_tv);
        tvDao = new ThanhVienDao(getActivity());
        thanhVien = new ThanhVien();
        //----set listView----
        capnhatLv();
        //---code---
        fabOpenDialogTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                themTV();
            }
        });


        listViewThanhVien.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                thanhVien = listTV.get(position);
                dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.dialog_them_thanhvien);
                edMaTV = dialog.findViewById(R.id.ed_maTV);
                edTenTV = dialog.findViewById(R.id.ed_tenTV);
                edNamSinh = dialog.findViewById(R.id.ed_namsinh);
                btnSave = dialog.findViewById(R.id.btn_save_tv);
                //kiem tra type 0 insert hay 1 update
                edMaTV.setEnabled(false);
                edMaTV.setText(String.valueOf(thanhVien.getMaTV()));
                edTenTV.setText(thanhVien.getHoTen());
                edNamSinh.setText(thanhVien.getNamSinh());
                btnSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        thanhVien.setHoTen(edTenTV.getText().toString());
                        thanhVien.setNamSinh(edNamSinh.getText().toString());
                        if (tvDao.update(thanhVien)>0){
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

    protected void themTV(){
        dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.dialog_them_thanhvien);
        edMaTV = dialog.findViewById(R.id.ed_maTV);
        edTenTV = dialog.findViewById(R.id.ed_tenTV);
        edNamSinh = dialog.findViewById(R.id.ed_namsinh);
        btnSave = dialog.findViewById(R.id.btn_save_tv);
        //kiem tra type 0 insert hay 1 update
        edMaTV.setEnabled(false);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                thanhVien = new ThanhVien();
                thanhVien.setHoTen(edTenTV.getText().toString());
                thanhVien.setNamSinh(edNamSinh.getText().toString());
                if(validate() > 0){
                        if(tvDao.insert(thanhVien)>0){
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

    private int validate(){
        int check = 1;
        if(edTenTV.getText().length() ==0 || edNamSinh.getText().length() ==0){
            Toast.makeText(getActivity(), "Không được để trống", Toast.LENGTH_SHORT).show();
            check = -1;
        }
        return check;
    }

    private void capnhatLv() {
        listTV = (ArrayList<ThanhVien>) tvDao.getAll();
        tvAdrapter = new ThanhVienAdrapter(getActivity(),this,listTV);
        listViewThanhVien.setAdapter(tvAdrapter);
    }

    public void deleteTv(final String id){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Delete thành viên");
        builder.setMessage("Có xoá không");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                 tvDao.detele(id);
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