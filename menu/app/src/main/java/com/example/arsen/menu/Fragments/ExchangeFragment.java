package com.example.arsen.menu.Fragments;


import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.arsen.menu.Adapters.BuySellAdapter;
import com.example.arsen.menu.Adapters.ExchangeAdapter;
import com.example.arsen.menu.DBHelper;
import com.example.arsen.menu.Global_variables;
import com.example.arsen.menu.Good;
import com.example.arsen.menu.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ExchangeFragment extends Fragment {
    protected View myView;
    private ImageButton arrow;
//    private boolean isPressed;
    private Spinner left_spinner, right_spinner;

    ArrayList<String> storage_list_left, storage_list_right;
    ArrayAdapter<String> storage_adapter_left, storage_adapter_right;
    private int position_left, position_right;

    //ExchangeAdapter goods_adapter_left, goods_adapter_right;
    ArrayList<Good> goods_list_left, goods_list_right;
    ListView left_storage_list, right_storage_list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_exchange, container, false);
        this.myView = view;

        getActivity().setTitle(R.string.main_exchange);
        arrow = view.findViewById(R.id.arrow);
//        isPressed = false;

        left_spinner = view.findViewById(R.id.spinner_storage_left);
        storage_list_left = new ArrayList<>();
        DBHelper.Get_Storages(storage_list_left, getActivity());

        right_spinner = view.findViewById(R.id.spinner_storage_right);
        storage_list_right = new ArrayList<>();
        DBHelper.Get_Storages(storage_list_right, getActivity());

        myListener();
        return view;
    }

    @Override
    public void onResume () {
        super.onResume();

        position_left = storage_list_left.indexOf(Global_variables.CURRENT_STORAGE);
        position_right = position_left == 0 ? 1 : 0;
        Notify_spinners();
        right_spinner.setSelection(position_right);
        left_spinner.setSelection(position_left);

        Notify_list_left();
        Notify_list_right();
    }

    public void Notify_spinners () {
        storage_adapter_left = new ArrayAdapter<String>(getActivity(), R.layout.item_spinner, storage_list_left) {
            @Override
            public boolean isEnabled(int p) {
                return p != position_right;
            }
            @Override
            public View getDropDownView(int p, View convertView, @NonNull ViewGroup parent) {
                View view = super.getDropDownView(p, convertView, parent);
                TextView tv = (TextView) view;
                if (p == position_right)
                    tv.setTextColor(Color.GRAY);
                else
                    tv.setTextColor(Color.BLACK);
                return view;
            }
        };
        storage_adapter_right = new ArrayAdapter<String>(getActivity(), R.layout.item_spinner, storage_list_right) {
            @Override
            public boolean isEnabled(int p) {
                return p != position_left;
            }
            @Override
            public View getDropDownView(int p, View convertView, @NonNull ViewGroup parent) {
                View view = super.getDropDownView(p, convertView, parent);
                TextView tv = (TextView) view;
                if (p == position_left)
                    tv.setTextColor(Color.GRAY);
                else
                    tv.setTextColor(Color.BLACK);
                return view;
            }
        };

        left_spinner.setAdapter(storage_adapter_left);
        right_spinner.setAdapter(storage_adapter_right);
    }

    public void Notify_list_left () {
        goods_list_left = new ArrayList<>();
        DBHelper.Fill_Goods_from_database(goods_list_left, DBHelper.TABLE_GOODS_TYPE,
                (String) left_spinner.getItemAtPosition(position_left), getActivity());
        ExchangeAdapter goods_adapter_left = new ExchangeAdapter(getActivity(), goods_list_left);
        left_storage_list = myView.findViewById(R.id.left_storage_list);
        left_storage_list.setAdapter(goods_adapter_left);
    }

    public void Notify_list_right () {
        goods_list_right = new ArrayList<>();
        DBHelper.Fill_Goods_from_database(goods_list_right, DBHelper.TABLE_GOODS_TYPE,
                (String)right_spinner.getItemAtPosition(position_right), getActivity());
        ExchangeAdapter goods_adapter_right = new ExchangeAdapter(getActivity(), goods_list_right);
        right_storage_list = myView.findViewById(R.id.right_storage_list);
        right_storage_list.setAdapter(goods_adapter_right);
    }

    public void myListener() {
        arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBHelper dbHelper = new DBHelper(getActivity());
                SQLiteDatabase database = dbHelper.getWritableDatabase();
                Good current_good;
                ContentValues cv = new ContentValues();
                for (int i = 0; i < goods_list_left.size(); ++i) {
                    current_good = goods_list_left.get(i);
                    if (current_good.isChecked) {
                        cv.put(dbHelper.KEY_STORAGE_NAME, (String) right_spinner.getSelectedItem());
                        cv.put(dbHelper.KEY_GOOD_TYPE, current_good.info[2]);
                        cv.put(dbHelper.KEY_GOOD_ARTICLE, current_good.info[3]);
                        cv.put(dbHelper.KEY_GOOD_SIZE, current_good.info[4]);
                        cv.put(dbHelper.KEY_GOOD_COLOR, current_good.info[5]);
                        cv.put(dbHelper.KEY_GOOD_PRODUCER, current_good.info[6]);
                        cv.put(dbHelper.KEY_GOOD_PRODUCER_CODE, current_good.info[7]);
                        database.update(DBHelper.TABLE_GOODS_TYPE, cv,
                                DBHelper.KEY_GOOD_ID + "= " + current_good.info[0], null);
                    }
                }
                for (int i = 0; i < goods_list_right.size(); ++i) {
                    current_good = goods_list_right.get(i);
                    if (current_good.isChecked) {
                        cv.put(dbHelper.KEY_STORAGE_NAME, (String) left_spinner.getSelectedItem());
                        cv.put(dbHelper.KEY_GOOD_TYPE, current_good.info[2]);
                        cv.put(dbHelper.KEY_GOOD_ARTICLE, current_good.info[3]);
                        cv.put(dbHelper.KEY_GOOD_SIZE, current_good.info[4]);
                        cv.put(dbHelper.KEY_GOOD_COLOR, current_good.info[5]);
                        cv.put(dbHelper.KEY_GOOD_PRODUCER, current_good.info[6]);
                        cv.put(dbHelper.KEY_GOOD_PRODUCER_CODE, current_good.info[7]);
                        database.update(DBHelper.TABLE_GOODS_TYPE, cv,
                                DBHelper.KEY_GOOD_ID + "= " + current_good.info[0], null);
                    }
                }
                Notify_list_left();
                Notify_list_right();
                database.close();
            }
        });
        left_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                position_left = i;
                Notify_list_left ();
//                Toast.makeText(getActivity(), String.valueOf(position_left), Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
//
            }
        });
        right_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                position_right = i;
                Notify_list_right();
//                Toast.makeText(getActivity(), String.valueOf(position_right), Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }
}