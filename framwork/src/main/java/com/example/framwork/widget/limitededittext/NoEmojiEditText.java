package com.example.framwork.widget.limitededittext;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.Editable;
import android.text.InputFilter;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import com.example.framwork.R;
import com.example.framwork.widget.ContainsEmojiEditText;


/**
 * @formatter - string
 * @limitCount - string
 * @wordCountTextView - reference
 * @warningFontColor - color
 */
public class NoEmojiEditText extends ContainsEmojiEditText {
    private static final int DEFAULT_LIMIT = 500;
    private static final String DEFAULT_FORMATTER = "%s/%s";
    private static final int DEFAULT_COLOR = Color.RED;

    private TextView wordCountTextView;
    private int wordCountTextViewId;
    private int limitCount;
    private String formatter;
    private int warningFontColorResourceId;
    private ColorStateList wordCountTextViewColorStateList;
    private KeyboardHideListener keyboardHideListener;

    public NoEmojiEditText(Context context) {
        super(context);
        init(context, null);
    }

    public NoEmojiEditText(Context context, AttributeSet attrs) {
        super(context, attrs);

        init(context, attrs);
    }

    public NoEmojiEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    public int getLimitCount() {
        return limitCount;
    }

    public void setLimitCount(int limitCount) {
        this.limitCount = limitCount;
    }

    private void init(final Context context, final AttributeSet attrs) {
        setAttrs(context, attrs);

        getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @SuppressWarnings("deprecation")
                    @Override
                    public void onGlobalLayout() {
                        // Ensure you call it only once :
                        NoEmojiEditText.this.getViewTreeObserver().removeGlobalOnLayoutListener(
                                this);

                        wordCountTextView = (TextView) ((View) getParent())
                                .findViewById(wordCountTextViewId);
                        wordCountTextViewColorStateList = wordCountTextView.getTextColors();
                        setFilters();

                        displayWordCountText();

                        setTextChangedListener();
                    }
                });
    }

    @SuppressLint("Recycle")
    private void setAttrs(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.LimitedEditText);
        SimpleTypedArrayAdapter simpleTypedArrayAdapter = SimpleTypedArrayAdapter
                .getInstance(typedArray);
        formatter = simpleTypedArrayAdapter.getString(R.styleable.LimitedEditText_formatter,
                DEFAULT_FORMATTER);
        if (getLimitCount() == 0) {
            limitCount = simpleTypedArrayAdapter.getInt(R.styleable.LimitedEditText_limitCount,
                    DEFAULT_LIMIT);
        }
        wordCountTextViewId = simpleTypedArrayAdapter.getResourceId(
                R.styleable.LimitedEditText_wordCountTextView, -1);
        warningFontColorResourceId = simpleTypedArrayAdapter.getColor(
                R.styleable.LimitedEditText_warningFontColor, DEFAULT_COLOR);
        simpleTypedArrayAdapter.recycle();
    }

    private void setFilters() {
        InputFilter[] FilterArray = new InputFilter[1];
        FilterArray[0] = new InputFilter.LengthFilter(limitCount);
        setFilters(FilterArray);
    }

    private void setTextChangedListener() {
        addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                super.afterTextChanged(s);
                displayWordCountText();

                if (s.toString().length() >= limitCount) {
                    wordCountTextView.setTextColor(warningFontColorResourceId);
                } else {
                    wordCountTextView.setTextColor(wordCountTextViewColorStateList);
                }
            }

        });
    }

    private void displayWordCountText() {
        if (wordCountTextView != null)
            wordCountTextView.setText(String.format(formatter, limitCount - getText().toString().length(),
                    limitCount + ""));
    }

    @Override
    public boolean onKeyPreIme(int keyCode, KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            if (keyboardHideListener != null) {
                keyboardHideListener.keyboardHide();
            }
        }
        return super.dispatchKeyEvent(event);
    }

    public void setKeyboardHideListener(KeyboardHideListener keyboardHideListener) {
        this.keyboardHideListener = keyboardHideListener;
    }

    public static interface KeyboardHideListener {
        void keyboardHide();
    }

}
