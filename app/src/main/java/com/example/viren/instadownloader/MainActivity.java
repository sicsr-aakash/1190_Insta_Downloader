package com.example.viren.instadownloader;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import org.apache.commons.lang3.RandomStringUtils;


import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;





public class MainActivity extends ActionBarActivity {
    Button load_img;
    Button check;
    Button open;

    ImageView img;
    Bitmap bitmap;
    ProgressDialog pDialog;
    EditText paste_url;
    String imageUrl = "";
    int filenum;
    String link="";






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTheme(R.style.AppTheme);
        this.setContentView(R.layout.activity_main);
        paste_url = (EditText) findViewById(R.id.paste_url);
        load_img = (Button)findViewById(R.id.load);
        check = (Button)findViewById(R.id.check);
        img = (ImageView)findViewById(R.id.img);
        open=(Button)findViewById(R.id.open_instagram);




        check.setOnClickListener(new View.OnClickListener()


//-------------------------------------------------------------------------------------------------------------
        {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub

                new LoadImage().execute(paste_url.getText().toString());


//-------


//--------------

            }
        });


 //------------------------------------------------------------open instag-------------------------------------------------------


        open.setOnClickListener(new View.OnClickListener()


//-------------------------------------------------------------------------------------------------------------
        {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent LaunchIntent = getPackageManager().getLaunchIntentForPackage("com.instagram.android");

                try
                {
                    getPackageManager().getApplicationInfo("com.instagram.android", 0);
                    startActivity( LaunchIntent );
                }
                catch (PackageManager.NameNotFoundException e)
                {
                    Toast.makeText(MainActivity.this, "Instagram not installed \n Please install Instagram before proceeding", Toast.LENGTH_LONG).show();
                }





            }
        });
//---------------------------------------------------------------/open instag-------------------------------------------------------

        //------------------------------------------------------------------------------------+++++++++++++++++++++++++
        load_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                saveImage();
            }
        });







        //-----------------------------    -------------------------------------------------------------------------------
    }






    void saveImage() {
        File filename;
        try {
            String path = Environment.getExternalStorageDirectory().toString();
            Log.i("in save()", "after mkdir");
            String ext = "jpg";
            new File(path + "/InstaDownloader/").mkdir();
            String code =link.toString();
            String name = code.substring(code.lastIndexOf("/")+1, code.length());

          //  String name = String.format("%s.%s", RandomStringUtils.randomAlphanumeric(8), ext);
            filename = new File(path + "/InstaDownloader/img"+name+".jpg");
            Log.i("in save()", "after file");
            FileOutputStream out = new FileOutputStream(filename);
            Log.i("in save()", "after outputstream");
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
            Log.i("in save()", "after outputstream closed");
            MediaStore.Images.Media.insertImage(getContentResolver(),
                    filename.getAbsolutePath(), filename.getName(),
                    filename.getName());
            load_img.setText("Saved...");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //---------------------
    private class LoadImage extends AsyncTask<String, String, Bitmap> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage(paste_url.getText().toString());
            pDialog.show();
        }
        protected Bitmap doInBackground(String... args) {
            try {
                Document doc = Jsoup.connect(args[0]).get();
                Elements metalinks = doc.select("meta[property=og:image]");
                for(Element images : metalinks)
                    link=(images.attr("content"));
                bitmap = BitmapFactory.decodeStream((InputStream)new URL(link).getContent());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }
        protected void onPostExecute(Bitmap image) {
            if(image != null){
                img.setImageBitmap(image);
                pDialog.dismiss();
            }else{
                pDialog.dismiss();
                Toast.makeText(MainActivity.this, "Image Does Not exist or Network Error", Toast.LENGTH_SHORT).show();
            }
        }





    }







}
