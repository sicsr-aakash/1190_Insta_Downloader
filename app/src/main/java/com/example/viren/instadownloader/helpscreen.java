package com.example.viren.instadownloader;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;


public class helpscreen extends ActionBarActivity {

    Button credits;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_helpscreen);
        credits=(Button)findViewById(R.id.credits);




        //-----------------------------help button-----------------------------------------------------------------------

        credits.setOnClickListener(new View.OnClickListener()


//-------------------------------------------------------------------------------------------------------------
        {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub

                Intent credits = new Intent(getApplicationContext(), creditsscreen.class);

                startActivity(credits);
//-------


//--------------

            }
        });



        //-----------------------------------------/help button--------------------------------------------------------




    }



}
