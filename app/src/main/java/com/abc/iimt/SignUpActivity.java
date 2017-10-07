package com.abc.iimt;

import android.*;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SignUpActivity extends AppCompatActivity {
    Button signUp;
    EditText adm, univ, name, pwd, rePwd, mail, pno;
    Spinner sp1, sp2, sp3, sp4;
    ImageView imageView;
    String ImageDecode;
    int IMG_RESULT=1;
    Intent intent;
    int CAMERA=1;
    private int STORAGE_PERMISSION_CODE = 23;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        adm = (EditText) findViewById(R.id.adm_no);
        univ = (EditText) findViewById(R.id.univ_rno);
        name = (EditText) findViewById(R.id.name);
        pwd = (EditText) findViewById(R.id.pwd);
        rePwd = (EditText) findViewById(R.id.repwd);
        mail = (EditText) findViewById(R.id.mail);
        pno = (EditText) findViewById(R.id.pno);
        sp1 = (Spinner) findViewById(R.id.course);
        sp2 = (Spinner) findViewById(R.id.year);
        sp3 = (Spinner) findViewById(R.id.branch);
        sp4 = (Spinner) findViewById(R.id.section);
        signUp = (Button) findViewById(R.id.sign_up);
        imageView=(ImageView)findViewById(R.id.imgView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isReadStorageAllowed()) {
                    //If permission is already having then showing the toast
                    intent = new Intent(Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, IMG_RESULT);
                    //Existing the method with return
                    return;
                }

                //If the app has not the permission then asking for the permission
                requestStoragePermission();
            }
        });
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.Course, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp1.setAdapter(adapter1);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.Year, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp2.setAdapter(adapter2);
        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(this, R.array.Branch, android.R.layout.simple_spinner_item);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp3.setAdapter(adapter3);
        ArrayAdapter<CharSequence> adapter4 = ArrayAdapter.createFromResource(this, R.array.Section, android.R.layout.simple_spinner_item);
        adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp4.setAdapter(adapter4);
        final String admText = adm.getText().toString();
        final String nameText = name.getText().toString();
        // final String pwdText = pwd.getText().toString();
        final String rePwdText = rePwd.getText().toString();
        final String mailText = mail.getText().toString();
        final String univText = univ.getText().toString();
        final String courseText = sp1.getSelectedItem().toString();
        final String yearText = sp2.getSelectedItem().toString();
        final String branchText = sp3.getSelectedItem().toString();
        final String sectionText = sp4.getSelectedItem().toString();
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (adm.getText().toString().isEmpty()) {
                    adm.setError("Please Enter Admission No");
                } else if (univ.getText().toString().isEmpty()) {
                    univ.setError("Please Enter University Roll No.");
                } else if (name.getText().toString().isEmpty()) {
                    name.setError("Please Enter Name");
                } else if (pwd.getText().toString().isEmpty()) {
                    pwd.setError("Please Enter Password");
                } else if (rePwd.getText().toString().isEmpty()) {
                    rePwd.setError("Please Enter Confirm Password");
                } else if (pwd != rePwd) {
                    rePwd.setError("Password Didn't Match, please Enter again");
                } else if (!emailValidationCheck(mail.getText().toString())) {
                    mail.setError("Please enter E-mail");
                } else {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("message");
                    myRef.setValue("Name",nameText);

          /*          new AsyncTask<Void, Void, Void>() {
                        @Override
                        protected Void doInBackground(Void... voids) {

                            OkHttpClient okHttpClient = new OkHttpClient();

                            RequestBody requestBody = new FormBody.Builder()
                                    .add("adm", admText)
                                    .add("univ", univText)
                                    .add("mail", mailText)
                                    .add("course", courseText)
                                    .add("year", yearText)
                                    .add("branch", branchText)
                                    .add("section", sectionText)
                                    .add("name", nameText)
                                    // .add("password", pwdText)
                                    .build();

                            Request request = new Request.Builder()
                                    .method("POST", requestBody)
                                    .url("http://")
                                    .build();

                            try {

                                Response response = okHttpClient.newCall(request).execute();

                                if (response.isSuccessful()) {
                                    Log.d("IIMT", "onClick: " + response.body().string());

                                }

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            return null;
                        }
                    }.execute();*/
                }
            }
        });

    }

    public boolean emailValidationCheck(String email) {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        /*Bundle b = data.getExtras();
        bmp = (Bitmap) b.get("data");
        imageView.setImageBitmap(bmp);*/
        try {
            if (requestCode == IMG_RESULT && resultCode == RESULT_OK
                    && null != data)
            {

                Uri URI = data.getData();
                String[] FILE = { MediaStore.Images.Media.DATA };

                Cursor cursor = getContentResolver().query(URI,
                        FILE, null, null, null);

                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(FILE[0]);
                ImageDecode = cursor.getString(columnIndex);
                cursor.close();


                imageView.setImageBitmap(BitmapFactory
                        .decodeFile(ImageDecode));
            }
        } catch (Exception e) {
            Toast.makeText(this, "Please try again", Toast.LENGTH_LONG)
                    .show();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    //We are calling this method to check the permission status
    private boolean isReadStorageAllowed() {
        //Getting the permission status
        int result = ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE);

        //If permission is granted returning true
        if (result == PackageManager.PERMISSION_GRANTED)
            return true;

        //If permission is not granted returning false
        return false;
    }
    //Requesting permission
    private void requestStoragePermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)) {
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }

        //And finally ask for the permission
        ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        //Checking the request code of our request
        if (requestCode == STORAGE_PERMISSION_CODE) {

            //If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                //Displaying a toast
                Toast.makeText(this, "Permission granted now you can read the storage", Toast.LENGTH_LONG).show();
            } else {
                //Displaying another toast if permission is not granted
                Toast.makeText(this, "Oops you just denied the permission", Toast.LENGTH_LONG).show();
            }
        }
    }

}
