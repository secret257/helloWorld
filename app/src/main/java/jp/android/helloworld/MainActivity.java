package jp.android.helloworld;

//AndroidX
import androidx.appcompat.app.AppCompatActivity;
//import android.support.v7.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableRow;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {

    private UploadTask task;
    // wordを入れる
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.uriname);

        Button post = findViewById(R.id.post);

        // ボタンをタップして非同期処理を開始
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String param0 = editText.getText().toString();

                if(param0.length() != 0){
                    task = new UploadTask();
                    task.setListener(createListener());
                    task.execute(param0);
                }

            }
        });

        // 画面遷移
        Button trans = findViewById(R.id.trans);
        trans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), SubActivity.class);
                startActivity(intent);
            }
        });
    }


    @Override
    protected void onDestroy() {
        task.setListener(null);
        super.onDestroy();
    }

    private UploadTask.Listener createListener() {
        return new UploadTask.Listener() {
            @Override
            public void onSuccess(JSONArray result) {
//                textView.setText(result);
                // TableLayoutのグループを取得
                ViewGroup vg = (ViewGroup)findViewById(R.id.TableLayout1);
                int rowCnt = vg.getChildCount();
                if (rowCnt > 1) {
                    for (int j = 0; j < rowCnt; j++) {
                        vg.removeView((TableRow)vg.getChildAt(j+1));
                    }
                }

                try {
                for (int i=0; i < result.length(); i++) {
                    JSONObject j_obj = result.getJSONObject(i);

                    // 行を追加
                    getLayoutInflater().inflate(R.layout.table_row, vg);
                    // 文字設定
                    TableRow tr = (TableRow)vg.getChildAt(i+1);
                    ((TextView)(tr.getChildAt(0))).setText(j_obj.getString("USER_ID"));
                    ((TextView)(tr.getChildAt(1))).setHint(j_obj.getString("USER_NAME"));
                    ((TextView)(tr.getChildAt(2))).setText(j_obj.getString("SEQ"));
                    ((TextView)(tr.getChildAt(3))).setText(j_obj.getString("CREATE_DATE"));
                }
                }catch(JSONException e){
                    Log.e("Error","JSONデータが不正");
                    e.printStackTrace();
                }
            }
        };
    }
}