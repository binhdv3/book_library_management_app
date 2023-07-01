package vn.edu.spx.assignmentduanmau.FragmentPackage;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import vn.edu.spx.assignmentduanmau.DAO.ThuThuDao;
import vn.edu.spx.assignmentduanmau.Object.ThuThu;
import vn.edu.spx.assignmentduanmau.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Add_UserFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Add_UserFragment extends Fragment {
    
    private EditText edNameUser, edTendangnhap, edPass, edrePass;
    private Button btnSave, btnCancel;
    private ThuThuDao thuThuDao;


    public Add_UserFragment() {
        // Required empty public constructor
    }


    public static Add_UserFragment newInstance() {
        Add_UserFragment fragment = new Add_UserFragment();
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
        return inflater.inflate(R.layout.fragment_add__user, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //anh xa
        edNameUser = view.findViewById(R.id.ed_add_nameuser);
        edTendangnhap = view.findViewById(R.id.ed_add_tendangnhap);
        edPass = view.findViewById(R.id.ed_add_pass);
        edrePass = view.findViewById(R.id.ed_add_repass);
        btnSave = view.findViewById(R.id.btn_themUser);
        btnCancel = view.findViewById(R.id.btn_cancelUser);
        //----------
        thuThuDao = new ThuThuDao(getActivity());
        //----------
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edTendangnhap.setText("");
                edNameUser.setText("");
                edPass.setText("");
                edrePass.setText("");
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUser();
            }
        });
    }

    private void saveUser(){
        ThuThu thuThu = new ThuThu();
        thuThu.setMaTT(edTendangnhap.getText().toString());
        thuThu.setHoTen(edNameUser.getText().toString());
        thuThu.setMatKhau(edPass.getText().toString());
        if (validate() > 0){
            if(thuThuDao.insert(thuThu)>0){
                Toast.makeText(getActivity(), "Them thanh cong", Toast.LENGTH_SHORT).show();
                edTendangnhap.setText("");
                edNameUser.setText("");
                edPass.setText("");
                edrePass.setText("");
            } else {
                Toast.makeText(getActivity(), "Them that bai", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public int validate(){
        int check = 1;
        if (edTendangnhap.getText().length()==0
                || edNameUser.getText().length()==0
                || edPass.getText().length()==0
                || edrePass.getText().length()==0){
            Toast.makeText(getActivity(), "Phai nhap day du thong tin", Toast.LENGTH_SHORT).show();
            check = -1;
        }else {
            if(!edPass.getText().toString().equals(edrePass.getText().toString())){
                Toast.makeText(getActivity(), "mat khau khong truong khop", Toast.LENGTH_SHORT).show();
                check=-1;
            }
        }
        return check;
    }


}