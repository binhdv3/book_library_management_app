package vn.edu.spx.assignmentduanmau.FragmentPackage;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import vn.edu.spx.assignmentduanmau.DAO.ThongKeDao;
import vn.edu.spx.assignmentduanmau.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ThongKeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ThongKeFragment extends Fragment {

    Button btnTungay, btnDenngay, btnThongke;
    TextView tvTongtien;
    EditText edTungay, edDenngay;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    int mYear, mMouth, mDay;

    public ThongKeFragment() {
        // Required empty public constructor
    }
    
    public static ThongKeFragment newInstance() {
        ThongKeFragment fragment = new ThongKeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_doanh_thu, container, false);
        edTungay = v.findViewById(R.id.ed_tungay);
        edDenngay = v.findViewById(R.id.ed_denngay);
        btnTungay = v.findViewById(R.id.btn_tungay);
        btnDenngay = v.findViewById(R.id.btn_denngay);
        edDenngay = v.findViewById(R.id.ed_denngay);
        btnThongke = v.findViewById(R.id.btn_thongke);
        tvTongtien = v.findViewById(R.id.tv_tongtien);
        btnTungay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMouth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog d = new DatePickerDialog(getActivity(),
                        0,dataTuNgay,mYear,mMouth,mDay);
                d.show();
            }
        });
        btnDenngay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMouth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog d = new DatePickerDialog(getActivity(),
                        0,dataDenNgay,mYear,mMouth,mDay);
                d.show();
            }
        });
        btnThongke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tungay = edTungay.getText().toString();
                String denngay = edDenngay.getText().toString();
                ThongKeDao thongKeDao = new ThongKeDao(getActivity());
                tvTongtien.setText("Doanh thu: "+thongKeDao.getDanhThu(tungay,denngay)+" VNĐ");
            }
        });
        return v;
    }

    DatePickerDialog.OnDateSetListener dataTuNgay = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            mYear = year;
            mMouth = month;
            mDay = dayOfMonth;
            GregorianCalendar c = new GregorianCalendar(mYear,mMouth,mDay);
            edTungay.setText(simpleDateFormat.format(c.getTime()));
        }
    };
    DatePickerDialog.OnDateSetListener dataDenNgay = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            mYear = year;
            mMouth = month;
            mDay = dayOfMonth;
            GregorianCalendar c = new GregorianCalendar(mYear,mMouth,mDay);
            edDenngay.setText(simpleDateFormat.format(c.getTime()));
        }
    };
}