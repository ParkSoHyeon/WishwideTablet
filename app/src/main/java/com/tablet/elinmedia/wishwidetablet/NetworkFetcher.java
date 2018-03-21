package com.tablet.elinmedia.wishwidetablet;

import android.util.Log;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static android.content.ContentValues.TAG;

public class NetworkFetcher {

    public static HttpURLConnection request(String strUrl, StringBuffer strBufferParams) {
        HttpURLConnection urlConn = null;

        try {
            URL url = new URL("http://172.29.133.225:8080/common/login");

            urlConn = (HttpURLConnection)url.openConnection();

            urlConn.setRequestMethod("POST");
            urlConn.setRequestProperty("Accept-Charset", "UTF-8");

            String strParams = strBufferParams.toString();
            Log.d(TAG, "파라미터 확인: " + strParams);

            OutputStream os = urlConn.getOutputStream();
            os.write(strParams.getBytes("UTF-8"));
            os.flush();
            os.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return urlConn;
    }

    public static String combineResult(BufferedReader reader) {
        // [2-4]. 읽어온 결과물 리턴.
        // 요청한 URL의 출력물을 BufferedReader로 받는다.

        // 출력물의 라인과 그 합에 대한 변수.
        String line;
        String result = "";

        // 라인을 받아와 합친다.
        try {
            while ((line = reader.readLine()) != null){
                result += line;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.d(TAG, "결과 값 확인: " + result);

        return result;
    }

    //URL로부터 원천 데이터를 가져와서 그것을 바이트 배열로 반환
    public byte[] geturlBytes(String urlSpec) throws IOException {
        URL url = new URL(urlSpec);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        try{
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            InputStream inputStream = connection.getInputStream();

            if(connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new IOException(connection.getResponseMessage() + ": with " + urlSpec);
            }

            int byteRead = 0;
            byte[] buffer = new byte[1024];

            while ((byteRead = inputStream.read(buffer)) > 0) {
                byteArrayOutputStream.write(buffer, 0, byteRead);
            }

            byteArrayOutputStream.close();

            return byteArrayOutputStream.toByteArray();
        }
        finally {
            connection.disconnect();
        }
    }

    //getUrlBytes(String)에서 반환된 결과를 String으로 반환
    public String getUrlString(String urlSpec) throws IOException {
        return new String(geturlBytes(urlSpec));
    }
}
