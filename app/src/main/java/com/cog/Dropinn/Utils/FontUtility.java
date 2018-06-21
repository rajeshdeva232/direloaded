package com.cog.Dropinn.Utils;

import android.content.Context;
import android.graphics.Typeface;

/**
 * Created by test on 12/21/17.
 */

public class FontUtility {

    Context context;

    public FontUtility(Context context) {
        this.context = context;
    }

    //Changing Font to Roboto:
    public Typeface getLatoBold() {
        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/Lato-Bold.ttf");
        return typeface;
    }

    public Typeface getLatoRegular() {
        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/Lato-Regular.ttf");
        return typeface;
    }

    public Typeface getLatoLight() {
        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/Lato-Light.ttf");
        return typeface;
    }

    public Typeface getLatoBlack() {
        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/Lato-Black.ttf");
        return typeface;
    }

    public Typeface getLatoHairLine() {
        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/Lato-Hairline.ttf");
        return typeface;
    }
}
