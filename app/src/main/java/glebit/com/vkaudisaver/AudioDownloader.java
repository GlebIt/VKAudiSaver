package glebit.com.vkaudisaver;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.util.ByteArrayBuffer;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Gleb on 28.04.2016.
 */
public class AudioDownloader extends AsyncTask
{

    private AudioAdapter.ViewHolder mHolder;

    public AudioDownloader(AudioAdapter.ViewHolder holder)
    {
        mHolder=holder;
    }

    @Override
    protected Object doInBackground(Object[] params)
    {
            DownloadFromUrl(params[0].toString(), params[1].toString() + ".mp3");
//                ("https://cs3-2v4.vk-cdn.net/p16/0a8a6445814d69.mp3?extra=6zf_3AWtLjhQXPVjoD3VmC8NdmpKDsHZqncFfQWDBea6G6Cdtt73E7LQmbKYaGM2bDYZu1OgKshp_oiajJZaAle63DSL10mswj0m0LlOEe_xfQNfxLefZMn3sxvFVw5wx6c027YTqg"
//                , "download.mp3");
            return null;
    }

    public void DownloadFromUrl(String DownloadUrl, String fileName) {

        try {
            // выбираем папку(создаем если ее нет)
            File root = android.os.Environment.getExternalStorageDirectory();

            File dir = new File (root.getAbsolutePath() + "/VKAudio");
            if(dir.exists()==false) {
                dir.mkdirs();
            }

            URL url = new URL(DownloadUrl);
            File file = new File(dir, fileName);

            long startTime = System.currentTimeMillis();
//            Log.d("DownloadManager", "download begining");
//            Log.d("DownloadManager", "download url:" + url);
//            Log.d("DownloadManager", "downloaded file name:" + fileName);

            URLConnection ucon = url.openConnection();
            // получаем массив байт
            InputStream is = ucon.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is);

            ByteArrayBuffer baf = new ByteArrayBuffer(5000);
            int current = 0;
            while ((current = bis.read()) != -1) {
                baf.append((byte) current);
            }

            // пишем массив в файл
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(baf.toByteArray());
            fos.flush();
            fos.close();
//            Log.d("DownloadManager", "download ready in" + ((System.currentTimeMillis() - startTime) / 1000) + " sec");

        } catch (IOException e) {
            Log.d("DownloadManager", "Error: " + e);
        }
    }

    @Override
    protected void onPostExecute(Object o)
    {
        // сменить иконку
        //mHolder.downloadImageView.setImageResource(R.drawable.ic_download_complete_24);
    }
}