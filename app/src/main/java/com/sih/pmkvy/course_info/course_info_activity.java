package com.sih.pmkvy.course_info;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.sih.pmkvy.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hp on 24-03-2017.
 */

public class course_info_activity extends AppCompatActivity {

    List<couse_info_data> course;
    RecyclerView rv;
    String center_id, course_id,job_role_name;

    @Override

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.course_info);

        rv = (RecyclerView) findViewById(R.id.recyclerview1);
        LinearLayoutManager linearlayout = new LinearLayoutManager(this);
        rv.setLayoutManager(linearlayout);

        Bundle b = getIntent().getExtras();
        center_id = b.getString("training_center_id");
        course_id = b.getString("course_id");
        job_role_name=b.getString("job_role_name");
        course = new ArrayList<>();
        new get_request(this, course, rv, center_id, course_id,job_role_name).execute();
        InitializedData();
        InitializeAdapter();

    }

    private void InitializedData() {

        course.add(new couse_info_data("12/03/85", "30/04/86", "20", "Availaible"));
        course.add(new couse_info_data("15/05/85", "30/08/86", "40", "Availaible"));
        course.add(new couse_info_data("20/08/85", "30/10/86", "25", "Availaible"));


    }

    private void InitializeAdapter() {
        course_info_adapter cid = new course_info_adapter(course);
        rv.setAdapter(cid);
        cid.notifyDataSetChanged();

    }
}

class get_request extends AsyncTask<String, Void, String> {
    Context context;
    List<couse_info_data> course;
    RecyclerView rv;
    String center_id, course_id,job_role_name;
    JSONObject json;
    Boolean flag;


    public get_request(Context context, List<couse_info_data> course, RecyclerView rv, String center_id, String course_id,String job_role_name) {
        this.context = context;
        this.course = course;
        this.rv = rv;
        this.center_id = center_id;
        this.course_id = course_id;
        this.job_role_name=job_role_name;

    }

    @Override
    protected void onPostExecute(String s) {
        Toast.makeText(context.getApplicationContext(),s,Toast.LENGTH_SHORT).show();
        String job_sector, job_role, course_name;
        //TODO: Add validate check so that result is true or not

        try {


        } catch (Exception e) {
            Log.d("ERROR", e.toString());

        }

    }

    @Override

    protected String doInBackground(String... params) {

        try {
            String link = context.getResources().getString(R.string.link) + "/api/batchinfocourse/";

            URL url = new URL(link);
            URLConnection con = url.openConnection();

            con.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());

            json = new JSONObject();
            JSONObject add = new JSONObject();

            //add.put("job_role_name", job_role_name);
            add.put("training_center_id","t1");
            add.put("course_id","c1");

            wr.write(add.toString());
            wr.flush();

            BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));

            StringBuilder sb = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                Log.d("LINE : ", line);
                if (line.equals("true")) {
                    //TODO: 3/20/2017 add response checking from server format is in jason
                    flag = true;
                } else
                    flag = false;

                sb.append(line);
            }
            return sb.toString();


        } catch (Exception e) {
            Log.d("ERROR", e.getMessage());
            return "Exception: " + e.getMessage();
        }

    }
}
