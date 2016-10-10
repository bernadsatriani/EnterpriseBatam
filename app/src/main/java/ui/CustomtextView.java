package ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import com.bpbatam.enterprise.R;

public class CustomtextView extends TextView{
    private int defaultDimension = 0;
    private int TYPE_BOLD = 1;
    private int TYPE_ITALIC = 2;
    private int FONT_HEADER = 1;
    private int FONT_HEADER_REGULER = 2;
    private int FONT_CONTENT = 3;
    private int FONT_LABEL = 4;
    private int FONT_KLAVIKA = 5;
    private int FONT_ROBOTO = 6;
    private int FONT_AVENIR_BLACK = 7;
    private int FONT_AVENIR_BLACK_OBLIQUE = 8;
    private int FONT_AVENIR_BOOK = 9;
    private int FONT_AVENIR_BOOK_OBLIQUE = 10;
    private int FONT_AVENIR_HEAVY = 11;
    private int FONT_AVENIR_HEAVY_OBLIQUE = 12;
    private int FONT_AVENIR_LIGHT = 13;
    private int FONT_AVENIR_LIGHT_OBLIQUE = 14;
    private int GOTHAM_BLACK =15;
    private int GOTHAM_BOLD =16;
    private int GOTHAM_BOOK = 17;
    private int GOTHAM_LIGHT = 18;
    private int GOTHAM_MEDIUM = 19;
    private int GOTHAM_LIGHTITALIC = 20;

    private int fontType;
    private int fontName;

    public CustomtextView(Context context) {
        super(context);
        init(null, 0);
    }
    public CustomtextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }
    public CustomtextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }
    private void init(AttributeSet attrs, int defStyle) {
        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.font, defStyle, 0);
        fontName = a.getInt(R.styleable.font_name, defaultDimension);
        fontType = a.getInt(R.styleable.font_type, defaultDimension);
        a.recycle();
        if (fontName == FONT_HEADER) {
            setFontType(Typeface.createFromAsset(getContext().getAssets(), "Header.ttf"));
        } else if (fontName == FONT_HEADER_REGULER) {
            setFontType(Typeface.createFromAsset(getContext().getAssets(), "Header_Reguler.ttf"));
        }else if (fontName ==FONT_CONTENT){
            setFontType(Typeface.createFromAsset(getContext().getAssets(), "Content.ttf"));
        }else if (fontName == FONT_LABEL){
            setFontType(Typeface.createFromAsset(getContext().getAssets(), "Label.ttf"));
        }else if (fontName == FONT_KLAVIKA){
            setFontType(Typeface.createFromAsset(getContext().getAssets(), "Klavika.ttf"));
        }else if (fontName == FONT_ROBOTO){
            setFontType(Typeface.createFromAsset(getContext().getAssets(), "Roboto.ttf"));
        }else if (fontName == FONT_AVENIR_BLACK) {
            setFontType(Typeface.createFromAsset(getContext().getAssets(), "AvenirLTStd-Black.otf"));
        }else if (fontName == FONT_AVENIR_BLACK_OBLIQUE) {
            setFontType(Typeface.createFromAsset(getContext().getAssets(), "AvenirLTStd-BlackOblique.otf"));
        }else if (fontName == FONT_AVENIR_BOOK) {
            setFontType(Typeface.createFromAsset(getContext().getAssets(), "AvenirLTStd-Book.otf"));
        }else if (fontName == FONT_AVENIR_BOOK_OBLIQUE) {
            setFontType(Typeface.createFromAsset(getContext().getAssets(), "AvenirLTStd-BookOblique.otf"));
        }else if (fontName == FONT_AVENIR_HEAVY) {
            setFontType(Typeface.createFromAsset(getContext().getAssets(), "AvenirLTStd-Heavy.otf"));
        }else if (fontName == FONT_AVENIR_HEAVY_OBLIQUE) {
            setFontType(Typeface.createFromAsset(getContext().getAssets(), "AvenirLTStd-HeavyOblique.otf"));
        }else if (fontName == FONT_AVENIR_LIGHT) {
            setFontType(Typeface.createFromAsset(getContext().getAssets(), "AvenirLTStd-Light.otf"));
        }else if (fontName == FONT_AVENIR_LIGHT_OBLIQUE) {
            setFontType(Typeface.createFromAsset(getContext().getAssets(), "AvenirLTStd-LightOblique.otf"));
        }else if (fontName == GOTHAM_BLACK) {
            setFontType(Typeface.createFromAsset(getContext().getAssets(), "GOTHAM-BLACK.TTF"));
        }else if (fontName == GOTHAM_BOLD) {
            setFontType(Typeface.createFromAsset(getContext().getAssets(), "GOTHAM-BOLD.TTF"));
        }else if (fontName == GOTHAM_BOOK) {
            setFontType(Typeface.createFromAsset(getContext().getAssets(), "GOTHAM-BOOK.ttf"));
        }else if (fontName == GOTHAM_LIGHT) {
            setFontType(Typeface.createFromAsset(getContext().getAssets(), "GOTHAM-LIGHT.TTF"));
        }else if (fontName == GOTHAM_LIGHTITALIC) {
            setFontType(Typeface.createFromAsset(getContext().getAssets(), "Gotham-LightItalic.otf"));
        }else if (fontName == GOTHAM_MEDIUM) {
            setFontType(Typeface.createFromAsset(getContext().getAssets(), "GOTHAM-MEDIUM.TTF"));
        }
    }
    public void setFontType(Typeface font) {
        if (fontType == TYPE_BOLD) {
            setTypeface(font, Typeface.BOLD);
        } else if (fontType == TYPE_ITALIC) {
            setTypeface(font, Typeface.ITALIC);
        } else {
            setTypeface(font);
        }
    }
}
