package com.dhairytripathi.walls.ui.Custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatImageView;

public class AppCompactImageView extends AppCompatImageView {

    private float radius = 18.0f;
    private Path path;
    private RectF rect;

    public AppCompactImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AppCompactImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        path = new Path();

    }




    @Override
    protected void onDraw(Canvas canvas) {
        rect = new RectF(0, 0, this.getWidth(), this.getHeight());
        path.addRoundRect(rect, radius, radius, Path.Direction.CW);
        canvas.clipPath(path);
        super.onDraw(canvas);
    }

}
