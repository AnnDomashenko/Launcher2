package ua.com.doko.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        findViewById(R.id.call).setOnClickListener(onClickListener);
        findViewById(R.id.apps).setOnClickListener(onClickListener);
        findViewById(R.id.send_sms).setOnClickListener(onClickListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            openSettings();
        }
        return super.onOptionsItemSelected(item);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.call:
                    openCall();
                    break;
                case R.id.apps:
                    openApps();
                    break;
                case R.id.send_sms:
                    openSendSms();
                    break;
            }
        }
    };

    private void openSettings() {
        Intent intent = new Intent(android.provider.Settings.ACTION_SETTINGS);
        startActivity(intent);
    }

    private void openSendSms() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("sms:"));
        intent.putExtra("sms_body", "");
        startActivity(intent);
    }

    private void openCall() {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", "", null));
        startActivity(intent);
    }

    private void openApps() {
        Intent intent = new Intent(MainActivity.this, AppActivity.class);
        startActivity(intent);
    }
}
