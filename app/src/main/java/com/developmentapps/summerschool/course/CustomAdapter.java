package com.developmentapps.summerschool.course;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.developmentapps.summerschool.R;

import java.util.ArrayList;
import java.util.HashMap;

public class CustomAdapter extends SimpleAdapter
{

    Context context;
    public CustomAdapter(Context context, ArrayList<HashMap<String, String>> list, int resource, String name[], int[] ids)
    {
        super(context,list,resource,name,ids);

        this.context=context;
    }

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent)
    {

        View view=super.getView(position, convertView, parent);

        Button button=view.findViewById(R.id.enroll_details);

        TextView textView=view.findViewById(R.id.Instructor_name);
        TextView t=view.findViewById(R.id.email);
        TextView mobile=view.findViewById(R.id.mobile);
        TextView experience=view.findViewById(R.id.Experience);
        TextView institution=view.findViewById(R.id.Institution_name);
        TextView course=view.findViewById(R.id.course);



        final String name=textView.getText().toString();
        final String email=t.getText().toString();
        final String Mobile=mobile.getText().toString();
        final String Experience=experience.getText().toString();
        final String Institution=institution.getText().toString();
        final String Course=course.getText().toString();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                Bundle bundle = new Bundle();
                bundle.putString("InstructorName",name);
                bundle.putString("email",email);
                bundle.putString("mobile",Mobile);
                bundle.putString("experience",Experience);
                bundle.putString("institution",Institution);
                bundle.putString("course",Course);
                Intent intent = new Intent(context,coursePage.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtras(bundle);
                context.startActivity(intent);
                Toast.makeText(context, ""+position+"\nname"+name+"\nemail"+email,Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }


}
