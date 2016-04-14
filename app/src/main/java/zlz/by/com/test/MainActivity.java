package zlz.by.com.test;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {
//    http://japi.juhe.cn/comic/chapterContent?comicName=火影忍者&id=139834&key=
    private String str;
    HttpUrlConnect zlz = new HttpUrlConnect();
    private Handler myhandler = new Handler(){};
    List<String> imglist = new ArrayList<String>();
    private ImageView img;
    private int time = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        img= (ImageView) findViewById(R.id.img);
        img.setOnClickListener(myclick);
        new Thread(new Runnable() {
            @Override
            public void run() {
                str = zlz.httpgetjson("http://japi.juhe.cn/comic/chapterContent","comicName=火影忍者&id=139834",
                        "POST","key=5cd740e8bbca7ed129972c4577da0a13");
                Log.e("zlz", str + "llll");

                try {
                    JSONTokener JST = new JSONTokener(str);
                    JSONObject zlz1 = (JSONObject) JST.nextValue();
                    String zlz2 = zlz1.getString("result");
                    JSONObject zlz3 = new JSONObject(zlz2);
                    JSONArray  zlz4  =  zlz3.getJSONArray("imageList");
                    Log.e("zlz",zlz2.length() + "");
                    for(int i = 0; i < zlz2.length();i++)
                    {
                        JSONObject jszlz = zlz4.getJSONObject(i);
                        String zlz6 = jszlz.getString("imageUrl");
                        imglist.add(zlz6);


                    }
                } catch (JSONException e) {

                    e.printStackTrace();
                }

            }



        }).start();

    }
 private View.OnClickListener myclick = new View.OnClickListener() {
     @Override
     public void onClick(View v) {

         Log.e("zlz",time + "");

           new Thread(new Runnable() {
               @Override
               public void run() {
                   try {
                       URL url = new URL(imglist.get(time));
                       HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                      final  Bitmap bitmap = BitmapFactory.decodeStream(conn.getInputStream());
                       myhandler.post(new Runnable() {
                           @Override
                           public void run() {

                               img.setImageBitmap(bitmap);
                               time ++ ;
                               if(time == 105){
                                   time = 0;


                               }


                           }
                       });


                       } catch (IOException e) {
                           e.printStackTrace();
                       }





               }
           }).start();


     }
 };

}
