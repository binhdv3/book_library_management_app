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

import vn.edu.spx.assignmentduanmau.Object.Sach;
import vn.edu.spx.assignmentduanmau.Object.ThanhVien;
import vn.edu.spx.assignmentduanmau.R;

public class ThanhVienSpinnerAdrapter extends ArrayAdapter<ThanhVien> {

    private Context context;
    private ArrayList<ThanhVien> list;
    TextView tvmaTV, tvtenTV;

    public ThanhVienSpinnerAdrapter(@NonNull Context context, ArrayList<ThanhVien> list) {
        super(context, 0, list);
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if(v == null){
            LayoutInflater inflater = (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.item_spinner_thanhvien,null);
        }
        final ThanhVien thanhVien = list.get(position);
        if(thanhVien != null){ //nếu đối tượng k rỗng
            tvmaTV = v.findViewById(R.id.tv_maTV_sp);
            tvtenTV =v.findViewById(R.id.tv_tenTV_sp);

            tvmaTV.setText(thanhVien.getMaTV()+". ");
            tvtenTV.setText(thanhVien.getHoTen());
        }
        return v;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if(v == null){
            LayoutInflater inflater = (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.item_spinner_thanhvien,null);
        }
        final ThanhVien thanhVien = list.get(position);
        if(thanhVien != null){ //nếu đối tượng k rỗng
            tvmaTV = v.findViewById(R.id.tv_maTV_sp);
            tvtenTV =v.findViewById(R.id.tv_tenTV_sp);

            tvmaTV.setText(thanhVien.getMaTV()+". ");
            tvtenTV.setText(thanhVien.getHoTen());
        }
        return v;
    }
}
