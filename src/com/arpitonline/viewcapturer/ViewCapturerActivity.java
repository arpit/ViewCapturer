package com.arpitonline.viewcapturer;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.Button;

public class ViewCapturerActivity extends Activity {

	private static final String TAG = "ViewCapturerActivity";
	
	private Button capture_drawingcache;
	private Button capture_canvas;
	
	private View canvasView;
	private View targetView;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_capturer);
        
        Button moving_button = (Button)findViewById(R.id.moving_button);
        TranslateAnimation anim = (TranslateAnimation)AnimationUtils.loadAnimation(this, R.anim.move);
        moving_button.startAnimation(anim);
        
        
        capture_canvas = (Button)findViewById(R.id.capture_canvas);
        canvasView  = findViewById(R.id.canvas);
        targetView = findViewById(R.id.source);
        
        capture_canvas.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				ViewCapturerActivity.this.captureUsingCanvas();
				
			}
		});
        capture_drawingcache = (Button)findViewById(R.id.capture_dc);
        canvasView  = findViewById(R.id.canvas);
        targetView = findViewById(R.id.source);
        
        capture_drawingcache.setOnClickListener(new View.OnClickListener() {
        	
        	@Override
        	public void onClick(View v) {
        		
        		ViewCapturerActivity.this.captureUsingDrawingCache();
        		
        	}
        });
    }
    
    private void captureUsingCanvas(){
    	Bitmap b = Bitmap.createBitmap(targetView.getWidth(),targetView.getHeight(), Bitmap.Config.ARGB_8888);
    	Canvas c = new Canvas(b);
    	targetView.draw(c);
    	BitmapDrawable d = new BitmapDrawable(getResources(), b);
    	canvasView.setBackgroundDrawable(d);
    }
    private void captureUsingDrawingCache(){
    	
    	targetView.buildDrawingCache();
    	Bitmap b1 = targetView.getDrawingCache();
    	// copy this bitmap otherwise distroying the cache will destroy 
    	// the bitmap for the referencing drawable and you'll not
    	// get the captured view
    	Bitmap b = b1.copy(Bitmap.Config.ARGB_8888, false);
    	BitmapDrawable d = new BitmapDrawable(b);
    	canvasView.setBackgroundDrawable(d);
    	targetView.destroyDrawingCache();
    }

    
    
}
