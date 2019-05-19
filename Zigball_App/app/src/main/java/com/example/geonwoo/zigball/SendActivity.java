package com.example.geonwoo.zigball;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashMap;

public class SendActivity extends AppCompatActivity {

    String sendText;
    String viewText = "";
    Button btn_spin;
    Button btn_push;
    Button btn_move;
    Button btn_send;

    EditText text_spin;
    EditText text_push;
    EditText text_move;

    TextView textview_spin;
    TextView textview_push;
    TextView textview_move;
    TextView textview_pattern;

    //post 방식으로 보내기 위해서 해쉬맵 선언
    HashMap<String, String> mdata = new HashMap<String, String>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send);// 이것도 마찬가지

        btn_spin = (Button)findViewById(R.id.btn_spin);
        btn_push = (Button)findViewById(R.id.btn_push);
        btn_move = (Button)findViewById(R.id.btn_move);
        btn_send = (Button)findViewById(R.id.btn_send);

        text_spin = (EditText)findViewById(R.id.text_spin);
        text_push = (EditText)findViewById(R.id.text_push);
        text_move = (EditText)findViewById(R.id.text_move); //각각 버튼 액티비티에 있는거 이어주기

        textview_spin = findViewById(R.id.textview_spin);
        textview_push = findViewById(R.id.textview_push);
        textview_move = findViewById(R.id.textview_move);
        textview_pattern = findViewById(R.id.textview_pattern);

        textview_spin.setText("SPIN");
        textview_push.setText("PUSH");
        textview_move.setText("MOVE");
        textview_pattern.setText("");

        Intent intent = getIntent();
        if(intent.getExtras()!=null) {
            Log.i("abc", "hello");
            String context = intent.getExtras().getString("context");
            String[] value = context.split(",");
            text_spin.setText(value[0]);
            text_push.setText(value[1]);
            text_move.setText(value[2]);
        }

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    mdata.put("data", sendText); //해쉬맵에 data 라는 이름으로 sendText 전송

                    GetData task = new GetData(mdata); //post 정보 넣고
                    String tmp = task.execute(MainActivity.URL).get(); //url로 접속(서버로 접속) , url은 MainActivity class 에서 static 으로 선언한거 가져온거

                } catch (Exception e) {
                    Log.i("kim", e.toString());
                }
            }
        });

        btn_spin.setOnClickListener(new View.OnClickListener() { //여기서부턴 각각 버튼 이벤트 만들어준거(틀만 짜놓는거라 대충 해놨음)
            @Override
            public void onClick(View v) {
                try {
                    sendText += text_spin.getText().toString() + ","; // ooo,ooo,ooo, 이런식으로 보낼예정
                    viewText += "s-" + text_spin.getText().toString() + ",";
                    textview_pattern.setText(viewText);
                } catch (Exception e) {
                    Log.i("kim", e.toString());
                }
            }
        });

        btn_push.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    sendText += text_push.getText().toString() + ","; // ooo,ooo,ooo, 이런식으로 보낼예정
                    viewText += "p-" + text_push.getText().toString() + ",";
                    textview_pattern.setText(viewText);
                } catch (Exception e) {
                    Log.i("kim", e.toString());
                }
            }
        });

        btn_move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    sendText += text_move.getText().toString() + ","; // ooo,ooo,ooo, 이런식으로 보낼예정
                    viewText += "m-" + text_move.getText().toString() + ",";
                    textview_pattern.setText(viewText);
                } catch (Exception e) {
                    Log.i("kim", e.toString());
                }
            }
        });
    }
}
