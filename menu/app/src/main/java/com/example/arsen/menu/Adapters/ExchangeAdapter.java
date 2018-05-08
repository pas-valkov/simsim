package com.example.arsen.menu.Adapters;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.arsen.menu.DBHelper;
import com.example.arsen.menu.Fragments.AddFragment;
import com.example.arsen.menu.Fragments.BuyFragment;
import com.example.arsen.menu.Fragments.ControlFragment;
import com.example.arsen.menu.Fragments.SellFragment;
import com.example.arsen.menu.Good;
import com.example.arsen.menu.R;

import java.util.ArrayList;

public class ExchangeAdapter extends BaseAdapter {
    Context ctx;
    LayoutInflater lInflater;
    ArrayList<Good> objects;

    public ExchangeAdapter(Context context, ArrayList<Good> goods) {
        ctx = context;
        objects = goods;
        lInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    // кол-во элементов
    @Override
    public int getCount() {
        return objects.size();
    }

    // элемент по позиции
    @Override
    public Object getItem(int position) {
        return objects.get(position);
    }

    // id по позиции
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // используем созданные, но не используемые view
        View view = convertView;
        if (view == null)
            view = lInflater.inflate(R.layout.item_exchange, parent, false);

        final Good p = getProduct(position);

        ((TextView) view.findViewById(R.id.product_name)).setText(p.info[3]);//article
        ((TextView) view.findViewById(R.id.info_type)).setText(p.info[2]);

        CheckBox checkBox = view.findViewById(R.id.cbBox);
        checkBox.setOnCheckedChangeListener(myListener);
        checkBox.setTag(position);
        checkBox.setChecked(p.isChecked);
        return view;
    }

    CompoundButton.OnCheckedChangeListener myListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            getProduct((Integer) compoundButton.getTag()).isChecked = b;    //TODO REDO
        }
    };

    // товар по позиции
    Good getProduct(int position) {
        return ((Good) getItem(position));
    }
}
