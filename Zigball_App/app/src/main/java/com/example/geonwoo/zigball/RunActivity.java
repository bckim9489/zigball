package com.example.geonwoo.zigball;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class RunActivity extends Activity {

    Button btn_delete;
    Button btn_modify;
    boolean delete_list = false;
    boolean modify_list = false;
    private ListView listview ;
    private ListViewAdapter adapter;
    private ArrayList<Integer> img = new ArrayList<Integer>(); //{R.drawable.switch_3_3,R.drawable.switch_1_2,R.drawable.valve};
    private ArrayList<String> Title = new ArrayList<String>(); //{"거실","안방","가스밸브"};
    private ArrayList<String> Context = new ArrayList<String>(); //{"0,1,30","0,1,60","90,0,0"};

    public void test(){
        inputData(R.drawable.switch_3_3, "거실", "0,1,30");     // 임시 값
        inputData(R.drawable.switch_1_2, "안방", "0,1,60");     // 임시 값
        inputData(R.drawable.valve, "밸브", "90,0,0");     // 임시 값
    }

    protected void inputData(int img, String title, String context){ // 값 입력
        this.img.add(img);
        this.Title.add(title);
        this.Context.add(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        test();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_run);
        btn_delete = (Button)findViewById(R.id.btn_delete); // 삭제 버튼
        btn_modify = (Button)findViewById(R.id.btn_modify); // 수정 버튼
        btn_delete.setText("Delete");
        btn_modify.setText("Modify");
        delete_list = false;         // 삭제 상태
        modify_list = false;

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if(delete_list == false) {
                        delete_list = true;     // 삭제 상태
                        btn_delete.setBackgroundColor(Color.rgb(255, 0, 0));
                    }
                    else{
                        delete_list = false;    // 실행 상태
                        btn_delete.setBackgroundColor(Color.rgb(200, 200, 200));
                    }
                } catch (Exception e) {
                    Log.i("kim", e.toString());
                }
            }
        });

        //변수 초기화
        adapter = new ListViewAdapter();
        listview  = (ListView) findViewById(R.id.List_view);
        //어뎁터 할당
        listview.setAdapter(adapter);
        //adapter를 통한 값 전달
        for(int i=0; i<img.size();i++){
            adapter.addVO(ContextCompat.getDrawable(this,img.get(i)),Title.get(i),Context.get(i));
        }
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {        // 메시지박스
                if(delete_list == true){    // 삭제 메시지창
                    AlertDialog.Builder alert_confirm = new AlertDialog.Builder(RunActivity.this);
                    alert_confirm.setMessage("삭제하시겠습니까?").setCancelable(false).setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) { // 확인 눌렀을 시 삭제
                            adapter.delete(position);
                            img.remove(position);
                            Context.remove(position);
                            Title.remove(position);
                            adapter.notifyDataSetChanged();
                            return;
                        }
                    }).setNegativeButton("취소", new DialogInterface.OnClickListener() { // 취소 눌렀을 시 return
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // 'No'
                            return;
                        }
                    });
                    AlertDialog alert = alert_confirm.create();
                    alert.show();
                    // pattern Index PHP로 보내서 삭제, index == position
                }
                else {
                    Intent intent = new Intent(getApplicationContext(), SendActivity.class); // 전송
                    intent.putExtra("context", Context.get(position));
                    startActivity(intent);
                }
            }
        });
    }
}