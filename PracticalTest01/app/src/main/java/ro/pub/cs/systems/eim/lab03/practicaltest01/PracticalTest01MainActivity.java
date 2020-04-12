package ro.pub.cs.systems.eim.lab03.practicaltest01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import ro.pub.cs.systems.eim.lab03.practicaltest01.service.PracticalTest01Service;
import ro.pub.cs.systems.eim.lab03.practicaltest01.utilities.Constants;

public class PracticalTest01MainActivity extends AppCompatActivity {

    private EditText textLeft;
    private EditText textRight;
    private Button incrLeft;
    private Button incrRight;
    private Button navigate;

    private Integer serviceStatus = Constants.SERVICE_STOPPED;

    private IncrementLeftButtonListener incrLeftListener = new IncrementLeftButtonListener();
    private class IncrementLeftButtonListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            textLeft.setText(String.valueOf(Integer.parseInt(textLeft.getText().toString()) + 1));

            int leftNumberOfClicks = Integer.parseInt(textLeft.getText().toString());
            int rightNumberOfClicks = Integer.parseInt(textRight.getText().toString());

            if (leftNumberOfClicks + rightNumberOfClicks > Constants.NUMBER_OF_CLICKS_THRESHOLD
                    && serviceStatus == Constants.SERVICE_STOPPED) {
                Intent intent = new Intent(getApplicationContext(), PracticalTest01Service.class);
                intent.putExtra(Constants.FIRST_NUMBER, leftNumberOfClicks);
                intent.putExtra(Constants.SECOND_NUMBER, rightNumberOfClicks);
                getApplicationContext().startService(intent);
                serviceStatus = Constants.SERVICE_STARTED;
            }
        }
    }

    private IncrementRightButtonListener incrRightListener = new IncrementRightButtonListener();
    private class IncrementRightButtonListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            textRight.setText(String.valueOf(Integer.parseInt(textRight.getText().toString()) + 1));

            int leftNumberOfClicks = Integer.parseInt(textLeft.getText().toString());
            int rightNumberOfClicks = Integer.parseInt(textRight.getText().toString());

            if (leftNumberOfClicks + rightNumberOfClicks > Constants.NUMBER_OF_CLICKS_THRESHOLD
                    && serviceStatus == Constants.SERVICE_STOPPED) {
                Intent intent = new Intent(getApplicationContext(), PracticalTest01Service.class);
                intent.putExtra(Constants.FIRST_NUMBER, leftNumberOfClicks);
                intent.putExtra(Constants.SECOND_NUMBER, rightNumberOfClicks);
                getApplicationContext().startService(intent);
                serviceStatus = Constants.SERVICE_STARTED;
            }
        }
    }

    private NavigateButtonClickListener navigateButtonClickListener = new NavigateButtonClickListener();
    private class NavigateButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getApplicationContext(), PracticalTest01SecondaryActivity.class);
            intent.putExtra("number_of_pressings", Integer.parseInt(textLeft.getText().toString()) + Integer.parseInt(textRight.getText().toString()));
            startActivityForResult(intent, 1);
        }
    }

    private MessageBroadcastReceiver messageBroadcastReceiver = new MessageBroadcastReceiver();
    private class MessageBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(Constants.BROADCAST_RECEIVER_TAG, intent.getStringExtra(Constants.BROADCAST_RECEIVER_EXTRA));
        }
    }

    private IntentFilter intentFilter = new IntentFilter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_main);

        for (int index = 0; index < Constants.actionTypes.length; index++) {
            intentFilter.addAction(Constants.actionTypes[index]);
        }

        textLeft = (EditText)findViewById(R.id.left_edit_text);
        textRight = (EditText)findViewById(R.id.right_edit_text);
        textLeft.setText("0");
        textRight.setText("0");

        incrLeft = (Button)findViewById(R.id.left_button);
        incrRight = (Button)findViewById(R.id.right_button);
        incrLeft.setOnClickListener(incrLeftListener);
        incrRight.setOnClickListener(incrRightListener);

        navigate = (Button)findViewById(R.id.navigate_to_secondary_activity_button);
        navigate.setOnClickListener(navigateButtonClickListener);

        for (int index = 0; index < Constants.actionTypes.length; index++) {
            intentFilter.addAction(Constants.actionTypes[index]);
        }
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == 1) {
            Toast.makeText(getApplication(), "Aplicatie realizata cu succes apasat de " + resultCode, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(messageBroadcastReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        unregisterReceiver(messageBroadcastReceiver);
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        Intent intent = new Intent(this, PracticalTest01Service.class);
        stopService(intent);
        super.onDestroy();
    }
}
