package com.tablet.elinmedia.wishwidetablet;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;

public class ImageManager {
    private static final String TAG = "ImageManager";
    public String bitmapToString(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
        byte[] bytes = outputStream.toByteArray();

        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }

    public Bitmap stringToBitmap(String encodedString) throws Exception {
        byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);

        return bitmap;
    }

    public Bitmap fileToBitmap(String imagePath) {
        Bitmap bitmap = BitmapFactory.decodeFile(imagePath);

//        int[] data = new int[bitmap.getWidth() * bitmap.getHeight()];
//        bitmap.getPixels(data, 0, bitmap.getWidth(), 0, 0,
//                bitmap.getWidth(), bitmap.getHeight());

        return bitmap;
    }

    //Cloud에서 다운로드한 파일을 콘텐츠 폴더에 파일명을 "콘텐츠명.확장자"로 저장
    public void downloadImage() {
//        Log.i(TAG, "콘텐츠 아이템 확인 : " + content);
        File contentsDir = new File("");
        int readByte = 0;

        //콘텐츠 관리 폴더 존재 확인, 없으면 생성
        boolean isContentExist = checkDirectory(contentsDir);

        if(!isContentExist) {
            makeDirectory(contentsDir);
        }

        HttpURLConnection connection = null;
        InputStream inputStream = null;
        FileOutputStream outputStream = null;
        try {
            //인터넷 연결
//            URL url = new URL(content.getContentFileUrl());
//            connection = (HttpURLConnection) url.openConnection();
            connection.connect();

            inputStream = new BufferedInputStream(connection.getInputStream());

//            outputStream = new FileOutputStream(content.getContentPath());
            byte[] buffer = new byte[connection.getContentLength()];

            while ((readByte = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, readByte);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (outputStream != null) {
                    outputStream.close();
                }

                if (inputStream != null) {
                    inputStream.close();
                }

                if (connection != null) {
                    connection.disconnect();
                }
            }
            catch (IOException e) {}
        }
    }

    //파일 디렉토리 존재 여부 체크
    public boolean checkDirectory(File dir) {
        return dir.exists();
    }

    //파일 디렉토리 생성
    public boolean makeDirectory(File dir) {
        return dir.mkdirs();
    }
}
