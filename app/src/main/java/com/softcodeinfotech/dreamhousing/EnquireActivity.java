package com.softcodeinfotech.dreamhousing;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class EnquireActivity extends AppCompatActivity {

    TextView individual, agent;
    EditText name, emailid, mobile;
    Button enquirebtn;
    ImageButton close;
    String nam, email, mob, usertype;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enquire);
        individual = findViewById(R.id.individualbtn);
        agent = findViewById(R.id.agentbtn);
        name = findViewById(R.id.name_enquiry);
        emailid = findViewById(R.id.emailtext);
        mobile = findViewById(R.id.mobile_numb);
        enquirebtn = findViewById(R.id.enquirenow_btn);
        close = findViewById(R.id.closebtn);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        individual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                usertype = "individual";
                individual.setBackgroundResource(R.drawable.border_btn);
                agent.setBackgroundResource(R.drawable.border_btn_grey);
            }
        });
        agent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                usertype = "agent";
                individual.setBackgroundResource(R.drawable.border_btn_grey);
                agent.setBackgroundResource(R.drawable.border_btn);
            }
        });

        enquirebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nam = name.getText().toString();
                email = emailid.getText().toString();
                mob = mobile.getText().toString();
                if (nam.isEmpty() || email.isEmpty() || mob.isEmpty() || usertype.isEmpty()) {
                    Toast.makeText(EnquireActivity.this, "Incomplete form, Fill all fields", Toast.LENGTH_SHORT).show();
                } else {
                    Intent mEmail = new Intent(Intent.ACTION_SEND);
                    mEmail.putExtra(Intent.EXTRA_EMAIL, new String[]{"ddreamhousing@gmail.com"});
                    mEmail.putExtra(Intent.EXTRA_SUBJECT, "DreamHousing Android App Enquiry");
                    mEmail.putExtra(Intent.EXTRA_TEXT, "User Type :" + usertype + " Name :" + nam + " Email Id :" + email + " Mobile Number :" + mob);
// prompts to choose email client
                    mEmail.setType("message/rfc822");
                    startActivity(Intent.createChooser(mEmail, "Choose an email client to send your"));
                }
            }
        });

    }
}
