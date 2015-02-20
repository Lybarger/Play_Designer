package uw.playdesigner5;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.shapes.Shape;
import android.view.View;
import android.content.Context;
import android.util.AttributeSet;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.MotionEvent;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lybar_000 on 2/19/2015.
 */
public class DrawingView extends View {
    //http://code.tutsplus.com/tutorials/android-sdk-create-a-drawing-app-touch-interaction--mobile-19202

    //drawing path
    /*
    The Path class encapsulates compound (multiple contour) geometric paths
    consisting of straight line segments, quadratic curves, and cubic curves
     */
    private Path drawPath;

    //drawing and canvas paint
    /*
    The Paint class holds the style and color information about how to draw geometries,
    text and bitmaps.
     */
    private Paint paint_path, paint_circle, canvasPaint, paint_text;

    //initial color
    private int paintColor = 0xFF660000;

    //canvas
    /*
    he Canvas class holds the "draw" calls. To draw something, you need 4 basic components:
    A Bitmap to hold the pixels,
    a Canvas to host the draw calls (writing into the bitmap),
    a drawing primitive (e.g. Rect, Path, text, Bitmap), and
    a paint (to describe the colors and styles for the drawing).
     */
    private Canvas drawCanvas;
    //canvas bitmap

    private Bitmap canvasBitmap;

    private int player_size = 40;
    private int selection_size = player_size*2;

    private float x_current = 100;
    private float y_current = 100;
    private int text_size = 60;
    private location player_position;
    private Boolean selection_status = false;
    private int text_shift = text_size*6/20;


    public DrawingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setupDrawing();


    }

    private void setupDrawing() {
//get drawing area setup for interaction
        drawPath = new Path();
        paint_path = new Paint();
        paint_path.setColor(paintColor);
        paint_path.setAntiAlias(true);
        paint_path.setStrokeWidth(10);
        paint_path.setStyle(Paint.Style.STROKE);
        paint_path.setStrokeJoin(Paint.Join.ROUND);
        paint_path.setStrokeCap(Paint.Cap.ROUND);

        paint_circle = new Paint();
        paint_circle.setStyle(Paint.Style.FILL);
        paint_circle.setColor(Color.GREEN);

        paint_text = new Paint();
        paint_text.setTextSize(text_size);
        paint_text.setTextAlign(Paint.Align.CENTER);
        Typeface tf = Typeface.create("Helvetica", Typeface.BOLD);
        paint_text.setTypeface(tf);



        canvasPaint = new Paint(Paint.DITHER_FLAG);

        player_position=location_projected(100, 100);




    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {

        super.onSizeChanged(w, h, oldw, oldh);


        canvasBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        drawCanvas = new Canvas(canvasBitmap);
        drawCanvas.drawCircle(player_position.x, player_position.y, player_size, paint_circle);
        drawCanvas.drawText("1",player_position.x, player_position.y + text_shift,paint_text);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(canvasBitmap, 0, 0, canvasPaint);
        canvas.drawPath(drawPath, paint_path);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //detect user touch
        x_current = event.getX();
        y_current = event.getY();
        location location_current= location_projected(x_current, y_current);


        float x_initial = 100;
        float y_initial = 100;
        float x_final;
        float y_final;


        double distance_x = location_current.x - player_position.x;
        double distance_y = location_current.y - player_position.y;
        double distance = Math.pow(Math.pow(distance_x, 2) + Math.pow(distance_y, 2), 0.5);


            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    x_initial = x_current;
                    y_initial = y_current;
                    //outside ImageView
                    //drawCanvas.drawCircle(location_current.x1, location_current.y1, player_size, paint_path);

                    selection_status = (distance < selection_size);
                    if(selection_status) {
                        location_current = player_position;
                        //drawPath.lineTo(location_current.x1, location_current.y1);
                        drawPath.moveTo(location_current.x, location_current.y);
                    }
                    break;
                case MotionEvent.ACTION_MOVE:
                    if(selection_status) {

                        drawPath.lineTo(x_current, y_current);
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    x_final = x_current;
                    y_final = y_current;
                    if(selection_status) {
                        drawCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
                        drawCanvas.drawPath(drawPath, paint_path);
                        drawPath.reset();
                        player_position = location_current;
                        drawCanvas.drawCircle(player_position.x, player_position.y, player_size, paint_circle);
                        drawCanvas.drawText("1",player_position.x, player_position.y + text_shift,paint_text);

                        selection_status = false;
                    }
                    break;
                default:
                    return false;
            }
        invalidate();
        return true;
    }




    public void setColor(String newColor) {
//set color
        invalidate();
        paintColor = Color.parseColor(newColor);
        paint_path.setColor(paintColor);

    }

    class location{
        float x;
        float y;
        location(float tx, float ty){
            x = tx;
            y = ty;
        }
    }

    class player_location{
        float x1;
        float y1;
        float x2;
        float y2;
        float x3;
        float y3;
        float x4;
        float y4;
        float x5;
        float y5;
        player_location(float tx1, float ty1, float tx2, float ty2,float tx3, float ty3,float tx4, float ty4,float tx5, float ty5){
            x1 = tx1;
            y1 = ty1;
            x2 = tx2;
            y2 = ty2;
            x3 = tx3;
            y3 = ty3;
            x4 = tx4;
            y4 = ty4;
            x5 = tx5;
            y5 = ty5;
        }
    }

    private location location_projected(float x, float y){
        float x_projected = x;
        float y_projected = y;
        return new location(x_projected, y_projected);

    }



    private void position_update(location location_current) {


    }


}