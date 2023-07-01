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

import vn.edu.spx.assignmentduanmau.FragmentPackage.QL_LoaiSachFragment;
import vn.edu.spx.assignmentduanmau.FragmentPackage.Top10Fragment;
import vn.edu.spx.assignmentduanmau.Object.LoaiSach;
import vn.edu.spx.assignmentduanmau.Object.ThanhVien;
import vn.edu.spx.assignmentduanmau.Object.Top;
import vn.edu.spx.assignmentduanmau.R;

public class TopAdrapter extends ArrayAdapter<Top> {

    private Context context;
    Top10Fragment fragment;
    private ArrayList<Top> lists;
    TextView tvsoluong, tvtensach;


    public TopAdrapter(@NonNull Context context, Top10Fragment fragment, @NonNull ArrayList<Top> lists) {
        super(context, 0, lists );
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
            v = inflater.inflate(R.layout.item_top,null);
        }
        final Top top = lists.get(position);
        if(top != null){ //nếu đối tượng k rỗng
            tvsoluong = v.findViewById(R.id.tv_soluong_item_top);
            tvtensach =v.findViewById(R.id.tv_tensach_item_top);

            tvsoluong.setText("Số lượng: "+top.getSoLuong());
            tvtensach.setText("Tên sách: "+top.getTenSach());
        }
        return v;
    }
}
