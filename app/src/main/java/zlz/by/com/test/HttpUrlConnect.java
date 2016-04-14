package zlz.by.com.test;

import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by mac on 16/4/13.
 */
public class HttpUrlConnect {
    private  String  urlstr;
    private  String  content;

    private  String  key;

   public  String  httpgetjson(String urlstr,String content, final String  method,String key)
   {
       this.urlstr  = urlstr;
       this.content = content;
       this.key = key;
       String resultjson ;
        if(method == "GET"){
              resultjson = getjson();

        }else {
              resultjson = postjson();

         }
         Log.e("zlz",postjson() + "最");
        return resultjson;
   }
   private String getjson(){
       String str1 ="";


       try {
           String str = urlstr + "key=" + key + "&" + content;
           Log.e("zlz",key);
           Log.e("zlz",str);
           URL url = new URL(str);
           HttpURLConnection conn = (HttpURLConnection) url.openConnection();
           BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
           StringBuilder sb = new StringBuilder();
           String line = "";
           while ((line = br.readLine()) != null)
           {
             sb.append(line);

           }
           conn.disconnect();


           } catch (IOException e) {
               e.printStackTrace();
           }
       return  str1;

   }
    private  String postjson(){
        String str = "";
        try {
            Log.e("zlz", "post");
            URL url = new URL(urlstr);
            Log.e("zlz", urlstr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
            if(key == null){
            bw.write(content);}
            else {
              bw.write(content +"&"+ key);
              Log.e("zlz",content +"&"+ key);
            }
            bw.flush();
            bw.close();
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = "";
            while ((line = br.readLine()) != null)
            {
                sb.append(line);
                Log.e("zlz", line);

            }
            conn.disconnect();
            str = sb.toString();

            } catch (IOException e) {
                e.printStackTrace();
            }

        return   str;

    }


}