package com.example.cameraapp;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

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

    String currentPhotoPath;
    public static String tempFileName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //강제종료 했을때 임시파일 제거하기 위한 서비스 실행
        startService(new Intent(getBaseContext(), ClearService.class));

        //카메라 버튼
        btn1 = findViewById(R.id.btn1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activeCamera();
            }
        });

        //카메라 결과 받기
        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == RESULT_OK) {
                    copyImageToGallery();
                } else {
                    deleteTempFile();
                }
            }
        });

        //갤러리 이동
        btn2 = findViewById(R.id.btn2);
        imageView = findViewById(R.id.imageView);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activeGallery();
            }
        });

        //갤러리 결과 받기
        activityResultLauncher2 = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == RESULT_OK) {
                    Uri uri = result.getData().getData();
                    saveImageToApp(uri);
//                    Glide.with(getApplicationContext()).load(uri).into(imageView);
                }
            }
        });

        //저장된 사진을 리스트로 출력
        btn3 = findViewById(R.id.btn3);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), GalleryActivity.class);
                startActivity(intent);
            }
        });

    }



    //갤러리에서 선택한 이미지를 앱 내 저장소에 저장(확장자 안붙음)
//    public void saveImage2(Bitmap bitmap) {
//        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//        String imageFileName = "Gallery_" + timeStamp + "_";
//        File tempFile = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), imageFileName);
//        try {
//            tempFile.createNewFile();
//            FileOutputStream outputStream = new FileOutputStream(tempFile);
//            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
//            outputStream.flush();
//            outputStream.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    //uri를 path로 바꾸기(사용X)
//    public String getRealPathFromUri(Uri contentUri) {
//        String[] proj = {MediaStore.Images.Media.DATA};
//
//        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
//        cursor.moveToNext();
//        @SuppressLint("Range") String path = cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DATA));
//        Uri uri = Uri.fromFile(new File(path));
//        cursor.close();
//
//        return path;
//    }

    //카메라 인텐트 설정, 실행
    public void activeCamera() {
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
                activityResultLauncher.launch(takePictureIntent);
            }
        }
    }


    //갤러리 인텐트 설정, 실행
    public void activeGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        activityResultLauncher2.launch(intent);
    }


    //갤러리에서 선택한 이미지를 앱 내 저장소에 저장
    public void saveImageToApp(Uri uri) {
        ContentResolver resolver = getContentResolver();
        try {
            InputStream inputStream = resolver.openInputStream(uri);
            Bitmap imgBitmap = BitmapFactory.decodeStream(inputStream);
            imageView.setImageBitmap(imgBitmap);
            inputStream.close();
            saveImage(imgBitmap);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //bitmap을 jpg로 변환해서 저장(확장자 붙음)
    public void saveImage(Bitmap bitmap) {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "Gallery_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        try {
            File image = File.createTempFile(imageFileName, ".jpg", storageDir);
            FileOutputStream outputStream = new FileOutputStream(image);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //이미지 저장할 임시파일 생성
    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);

        currentPhotoPath = image.getAbsolutePath();

        //강제종료 처리위한 경로값 대입
        tempFileName = new String(currentPhotoPath);
        return image;
    }

    //앱 내부공간에 저장되어있는 파일을 갤러리 저장공간으로 복사
    public void copyImageToGallery() {
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

    //카메라 찍지 않았을때 임시파일 삭제
    public void deleteTempFile() {
        File file = new File(currentPhotoPath);
                        if (file.exists()) {
            file.delete();
        }
    }








}