package vn.edu.spx.assignmentduanmau.FragmentPackage;

import android.content.Context;
import android.content.SharedPreferences;
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
 * Use the {@link DoiMatKhauFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DoiMatKhauFragment extends Fragment {

    private EditText passOld, passNew, rePass;
    private Button btnSave, btnCancel;
    private ThuThuDao thuThuDao;

    public DoiMatKhauFragment() {
        // Required empty public constructor
    }

    public static DoiMatKhauFragment newInstance() {
        DoiMatKhauFragment fragment = new DoiMatKhauFragment();
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
        return inflater.inflate(R.layout.fragment_doi_mat_khau, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        passOld = view.findViewById(R.id.ed_passOld);
        passNew = view.findViewById(R.id.ed_passNew);
        rePass = view.findViewById(R.id.ed_rePass);
        btnSave = view.findViewById(R.id.btn_savePass);
        btnCancel = view.findViewById(R.id.btn_cancelPass);
        //--------------
        //--------------
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                passOld.setText("");
                passNew.setText("");
                rePass.setText("");
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doimatkhau();
            }
        });
    }

    public void doimatkhau(){
        thuThuDao = new ThuThuDao(getActivity());
        SharedPreferences pref = getActivity().getSharedPreferences("USER_FILE", Context.MODE_PRIVATE);
        String user = pref.getString("USERNAME","");
        if(validate() > 0){
           ThuThu thuThu = thuThuDao.getId(user);
           thuThu.setMatKhau(passNew.getText().toString());
           thuThuDao.update(thuThu);
           if(thuThuDao.update(thuThu)>0){
               Toast.makeText(getActivity(), "Doi mk thanh cong", Toast.LENGTH_SHORT).show();
           }else {
               Toast.makeText(getActivity(), "Doi mk that bai", Toast.LENGTH_SHORT).show();
           }
        }
    }

    public int validate(){
        int check = 1;
        if(passOld.getText().length()==0
                || passNew.getText().length()==0
                || rePass.getText().length()==0){
            Toast.makeText(getActivity(), "ban phai nhap du thong tin", Toast.LENGTH_SHORT).show();
            check = -1;
        } else {
            SharedPreferences pref = getActivity().getSharedPreferences("USER_FILE", Context.MODE_PRIVATE);
            String passOl = pref.getString("PASS","");
            String newPass = passNew.getText().toString();
            String rePa = rePass.getText().toString();
            if(!passOl.equals(passOld.getText().toString())){
                Toast.makeText(getActivity(), "mat khau cu sai", Toast.LENGTH_SHORT).show();
                check = -1;
            }
            if(!newPass.equals(rePa)){
                Toast.makeText(getActivity(), "khong trung mat khau", Toast.LENGTH_SHORT).show();
                check = -1;
            }
        }
        return check;
    }
}