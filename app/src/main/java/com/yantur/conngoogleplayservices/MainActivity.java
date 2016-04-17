package com.yantur.conngoogleplayservices;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;


public class MainActivity extends Activity {


    TextView searchResult;
    EditText editText;
    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = (EditText) findViewById(R.id.editText);
        searchResult = (TextView) findViewById(R.id.result);
        linearLayout = (LinearLayout) findViewById(R.id.layout);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BackgroundRequest backgroundRequest = new BackgroundRequest(MainActivity.this, searchResult);
                String query = editText.getText().toString();
                query = query.replaceAll(" ", "+");
                String string_url = "https://www.googleapis.com/customsearch/v1?q=" + query +
                        "&cx=001121747010064086049:xwke6c1btya&key=AIzaSyCzwYJdfd_8W1BbTMCQggMFA8edh-7MPRw";
                backgroundRequest.execute(string_url);
            }
        });


    }
}

