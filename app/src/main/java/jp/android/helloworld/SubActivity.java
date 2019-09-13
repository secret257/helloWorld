package jp.android.helloworld;

//AndroidX
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
//import android.support.v7.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;


public class SubActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sub);

        Button returnButton = findViewById(R.id.return_button);
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog=new AlertDialog.Builder(SubActivity.this);

                // ダイアログの設定
                alertDialog.setTitle("title");      //タイトル設定
                alertDialog.setMessage("massage");  //内容(メッセージ)設定

                // OK(肯定的な)ボタンの設定
                alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // OKボタン押下時の処理
                        Log.d("AlertDialog", "Positive which :" + which);
                        finish();
                    }
                });

                // SKIP(中立的な)ボタンの設定
                alertDialog.setNeutralButton("SKIP", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // SKIPボタン押下時の処理
                        Log.d("AlertDialog", "Neutral which :" + which);
                    }
                });

                // NG(否定的な)ボタンの設定
                alertDialog.setNegativeButton("NG", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // NGボタン押下時の処理
                        Log.d("AlertDialog", "Negative which :" + which);
                    }
                });

                // ダイアログの作成と描画
                alertDialog.show();
            }
        });


//        Button popupButton = (Button)findViewById(R.id.popup_button);
//        //ポップアップを表示するボタン
//        popupButton.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View v)
//            {
//                PopupWindow popupWin;
//
//                /*ポップアップに表示するレイアウトの設定*/
//                LinearLayout popLayout
//                        = (LinearLayout)getLayoutInflater().inflate(
//                        R.layout.popup_window, null);
//                TextView popupText
//                        = (TextView)popLayout.findViewById(R.id.popup_text);
//                popupText.setText("Popup Text");
//
//                /*ポップアップの作成*/
//                popupWin = new PopupWindow(SubActivity.this);
//                popupWin.setWindowLayoutMode(
//                        LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//                popupWin.setContentView(popLayout);
//                popupWin.setBackgroundDrawable(null);
//                popupWin.showAsDropDown(v, 0, 0);
//                //ボタンの下にポップアップを表示
//
//            }
//        });
    }
}