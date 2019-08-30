package com.example.framwork.utils.permission;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;

import com.example.framwork.R;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.runtime.Permission;

import java.util.List;

import es.dmoral.toasty.Toasty;

/**
 * Created by lenovo on 2018/5/10.
 */

public class PermissionUtil {
    private Rationale mRationale;
    public static final int PERMISSION_SETTING = 1010;
    public static final int PERMISSION_SUCCESS = 1;
    public static final int PERMISSION_FAILED = 2;
    public static final String[] LOCATION = new String[]{
            Permission.ACCESS_FINE_LOCATION,
            Permission.ACCESS_COARSE_LOCATION,
            Permission.WRITE_EXTERNAL_STORAGE,
            Permission.READ_EXTERNAL_STORAGE,
            Permission.READ_PHONE_STATE
    };
    public static final String[] STORAGE = new String[]{
            Permission.WRITE_EXTERNAL_STORAGE,
            Permission.READ_EXTERNAL_STORAGE,
    };

    /**
     * 内部类实现单例模式
     * 延迟加载，减少内存开销
     *
     * @author xuzhaohu
     */
    private static class SingletonHolder {
        private static PermissionUtil instance = new PermissionUtil();

    }

    /**
     * 私有的构造函数
     */
    private PermissionUtil() {
    }

    public static PermissionUtil getInstance() {
        return PermissionUtil.SingletonHolder.instance;
    }

    public interface IPermissionsCallBck {
        void premissionsCallback(int code);
    }

    public void requestPermission(final Activity activity, final IPermissionsCallBck permissionsCallBck, String... permissions) {
        if (mRationale == null)
            mRationale = new DefaultRationale();
        AndPermission.with(activity)
                .runtime()
                .permission(permissions)
                .rationale(mRationale)
                .onGranted(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> permissions) {
                        if (permissionsCallBck != null)
                            permissionsCallBck.premissionsCallback(PERMISSION_SUCCESS);
                    }
                })
                .onDenied(new Action<List<String>>() {
                    @Override
                    public void onAction(@NonNull List<String> permissions) {
                        Toasty.warning(activity, "权限申请失败，可能会影响您的正常使用").show();
                        if (permissionsCallBck != null)
                            permissionsCallBck.premissionsCallback(PERMISSION_FAILED);
                        if (AndPermission.hasAlwaysDeniedPermission(activity, permissions)) {
                            showSetting(activity, permissions);
                        }
                    }
                })
                .start();
    }


    public void showSetting(final Context context, final List<String> permissions) {
        List<String> permissionNames = Permission.transformText(context, permissions);
        String message = context.getString(R.string.message_permission_always_failed, TextUtils.join("\n", permissionNames));
        new AlertDialog.Builder(context)
                .setCancelable(false)
                .setTitle(R.string.title_dialog)
                .setMessage(message)
                .setPositiveButton(R.string.setting, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AndPermission.with(context)
                                .runtime()
                                .setting()
                                .start(PERMISSION_SETTING);
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .show();
    }
}
