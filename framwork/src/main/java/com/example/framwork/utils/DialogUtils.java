package com.example.framwork.utils;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;

import com.example.framwork.R;
import com.example.framwork.adapter.BaseViewHolder;
import com.example.framwork.adapter.listview.CommonAdapter;
import com.example.framwork.bean.DialogListInfo;
import com.example.framwork.widget.CommonDialog;
import com.example.framwork.widget.CustomerGridView;
import com.example.framwork.widget.CustomerListView;

import java.util.List;


/**
 * Dialog对话框生成工具类
 *
 * @author zhuyanfei
 */
public class DialogUtils {
    /**
     * 内部类实现单例模式
     * 延迟加载，减少内存开销
     *
     * @author xuzhaohu
     */
    private static class SingletonHolder {
        private static DialogUtils instance = new DialogUtils();
    }

    /**
     * 私有的构造函数
     */
    private DialogUtils() {

    }

    private CommonAdapter mAdapter;
    private CommonAdapter mGAdapter;

    public static DialogUtils getInstance() {
        return DialogUtils.SingletonHolder.instance;
    }

    public static CommonDialog.Builder getDialogBuilder(Context context) {
        return new CommonDialog.Builder(context);
    }

    /**
     * 中间Dialog
     *
     * @param context
     * @param
     * @return
     */
    public Dialog getCenterDialog(Context context, int id) {
        Dialog dialog = new Dialog(context, R.style.Dialog_Fullscreen);
        dialog.setContentView(id);

        Window dialogWindow = dialog.getWindow();
        //dialogWindow.setWindowAnimations(R.style.mystyle);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = context.getResources().getDisplayMetrics().widthPixels;
        lp.alpha = 1.0f;
        dialogWindow.setAttributes(lp);
        dialogWindow.setGravity(Gravity.CENTER);
        dialog.setCanceledOnTouchOutside(true);

        return dialog;
    }

    public Dialog getCenterDialog(Context context, boolean iscan, int id) {
        Dialog dialog = new Dialog(context, R.style.Dialog_Fullscreen);
        dialog.setContentView(id);

        Window dialogWindow = dialog.getWindow();
        //dialogWindow.setWindowAnimations(R.style.mystyle);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = context.getResources().getDisplayMetrics().widthPixels;
        lp.alpha = 1.0f;
        dialogWindow.setAttributes(lp);
        dialogWindow.setGravity(Gravity.CENTER);
        dialog.setCanceledOnTouchOutside(iscan);

        return dialog;
    }

    /**
     * 点击对话框外可取消
     *
     * @param context
     * @param id
     * @return
     */
    public Dialog getCenterCancelDialog(Context context, int id) {
        Dialog dialog = new Dialog(context, R.style.custom_cancel_dialog);
        dialog.setContentView(id);

        Window dialogWindow = dialog.getWindow();
        //dialogWindow.setWindowAnimations(R.style.mystyle);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = context.getResources().getDisplayMetrics().widthPixels;
        lp.alpha = 1.0f;
        dialogWindow.setAttributes(lp);
        dialogWindow.setGravity(Gravity.CENTER);
        dialog.setCanceledOnTouchOutside(true);

        return dialog;
    }

    /**
     * 中间Dialog
     *
     * @param context
     * @param
     * @return
     */
    public Dialog getCenterDialog(Context context, int id, double width) {
        Dialog dialog = new Dialog(context, R.style.Dialog_Fullscreen);
        dialog.setContentView(id);

        Window dialogWindow = dialog.getWindow();
        //dialogWindow.setWindowAnimations(R.style.mystyle);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = (int) ((context.getResources().getDisplayMetrics().widthPixels) * width);
        lp.alpha = 1.0f;
        dialogWindow.setAttributes(lp);
        dialogWindow.setGravity(Gravity.CENTER);
        dialog.setCanceledOnTouchOutside(false);

        return dialog;
    }

    /**
     * 底部Dialog 有动画
     *
     * @param context
     * @param id      资源ID
     * @return
     */
    public Dialog getBottomDialog(Context context, int id) {
        Dialog dialog = new Dialog(context, R.style.Dialog_Fullscreen);
        dialog.setContentView(id);

        Window dialogWindow = dialog.getWindow();
        dialogWindow.setWindowAnimations(R.style.Dialog_Bottom);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = context.getResources().getDisplayMetrics().widthPixels;
        lp.alpha = 1.0f;
        dialogWindow.setAttributes(lp);
        dialogWindow.setGravity(Gravity.BOTTOM);
        dialog.setCanceledOnTouchOutside(true);

        return dialog;
    }

    /**
     * 底部Dialog 有动画
     *
     * @param context
     * @param id      资源ID
     * @return
     */
    public Dialog getBottomTranslucentDialog(Context context, int id) {
        Dialog dialog = new Dialog(context, R.style.Dialog_Fullscreen_Translucent);
        dialog.setContentView(id);

        Window dialogWindow = dialog.getWindow();
        dialogWindow.setWindowAnimations(R.style.Dialog_Bottom);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = context.getResources().getDisplayMetrics().widthPixels;
        lp.alpha = 1.0f;
        dialogWindow.setAttributes(lp);
        dialogWindow.setGravity(Gravity.BOTTOM);
        dialog.setCanceledOnTouchOutside(true);

        return dialog;
    }
    /**
     * 底部Dialog 有动画
     *
     * @param context
     * @param id      资源ID
     * @return
     */
    public Dialog getBottomTranslucentDialog(Context context, View id) {
        Dialog dialog = new Dialog(context, R.style.Dialog_Fullscreen_Translucent);
        dialog.setContentView(id);

        Window dialogWindow = dialog.getWindow();
        dialogWindow.setWindowAnimations(R.style.Dialog_Bottom);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = context.getResources().getDisplayMetrics().widthPixels;
        lp.alpha = 1.0f;
        dialogWindow.setAttributes(lp);
        dialogWindow.setGravity(Gravity.BOTTOM);
        dialog.setCanceledOnTouchOutside(true);

        return dialog;
    }

