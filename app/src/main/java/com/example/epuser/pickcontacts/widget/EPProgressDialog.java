package com.example.epuser.pickcontacts.widget;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.epuser.pickcontacts.R;


/**
 * Created by ADMIN on 05-Sep-16.
 */
public class EPProgressDialog extends ProgressDialog {
    private AnimationDrawable animation;

    public EPProgressDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.progress_layout);

        ImageView la = (ImageView) findViewById(R.id.progress_img);
        la.setBackgroundResource(R.drawable.ep_progress);
        animation = (AnimationDrawable) la.getBackground();
    }

    @Override
    public void show() {
        super.show();
        animation.start();
    }

    @Override
    public void dismiss() {
        super.dismiss();
        animation.stop();
    }
}
