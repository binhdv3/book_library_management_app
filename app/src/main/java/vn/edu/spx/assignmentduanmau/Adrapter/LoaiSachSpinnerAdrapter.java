package vn.edu.spx.assignmentduanmau.Adrapter;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

import vn.edu.spx.assignmentduanmau.Object.LoaiSach;
import vn.edu.spx.assignmentduanmau.R;

public class LoaiSachSpinnerAdrapter extends ArrayAdapter<LoaiSach> {

    private Context context;
    private ArrayList<LoaiSach> list;
    TextView tvmaLS, tvTenLS;

    public LoaiSachSpinnerAdrapter(@NonNull Context context, ArrayList<LoaiSach> list) {
        super(context, 0,list);
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
            v = inflater.inflate(R.layout.item_spinner_loaisach,null);
        }
        final LoaiSach loaiSach = list.get(position);
        if(loaiSach != null){ //nếu đối tượng k rỗng
            tvmaLS = v.findViewById(R.id.tv_maLS_sp);
            tvTenLS =v.findViewById(R.id.tv_tenLS_sp);

            tvmaLS.setText(loaiSach.getMaLoai()+". ");
            tvTenLS.setText(loaiSach.getTenLoai());
        }
        return v;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if(v == null){
            LayoutInflater inflater = (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.item_spinner_loaisach,null);
        }
        final LoaiSach loaiSach = list.get(position);
        if(loaiSach != null){ //nếu đối tượng k rỗng
            tvmaLS = v.findViewById(R.id.tv_maLS_sp);
            tvTenLS =v.findViewById(R.id.tv_tenLS_sp);

            tvmaLS.setText(loaiSach.getMaLoai()+". ");
            tvTenLS.setText(loaiSach.getTenLoai());
        }
        return v;
    }
}
