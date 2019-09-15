package com.example.e_quiz;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.Stack;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

public class test extends AppCompatActivity implements View.OnClickListener {

    TextView t;
    Stack<Integer> st;
    AlertDialog.Builder builder;
    private static final String FORMAT = "%02d:%02d";
    long  minutes;
    RadioButton A,B,C,D;
    Button next,pre;
    ImageView read;
    Random random;
    int[] arr;
    private int i=0,qn=0,mn;
    private int quizCount;
    int score = 0;
    int correct = 0;
    int wrong = 0;
    String sub,test="";
    String arruid[],crrctans,uans;
        TextView q,qno;
    private DatabaseReference mDatabase;
    ProgressDialog prog;
    User user=new User();
    private TextToSpeech textToSpeech;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        setTitle("Test");
        savedInstanceState=getIntent().getExtras();
        if(savedInstanceState!=null)
        {
            sub= savedInstanceState.getString("sub");
        }
        gettime();
        st=new Stack<>();
        st.setSize(20);
        st.clear();
        random=new Random();
        t=findViewById(R.id.timer);
        A=(RadioButton)findViewById(R.id.r1);
        B=(RadioButton)findViewById(R.id.r2);
        C=(RadioButton)findViewById(R.id.r3);
        D=(RadioButton)findViewById(R.id.r4);
        read=(ImageView)findViewById(R.id.read);
        next=(Button)findViewById(R.id.next);next.setOnClickListener(this);
        pre=(Button)findViewById(R.id.pre);pre.setOnClickListener(this);
        q=(TextView)findViewById(R.id.que);
        qno=(TextView)findViewById(R.id.qno);
        prog=new ProgressDialog(com.example.e_quiz.test.this);
        prog.setMessage("Loading Data !!");
        prog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        prog.setIndeterminate(true);
        prog.setProgress(1);
        prog.show();
       // getQ();

        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int ttsLang = textToSpeech.setLanguage(Locale.US);

