package vn.edu.spx.assignmentduanmau.FragmentPackage;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import vn.edu.spx.assignmentduanmau.Adrapter.TopAdrapter;
import vn.edu.spx.assignmentduanmau.DAO.ThongKeDao;
import vn.edu.spx.assignmentduanmau.Object.Top;
import vn.edu.spx.assignmentduanmau.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Top10Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Top10Fragment extends Fragment {

    ListView lvTop;
    ArrayList<Top> list;
    TopAdrapter adrapter;

    public Top10Fragment() {
        // Required empty public constructor
    }

    public static Top10Fragment newInstance() {
        Top10Fragment fragment = new Top10Fragment();
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
        View v = inflater.inflate(R.layout.fragment_top10, container, false);
        lvTop = v.findViewById(R.id.lv_top);
        ThongKeDao thongKeDao = new ThongKeDao(getActivity());
        list = (ArrayList<Top>) thongKeDao.getTop();
        adrapter = new TopAdrapter(getActivity(),this,list);
        lvTop.setAdapter(adrapter);
        return v;
    }
}