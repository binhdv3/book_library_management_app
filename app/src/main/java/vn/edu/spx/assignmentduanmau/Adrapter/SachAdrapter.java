package vn.edu.spx.assignmentduanmau.Adrapter;

import android.app.Notification;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.icu.text.RelativeDateTimeFormatter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import vn.edu.spx.assignmentduanmau.DAO.LoaiSachDao;
import vn.edu.spx.assignmentduanmau.FragmentPackage.QL_LoaiSachFragment;
import vn.edu.spx.assignmentduanmau.FragmentPackage.QL_SachFragment;
import vn.edu.spx.assignmentduanmau.Object.LoaiSach;
import vn.edu.spx.assignmentduanmau.Object.Sach;
import vn.edu.spx.assignmentduanmau.R;

public class SachAdrapter extends ArrayAdapter<Sach> {

    private Context context;
    QL_SachFragment fragment;
    private ArrayList<Sach> lists;
    TextView tvMaS, tvTenS, tvDelS, tvGiathueS, tvLoaiS,tvSoTrangS;

    public SachAdrapter(@NonNull Context context,QL_SachFragment fragment, @NonNull ArrayList<Sach> lists) {
        super(context, 0, lists);
        this.context = context;
        this.lists = lists;
        this.fragment = fragment;
    }

    public void setData(ArrayList<Sach> lists){
        this.lists = lists;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if(v == null){
            LayoutInflater inflater = (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.item_sach,null);
        }
        final Sach sach = lists.get(position);
        if(sach != null){ //nếu đối tượng k rỗng
            LoaiSachDao loaiSachDao = new LoaiSachDao(context);
            LoaiSach loaiSach = loaiSachDao.getId(String.valueOf(sach.getMaLoai()));
            tvMaS = v.findViewById(R.id.tv_maS);
            tvTenS =v.findViewById(R.id.tv_tenS);
            tvGiathueS = v.findViewById(R.id.tv_giathueS);
            tvLoaiS = v.findViewById(R.id.tv_maLS_s);
            tvSoTrangS = v.findViewById(R.id.tv_sotrang_s);
            tvDelS =v.findViewById(R.id.tv_deleteS);

            tvMaS.setText("Mã sách: "+sach.getMaLoai());
            tvTenS.setText("Tên sách: "+sach.getTenSach());
            tvGiathueS.setText("Giá thuê: "+sach.getGiaThue());
            tvLoaiS.setText("Loại sách: "+loaiSach.getTenLoai());
            if (sach.getSoTrang() > 1000){
                tvSoTrangS.setTypeface(null, Typeface.BOLD);
            }
            tvSoTrangS.setText("So trang: "+sach.getSoTrang());
        }
        tvDelS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment.deleteS(String.valueOf(sach.getMaLoai()));
            }
        });
        return v;
    }
}
