package com.example.geonwoo.zigball;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    Button btn_ip;
    EditText text_ip;
    static String URL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); //보라색으로 적힌 이름의 액티비티와 연결 (app\res\layout 에 있음)

        btn_ip = (Button) findViewById(R.id.btn_ip);
        text_ip = (EditText) findViewById(R.id.text_ip); //액티비티에 있는것과 이어주기

        btn_ip.setOnClickListener(new View.OnClickListener() { //버튼이벤트 익명메소드로 작성
            @Override
            public void onClick(View v) {
                try {
                    URL = text_ip.getText().toString(); //아이피 작성하면 그거 얻어오는 코드

                    Intent intent = new Intent(getApplicationContext(), HomeActivity.class); //다음 화면으로 넘기는 코드
                    startActivity(intent); //다음화면으로 넘기는 코드

                } catch (Exception e) {
                    Log.i("kim", e.toString()); //예외 처리날시 출력 (어디 출력되는지는 난중에 알려줄게)
                }
            }
        });
    }
}
