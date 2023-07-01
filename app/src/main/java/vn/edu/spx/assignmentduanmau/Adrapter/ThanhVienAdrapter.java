package vn.edu.spx.assignmentduanmau.Adrapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import vn.edu.spx.assignmentduanmau.FragmentPackage.QL_ThanhVienFragment;
import vn.edu.spx.assignmentduanmau.Object.ThanhVien;
import vn.edu.spx.assignmentduanmau.R;

public class ThanhVienAdrapter extends ArrayAdapter<ThanhVien> {

    private Context context;
    QL_ThanhVienFragment fragment;
    private ArrayList<ThanhVien> list;
    TextView tvMaTV, tvTenTV, tvNamSinh, tvDelTV;

    public ThanhVienAdrapter(@NonNull Context context,QL_ThanhVienFragment fragment, @NonNull ArrayList<ThanhVien> list) {
        super(context, 0, list);
        this.context = context;
        this.list = list;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if(v == null){
            LayoutInflater inflater = (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.item_thanhvien,null);
        }
        final ThanhVien thanhVienObj = list.get(position);
        if(thanhVienObj != null){ //nếu đối tượng k rỗng
            tvMaTV = v.findViewById(R.id.tv_maTV);
            tvTenTV =v.findViewById(R.id.tv_tenTV);
            tvNamSinh =v.findViewById(R.id.tv_namsinh);
            tvDelTV =v.findViewById(R.id.tv_deleteTv);

            tvMaTV.setText("Mã thành viên: "+thanhVienObj.getMaTV());
            tvTenTV.setText("Tên thành viên: "+thanhVienObj.getHoTen());
            tvNamSinh.setText("Năm sinh: "+thanhVienObj.getNamSinh());
        }
        tvDelTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment.deleteTv(String.valueOf(thanhVienObj.getMaTV()));
            }
        });
        return v;
    }
}
