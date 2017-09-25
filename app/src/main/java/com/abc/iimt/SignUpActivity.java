package com.abc.iimt;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class SignUpActivity extends AppCompatActivity {
    Button signUp;
    EditText adm, univ, name, pwd, rePwd, mail, pno;
    Spinner sp1, sp2, sp3, sp4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        adm = (EditText) findViewById(R.id.adm_no);
        univ = (EditText) findViewById(R.id.univ_rno);
        name = (EditText) findViewById(R.id.name);
        pwd = (EditText) findViewById(R.id.password);
        rePwd = (EditText) findViewById(R.id.repwd);
        mail = (EditText) findViewById(R.id.mail);
        pno = (EditText) findViewById(R.id.pno);
        sp1 = (Spinner) findViewById(R.id.course);
        sp2 = (Spinner) findViewById(R.id.year);
        sp3 = (Spinner) findViewById(R.id.branch);
        sp4 = (Spinner) findViewById(R.id.section);
        signUp= (Button) findViewById(R.id.sign_up);
        String[] year = {"1", "2", "3", "4"};
        String[] branch = {"CSE", "Mechanical", "Electrical", "Electronics", "Civil","Agriculture","IT"};
        String[] section = {"A", "B", "C", "D", "E", "F", "G"};
        ArrayAdapter<CharSequence> adapter1 =ArrayAdapter.createFromResource(this,R.array.course,android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp1.setAdapter(adapter1);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, year);
        sp2.setAdapter(adapter2);
        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, branch);
        sp3.setAdapter(adapter3);
        ArrayAdapter<String> adapter4 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, section);
        sp4.setAdapter(adapter4);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });
    }
}
