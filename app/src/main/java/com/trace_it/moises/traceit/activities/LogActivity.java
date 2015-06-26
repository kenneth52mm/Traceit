package com.trace_it.moises.traceit.activities;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.trace_it.moises.traceit.R;
import com.trace_it.moises.traceit.business.LoginWSHelper;

public class LogActivity extends ActionBarActivity {

    private LoginWSHelper helper = new LoginWSHelper();
    private TextView txtMessage;
    private EditText txtUsername;
    private EditText txtPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);

        txtMessage = (TextView)findViewById(R.id.TxtMessage);
        txtUsername = (EditText)findViewById(R.id.TxtUsername);
        txtPassword = (EditText)findViewById(R.id.TxtPassword);
    }

    public void validarUsuario(View v){
        helper.execute(txtUsername.getText().toString(), txtPassword.getText().toString());
        txtMessage.setText("Existe: " + helper.getValor());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_log, menu);
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
}
