package vn.edu.spx.assignmentduanmau;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import vn.edu.spx.assignmentduanmau.DAO.ThuThuDao;
import vn.edu.spx.assignmentduanmau.FragmentPackage.QL_PhieuMuonFragment;

public class MainActivity extends AppCompatActivity {

    private LinearLayout manhinhchao;
    private Button login,cancel;
    private EditText edUserName, edPass;
    private CheckBox chkSavePass;
    private String strUserName, strPass;
    private ThuThuDao thuThuDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //ánh xạ
       manhinhchao = findViewById(R.id.linear_wellcome);
       login = findViewById(R.id.btn_login);
       cancel = findViewById(R.id.btn_cancel);
       edUserName = findViewById(R.id.ed_username);
       edPass = findViewById(R.id.ed_pass);
       chkSavePass = findViewById(R.id.chk_savepass);

       //-----
        thuThuDao = new ThuThuDao(this);
        //---
        Handler handler= new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                manhinhchao.setVisibility(View.GONE);
            }
        },1500);

        //doc user , pass trong sharedPreferences
        SharedPreferences pref = getSharedPreferences("USER_FILE",MODE_PRIVATE);
        edUserName.setText(pref.getString("USERNAME",""));
        edPass.setText(pref.getString("PASS",""));
        chkSavePass.setChecked(pref.getBoolean("REMEMBER",false));

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkLogin();
            }
        });

        xoatrang();
    }

    private void xoatrang() {
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edUserName.setText("");
                edPass.setText("");
                chkSavePass.setChecked(false);
            }
        });
    }

    private void checkLogin() {
        thuThuDao = new ThuThuDao(this);
        strUserName = edUserName.getText().toString();
        strPass = edPass.getText().toString();

        if(strUserName.isEmpty() || strPass.isEmpty()) {
            Toast.makeText(this, "Không được bỏ trống!", Toast.LENGTH_SHORT).show();
        }else {
            if(thuThuDao.checkLogin(strUserName,strPass) > 0){
                Toast.makeText(this, "Login thanh cong", Toast.LENGTH_SHORT).show();
                rememberUser(strUserName,strPass,chkSavePass.isChecked());
                Intent intentLogin = new Intent(MainActivity.this, HomeActivity.class);
                intentLogin.putExtra("user",strUserName);
                startActivity(intentLogin);
                finish();
            }else{
                Toast.makeText(this, "Ten dang nhap hoac mat khau khong dung", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void rememberUser(String u, String p, boolean status){
        SharedPreferences pref = getSharedPreferences("USER_FILE",MODE_PRIVATE);
        SharedPreferences.Editor edit = pref.edit();
        if(!status){
            //xoa tinh trng truoc do
            edit.clear();
        }else {
            //luu tru du lieu
            edit.putString("USERNAME",u);
            edit.putString("PASS",p);
            edit.putBoolean("REMEMBER",status);
        }
        //luu edit
        edit.commit();
    }

}