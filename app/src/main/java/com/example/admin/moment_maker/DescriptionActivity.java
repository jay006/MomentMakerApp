package com.example.admin.moment_maker;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;

public class DescriptionActivity extends AppCompatActivity {
    ImageView imageView;
    Button buttonGallery,uploadbtn,createbtn;
    File file;
    Uri uri;
    private static final int PICK_IMAGE_REQUEST=1;
    Intent GalIntent, CropIntent ;
    EditText txtDate,hugdescription,hugtitle;
    private int mYear, mMonth, mDay;
    private DatabaseReference mDatabase;
    private StorageReference ref;
    String dateString,descriptionString,titleString;
    ProgressBar mprogressbar;
    private StorageTask mTask;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);
        imageView = findViewById(R.id.image);
        mprogressbar=(ProgressBar)findViewById(R.id.progressBar);
        createbtn=(Button)findViewById(R.id.createbtn);
        hugtitle=(EditText)findViewById(R.id.hugtitle);
        hugdescription=(EditText)findViewById(R.id.description);
        buttonGallery = findViewById(R.id.choosePhoto);
        buttonGallery.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View view) {
                GetImageFromGallery();
            }
        });
        txtDate = findViewById(R.id.duedate);
        uploadbtn=(Button)findViewById(R.id.uploadbtn);


        mDatabase=FirebaseDatabase.getInstance().getReference("Hugs");
        ref=FirebaseStorage.getInstance().getReference("Uploads");


        uploadbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mTask!=null && mTask.isInProgress()){
                    Toast.makeText(DescriptionActivity.this, "Upload in Progress", Toast.LENGTH_SHORT).show();
                }else {
                    uploadFile();
                    uploadbtn.setVisibility(View.GONE);
                }
            }
        });
        createbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent display=new Intent(DescriptionActivity.this,HugActivity.class);
                startActivity(display);
            }
        });

    }

    private String getFileExtension(Uri uri){
        ContentResolver cr=getContentResolver();
        MimeTypeMap mime=MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(uri));
    }
    private void uploadFile() {

        if(uri!=null){
            StorageReference fileref=ref.child(System.currentTimeMillis()+"."+getFileExtension(uri));
            mTask=fileref.putFile(uri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            Handler handler=new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {

                                    mprogressbar.setProgress(0);
                                }
                            },500);

                            Toast.makeText(DescriptionActivity.this, "Upload Successful", Toast.LENGTH_SHORT).show();

                            titleString=hugtitle.getText().toString();
                            descriptionString=hugdescription.getText().toString().trim();
                            dateString=txtDate.getText().toString();

                            if(!TextUtils.isEmpty(titleString)&&!TextUtils.isEmpty(descriptionString)&&!TextUtils.isEmpty(dateString)){

                                String id=mDatabase.push().getKey();
                                UserDetails userDetails=new UserDetails(id,titleString,descriptionString,dateString,taskSnapshot.getUploadSessionUri().toString());
                                mDatabase.child(id).setValue(userDetails);

                            }else {
                                Toast.makeText(DescriptionActivity.this, "Please fill in the details!", Toast.LENGTH_SHORT).show();
                            }

                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress=(100.0*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                            mprogressbar.setProgress((int)progress);
                        }
                    });
            
        }else {
            Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();
        }
    }


    public void GetImageFromGallery(){
        GalIntent=new Intent();
        GalIntent.setType("image/*");
        GalIntent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(GalIntent,PICK_IMAGE_REQUEST);
        buttonGallery.setVisibility(View.GONE);
        uploadbtn.setVisibility(View.VISIBLE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_IMAGE_REQUEST && resultCode==RESULT_OK && data!=null &&data.getData()!=null) {
           uri=data.getData();
            Picasso.with(this).load(uri).into(imageView);
        }

    }
    public void ImageCropFunction() {
        // Image Crop Code
        try {
            CropIntent = new Intent("com.android.camera.action.CROP");
            CropIntent.setDataAndType(uri, "image/*");
            CropIntent.putExtra("crop", "true");
            CropIntent.putExtra("outputX", 180);
            CropIntent.putExtra("outputY", 180);
            CropIntent.putExtra("aspectX", 3);
            CropIntent.putExtra("aspectY", 4);
            CropIntent.putExtra("scaleUpIfNeeded", true);
            CropIntent.putExtra("return-data", true);
            startActivityForResult(CropIntent, 1);
        } catch (ActivityNotFoundException e) {}
    }

    public void date(View view) {
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                txtDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year); }}, mYear, mMonth, mDay);
        datePickerDialog.show();

    }


}
