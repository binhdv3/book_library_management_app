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

import vn.edu.spx.assignmentduanmau.Object.LoaiSach;
import vn.edu.spx.assignmentduanmau.Object.Sach;
import vn.edu.spx.assignmentduanmau.R;

public class SachSpinnerAdrapter extends ArrayAdapter<Sach> {

    private Context context;
    private ArrayList<Sach> list;
    TextView tvmaS, tvtenS;

    public SachSpinnerAdrapter(@NonNull Context context, ArrayList<Sach> list) {
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
            v = inflater.inflate(R.layout.item_spinner_sach,null);
        }
        final Sach sach = list.get(position);
        if(sach != null){ //nếu đối tượng k rỗng
            tvmaS = v.findViewById(R.id.tv_maS_sp);
            tvtenS =v.findViewById(R.id.tv_tenS_sp);

            tvmaS.setText(sach.getMaSach()+". ");
            tvtenS.setText(sach.getTenSach());
        }
        return v;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if(v == null){
            LayoutInflater inflater = (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.item_spinner_sach,null);
        }
        final Sach sach = list.get(position);
        if(sach != null){ //nếu đối tượng k rỗng
            tvmaS = v.findViewById(R.id.tv_maS_sp);
            tvtenS =v.findViewById(R.id.tv_tenS_sp);

            tvmaS.setText(sach.getMaSach()+". ");
            tvtenS.setText(sach.getTenSach());
        }
        return v;
    }
}
