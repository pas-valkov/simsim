package com.example.arsen.menu.Fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.arsen.menu.Adapters.BuySellAdapter;
import com.example.arsen.menu.DBHelper;
import com.example.arsen.menu.Global_variables;
import com.example.arsen.menu.Good;
import com.example.arsen.menu.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class BuyFragment extends Fragment {

    protected View myView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_buy_sell, container, false);
        this.myView = view;

        getActivity().setTitle(R.string.buy_bar_name);

        final ArrayList<Good> goods = new ArrayList<>();
        DBHelper.Fill_Goods_from_database(goods, DBHelper.TABLE_GOODS_TYPE, Global_variables.CURRENT_STORAGE, getActivity());
        final BuySellAdapter buy_sell_adapter = new BuySellAdapter(getActivity(), goods);
        final ListView listView = myView.findViewById(R.id.buy_list);
        listView.setAdapter(buy_sell_adapter);

        return view;
    }

    public void on_Purchase_page_2 (View view) {
//        boolean[] is_settled = new boolean[number_of_purchases];
//        LinearLayout mainLayer = myView.findViewById(R.id.Purchase_list_layout);
//        int count = mainLayer.getChildCount();
//        CheckBox ccc;
//        for (int i = 0; i < count; ++i) {
//            ccc = (CheckBox) mainLayer.getChildAt(i);
//            is_settled[i] = ccc.isChecked();
//        }

    }

}
