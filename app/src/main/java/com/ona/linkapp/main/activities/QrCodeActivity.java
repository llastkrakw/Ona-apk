package com.ona.linkapp.main.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.sumimakito.awesomeqr.AwesomeQrRenderer;
import com.github.sumimakito.awesomeqr.RenderResult;
import com.github.sumimakito.awesomeqr.option.RenderOption;
import com.github.sumimakito.awesomeqr.option.background.BlendBackground;
import com.github.sumimakito.awesomeqr.option.background.GifBackground;
import com.github.sumimakito.awesomeqr.option.background.StillBackground;
import com.github.sumimakito.awesomeqr.option.color.Color;
import com.github.sumimakito.awesomeqr.option.logo.Logo;
import com.ona.linkapp.R;
import com.ona.linkapp.helpers.ImageResize;
import com.ona.linkapp.models.Collection;
import com.ona.linkapp.models.Link;

import java.io.File;

public class QrCodeActivity extends AppCompatActivity {

    private Link link;
    private Collection collection;
    private ImageView imageView;
    private TextView  title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_code);

        imageView = (ImageView) findViewById(R.id.qr_code);
        title = (TextView) findViewById(R.id.title);

        link = getIntent().getParcelableExtra("Link");
        collection = getIntent().getParcelableExtra("Collection");
        if(link != null)
            title.setText(link.getTitle());
        if(collection != null)
            title.setText(collection.getTitle());

        // Java


        // A blend background (to draw a QR code onto an area of a still image)
/*        BlendBackground background = new BlendBackground();
        background.setBitmap(ImageResize.decodeSampledBitmapFromResource(getResources(), R.drawable.ic_logo_blanc, 14, 48));
        background.setClippingRect(new Rect(0, 0, 200, 200));
        background.setAlpha(0.7f);
        background.setBorderRadius(10); // radius for blending corners*/

        Bitmap icon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_logo_blue);
        Logo logo = new Logo();
        logo.setBitmap(icon);
        logo.setBorderRadius(0); // radius for logo's corners
        logo.setBorderWidth(14); // width of the border to be added around the logo
        logo.setScale(0.0f); // scale for the logo in the QR code
        //logo.setClippingRect(new RectF(200, 200, 200, 200)); // crop the logo image before applying it to the QR code


        Color color = new Color();
        color.setLight(0xFFFFFFFF); // for blank spaces
        color.setDark(0xFF0030DF); // for non-blank spaces
        color.setAuto(false); // set to true to automatically pick out colors from the background image (will only work if background image is present)


        RenderOption renderOption = new RenderOption();

        if(link != null)
            renderOption.setContent(link.getUrl()); // content to encode
        if(collection != null)
            renderOption.setContent(collection.getUrl()); // content to encode


        renderOption.setSize(800); // size of the final QR code image
        renderOption.setBorderWidth(20); // width of the empty space around the QR code
        renderOption.setPatternScale(0.35f); // (optional) specify a scale for patterns
        renderOption.setRoundedPatterns(true); // (optional) if true, blocks will be drawn as dots instead
        renderOption.setClearBorder(true); // if set to true, the background will NOT be drawn on the border area
        renderOption.setColor(color); // set a color palette for the QR code
        renderOption.setLogo(logo); // set a logo, keep reading to find more about it


        RenderResult result = null;
        try {
            result = AwesomeQrRenderer.render(renderOption);
            if (result.getBitmap() != null) {
                // play with the bitmap
                imageView.setImageBitmap(result.getBitmap());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}