package com.example.arsen.menu.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.arsen.menu.Adapters.ControlAdapter;
import com.example.arsen.menu.Global_variables;
import com.example.arsen.menu.Good;
import com.example.arsen.menu.DBHelper;
import com.example.arsen.menu.R;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ControlFragment extends Fragment {
    protected View myView;
//    AddFragment fadd;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view;
        final ArrayList<Good> goods = new ArrayList<>();
        DBHelper.Fill_Goods_from_database(goods, DBHelper.TABLE_GOODS_TYPE, Global_variables.CURRENT_STORAGE, getActivity());
//        if (goods.size() > 0) {
            //TODO goods.sort
            view = inflater.inflate(R.layout.fragment_control, container, false);
            this.myView = view;
            getActivity().setTitle(R.string.control_bar_name);
            final ControlAdapter controlAdapter = new ControlAdapter(getActivity(), goods);
            final ListView listView = myView.findViewById(R.id.lvMain);
            listView.setAdapter(controlAdapter);
//        }
//        else {
//            view = inflater.inflate(R.layout.no_results, container, false);
//            ((TextView)view.findViewById(R.id.no_result)).setText(R.string.no_result);
//            this.myView = view;
//        }
        return view;
    }
}
