package com.example.admin.moment_maker;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;

public class DescriptionActivity extends AppCompatActivity {
    ImageView imageView;
    Button buttonGallery,createbtn ;
    File file;
    Uri uri;
    Intent GalIntent, CropIntent ;
    EditText txtDate,hugdescription,hugtitle;
    private int mYear, mMonth, mDay;
    private DatabaseReference mDatabase;
    String dateString,descriptionString,titleString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);
        imageView = findViewById(R.id.image);
        hugtitle=(EditText)findViewById(R.id.hugtitle);
        hugdescription=(EditText)findViewById(R.id.description);
        buttonGallery = findViewById(R.id.choosePhoto);
        createbtn=(Button)findViewById(R.id.createHug);
        buttonGallery.setOnClickListener(new View.OnClickListener()
        {@Override public void onClick(View view) {GetImageFromGallery();}});
        txtDate = findViewById(R.id.duedate);

        mDatabase=FirebaseDatabase.getInstance().getReference("Hugs");

        createbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                addStudents();
                Intent display=new Intent(DescriptionActivity.this,HugActivity.class);
                startActivity(display);
            }
        });


    }



    public void GetImageFromGallery(){
        GalIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(Intent.createChooser(GalIntent, "Select Image From Gallery"), 2);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 2) {
            if (data != null) {
                uri = data.getData();
                ImageCropFunction();
            }
        }
        else if (requestCode == 1) {
            if (data != null) {
                Bundle bundle = data.getExtras();
                Bitmap bitmap = bundle.getParcelable("data");
                imageView.setImageBitmap(bitmap);

            }
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

    public void addStudents(){
        titleString=hugtitle.getText().toString();
        descriptionString=hugdescription.getText().toString().trim();
        dateString=txtDate.getText().toString();

        if(!TextUtils.isEmpty(titleString)&&!TextUtils.isEmpty(descriptionString)&&!TextUtils.isEmpty(dateString)){

            String id=mDatabase.push().getKey();
            UserDetails userDetails=new UserDetails(id,titleString,descriptionString,dateString);
            mDatabase.child(id).setValue(userDetails);
            hugtitle.setText("");
            hugdescription.setText("");
            txtDate.setText("");
        }else {
            Toast.makeText(this, "Please fill in the details!", Toast.LENGTH_SHORT).show();
        }
    }
}
