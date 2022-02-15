package com.example.cameraapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.bumptech.glide.Glide;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    ActivityResultLauncher<Intent> activityResultLauncher;
    ActivityResultLauncher<Intent> activityResultLauncher2;
    Button btn1;
    Button btn2;
    Button btn3;
    ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //카메라 버튼
        btn1 = findViewById(R.id.btn1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //카메라 인텐트 설정
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    File photoFile = null;
                    try {
                        photoFile = createImageFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    if (photoFile != null) {
                        Uri photoURI = FileProvider.getUriForFile(getApplicationContext(), "com.example.android.fileprovider", photoFile);
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
//                        Log.d("1", "uri ;  " + photoURI.getPath());
                        activityResultLauncher.launch(takePictureIntent);
                    }
                }
            }
        });

        //카메라 결과 받기
        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == RESULT_OK) {


                    //앱 내부공간에 저장되어있는 파일을 갤러리 저장공간으로 복사
                    String inputPath = currentPhotoPath.substring(0, currentPhotoPath.lastIndexOf("/"));
                    String inputFile = currentPhotoPath.substring(currentPhotoPath.lastIndexOf("/")+1);
                    String outputPath = "/storage/emulated/0/DCIM/Camera";
                    InputStream in = null;
                    OutputStream out = null;

                    try {
                        File dir = new File(outputPath);
                        if(!dir.exists()) {
                            dir.mkdirs();
                        }

                        Log.d("1", "asd :  " + inputPath);

                        in = new FileInputStream(inputPath + "/" + inputFile);
                        out = new FileOutputStream(outputPath + "/" + inputFile);

                        byte[] buffer = new byte[1024];
                        int read;
                        while((read = in.read(buffer)) != -1) {
                            out.write(buffer, 0, read);
                        }
                        in.close();
                        in = null;

                        out.flush();
                        out.close();
                        out = null;

                        File tmp_file = new File(outputPath + "/" + inputFile);
                        getApplicationContext().sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(tmp_file)));

                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                }
            }
        });

        //갤러리 이동
        btn2 = findViewById(R.id.btn2);
        imageView = findViewById(R.id.imageView);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                activityResultLauncher2.launch(intent);
            }
        });
        activityResultLauncher2 = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == RESULT_OK) {
                    Uri uri = result.getData().getData();
                    String path = result.toString();
                    Glide.with(getApplicationContext()).load(uri).into(imageView);
//                    String path = uri.getPath();
//                    String[] split = path.split(":");






//                    Log.d("2", "uri :   " + getRealPathFromUri(uri));
//                    Log.d("1", "log :   " + split[0] + "    " + split[1]);
                    Log.d("3", "path  :  " + path);
                }
            }
        });

    }

    //uri를 path로 바꾸기
    public String getRealPathFromUri(Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};

        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        cursor.moveToNext();
        @SuppressLint("Range") String path = cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DATA));
        Uri uri = Uri.fromFile(new File(path));
        cursor.close();

        return path;
    }

    //파일 경로, 이름 생성
    String currentPhotoPath;
    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);

        currentPhotoPath = image.getAbsolutePath();
        return image;
    }




}