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

public class ControlAdapter extends BaseAdapter {
    Context ctx;
    LayoutInflater lInflater;
    ArrayList<Good> objects;

    public ControlAdapter(Context context, ArrayList<Good> goods) {
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
            view = lInflater.inflate(R.layout.item_control, parent, false);

        final Good p = getProduct(position);

        ((TextView) view.findViewById(R.id.product_name)).setText(p.info[3]);//article
        ((TextView) view.findViewById(R.id.info_type)).setText(p.info[2]);
        ((TextView) view.findViewById(R.id.info_size)).setText(p.info[4]);
        ((TextView) view.findViewById(R.id.info_color)).setText(p.info[5]);
        ((TextView) view.findViewById(R.id.info_producer)).setText(p.info[6]);
        if (p.info[7].equals(""))
            ((TextView) view.findViewById(R.id.info_vendor_code)).setText("     ");
        else
            ((TextView) view.findViewById(R.id.info_vendor_code)).setText(p.info[7]);

            ImageButton delete = view.findViewById(R.id.delete);
            delete.setFocusable(false);
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DBHelper dbHelper = new DBHelper(ctx);
                    SQLiteDatabase database = dbHelper.getWritableDatabase();
                    database.delete(DBHelper.TABLE_GOODS_TYPE, DBHelper.KEY_GOOD_ID + "= ?", new String[]{p.info[0]});
//                    objects.remove(position);     //find good by _id which is [0]
//                        final ControlAdapter controlAdapter = new ControlAdapter(ctx, goods, ControlFragment.this);
//                        controlAdapter.notifyDataSetChanged();    //TODO REDO
//                        listView.setAdapter(controlAdapter);

                    ((LinearLayout)view.getParent()).removeAllViews();

//                    if (objects.size() == 0) {
//                        Toast.makeText(ctx, "NOTHING", Toast.LENGTH_LONG).show();
//                        TextView textView = view.findViewById(R.id.no_result);
//                        textView.setText(R.string.no_result);
//                        (()view.getParent().getParent());
//                    }

                    database.close();
                    Toast.makeText(ctx, R.string.item_delete_message, Toast.LENGTH_SHORT).show();
                }
            });
            ImageButton rewrite = (view.findViewById(R.id.rewrite));
            rewrite.setFocusable(false);
            rewrite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle edited_item = new Bundle();
                    int id_rewrited_element = Integer.valueOf(p.info[0]);
                    edited_item.putInt("id_of_edited_item", id_rewrited_element);
                    AddFragment fadd = new AddFragment();
                    fadd.setArguments(edited_item);
                    FragmentManager manager = ((Activity) ctx).getFragmentManager();
                    FragmentTransaction ftrans = manager.beginTransaction();
                    ftrans.replace(R.id.container, fadd);
                    ftrans.commit();
                }
            });
        return view;
    }

    // товар по позиции
    private Good getProduct(int position) {
        return ((Good) getItem(position));
    }
}
