package ro.pub.cs.systems.eim.lab03.practicaltest01;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class PracticalTest01MainActivity extends AppCompatActivity {

    private EditText textLeft;
    private EditText textRight;
    private Button incrLeft;
    private Button incrRight;

    private IncrementLeftButtonListener incrLeftListener = new IncrementLeftButtonListener();
    private class IncrementLeftButtonListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            textLeft.setText(String.valueOf(Integer.parseInt(textLeft.getText().toString()) + 1));
        }
    }

    private IncrementRightButtonListener incrRightListener = new IncrementRightButtonListener();
    private class IncrementRightButtonListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            textRight.setText(String.valueOf(Integer.parseInt(textRight.getText().toString()) + 1));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_main);

        textLeft = (EditText)findViewById(R.id.left_edit_text);
        textRight = (EditText)findViewById(R.id.right_edit_text);
        textLeft.setText("0");
        textRight.setText("0");

        incrLeft = (Button)findViewById(R.id.left_button);
        incrRight = (Button)findViewById(R.id.right_button);
        incrLeft.setOnClickListener(incrLeftListener);
        incrRight.setOnClickListener(incrRightListener);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        EditText tl = (EditText)findViewById(R.id.left_edit_text);
        EditText tr = (EditText)findViewById(R.id.right_edit_text);
        savedInstanceState.putString("left_edit_text", textLeft.getText().toString());
        savedInstanceState.putString("right_edit_text", textRight.getText().toString());
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState.containsKey("left_edit_text")) {
            EditText tl = (EditText)findViewById(R.id.left_edit_text);
            tl.setText(savedInstanceState.getString("left_edit_text"));
        }
        if (savedInstanceState.containsKey("right_edit_text")) {
            EditText tr = (EditText)findViewById(R.id.right_edit_text);
            tr.setText(savedInstanceState.getString("right_edit_text"));
        }
    }
}