    /**
     * 底部Dialog，有动画
     *
     * @param context
     * @param view    View对象
     * @return
     */
    public Dialog getBottomDialog(Context context, View view) {
        Dialog dialog = new Dialog(context, R.style.Dialog_Fullscreen);
        dialog.setContentView(view);

        Window dialogWindow = dialog.getWindow();
        dialogWindow.setWindowAnimations(R.style.Dialog_Bottom);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = context.getResources().getDisplayMetrics().widthPixels;
        lp.alpha = 1.0f;
        dialogWindow.setAttributes(lp);
        dialogWindow.setGravity(Gravity.BOTTOM);
        dialog.setCanceledOnTouchOutside(true);

        return dialog;
    }

    /**
     * 底部Dialog，有动画
     *
     * @param context
     * @return
     */
    public Dialog getBottomListDialog(Context context, final List<? extends DialogListInfo> list, final int selectColor, final OnItemClickListener onItemClickListener) {
        final Dialog dialog = new Dialog(context, R.style.Dialog_Fullscreen);
        dialog.setContentView(R.layout.dialog_listview);
        Window dialogWindow = dialog.getWindow();
        dialogWindow.setWindowAnimations(R.style.Dialog_Bottom);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = context.getResources().getDisplayMetrics().widthPixels;
        lp.alpha = 1.0f;
        dialogWindow.setAttributes(lp);
        dialogWindow.setGravity(Gravity.BOTTOM);
        dialog.setCanceledOnTouchOutside(true);
        CustomerListView listView = dialog.findViewById(R.id.lv_content);
        listView.setAdapter(mAdapter = new CommonAdapter<DialogListInfo>(context, (List<DialogListInfo>) list) {
            @Override
            public void convert(BaseViewHolder holder, final DialogListInfo s, final int p) {
                holder.setText(R.id.tv_content, s.getContent());
                if (curPos == p) {
                    holder.setTextColorRes(R.id.tv_content, selectColor);
                } else {
                    holder.setTextColorRes(R.id.tv_content, R.color.gray_98);
                }
            }

            @Override
            protected int getItemViewLayoutId(int position, DialogListInfo item) {
                return R.layout.item_lv_dialog;
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mAdapter.setCurPos(position);
                if (onItemClickListener != null) {
                    onItemClickListener.itemClickListener(list.get(position), position);
                }
                if (dialog != null && dialog.isShowing())
                    dialog.dismiss();
            }
        });
        return dialog;
    }

    /**
     * 底部Dialog，有动画
     *
     * @param context
     * @return
     */
    public Dialog getBottomGrideDialog(Context context, final List<? extends DialogListInfo> list, final int selectColor, final OnItemClickListener onItemClickListener) {
        final Dialog dialog = new Dialog(context, R.style.Dialog_Fullscreen);
        dialog.setContentView(R.layout.dialog_gride);
        Window dialogWindow = dialog.getWindow();
        dialogWindow.setWindowAnimations(R.style.Dialog_Bottom);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = context.getResources().getDisplayMetrics().widthPixels;
        lp.alpha = 1.0f;
        dialogWindow.setAttributes(lp);
        dialogWindow.setGravity(Gravity.BOTTOM);
        dialog.setCanceledOnTouchOutside(true);
        CustomerGridView listView = dialog.findViewById(R.id.lv_content);

        listView.setAdapter(mGAdapter = new CommonAdapter<DialogListInfo>(context, (List<DialogListInfo>) list) {
            @Override
            public void convert(BaseViewHolder holder, final DialogListInfo s, final int p) {
                holder.setText(R.id.tv_content, s.getContent());
                if (curPos == p) {
                    holder.setTextColorRes(R.id.tv_content, selectColor);
                } else {
                    holder.setTextColorRes(R.id.tv_content, R.color.gray_98);
                }
            }

            @Override
            protected int getItemViewLayoutId(int position, DialogListInfo item) {
                return R.layout.item_lv_dialog;
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mGAdapter.setCurPos(position);
                if (onItemClickListener != null) {
                    onItemClickListener.itemClickListener(list.get(position), position);
                }
                if (dialog != null && dialog.isShowing())
                    dialog.dismiss();
            }
        });
        return dialog;
    }

    public void refreshAdapter() {
        if (mAdapter != null)
            mAdapter.notifyDataSetChanged();
        if (mGAdapter != null)
            mGAdapter.notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void itemClickListener(DialogListInfo s, int pos);
    }

    public AlertDialog.Builder getDialog(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        return builder;
    }

    /***
     * 获取一个信息对话框，注意需要自己手动调用show方法显示
     * @param context
     * @param message
     * @param onClickListener
     * @return
     */
    public AlertDialog.Builder getMessageDialog(Context context, String message, android.content.DialogInterface.OnClickListener onClickListener) {
        AlertDialog.Builder builder = getDialog(context);
        builder.setMessage(message);
        builder.setPositiveButton("确定", onClickListener);
        return builder;
    }

    public AlertDialog.Builder getMessageDialog(Context context, String message) {
        return getMessageDialog(context, message, null);
    }
}
