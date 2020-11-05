package com.example.colortiles;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class TilesView extends View {
    boolean flag = true;
    //int outline = 20;
    ArrayList<ArrayList<Integer>> tiles = new ArrayList<ArrayList<Integer>>();
    int darkColor = Color.parseColor("#191970");
    int lightColor = Color.parseColor("#7B68EE");
    int width = 200;
    int height = 350;
    int left = 50;
    int top = 100;
    
    public TilesView(Context context) {
        super(context);

    }

    public TilesView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        int l,t;
        for(int j = 0; j < 4; j++){
            for (int i = 0; i < 4; i++) {
                ArrayList<Integer> params = new ArrayList<Integer>();
                l = j*width + left*(j+1);
                t = i*height + top*(i+1);

                int color = 2;
                if (Math.random() > 0.5)
                    color = 1;
                else
                    color = 0;

                params.add(l);
                params.add(t);
                params.add(l+width);
                params.add(t+height);
                params.add(color);

                tiles.add(params);
            }
        }
    }

    @Override
    public void onDraw(Canvas canvas) {
        Paint light = new Paint();
        light.setColor(lightColor);
        Paint dark = new Paint();
        dark.setColor(darkColor);

        light.setStyle(Paint.Style.FILL);
        dark.setStyle(Paint.Style.FILL);

        for(int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                Rect tile = new Rect();
                System.out.println();
                tile.set(tiles.get(4 * i + j).get(0), tiles.get(4 * i + j).get(1), tiles.get(4 * i + j).get(2), tiles.get(4 * i + j).get(3));
                if (tiles.get(4 * i + j).get(4) == 0)
                    canvas.drawRect(tile, light);
                else
                    canvas.drawRect(tile, dark);
            }
        }
        super.onDraw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        int x_i, x_j;
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    if (tiles.get(4*i + j).get(0) <= x && tiles.get(4*i + j).get(2) >= x) {
                        if (tiles.get(4*i + j).get(1) <= y && tiles.get(4*i + j).get(3) >= y) {
                            x_i = i;
                            x_j = j;
                            for (int row = 0; row < 4; row++) { // пересечение линий
                                for (int col = 0; col < 4; col++) {
                                    if (row == x_i || col == x_j) {
                                        if (tiles.get(4*row + col).get(4) == 0)
                                            tiles.get(4*row + col).set(4, 1);
                                        else
                                            tiles.get(4*row + col).set(4, 0);
                                    }
                                }
                            }
                            break;
                        }
                    }
                }
            }

        invalidate();
        return super.onTouchEvent(event);
    }
}
