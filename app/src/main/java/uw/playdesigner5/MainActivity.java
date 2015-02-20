package uw.playdesigner5;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    private DrawingView drawView;
    private ImageButton currPaint;
    TextView text_summary;
    List<PlayModel> list = new ArrayList<PlayModel>();
    DatabaseHelper db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawView = (DrawingView)findViewById(R.id.drawing);
        LinearLayout paintLayout = (LinearLayout)findViewById(R.id.paint_colors);
        currPaint = (ImageButton)paintLayout.getChildAt(0);
        currPaint.setImageDrawable(getResources().getDrawable(R.drawable.paint_pressed));
        text_summary = (TextView)findViewById(R.id.summary_info);


        db = new DatabaseHelper(getApplicationContext());

        PlayModel play = new PlayModel();
        //play.index =1;
        play.player_id=3;
        play.position_x=200;
        play.position_y=400;
        db.addPositionDetail(play);

        list = db.getPositionList();
        print(list);

       /*final FrameLayout target = (FrameLayout) findViewById(R.id.drawing);
        target.post(new Runnable() {

            @Override
            public void run() {
                int width = target.getWidth();
                int height = width;
                FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) target.getLayoutParams();
                lp.height = height;
                target.setLayoutParams(lp);
            }

        });*/
    }

    private void print(List<PlayModel> list) {
        // TODO Auto-generated method stub
        String value = "";
        for(PlayModel sm : list){
            value = value+"index: "+sm.index+", player: "+sm.player_id+" x: "+sm.position_x+" y: "+sm.position_y+"\n";
        }
        text_summary.setText(value);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void paintClicked(View view){
        //use chosen color
        if(view!=currPaint){
//update color
            ImageButton imgView = (ImageButton)view;
            String color = view.getTag().toString();
            drawView.setColor(color);
            imgView.setImageDrawable(getResources().getDrawable(R.drawable.paint_pressed));
            currPaint.setImageDrawable(getResources().getDrawable(R.drawable.paint));
            currPaint=(ImageButton)view;
        }
    }

}
