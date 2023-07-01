package vn.edu.spx.assignmentduanmau.Adrapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import vn.edu.spx.assignmentduanmau.FragmentPackage.QL_LoaiSachFragment;
import vn.edu.spx.assignmentduanmau.FragmentPackage.QL_ThanhVienFragment;
import vn.edu.spx.assignmentduanmau.Object.LoaiSach;
import vn.edu.spx.assignmentduanmau.Object.ThanhVien;
import vn.edu.spx.assignmentduanmau.R;

public class LoaiSachAdrapter extends ArrayAdapter<LoaiSach> {

    private Context context;
    QL_LoaiSachFragment fragment;
    private ArrayList<LoaiSach> lists;
    TextView tvMaLS, tvTenLS, tvDelLS, tvNhaCc;

    public LoaiSachAdrapter(@NonNull Context context, QL_LoaiSachFragment fragment, @NonNull ArrayList<LoaiSach> lists) {
        super(context, 0, lists);
        this.context = context;
        this.lists = lists;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if(v == null){
            LayoutInflater inflater = (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.item_loaisach,null);
        }
        final LoaiSach loaiSach = lists.get(position);
        if(loaiSach != null){ //nếu đối tượng k rỗng
            tvMaLS = v.findViewById(R.id.tv_maLS);
            tvTenLS =v.findViewById(R.id.tv_tenLS);
            tvNhaCc = v.findViewById(R.id.tv_nhaCCLS);
            tvDelLS =v.findViewById(R.id.tv_deleteLS);
            if(checkText(loaiSach.getTenLoai()).equalsIgnoreCase("n")){
                tvTenLS.setTextColor(Color.GREEN);
            }else if(checkText(loaiSach.getTenLoai()).equalsIgnoreCase("a")){
                tvTenLS.setTextColor(Color.RED);
            }
            tvMaLS.setText("Mã loại sách: "+loaiSach.getMaLoai());
            tvTenLS.setText("Tên loại sách: "+loaiSach.getTenLoai());
            if(checkText(loaiSach.getNhaCC()).equalsIgnoreCase("n")){
                tvNhaCc.setTextColor(Color.BLUE);
            }else if(checkText(loaiSach.getNhaCC()).equalsIgnoreCase("a")){
                tvNhaCc.setTextColor(Color.YELLOW);
            }
            tvNhaCc.setText("Nhà cung cấp: "+loaiSach.getNhaCC());
        }
        tvDelLS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment.deleteLS(String.valueOf(loaiSach.getMaLoai()));
            }
        });
        return v;
    }

    public String checkText (String str){
        for (int i=0; i< str.length();i++){
            if (str.substring(i,i+1).equalsIgnoreCase("n")){
                return "n";
            }else if (str.substring(i,i+1).equalsIgnoreCase("a")){
                return "a";
            }
         }
        return "";
    }
}
