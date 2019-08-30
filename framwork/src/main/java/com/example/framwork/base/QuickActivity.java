package com.example.framwork.base;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.framwork.R;
import com.example.framwork.baseapp.AppManager;
import com.example.framwork.noHttp.CallServer;
import com.example.framwork.utils.DialogUtils;
import com.example.framwork.utils.StatusBarUtil;
import com.example.framwork.widget.customtoolbar.CommonTitle;
import com.example.framwork.widget.kprogresshud.KProgressHUD;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import es.dmoral.toasty.Toasty;


public abstract class QuickActivity extends AppCompatActivity {
    protected Activity mActivity;
    private String imgurl = "";
    protected KProgressHUD progressHUD;
    protected Bundle bundle;
    protected Dialog oneBtnDialog;
    protected Dialog twoBtnDialog;
    protected int mScreenWidth = 0;
    protected int mScreenHeight = 0;
    protected float mScreenDensity = 0.0f;

    /**
     * overridePendingTransition mode
     */
    public enum TransitionMode {
        LEFT, RIGHT, TOP, BOTTOM, SCALE, FADE
    }

    /**
     * butterknife 8+ support
     */
    private Unbinder mUnbinder;
    /**
     * default toolbar
     */
    protected CommonTitle actionBar;
    protected FrameLayout toolbarLayout;
    protected LinearLayout contentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bundle = savedInstanceState;
        mActivity = this;
        if (isUseEventBus() && !EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        mScreenDensity = displayMetrics.density;
        mScreenHeight = displayMetrics.heightPixels;
        mScreenWidth = displayMetrics.widthPixels;
        initPUserInfo();
        getIntentData(getIntent());
        int layoutId = getContentViewLayoutID();
        if (layoutId != 0) {
            setContentView(layoutId);
        } else {
            throw new IllegalArgumentException("必须返回正确的布局资源ID");
        }
        AppManager.getAppManager().addActivity(this);
        initViewsAndEvents();
    }


    protected abstract void getIntentData(Intent intent);

