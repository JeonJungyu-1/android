package com.example.permission2;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Map;

public class MainActivity extends AppCompatActivity {

    //카메라
    Button btn1;
    ImageView resultImageView;
    ActivityResultLauncher<Intent> startACtivityResult1;
    static final int PERMISSIONS_REQUEST1 = 0x0000001;

    //블루투스
    Button btn2;
    ActivityResultLauncher<String> requestPermissionLauncher2;

    //지도
    Button btn3;
    ActivityResultLauncher<String[]> requestPermissionLauncher3;

    //마이크
    Button btn4;
    ActivityResultLauncher<String> requestPermissionLauncher4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //카메라
        btn1 = findViewById(R.id.btn1);
        resultImageView = findViewById(R.id.resultImageView);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (OnCheckPermission1()) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startACtivityResult1.launch(intent);
                }

            }
        });
        startACtivityResult1 = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == RESULT_OK) {
                    Bitmap bitmap = (Bitmap) result.getData().getExtras().get("data");
                    resultImageView.setImageBitmap(bitmap);
                }
            }
        });

        //블루투스
        btn2 = findViewById(R.id.btn2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                Intent intent;
                if (mBluetoothAdapter.isEnabled()) {
                    Toast.makeText(getApplicationContext(), "블루투스 시작", Toast.LENGTH_SHORT).show();
                } else {
                    intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    OnCheckPermission2(intent);
                }
            }
        });
        requestPermissionLauncher2 = registerForActivityResult(new ActivityResultContracts.RequestPermission(), new ActivityResultCallback<Boolean>() {
            @Override
            public void onActivityResult(Boolean isGranted) {
                if (isGranted) {
                    Toast.makeText(getApplicationContext(), "블루투스 권한 부여", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "블루투스 권한 부여 실패", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //지도
        btn3 = findViewById(R.id.btn3);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (OncheckPermission3()) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:37.5662952, 126.9779451"));
                    startActivity(intent);
                }
            }
        });

        requestPermissionLauncher3 = registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), new ActivityResultCallback<Map<String, Boolean>>() {
            @Override
            public void onActivityResult(Map<String, Boolean> result) {
                Boolean fineLoactionGranted = result.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false);
                Boolean coarseLocationGranted = result.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false);
                if (fineLoactionGranted != null && fineLoactionGranted) {
                    Toast.makeText(getApplicationContext(), "fine 권한 부여됨", Toast.LENGTH_SHORT).show();
                } else if (coarseLocationGranted != null && coarseLocationGranted) {
                    Toast.makeText(getApplicationContext(), "coarse 권한 부여됨", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "권한 부여되지 않음", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //마이크
        btn4 = findViewById(R.id.btn4);
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (OncheckPermission4()) {
                    Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                    intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "음성인식");
                    startActivityForResult(intent, 50);
                }
            }
        });

        requestPermissionLauncher4 = registerForActivityResult(new ActivityResultContracts.RequestPermission(), new ActivityResultCallback<Boolean>() {
            @Override
            public void onActivityResult(Boolean result) {
                if (result) {
                    Toast.makeText(getApplicationContext(), "마이크 권한 부여됨", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "마이크 권한 부여되지 않음", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    //카메라 권한 확인
    public boolean OnCheckPermission1() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
                Toast.makeText(this, "카메라 권한 필요", Toast.LENGTH_SHORT).show();
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CAMERA},
                        PERMISSIONS_REQUEST1);
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CAMERA},
                        PERMISSIONS_REQUEST1);
            }
            return false;
        } else {
            return true;
        }
    }

    //블루투스 권한 확인
    public void OnCheckPermission2(Intent intent) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.BLUETOOTH_CONNECT)) {
                Toast.makeText(this, "블루투스 권한 필요", Toast.LENGTH_SHORT).show();
                requestPermissionLauncher2.launch(Manifest.permission.BLUETOOTH_CONNECT);
            } else {
                requestPermissionLauncher2.launch(Manifest.permission.BLUETOOTH_CONNECT);
            }
        } else {
            startActivity(intent);
        }
    }

    //권한 여부 확인(예전 방법, 카메라만)
    @SuppressLint("MissingSuperCall")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST1:
                if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "카메라 권한 설정됨", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "카메라 권한 거부됨", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    //지도 권한 확인
    public boolean OncheckPermission3() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                requestPermissionLauncher3.launch(new String[] {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION});
            } else {
                requestPermissionLauncher3.launch(new String[] {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION});
            }
            return false;
        } else {
            return true;
        }
    }

    //마이크 권한 확인
    public boolean OncheckPermission4() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.RECORD_AUDIO)) {
                requestPermissionLauncher4.launch(Manifest.permission.RECORD_AUDIO);
            } else {
                requestPermissionLauncher4.launch(Manifest.permission.RECORD_AUDIO);
            }
            return false;
        } else {
            return true;
        }
    }

}