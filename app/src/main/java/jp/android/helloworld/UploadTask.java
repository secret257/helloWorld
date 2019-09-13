package jp.android.helloworld;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;


public class UploadTask extends AsyncTask<String, Void, JSONArray> {

    private Listener listener;

    // 非同期処理
    @Override
    protected JSONArray doInBackground(String... params) {

        // 使用するサーバーのURLに合わせる
        String urlSt = "http://192.168.0.10/test/test.php";

        HttpURLConnection httpConn = null;
        String result = null;
        JSONArray json_array = new JSONArray();
        String user_name = "USER_NAME="+params[0];

        try{
            // URL設定
            URL url = new URL(urlSt);

            // HttpURLConnection
            httpConn = (HttpURLConnection) url.openConnection();

            // request POST
            httpConn.setRequestMethod("POST");

            // no Redirects
            httpConn.setInstanceFollowRedirects(false);

            // データを書き込む
            httpConn.setDoOutput(true);

            // 時間制限
            httpConn.setReadTimeout(10000);
            httpConn.setConnectTimeout(20000);

            // 接続
            httpConn.connect();

            try(// POSTデータ送信処理
                OutputStream outStream = httpConn.getOutputStream()) {
                outStream.write( user_name.getBytes(StandardCharsets.UTF_8));
                outStream.flush();
                Log.d("debug","flush");
            } catch (IOException e) {
                // POST送信エラー
                e.printStackTrace();
                result = "POST送信エラー";
            }

            final int status = httpConn.getResponseCode();
            if (status == HttpURLConnection.HTTP_OK) {
                // レスポンスを受け取る処理等
                result="HTTP_OK";

                InputStream stream = httpConn.getInputStream();
                StringBuffer sb = new StringBuffer();
                String line = "";
                BufferedReader br = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
                // JSON解析：json_arrayにデータを格納。これでMySQLデータ読み込み完了です。
                try{
//                    JSONObject json_data = new JSONObject(sb.toString());
                    json_array = new JSONArray(sb.toString());
                    for (int i=0; i < json_array.length(); i++) {
                        JSONObject j_obj = json_array.getJSONObject(i);
                        Log.i( "Info", j_obj.getString("USER_ID"));
                        Log.i( "Info", j_obj.getString("USER_NAME"));
                        Log.i( "Info", j_obj.getString("SEQ"));
                        Log.i( "Info", j_obj.getString("CREATE_DATE"));
                    }
//                    JSONArray json_array = json_data.getJSONArray("response");
                }catch(JSONException e){
                    Log.e("Error","JSONデータが不正");
                    e.printStackTrace();
                }
            }
            else{
                result="status="+String.valueOf(status);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (httpConn != null) {
                httpConn.disconnect();
            }
        }
//        return result;
        return json_array;
    }

    // 非同期処理が終了後、結果をメインスレッドに返す
    @Override
    protected void onPostExecute(JSONArray result) {
        super.onPostExecute(result);

        if (listener != null) {
            listener.onSuccess(result);
        }
    }

    void setListener(Listener listener) {
        this.listener = listener;
    }

    interface Listener {
        void onSuccess(JSONArray result);
    }
}