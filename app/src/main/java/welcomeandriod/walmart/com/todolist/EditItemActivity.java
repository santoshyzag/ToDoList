package welcomeandriod.walmart.com.todolist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class EditItemActivity extends AppCompatActivity {
    private EditText editText;
    ArrayList<String> items;


    int fieldId = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        Intent intent = getIntent();
        fieldId = intent.getExtras().getInt("id");
        String lineId = readItems(fieldId);
        EditText editText = (EditText) findViewById(R.id.editText);

        editText.setText(lineId);
        editText.setSelection(lineId.length());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_item, menu);
        return true;
    }

    public void onSave(View view) {
        EditText editText = (EditText) findViewById(R.id.editText);
        String editedText = editText.getText().toString();


        if (!TextUtils.isEmpty(editedText)){

            writeItems(editedText);

            Intent intent = new Intent(this, MainActivity.class);
            setResult(2, intent);
//            System.out.println(RESULT_OK);
//            System.out.println(intent);
            finish();

        }

    }

    private String readItems(int id){

        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo1.txt");
        try{
            items = new ArrayList<String>(FileUtils.readLines(todoFile));
            String item = items.get(id);
            System.out.println(items.get(id));

        }
        catch (IOException e) {
            items = new ArrayList<>();
        }
        return items.get(id);
    }

    private void writeItems(String editedItem){
        items.remove(fieldId);

        items.add(fieldId, editedItem);

        File fileDir = getFilesDir();
        File todoFile = new File(fileDir, "todo1.txt");
        try{
            FileUtils.writeLines(todoFile, items);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }


}
