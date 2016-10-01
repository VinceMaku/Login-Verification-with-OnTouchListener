package com.example.bamble.my_android_app_practice;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class onTouchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_touch);

        final ImageView image = (ImageView) findViewById(R.id.imageView);

        image.setOnTouchListener(new View.OnTouchListener() {
            float initX = 0, finalX = 0, initY = 0, finalY = 0;
            float diffX,diffY;
            @Override
            public boolean onTouch(View v, MotionEvent motionEvent){

                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        initX = motionEvent.getX();
                        initY = motionEvent.getY();
                        TextView xy1=(TextView) findViewById(R.id.xy1);
                        xy1.setText(String.valueOf(initX)+","+ String.valueOf(initY));
                        //Toast.makeText(getApplicationContext(),""+String.format("ACTION_DOWN - x:%.2f, y:%.2f",initX,initY),Toast.LENGTH_SHORT).show();
                        return true;
                    case MotionEvent.ACTION_UP:
                        finalX = motionEvent.getX();
                        finalY = motionEvent.getY();
                        TextView xy2=(TextView) findViewById(R.id.xy2);
                        xy2.setText(String.valueOf(finalX)+","+ String.valueOf(finalY));
                        actionDisplay(initX, finalX, initY, finalY);
                        return true;
                }
                return false;
            }

            private void actionDisplay(float initX, float finalX, float initY, float finalY) {
                String msgX = "",msgY="",msgUD="",msgLR="";
                TextView dxy =(TextView)findViewById(R.id.dxy);
                TextView motion=(TextView) findViewById(R.id.motion);
                if (initX<finalX){
                    msgLR = String.format("SWIPED LEFT to RIGHT");
                    diffX=finalX-initX;
                    msgX= String.valueOf(diffX);
                }else if(initX>finalX){
                    msgLR = String.format("SWIPED RIGHT to LEFT");
                    diffX=initX-finalX;
                    msgX= String.valueOf(diffX);
                }
                //Toast.makeText(getApplicationContext(),""+msgX,Toast.LENGTH_SHORT).show();
                //Toast.makeText(getApplicationContext(),""+msgX,Toast.LENGTH_SHORT).show();
                if(initY<finalY){
                    msgUD = String.format("SWIPED UP to DOWN");
                    diffY=finalY-initY;
                    msgY= String.valueOf(diffY);
                }else if(initY>finalY){
                    msgUD = String.format("SWIPED DOWN to UP");
                    diffY=initY-finalY;
                    msgY= String.valueOf(diffY);

                }
                    //dxy.setText(msgX+ "," + msgY);

                TextView quadrant=(TextView) findViewById(R.id.quadrant);
                if(diffX==0){
                    dxy.setText("0,0");
                    motion.setText("NO SWIPED IDENTIFIED");
                    quadrant.setText("No Quadrant Found");
                }
                else if (diffX==0 && diffY >0){
                    dxy.setText("0 ,"+msgY);
                }
                else if (diffX>0 && diffY ==0){
                    dxy.setText(msgX +",0");
                }
                else if(diffX>0 && diffY>0){
                    dxy.setText(msgX+ "," + msgY);
                    motion.setText(msgLR+", "+msgUD);
                }
                if(initX==finalX && initY==finalY){
                    dxy.setText("0,0");
                    motion.setText("NO SWIPED IDENTIFIED");
                    quadrant.setText("No Quadrant Found");

                }else if (initX!=finalX && initY!=finalY){
                    if(initX<finalX && initY<finalY){
                        quadrant.setText("Quadrant 4");
                    }else if(initX>finalX && initY<finalY){
                        quadrant.setText("Quadrant 3");
                    }else if(initX>finalX && initY>finalY){
                        quadrant.setText("Quadrant 2");
                    }else if(initX<finalX && initY>finalY){
                        quadrant.setText("Quadrant 1");
                    }
                }
                //Toast.makeText(getApplicationContext(),""+msgY,Toast.LENGTH_SHORT).show();
            }

        });
    }
}
