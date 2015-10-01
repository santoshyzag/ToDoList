package welcomeandriod.walmart.com.todolist;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterViewAnimator;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends Activity {

    ArrayList<String> items;
    ArrayAdapter<String> itemsAdapater;
    ListView lvItems;

    private EditText etNewItem;
    private final int REQUEST_CODE = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvItems = (ListView) findViewById(R.id.lvItems);
        items = new ArrayList<>();
        readItems();
        itemsAdapater = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        lvItems.setAdapter(itemsAdapater);
        setupListViewListener();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onAddItem(View view) {


            EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
            String itemText = etNewItem.getText().toString();

        if (!TextUtils.isEmpty(itemText)) {
            itemsAdapater.add(itemText);
            etNewItem.setText("");
            writeItems();
        }
    }

    private void setupListViewListener() {

        lvItems.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int pos, long id) {

                        items.remove(pos);
                        itemsAdapater.notifyDataSetChanged();
                        writeItems();
                        return true;
                    }
                }
        );

        AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, EditItemActivity.class);
                intent.putExtra("id", position);
                intent.putExtra("item", items.get(position));
                 startActivity(intent);}
        };
        lvItems.setOnItemClickListener(itemClickListener);

    }


    private void readItems(){

        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo1.txt");
        try{
            items = new ArrayList<String>(FileUtils.readLines(todoFile));

        }
        catch (IOException e) {
            items = new ArrayList<>();
        }
    }

    private void writeItems(){
        File fileDir = getFilesDir();
        File todoFile = new File(fileDir, "todo1.txt");
        try{
            FileUtils.writeLines(todoFile, items);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
}



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        readItems();
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode ==  2){

        String item = data.getExtras().getString("Item");
        int position = data.getExtras().getInt("id");

        }

    }

    public void onClick(View view) {
        Intent intent = new Intent(MainActivity.this, EditItemActivity.class);

        startActivityForResult(intent, 2);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        readItems();
        itemsAdapater = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        lvItems.setAdapter(itemsAdapater);
        setupListViewListener();
    }
}