                    if (ttsLang == TextToSpeech.LANG_MISSING_DATA
                            || ttsLang == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("TTS", "The Language is not supported!");
                    } else {
                        Log.i("TTS", "Language Supported.");
                    }
                    Log.i("TTS", "Initialization success.");
                } else {
                    Toast.makeText(getApplicationContext(), "TTS Initialization failed!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data=q.getText().toString();
                Log.i("TTS", "button clicked: " + data);
                int speechStatus = textToSpeech.speak(data, TextToSpeech.QUEUE_FLUSH, null);

                if (speechStatus == TextToSpeech.ERROR) {
                    Log.e("TTS", "Error in converting Text to Speech!");
                }
            }

        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.mybutton)
        {
            dialog();
        }
        return super.onOptionsItemSelected(item);
    }

    void dialog()
    {
        builder = new AlertDialog.Builder(this);
        //Setting message_area manually and performing action on button click
        builder.setMessage("Are You sure to exit ?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        intent();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //  Action for 'NO' Button
                        dialog.cancel();

                    }
                });
        //Creating dialog box
        AlertDialog alert = builder.create();
        //Setting the title manually
        alert.setTitle("Alert");
        alert.show();
    }


    public void getQuestions(String str)
    {
        mDatabase = FirebaseDatabase.getInstance().getReference(sub).child(str);
       // mDatabase = FirebaseDatabase.getInstance().getReference(sub);
        mDatabase.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot snapshot)
            {
                user=snapshot.getValue(User.class);
                qno.setText(""+(i+1));
                q.setText(user.getQ());
                A.setText(user.getA());
                B.setText(user.getB());
                C.setText(user.getC());
                D.setText(user.getD());
                crrctans=user.getAns();

            }
            @Override
            public void onCancelled(DatabaseError databaseError)
            {
                Toast.makeText(getApplicationContext(),"Failed",Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public void onBackPressed() {
        dialog();
    }

    public void getQ()
    {

       // mDatabase = FirebaseDatabase.getInstance().getReference(sub).child("2");
        mDatabase = FirebaseDatabase.getInstance().getReference(sub);
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot)
            {
                int k=0;
              for (DataSnapshot childDataSnapshot : snapshot.getChildren())

                {
                  test=childDataSnapshot.getKey();
                    if(k!=qn){
                     arruid[k]=test;
                    k++;}
                  //  list.add(i,childDataSnapshot.getKey());
                    //i++;
                }
              prog.dismiss();
              getQuestions(arruid[arr[i]]);
               // Toast.makeText(getApplicationContext(),"noq : "+data.getnoq(sub),Toast.LENGTH_LONG).show();
            }
            @Override
            public void onCancelled(DatabaseError databaseError)
            {

               // Toast.makeText(getApplicationContext(),"Failed",Toast.LENGTH_LONG).show();
            }
        });
        //getQuestions(list.get(0));
    }



    private void uncheckedRadioButton() {

        A.setChecked(false);

        B.setChecked(false);

        C.setChecked(false);

        D.setChecked(false);

    }

    void gettime()
    { mDatabase = FirebaseDatabase.getInstance().getReference("Setting").child(sub);
        //   DatabaseReference data = mDatabase.child("Users").child(auth.getCurrentUser().getUid()).child("name");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for(DataSnapshot data:snapshot.getChildren())
                {
                    minutes=Long.parseLong(snapshot.child("time").getValue().toString());
                   qn=(int)Long.parseLong(snapshot.child("noq").getValue().toString());
                     new CountDownTimer(minutes, 1000) { // adjust the milli seconds here

                        public void onTick(long millisUntilFinished) {

                            t.setText("Time Left:" + String.format(FORMAT,

                                    TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(
                                            TimeUnit.MILLISECONDS.toHours(millisUntilFinished)),
                                    TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(
                                            TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
                        }

                        public void onFinish()
                        { intent();
                        }
                    }.start();
                }
               arruid =new String[qn];
                getQ();
                getno();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),"Failed",Toast.LENGTH_LONG).show();
            }
        });

    }
    public int[] getno()
    {
       // Toast.makeText(getApplicationContext(),"Array :"+qn,Toast.LENGTH_LONG).show();
        while (st.size()!=qn)
        // while (st.Count != 6)
        {
            mn = random.nextInt(qn);
            // m = rd.Next(0, 6);
            if (!st.contains(mn))
            {
                st.push(mn);
            }
        }
        int i;String s="";
        arr=new int[qn];
       for(i=0;i<qn;i++){ arr[i]=st.get(i);  //s+=" "+st.get(i).toString();
            }
    // Toast.makeText(getApplicationContext(),"Array :"+s,Toast.LENGTH_LONG).show();
        return arr;
    }

    private  void checkSelectedRaio()
    {
        if(A.isChecked() || B.isChecked() || C.isChecked() || D.isChecked())
        {

            if(A.isChecked()){uans="A";}
            else if(B.isChecked()){uans="B";}
            else if(C.isChecked()){uans="C";}
            else if(D.isChecked()){uans="D";}
            if(crrctans.equals(uans))
            {

                score++;
               // Toast.makeText(getApplicationContext(),"Correct",Toast.LENGTH_LONG).show();
            }else{
                wrong++;
                //Toast.makeText(getApplicationContext(),"Wrong",Toast.LENGTH_LONG).show();
                }
            if(i!=qn-1)
            {
                uncheckedRadioButton();
                i++;
                 getQuestions(arruid[arr[i]]);

            }
            else
            {
            intent();
            }
        }
        else
        {
            Toast.makeText(getApplicationContext(),"Please Select Answere!!",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        if(id==R.id.next)
        {
          checkSelectedRaio();
        }
        else if(id==R.id.pre)
        {
            if(i!=0)
            {
                uncheckedRadioButton();
                i--;
                getQuestions(arruid[arr[i]]);
            }
            else
            {
                Toast.makeText(getApplicationContext(),"You are on fisrt Question",Toast.LENGTH_LONG).show();
            }
        }
    }
    public void intent()
    {
        Intent i=new Intent(getApplicationContext(),UserResult.class);
        i.putExtra("Correct",""+score);
       i.putExtra("Wrong",""+wrong);
        startActivity(i);
    }
}
