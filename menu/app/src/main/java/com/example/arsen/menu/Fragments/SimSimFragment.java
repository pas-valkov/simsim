package com.example.arsen.menu.Fragments;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.arsen.menu.AddStorage;
import com.example.arsen.menu.DBHelper;
import com.example.arsen.menu.Global_variables;
import com.example.arsen.menu.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class SimSimFragment extends Fragment {
    protected View myView;
    private Spinner storages;
    private ImageButton add_storage_btn;
    private ImageButton remove_storage_btn;
    ArrayAdapter<String> adapter;
    ArrayList<String> list_of_storages;
    private int id_of_selected_item;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sim_sim, container, false);
        this.myView = view;

        storages = myView.findViewById(R.id.storages);
        list_of_storages = new ArrayList<>();
        DBHelper.Get_Storages(list_of_storages, getActivity());

        getActivity().setTitle(R.string.app_name);
        SetNavigationHeader(Global_variables.CURRENT_STORAGE);

        if (list_of_storages.size() > 0){
            adapter = new ArrayAdapter<>(getActivity(), R.layout.item_spinner, list_of_storages);
            storages.setAdapter(adapter);
        }
//        if (savedInstanceState != null) {
//            id_of_selected_item = (savedInstanceState.getInt("Choice", 0));
//            storages.setSelection(id_of_selected_item);
//        }
        add_storage_btn = view.findViewById(R.id.add_storage_btn);
        remove_storage_btn = view.findViewById(R.id.remove_storage_btn);
        myListener();
        return view;
    }
//    @Override
//    public void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//        outState.putInt("Choice", id_of_selected_item);//TODO
//    }
    private void myListener() {
        add_storage_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ToAddingStorage = new Intent(getActivity(), AddStorage.class);
                startActivity(ToAddingStorage);
            }
        });
        remove_storage_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (list_of_storages.size() > 0) {
                    DBHelper.Remove_Current_Storage(getActivity());
                    list_of_storages.remove(id_of_selected_item);
                    adapter.notifyDataSetChanged();
                    Global_variables.CURRENT_STORAGE = (String) storages.getSelectedItem();
                    SetNavigationHeader(Global_variables.CURRENT_STORAGE);
//                    id_of_selected_item = 0;
//                    storages.setSelection(id_of_selected_item);//TODO FUCKING KOSIAK
                    Toast.makeText(getActivity(), R.string.message_delete_storage, Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getActivity(), R.string.no_any_storage, Toast.LENGTH_SHORT).show();
                }
//                DBHelper.print_user_database(getActivity());
//                DBHelper.print_storages_database(getActivity());
//                DBHelper.print_goods_database(getActivity());
            }
        });
        storages.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Global_variables.CURRENT_STORAGE = (String) storages.getSelectedItem();
                SetNavigationHeader(Global_variables.CURRENT_STORAGE);
                id_of_selected_item = i;
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Global_variables.CURRENT_STORAGE = getString(R.string.no_any_storage);
                SetNavigationHeader(Global_variables.CURRENT_STORAGE);
                storages.setClickable(false);
            }
        });
    }
    private void SetNavigationHeader (String string) {
        NavigationView navigationView = getActivity().findViewById(R.id.nav_view);
        View header = navigationView.getHeaderView(0);
        ((TextView)header.findViewById(R.id.storage_name)).setText(string);
    }
}
