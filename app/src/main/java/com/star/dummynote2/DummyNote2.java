package com.star.dummynote2;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

class DummyNote2 extends AppCompatActivity implements AdapterView.OnItemClickListener {

    String editString1;
    private EditText editText1;
    ListView listView;
    private DB mDbHelper;
    private int mNoteNumber = 1;
    private long rowId;
    private int count;
//    private Cursor mNotesCursor;

    private void setAdapter()     {
        mDbHelper = new DB(this).open();
        fillData();
    }

    private void fillData() {
        Cursor mNotesCursor = mDbHelper.getAll();
        startManagingCursor(mNotesCursor);
        String[] from = new String[] { "note" };
        int[] to = new int[] { android.R.id.text1 };
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
                android.R.layout.simple_list_item_1, mNotesCursor, from, to  );
        listView.setAdapter(adapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dummy_note2);
        listView = (ListView) findViewById(R.id.listView);
        listView.setEmptyView(findViewById(R.id.empty));
        listView.setOnItemClickListener(this);
        setAdapter();
    }
//    private String[] note_array = {
//            "Activities",
//            "Services",
//            "Content Providers",
//            "Broadcast Receiver"
//    };
//
//    private DB mDbHelper;
//
//    private void setAdapter() {
//
//        mDbHelper = new DB(this);
//        mDbHelper.open();
//
//        ArrayAdapter adapter = new ArrayAdapter<String>(this ,
//        android.R.layout.simple_list_item_1,
//        note_array);
//        listView.setAdapter(adapter);
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0 , 1 , 0 ,"新增").setIcon(android.R.drawable.ic_menu_add).
                setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        menu.add(0 , 2 , 0 ,"刪除").setIcon(android.R.drawable.ic_menu_delete).
                setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        menu.add(0 , 3 , 0 ,"修改").setIcon(android.R.drawable.ic_menu_edit).
                setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()) {
//            case 1:
//                count++;
//                mDbHelper.create(count + "Note");
//                break;
//            case 2:
//                mDbHelper.delete(rowId);
//                setAdapter();
//                break;
        }
//            case 3:
        if (item.getItemId() == 1) {
            mNoteNumber = (listView.getCount() + 1);
            String str = mNoteNumber + "Note";
            mDbHelper.create(str);
            fillData();
        }
        else if(item.getItemId() == 2) {
            mDbHelper.delete(rowId);
            fillData();
        }
        else  {
            editText1 = new EditText(this);
            new AlertDialog.Builder(this).setTitle("修改項目名稱").setMessage("請輸入您想修改的項目名稱")
                    .setView(editText1).setPositiveButton("確認", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    mDbHelper.update(rowId, editText1.getText().toString());
                    fillData();
                }
            }).show();
        }return super.onOptionsItemSelected(item);
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        rowId = id;
        Toast.makeText(this , "第" + (position + 1) + "項" ,Toast.LENGTH_SHORT).show();
        AlertDialog.Builder rowId = new AlertDialog.Builder(this);
        rowId.setTitle("AlertDialog : " + (position + 1));
        rowId.setMessage("這裡可以用來顯示Alert訊息，要按[回上頁]鍵或是「確認」鈕才會關閉");
        rowId.setPositiveButton("確認", null);
        rowId.show();
    }
}

