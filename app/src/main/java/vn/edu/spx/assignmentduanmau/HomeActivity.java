package vn.edu.spx.assignmentduanmau;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import vn.edu.spx.assignmentduanmau.DAO.ThuThuDao;
import vn.edu.spx.assignmentduanmau.FragmentPackage.Add_UserFragment;
import vn.edu.spx.assignmentduanmau.FragmentPackage.DoiMatKhauFragment;
import vn.edu.spx.assignmentduanmau.FragmentPackage.QL_LoaiSachFragment;
import vn.edu.spx.assignmentduanmau.FragmentPackage.QL_PhieuMuonFragment;
import vn.edu.spx.assignmentduanmau.FragmentPackage.QL_SachFragment;
import vn.edu.spx.assignmentduanmau.FragmentPackage.QL_ThanhVienFragment;
import vn.edu.spx.assignmentduanmau.FragmentPackage.ThongKeFragment;
import vn.edu.spx.assignmentduanmau.FragmentPackage.Top10Fragment;
import vn.edu.spx.assignmentduanmau.Object.ThuThu;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private TextView tvNameHeader;
    private View headerView;
    private Intent intent;
    private ThuThuDao thuThuDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        drawerLayout = findViewById(R.id.id_drawerLayout);
        toolbar = findViewById(R.id.id_toolBar);
        navigationView = findViewById(R.id.nav_Naview);
        //-----set tv header thành user name
        headerView = navigationView.getHeaderView(0);
        tvNameHeader = headerView.findViewById(R.id.tv_name_header);
        intent = getIntent();
        String user = intent.getStringExtra("user");
        String userr = "admin";
        thuThuDao = new ThuThuDao(this);


        //neu la admin thi hien chuc nang them thanh vien
        if(user != null){
            ThuThu thuThu = thuThuDao.getId(user);
            String userName = thuThu.getHoTen();
            tvNameHeader.setText("Welcome "+userName+" !");

            if(user.equalsIgnoreCase("admin")) {
                navigationView.getMenu().findItem(R.id.id_addUser).setVisible(true);
            }else {
                navigationView.getMenu().findItem(R.id.id_addUser).setVisible(false);
            }
        }else {
            navigationView.getMenu().findItem(R.id.id_addUser).setVisible(true);
            tvNameHeader.setText("Welcome !");
        }


        //-----
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                drawerLayout,
                toolbar, 0 ,0);
        toggle.syncState();//hiển thị nút 3 gạch
        navigationView.setNavigationItemSelectedListener(this);
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction();
        replaceFragment(Add_UserFragment.newInstance());
        toolbar.setTitle("Đăng kí thành viên");
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.id_qlPM:
                replaceFragment(QL_PhieuMuonFragment.newInstance());
                toolbar.setTitle("Quản lý phiếu mượn");
                break;
            case R.id.id_qlLS:
                replaceFragment(QL_LoaiSachFragment.newInstance());
                toolbar.setTitle("Quản lý loại sách");
                break;
            case R.id.id_qlS:
                replaceFragment(QL_SachFragment.newInstance());
                toolbar.setTitle("Quản lý sách");
                break;
            case R.id.id_qlTV:
                replaceFragment(QL_ThanhVienFragment.newInstance());
                toolbar.setTitle("Quản lý thành viên");
                break;
            case R.id.id_top10Smnn:
                replaceFragment(Top10Fragment.newInstance());
                toolbar.setTitle("Top 10 sách cho mượn nhiều nhất");
                break;
            case R.id.id_thongke:
                replaceFragment(ThongKeFragment.newInstance());
                toolbar.setTitle("Thống kê");
                break;
            case R.id.id_addUser:
                replaceFragment(Add_UserFragment.newInstance());
                toolbar.setTitle("Thêm thành viên");
                break;
            case R.id.id_doiMK:
                toolbar.setTitle("Đổi mật khẩu");
                replaceFragment(DoiMatKhauFragment.newInstance());
                break;
            case R.id.id_DangXuat:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Logout");
                builder.setMessage("Đăng xuất?");
                builder.setNegativeButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intentLogin = new Intent(HomeActivity.this,MainActivity.class);
                        startActivity(intentLogin);
                        finish();
                        Toast.makeText(getApplicationContext(), "Logout sucessfully", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setPositiveButton("no", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), "Logout fail", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
                break;
        }
        drawerLayout.closeDrawer(navigationView);
        return false;
    }

    public void replaceFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.id_frameLayout,fragment);
        transaction.commit();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            if(drawerLayout.isDrawerOpen(navigationView)){
                drawerLayout.closeDrawer(navigationView);
            }else {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Logout");
                builder.setMessage("Đăng xuất?");
                builder.setNegativeButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intentLogin = new Intent(HomeActivity.this,MainActivity.class);
                        startActivity(intentLogin);
                        finish();
                        Toast.makeText(getApplicationContext(), "Logout sucessfully", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setPositiveButton("no", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), "Logout fail", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
            }
        }
        return true;
    }
}