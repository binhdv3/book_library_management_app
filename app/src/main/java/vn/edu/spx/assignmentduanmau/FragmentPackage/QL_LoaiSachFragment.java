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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import vn.edu.spx.assignmentduanmau.Adrapter.LoaiSachAdrapter;
import vn.edu.spx.assignmentduanmau.Adrapter.ThanhVienAdrapter;
import vn.edu.spx.assignmentduanmau.DAO.LoaiSachDao;
import vn.edu.spx.assignmentduanmau.DAO.ThanhVienDao;
import vn.edu.spx.assignmentduanmau.Object.LoaiSach;
import vn.edu.spx.assignmentduanmau.Object.ThanhVien;
import vn.edu.spx.assignmentduanmau.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link QL_LoaiSachFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QL_LoaiSachFragment extends Fragment {

    ListView lvLoaiSach;
    FloatingActionButton fabOpenDialogLS;
    ArrayList<LoaiSach> listLS;
    Dialog dialog;
    EditText edMaLS, edTenLS, edNhaCC, edsearchNhacc;
    Button btnSave;

    static LoaiSachDao lsDao;
    LoaiSachAdrapter adrapter;
    LoaiSach loaiSach;


    public QL_LoaiSachFragment() {
        // Required empty public constructor
    }


    public static QL_LoaiSachFragment newInstance() {
        QL_LoaiSachFragment fragment = new QL_LoaiSachFragment();
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
        View v = inflater.inflate(R.layout.fragment_q_l__loai_sach, container, false);
        lvLoaiSach = v.findViewById(R.id.lv_loaisach);
        fabOpenDialogLS = v.findViewById(R.id.fab_opendialog_ls);
        lsDao = new LoaiSachDao(getActivity());
        loaiSach = new LoaiSach();
        //----set listView----
        capnhatLv();
        //---code---
        fabOpenDialogLS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                themLS();
            }
        });


        lvLoaiSach.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                loaiSach = listLS.get(position);
                dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.dialog_them_loaisach);
                edMaLS = dialog.findViewById(R.id.ed_maLS);
                edTenLS = dialog.findViewById(R.id.ed_tenLS);
                edNhaCC = dialog.findViewById(R.id.ed_nhaCCLS);
                btnSave = dialog.findViewById(R.id.btn_save_ls);
                //kiem tra type 0 insert hay 1 update
                edMaLS.setEnabled(false);
                edMaLS.setText(loaiSach.getMaLoai()+"");
                edTenLS.setText(loaiSach.getTenLoai());
                edNhaCC.setText(loaiSach.getNhaCC());
                btnSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        loaiSach.setTenLoai(edTenLS.getText().toString());
                        loaiSach.setNhaCC(edNhaCC.getText().toString());
                        if (lsDao.update(loaiSach)>0){
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

        //-----------------SEARCH----------------
        edsearchNhacc = v.findViewById(R.id.ed_search_nhacc);
        edsearchNhacc.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_SEARCH)
                {
                    if(!edsearchNhacc.getText().toString().isEmpty()) // neu khong rong thi tim
                    {
                        listLS = lsDao.getSearchNameNhacc(edsearchNhacc.getText().toString().trim());
                        adrapter = new LoaiSachAdrapter(getActivity(),QL_LoaiSachFragment.this,listLS);
                        lvLoaiSach.setAdapter(adrapter);
                    }
                    else //rong thi tri ve list view cu
                    {
                        capnhatLv();
                    }
                }
                return false;
            }
        });
        return v;
    }

    protected void themLS(){
        dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.dialog_them_loaisach);
        edMaLS = dialog.findViewById(R.id.ed_maLS);
        edTenLS = dialog.findViewById(R.id.ed_tenLS);
        edNhaCC = dialog.findViewById(R.id.ed_nhaCCLS);
        btnSave = dialog.findViewById(R.id.btn_save_ls);
        //kiem tra type 0 insert hay 1 update
        edMaLS.setEnabled(false);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loaiSach = new LoaiSach();
                loaiSach.setTenLoai(edTenLS.getText().toString());
                loaiSach.setNhaCC(edNhaCC.getText().toString());
                if(validate() > 0){
                    if(lsDao.insert(loaiSach)>0){
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
        if(edTenLS.getText().length() ==0){
            Toast.makeText(getActivity(), "Không được để trống", Toast.LENGTH_SHORT).show();
            check = -1;
        }
        return check;
    }

    private void capnhatLv() {
        listLS = (ArrayList<LoaiSach>) lsDao.getAll();
        adrapter = new LoaiSachAdrapter(getActivity(),this,listLS);
        lvLoaiSach.setAdapter(adrapter);
    }

    public void deleteLS(final String id){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Delete");
        builder.setMessage("Có xoá không");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                lsDao.delete(id);
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