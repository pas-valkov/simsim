package com.example.arsen.menu.Fragments;


import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.arsen.menu.DBHelper;
import com.example.arsen.menu.EnterPage;
import com.example.arsen.menu.Global_variables;
import com.example.arsen.menu.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddFragment extends Fragment {
    protected View myView;
    private Button add_btn;
    private DBHelper dbHelper;
    private SQLiteDatabase Goods_database;
    private ContentValues cv;

    private EditText add_article;
    private Spinner  add_type;
    private EditText add_size;
    private EditText add_color;
    private EditText add_producer;
    private EditText add_vendor_code;
    private int id;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add, container, false);
        this.myView = view;

        add_btn         = myView.findViewById(R.id.add_btn);
        add_article     = myView.findViewById(R.id.article);
        add_type        = myView.findViewById(R.id.type);
        add_size        = myView.findViewById(R.id.size);
        add_color       = myView.findViewById(R.id.color);
        add_producer    = myView.findViewById(R.id.producer);
        add_vendor_code = myView.findViewById(R.id.vendor_code);
        myListener();

        dbHelper = new DBHelper(getActivity());
        Goods_database = dbHelper.getWritableDatabase();
        cv = new ContentValues();

        SetLayoutSettings();//TODO

        Bundle edited_item = getArguments();
        if (edited_item != null){
            id = edited_item.getInt("id_of_edited_item");
            getActivity().setTitle(R.string.edit_item);
            add_btn.setText(R.string.edit);
            Fill_fields_of_edited_item(id);
        }
        else {
            getActivity().setTitle(R.string.add_item);
        }
        return view;
    }
    @Override
    public void onPause () {
        super.onPause();
        Hide_Keyboard();
    }

    public void Hide_Keyboard () {
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public boolean CheckFields(String it1, String it2, String it3, String it4, String it5){
        if (it1.equals("") || it2.equals("") || it3.equals("") || it4.equals("") || it5.equals("")) {
            Toast.makeText(getActivity(), R.string.error_empty, Toast.LENGTH_LONG).show();
            return false;
        }
        //еще какие-то условия на поля
        return true;
    }

    public void myListener(){
        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String article = add_article.getText().toString();
                String type = String.valueOf(add_type.getSelectedItem());
                String size = add_size.getText().toString();
                String color = add_color.getText().toString();
                String producer = add_producer.getText().toString();
                String vendor_code = add_vendor_code.getText().toString();
                boolean test = CheckFields(color, article, size, producer, type);
                if (test){
                    cv.put(dbHelper.KEY_STORAGE_NAME, Global_variables.CURRENT_STORAGE);
                    cv.put(dbHelper.KEY_GOOD_ARTICLE, article);
                    cv.put(dbHelper.KEY_GOOD_TYPE, type);
                    cv.put(dbHelper.KEY_GOOD_SIZE, size);
                    cv.put(dbHelper.KEY_GOOD_COLOR, color);
                    cv.put(dbHelper.KEY_GOOD_PRODUCER, producer);
                    cv.put(dbHelper.KEY_GOOD_PRODUCER_CODE, vendor_code);

                    if (getActivity().getTitle().equals(getString(R.string.add_item))) {
                        Goods_database.insert(DBHelper.TABLE_GOODS_TYPE, null, cv);
                        Toast.makeText(getActivity(), R.string.item_add_message, Toast.LENGTH_LONG).show();
                    }
                    else {
                        int temp_id = id;
                        Goods_database.update(DBHelper.TABLE_GOODS_TYPE, cv,
                                DBHelper.KEY_GOOD_ID + "= " + temp_id, null);
                        ControlFragment fcontrol = new ControlFragment();
                        FragmentTransaction ftrans = getFragmentManager().beginTransaction();
                        ftrans.replace(R.id.container, fcontrol);
                        ftrans.commit();
                        Toast.makeText(getActivity(), R.string.item_edit_message, Toast.LENGTH_LONG).show();
                    }

//                    add_type.setSelection(0);//TODO
//                    add_article.setText("");
//                    add_size.setText("");
//                    add_color.setText("");
//                    add_producer.setText("");
//                    add_vendor_code.setText("");
                }
                DBHelper.print_user_database(getActivity());
                DBHelper.print_storages_database(getActivity());
                DBHelper.print_goods_database(getActivity());
                Hide_Keyboard();
            }
        });
    }

    private void Fill_fields_of_edited_item (int id) {
        Cursor cursor = Goods_database.query(DBHelper.TABLE_GOODS_TYPE, null,
                "_id = " + id, null, null, null, null);
        cursor.moveToFirst();

        add_article.setText(cursor.getString(cursor.getColumnIndex(DBHelper.KEY_GOOD_ARTICLE)));

        int i = 0;
        String tmp = cursor.getString(cursor.getColumnIndex(DBHelper.KEY_GOOD_TYPE));
        while (!add_type.getItemAtPosition(i).equals(tmp)) {
            ++i;
        }
        add_type.setSelection(i);

//        int pos = ((ArrayAdapter)add_type.getAdapter()).getPosition(tmp); //TODO

        add_size.setText(cursor.getString(cursor.getColumnIndex(DBHelper.KEY_GOOD_SIZE)));
        add_color.setText(cursor.getString(cursor.getColumnIndex(DBHelper.KEY_GOOD_COLOR)));
        add_producer.setText(cursor.getString(cursor.getColumnIndex(DBHelper.KEY_GOOD_PRODUCER)));
        tmp = cursor.getString(cursor.getColumnIndex(DBHelper.KEY_GOOD_PRODUCER_CODE));
        if (tmp.equalsIgnoreCase(""))
            add_vendor_code.setHint(R.string.vendor_code);
        else
            add_vendor_code.setText(cursor.getString(cursor.getColumnIndex(DBHelper.KEY_GOOD_PRODUCER_CODE)));
        cursor.close();
    }

    private void setRed (int id_elem, int id_str) {
        EditText element = myView.findViewById(id_elem);
        SpannableString str = new SpannableString(getString(id_str) + "*");
        str.setSpan(new ForegroundColorSpan(Color.RED), str.length()-1, str.length(), 0);
        element.setHint(str);
        element.setText("xer");//TODO
    }

    private void SetLayoutSettings () {
        setRed(R.id.article, R.string.article);
        setRed(R.id.size, R.string.size);
        setRed(R.id.color, R.string.color);
        setRed(R.id.producer, R.string.producer);
//        Spinner add_type = myView.findViewById(R.id.type);
//        add_type.setWidth(add_article.getWidth());
    }

}
