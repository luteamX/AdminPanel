package com.example.ideahack;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class AdminPanel extends AppCompatActivity implements View.OnClickListener {
    BarChart barChart;
    CardView totaluser,totalpost,topSubmitters,topVoters;
    int totalpostsize=0;
    TextView textUser,totalposttext;
    int totalusers;

    Toolbar toolbar;
    private LinearLayout mButton4;

    private FirebaseFirestore db=FirebaseFirestore.getInstance();
    private CollectionReference notebookRef = db.collection("userinfo");


    int TotalUser = 0; //map.size();
    int TotalSubmitPost = 0;
    int Collom1,Collom2,Collom3,Collom4,Collom5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);

        SharedPreferences sharedPreferences = getSharedPreferences("Graph",MODE_PRIVATE);
        Collom1 = sharedPreferences.getInt("TotalUser",0);
        Collom2 =  sharedPreferences.getInt("TotalSubmitPost",0);
        ///////////////Shared Input Finish////////////////
        mButton4 = (LinearLayout)findViewById(R.id.button4);
        mButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminPanel.this, TopVoters.class));
            }
        });

        totalposttext = findViewById(R.id.totalposttextz);

        barChart = findViewById(R.id.barchartid);

        textUser=findViewById(R.id.textTotaluser);

        toolbar=findViewById(R.id.custom_toolbar);



        barChart.setDrawBarShadow(false);
        barChart.setDrawValueAboveBar(true);
        barChart.setMaxVisibleValueCount(10);
        barChart.setPinchZoom(false);
        barChart.setDrawGridBackground(false);

        gettingData();





        ArrayList<BarEntry> barEntries = new ArrayList<>();

        barEntries.add(new BarEntry(1,Collom1));
        barEntries.add(new BarEntry(2, Collom2));
        barEntries.add(new BarEntry(3, 1));
        barEntries.add(new BarEntry(4, 4));
        barEntries.add(new BarEntry(5, 3));

        BarDataSet barDataSet = new BarDataSet(barEntries, "Summury");
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        BarData barData = new BarData(barDataSet);
        barData.setBarWidth(.7f);

        barChart.setData(barData);

        totaluser=findViewById(R.id.totaluserid);
        totalpost=findViewById(R.id.totalpost);
        topSubmitters=findViewById(R.id.topSubmitters);
        topVoters=findViewById(R.id.topVoters);

        totaluser.setOnClickListener(this);
        totalpost.setOnClickListener(this);
        topSubmitters.setOnClickListener(this);
        topVoters.setOnClickListener(this);

        setSupportActionBar(toolbar);



        getSupportActionBar().setTitle("Dashboard");

        notebookRef.get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        String data = "";

                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            Note note = documentSnapshot.toObject(Note.class);
                            //note.setDocumentId(documentSnapshot.getId());
                            String username = note.getUsername();
                            String uid = note.getUserid();          TotalUser++;
                            int givevote = note.getGiveVote();
                            int submit = note.getSubmit();          TotalSubmitPost = TotalSubmitPost + submit;
                            Toast.makeText(AdminPanel.this, "UID "+" "+ uid+"  Vote "+submit, Toast.LENGTH_SHORT).show();

                            textUser.setText(TotalUser+"");
                            totalposttext.setText(TotalSubmitPost+"");

                            SharedPreferences sharedPreferences = getSharedPreferences("Graph",MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();

                            editor.putInt("TotalUser",TotalUser);
                            editor.putInt("TotalSubmitPost",TotalSubmitPost);

                            editor.commit();

                        }

                    }
                });

            //Set Data
            //TotalUser = 0;
            //Toast.makeText(AdminPanel.this, "TotalUser "+" "+ TotalUser+"  TotalSubmitPost"+TotalSubmitPost, Toast.LENGTH_SHORT).show();

    }

    private void gettingData() {

        db.collection("Ideas")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful())
                {
                    for(QueryDocumentSnapshot documentSnapshot: task.getResult())
                    {

                    }
                    //Log.d("Totalp",String.valueOf(totalpostsize));
                }

            }
        });

        db.collection("userinfo")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {



            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.logout,menu);
        return true;

    }


    @Override
    public void onClick(View view) {

        switch (view.getId())
        {
            case R.id.totaluserid:

                break;

            case R.id.totalpost:
                break;
              case R.id.topSubmitters:
                break;
              case R.id.topVoters:

                break;

        }

    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.logoutid:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(AdminPanel.this,MainActivity.class));
                finish();

        }
        return super.onOptionsItemSelected(item);
    }

}