    @Override
    public void setContentView(int layoutResID) {
        // 添加toolbar布局
        if (isLoadDefaultTitleBar()) {
            initToolBarView();
            initContentView();
            initStatusBarView();
            contentView.addView(toolbarLayout, new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            View view = getContentView(layoutResID, contentView);
            if (view == null) {
                LayoutInflater.from(this).inflate(layoutResID, contentView, true);
                super.setContentView(contentView, new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT));
            } else {
                super.setContentView(view, new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT));
            }
        } else {
            super.setContentView(layoutResID);
        }
        mUnbinder = ButterKnife.bind(this);
        setStatusBar();
    }

    protected boolean isLoadDefaultTitleBar() {
        return false;
    }

    public void logoutRefreshView() {
    }

    public void loginRefreshView() {
    }

    protected void initPUserInfo() {
    }

    protected View getContentView(int layoutResID, LinearLayout contentView) {
        return null;
    }

    protected void initContentView() {
        contentView = new LinearLayout(this);
        contentView.setOrientation(LinearLayout.VERTICAL);
    }

    protected boolean setStatusBarTrans() {
        return false;
    }

    private void initStatusBarView() {
        if (setStatusBarTrans()) {
            StatusBarUtil.setTranslucentForImageView(mActivity, 0, actionBar);
        }
    }

    private void initToolBarView() {
        toolbarLayout = new FrameLayout(this);
        LayoutInflater.from(this).inflate(R.layout.layout_toolbar, toolbarLayout,
                true);
        actionBar = toolbarLayout.findViewById(R.id.action_bar);
        actionBar.getLeftRes().setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        if (setActionBarBackground() != 0)
            actionBar.setBackgroundResource(setActionBarBackground());
    }


    protected void setActionBarTitle(String title) {
        actionBar.setCenterText(title);
    }

    protected int setActionBarBackground() {
        return 0;
    }


    protected void setStatusBar() {
    }

    /**
     * 隐藏软键盘
     */
    protected void hideSoftInput(View view) {
        InputMethodManager mInputMethodManager = (InputMethodManager) getSystemService(this.INPUT_METHOD_SERVICE);
        mInputMethodManager
                .hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * 打开软键盘
     */
    protected void showSoftInput(View view) {
        InputMethodManager mInputMethodManager = (InputMethodManager) getSystemService(this.INPUT_METHOD_SERVICE);
        mInputMethodManager
                .showSoftInput(view, InputMethodManager.SHOW_FORCED);
    }

    /**
     * 获取布局文件
     *
     * @return 布局文件ID
     */
    protected abstract int getContentViewLayoutID();


    protected abstract void initViewsAndEvents();

    ;


    public void showProgress(Boolean isCancel, String hint) {
        if (progressHUD == null) {
            progressHUD = KProgressHUD.create(this).setStyle(KProgressHUD.Style.SPIN_INDETERMINATE).setDimAmount(0.5f);
        }
        if (!progressHUD.isShowing()) {
            if (!TextUtils.isEmpty(hint)) {
                progressHUD.setLabel(hint);
            } else {
                progressHUD.setLabel("拼命加载...");
            }
            progressHUD.setCancellable(isCancel);
            progressHUD.show();
        }
    }

    public void showProgress() {
        showProgress(true, "");
    }

    public void showProgress(String hint) {
        showProgress(true, hint);
    }

    public void showProgress(boolean isCancel) {
        showProgress(isCancel, "");
    }

    public void hideProgress() {
        if (progressHUD != null) {
            progressHUD.dismiss();
        }
    }

    @Override
    protected void onDestroy() {
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
        dismissQuickDialog();
        CallServer.getRequestInstance().cancelBySign(mActivity);
        if (isUseEventBus() && EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        super.onDestroy();
    }


    /**
     * 是否使用 EventBus
     *
     * @return
     */
    protected boolean isUseEventBus() {
        return false;
    }

    /***
     * 功能：长按图片保存到手机
     */
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuItem.OnMenuItemClickListener handler = new MenuItem.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getTitle() == "保存图片到手机") {
                    new SaveImage().execute(); // Android 4.0以后要使用线程来访问网络
                } else {
                    return false;
                }
                return true;
            }
        };
        if (v instanceof WebView) {
            WebView.HitTestResult result = ((WebView) v).getHitTestResult();
            if (result != null) {
                int type = result.getType();
                if (type == WebView.HitTestResult.IMAGE_TYPE || type == WebView.HitTestResult.SRC_IMAGE_ANCHOR_TYPE) {
                    imgurl = result.getExtra();
                    menu.setHeaderTitle("提示");
                    menu.add(0, v.getId(), 0, "保存图片到手机").setOnMenuItemClickListener(handler);
                    menu.add(0, v.getId(), 0, "查看大图").setOnMenuItemClickListener(handler);
                }
            }
        }
    }

    /***
     * 功能：用线程保存图片
     *
     * @author wangyp
     */
    private class SaveImage extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String result = "";
            try {
                String sdcard = Environment.getExternalStorageDirectory().toString();
                File file = new File(sdcard + "/Pictures");
                if (!file.exists()) {
                    file.mkdirs();
                }
                int idx = imgurl.lastIndexOf(".");
                String ext = imgurl.substring(idx);
                file = new File(sdcard + "/Pictures/" + new Date().getTime() + ext);
                InputStream inputStream = null;
                URL url = new URL(imgurl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setConnectTimeout(20000);
                if (conn.getResponseCode() == 200) {
                    inputStream = conn.getInputStream();
                }
                byte[] buffer = new byte[4096];
                int len = 0;
                FileOutputStream outStream = new FileOutputStream(file);
                while ((len = inputStream.read(buffer)) != -1) {
                    outStream.write(buffer, 0, len);
                }
                outStream.close();
                result = "图片已保存至：" + file.getAbsolutePath();
                //更新系统相册
                Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                Uri uri = Uri.fromFile(file);
                intent.setData(uri);
                sendBroadcast(intent);
            } catch (Exception e) {
                result = "保存失败！" + e.getLocalizedMessage();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            Toasty.normal(mActivity, result).show();
        }
    }

    /**
     * 提示错误信息
     *
     * @param errorMsg
     */
    public void toastError(String errorMsg) {
        Toasty.error(this, errorMsg, android.widget.Toast.LENGTH_SHORT).show();
    }

    public void toastNormal(String errorMsg) {
        Toasty.normal(this, errorMsg, android.widget.Toast.LENGTH_SHORT).show();
    }

    /**
     * 提示基本信息
     *
     * @param errorMsg
     */
    public void toastInfo(String errorMsg) {
        Toasty.info(this, errorMsg, android.widget.Toast.LENGTH_SHORT).show();
    }

    /**
     * 提示成功信息
     *
     * @param errorMsg
     */
    public void toastSuccess(String errorMsg) {
        Toasty.success(this, errorMsg, android.widget.Toast.LENGTH_SHORT).show();
    }

    /**
     * 提示警告信息
     *
     * @param errorMsg
     */
    public void toastWarning(String errorMsg) {
        Toasty.warning(this, errorMsg, android.widget.Toast.LENGTH_SHORT).show();
    }

    @Override
    public void finish() {
        super.finish();
        AppManager.getAppManager().removeActivity(this);
//        if (toggleOverridePendingTransition()) {
//            switch (getOverridePendingTransitionMode()) {
//                case LEFT:
//                    overridePendingTransition(R.anim.left_in, R.anim.left_out);
//                    break;
//                case RIGHT:
//                    overridePendingTransition(R.anim.right_in, R.anim.right_out);
//                    break;
//                case TOP:
//                    overridePendingTransition(R.anim.top_in, R.anim.top_out);
//                    break;
//                case BOTTOM:
//                    overridePendingTransition(R.anim.bottom_in, R.anim.bottom_out);
//                    break;
//                case SCALE:
//                    overridePendingTransition(R.anim.scale_in, R.anim.scale_out);
//                    break;
//                case FADE:
//                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
//                    break;
//            }
//        }
    }

    /**
     * toggle overridePendingTransition
     *
     * @return
     */
    protected boolean toggleOverridePendingTransition() {
        return true;
    }

    ;

    /**
     * get the overridePendingTransition mode
     */
    protected TransitionMode getOverridePendingTransitionMode() {
        return TransitionMode.LEFT;
    }

    protected void showTwoBtnDialog(String title, String leftTxt, String rightTxt, final IDialogListener listener) {
        showTwoBtnDialog(title, leftTxt, rightTxt, 0, 0, listener);
    }

    protected void showTwoBtnDialog(String title, String leftTxt, String rightTxt, int leftTxtColor, int rightTxtColor, final IDialogListener listener) {
        if (twoBtnDialog == null) {
            twoBtnDialog = DialogUtils.getInstance().getCenterDialog(mActivity, R.layout.dialog_two_btn);
            TextView txtTitle = twoBtnDialog.findViewById(R.id.txt_title);
            Button btnLeft = twoBtnDialog.findViewById(R.id.btn_left);
            Button btnRight = twoBtnDialog.findViewById(R.id.btn_right);
            if (leftTxtColor != 0) {
                btnLeft.setTextColor(ContextCompat.getColor(mActivity, leftTxtColor));
            }
            if (rightTxtColor != 0) {
                btnRight.setTextColor(ContextCompat.getColor(mActivity, rightTxtColor));
            }
            txtTitle.setText(title);
            btnLeft.setText(leftTxt);
            btnRight.setText(rightTxt);
            btnLeft.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.leftClick();
                    }
                    dismissQuickDialog();
                }
            });
            btnRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.rightClick();
                    }
                    dismissQuickDialog();
                }
            });
        }
        twoBtnDialog.show();
    }

    protected void showOneBtnDialog(String title, String btn, final boolean isCancel, int btnTxtColor, final IOneDialogListener listener) {
        if (oneBtnDialog == null) {
            oneBtnDialog = DialogUtils.getInstance().getCenterDialog(mActivity, isCancel, R.layout.dialog_one_btn);
            TextView txtTitle = oneBtnDialog.findViewById(R.id.txt_title);
            Button btnRight = oneBtnDialog.findViewById(R.id.btn_right);
            if (btnTxtColor != 0) {
                btnRight.setTextColor(ContextCompat.getColor(mActivity, btnTxtColor));
            }
            txtTitle.setText(title);
            btnRight.setText(btn);
            btnRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.clickLisenter();
                    }
                    dismissQuickDialog();
                }
            });
            oneBtnDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialogInterface) {
                    if (!isCancel) {
                        finish();
                    }
                }
            });
        }
        oneBtnDialog.show();
    }

    public interface IDialogListener {
        void rightClick();

        void leftClick();
    }

    public interface IOneDialogListener {
        void clickLisenter();
    }

    public void dismissQuickDialog() {
        if (oneBtnDialog != null && oneBtnDialog.isShowing()) {
            oneBtnDialog.dismiss();
        }
        if (twoBtnDialog != null && twoBtnDialog.isShowing()) {
            twoBtnDialog.dismiss();
        }
    }
    public void setDrawableLeft(@DrawableRes int icon, TextView view) {
        Drawable drawable = getResources().getDrawable(
                icon);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(),
                drawable.getMinimumHeight());
        view.setCompoundDrawables(drawable, null, null, null);
    }

    public void setDrawableRight(@DrawableRes int icon, TextView view) {
        Drawable drawable = getResources().getDrawable(
                icon);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(),
                drawable.getMinimumHeight());
        view.setCompoundDrawables(null, null, drawable, null);
    }

    public void setDrawableNull(TextView view) {
        view.setCompoundDrawables(null, null, null, null);
    }

}

