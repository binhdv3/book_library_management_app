package vn.edu.spx.assignmentduanmau;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import vn.edu.spx.assignmentduanmau.FragmentPackage.Add_UserFragment;

public class DangKictivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_kiactivity2);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.id_frameLayout_dk,Add_UserFragment.newInstance());
        transaction.commit();
    }
}