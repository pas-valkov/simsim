package com.example.arsen.menu.Fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.arsen.menu.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SellFragment extends Fragment {

    protected View myView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_buy_sell, container, false);
        this.myView = view;

        getActivity().setTitle(R.string.sell_bar_name);

        return view;
    }

//    public void on_Purchase_page_2 (View view) {
//        boolean[] is_settled = new boolean[number_of_purchases];
//        LinearLayout mainLayer = (LinearLayout) myView.findViewById(R.id.Sell_list_layout);
//        int count = mainLayer.getChildCount();
//        CheckBox ccc;
//        for (int i = 0; i < count; ++i) {
//            ccc = (CheckBox) mainLayer.getChildAt(i);
//            is_settled[i] = ccc.isChecked();
//        }
//        /*
//        Intent ToPurchase_2Page = new Intent(this, Purchase_2.class);
//        ToPurchase_2Page.putExtra("blabla", is_settled);
//        startActivity(ToPurchase_2Page);*/
//
//
//    }
}
