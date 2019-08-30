package com.example.framwork.utils;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.framwork.R;
import com.example.framwork.base.BaseListBean;
import com.example.framwork.mvp.BaseRecyclePresenter;
import com.example.framwork.mvp.CustomRequest;
import com.example.framwork.noHttp.Bean.BaseResponseBean;
import com.example.framwork.noHttp.OnRequestListener;
import com.example.framwork.ricyclerview.DividerGridItemDecoration;
import com.example.framwork.ricyclerview.DividerItemWideDecoration;
import com.example.framwork.ricyclerview.GridSpacingItemDecoration;
import com.example.framwork.widget.LoadDataLayout;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.yanzhenjie.nohttp.Logger;
import com.yanzhenjie.nohttp.error.NetworkError;
import com.yanzhenjie.nohttp.error.TimeoutError;

import java.util.HashMap;
import java.util.List;


/**
 * 下拉刷新 上拉加载集中处理类
 * Created by zhaobo on 2017/5/31.
 */

public class SuperRecyclerViewUtils implements OnRefreshLoadMoreListener,
        LoadDataLayout.OnReloadListener {
    private RecyclerView mRecyclerView;
    private SmartRefreshLayout mRefreshLayout;
    private BaseQuickAdapter mAdapter;
    private CallBack mCallBack;
    private String emptyStr;
    private int emptyRes;
    private int loginBtnBg;
    private int loginBtnTextColor;
    private IResterLoginCallBack iResterLoginCallBack;
    private int mPage = 1;
    private GridLayoutManager gridLayoutManager;
    private LinearLayoutManager linearLayoutManager;
    private LoadDataLayoutUtils mLoadDataUtils;
    private IInterceptRefresh interceptRefresh;
    private boolean isShowEmpty = true;
    private BaseRecyclePresenter recyclePresenter;

    public void setInterceptRefresh(IInterceptRefresh interceptRefresh) {
        this.interceptRefresh = interceptRefresh;
    }

    public interface IInterceptRefresh {
        void interceptRefresh();
    }

    public interface IResterLoginCallBack {
        void goToLogin();
    }

    public boolean isShowEmpty() {
        return isShowEmpty;
    }

    public void setShowEmpty(boolean showEmpty) {
        isShowEmpty = showEmpty;
    }

    public LoadDataLayoutUtils getmLoadDataUtils() {
        return mLoadDataUtils;
    }

    public BaseQuickAdapter getmAdapter() {
        return mAdapter;
    }

    public boolean contains(Object o) {
        return mAdapter.getData().contains(o);
    }

    public int indexOf(Object o) {
        return mAdapter.getData().indexOf(o);
    }

    public void refreshAdapter() {
        mAdapter.notifyDataSetChanged();
    }

    public Object getObj(int index) {
        return mAdapter.getData().get(index);
    }

    public SuperRecyclerViewUtils(Activity activity, RecyclerView mRecyclerView, SmartRefreshLayout mRefreshLayout, LoadDataLayout loadDataLayout, BaseQuickAdapter xadapter, CallBack xcallback) {
        this.mRecyclerView = mRecyclerView;
        this.mRefreshLayout = mRefreshLayout;
        this.mAdapter = xadapter;
        this.mCallBack = xcallback;
        recyclePresenter = new BaseRecyclePresenter(activity, null, 0);
        recyclePresenter.setRecyclerViewUtils(this);
        if (loadDataLayout != null) {
            loadDataLayout.setReloadBtnTextColor(R.color.gray_9b);
            mLoadDataUtils = new LoadDataLayoutUtils(loadDataLayout, this);
        }
        if (mRefreshLayout != null) {
            mRefreshLayout.setOnRefreshLoadMoreListener(this);
            mRefreshLayout.setEnableHeaderTranslationContent(true);//是否下拉Header的时候向下平移列表或者内容
            mRefreshLayout.setEnableFooterTranslationContent(true);//是否上拉Footer的时候向上平移列表或者内容
            mRefreshLayout.setEnableLoadMore(true);
        }
        if (xadapter != null) {
            xadapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        }
    }

    /**
     * 本地list初始化  无需和presenter打交道
     */
    public SuperRecyclerViewUtils(RecyclerView mRecyclerView, BaseQuickAdapter xadapter) {
        this.mRecyclerView = mRecyclerView;
        this.mAdapter = xadapter;
        if (xadapter != null) {
            xadapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        }
    }

    public RecyclerView getmRecyclerView() {
        return mRecyclerView;
    }

    public SuperRecyclerViewUtils setEmptyRes(String emptyStr, int emptyRes) {
        this.emptyRes = emptyRes;
        this.emptyStr = emptyStr;
        return this;
    }

    public SuperRecyclerViewUtils setLoginCall(IResterLoginCallBack iResterLoginCallBack) {
        this.iResterLoginCallBack = iResterLoginCallBack;
        return this;
    }

    public SuperRecyclerViewUtils setLoginRes(int loginBtnTextColor, int bgRes) {
        this.loginBtnBg = bgRes;
        this.loginBtnTextColor = loginBtnTextColor;
        return this;
    }

    /**
     * 设置recyclerview 为listview
     * showLine 是否显示分割线
     */
    public SuperRecyclerViewUtils setRecyclerViewForList(Context context, int height) {
        setRecyclerViewForList(context, R.color.gray_f2, height);
        return this;
    }

    public SuperRecyclerViewUtils setRecyclerViewForList(Context context, int lineColor, int height) {
        linearLayoutManager = new LinearLayoutManager(context);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        if (height != 0) {
            mRecyclerView.addItemDecoration(new DividerItemWideDecoration(context, LinearLayoutManager.VERTICAL, height, lineColor));
        }
        mRecyclerView.setAdapter(mAdapter);
        return this;
    }

    /**
     * 设置recyclerview 为gridview
     */
    public SuperRecyclerViewUtils setRecyclerViewForGrid(Context context, int columns, int spacing, boolean includeEdge, boolean showLine) {
        gridLayoutManager = new GridLayoutManager(context, columns, GridLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        if (showLine) {
            mRecyclerView.addItemDecoration(new DividerGridItemDecoration(context, LinearLayoutManager.VERTICAL));
        } else if (spacing != 0) {
            mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(columns, DensityUtil.getInstance().dip2px(context, spacing), includeEdge));
        }
        mRecyclerView.setAdapter(mAdapter);
        return this;
    }

    /**
     * 设置recyclerview 头部
     */
    public SuperRecyclerViewUtils addHeader(View headerView) {
        if (mAdapter != null) {
            mAdapter.addHeaderView(headerView);
        }
        return this;
    }

    public SuperRecyclerViewUtils showMore() {
        if (mRefreshLayout != null)
            mRefreshLayout.setNoMoreData(false);
        return this;
    }

    /**
     * 关闭加载更多
     *
     * @return
     */
    public SuperRecyclerViewUtils closeMore() {
        if (mRefreshLayout != null)
            mRefreshLayout.setEnableLoadMore(false);
        return this;
    }

    public SuperRecyclerViewUtils finishMore() {
        if (mRefreshLayout != null)
            mRefreshLayout.finishLoadMoreWithNoMoreData();
        return this;
    }

    public SuperRecyclerViewUtils closeRefresh() {
        if (mRefreshLayout != null)
            mRefreshLayout.setEnableRefresh(false);
        return this;
    }

    public void openRefresh() {
        if (mRefreshLayout != null)
            mRefreshLayout.setEnableRefresh(true);
    }

    public void openMore() {
        if (mRefreshLayout != null)
            mRefreshLayout.setEnableLoadMore(true);
    }

    public SuperRecyclerViewUtils hideRefresh(boolean isSuccess) {
        if (mRefreshLayout != null)
            mRefreshLayout.finishRefresh(isSuccess);
        return this;
    }

    public SuperRecyclerViewUtils hideLoadMore(boolean isSuccess) {
        if (mRefreshLayout != null)
            mRefreshLayout.finishLoadMore(isSuccess);
        return this;
    }

    public SuperRecyclerViewUtils callAndRefresh() {
        if (mRefreshLayout != null)
            mRefreshLayout.autoRefresh();
        return this;
    }

    public SuperRecyclerViewUtils call() {
        if (mLoadDataUtils != null) {
            mLoadDataUtils.showLoading("拼命加载中");
        }
        onRefresh(mRefreshLayout);
        return this;
    }

    public void logout() {
        mLoadDataUtils.showNoLogin("登录已过期,请重新登录~", loginBtnTextColor, loginBtnBg);
    }

    /**
     * 只针对本地数据
     *
     * @return
     */
    public SuperRecyclerViewUtils show() {
        mRecyclerView.setAdapter(mAdapter);
        if (mLoadDataUtils != null && mLoadDataUtils.getLoadDataLayout() != null) {
            if (mAdapter.getData().size() == 0 && isShowEmpty) {
                mLoadDataUtils.showEmpty(TextUtils.isEmpty(emptyStr) ? "空空如也~" : emptyStr, emptyRes);
            } else {
                mLoadDataUtils.showContent();
            }
        }
        hideRefresh(true);
        return this;
    }

    public void clearAll() {
        mAdapter.getData().clear();
        if (mLoadDataUtils != null && mLoadDataUtils.getLoadDataLayout() != null) {
            mLoadDataUtils.showEmpty(TextUtils.isEmpty(emptyStr) ? "空空如也~" : emptyStr, emptyRes);
        }
    }

    private void onFail(int errorCode, Exception exception, String error) {
        if (mPage == 1) {
            hideRefresh(false);
        } else {
            hideLoadMore(false);
        }
        if (mPage > 1) {
            //如果加载更多失败，page页回退
            mPage--;
        }
        if (mLoadDataUtils != null && mAdapter.getData().size() == 0) {
            if (exception != null && (exception instanceof NetworkError || exception instanceof TimeoutError)) {
                mLoadDataUtils.showNoWifiError(error);
            } else if (CustomRequest.getConfig().getFilter() != null && CustomRequest.getConfig().getFilter().logoutOfDate(errorCode)) {
                mLoadDataUtils.showNoLogin(error, loginBtnTextColor, loginBtnBg);
            } else {
                mLoadDataUtils.showError(error);
            }
        }

    }

    private void onInitGreenDaoList(List<Object> list) {
        if (mRecyclerView.getAdapter() == null) {
            setAdapter();
        }
        openRefresh();
        mLoadDataUtils.showContent();
        mAdapter.replaceData(list);

    }

    private void onSuccess(BaseListBean baseBean) {
        if (mLoadDataUtils != null)
            mLoadDataUtils.showContent();
        openMore();
        if (mPage == 1) {
            hideRefresh(true);
            if (baseBean.getList().size() == 0) {
                closeMore();
                if (mLoadDataUtils != null && isShowEmpty) {
                    mLoadDataUtils.showEmpty(TextUtils.isEmpty(emptyStr) ? "空空如也~" : emptyStr, emptyRes);
                    return;
                }
            }
            if (mRecyclerView.getAdapter() == null) {
                if (mAdapter == null) {
                    Logger.e("初始化adapter");
                }
                setAdapter();
            }
            mAdapter.replaceData(baseBean.getList());
        } else {
            hideLoadMore(true);
            if (baseBean.getList().size() == 0) {
                finishMore();
            } else {
                mAdapter.addData(baseBean.getList());
                mAdapter.notifyDataSetChanged();
            }

        }
    }

    private void onSuccessArray(List<Object> list) {
        if (mLoadDataUtils != null)
            mLoadDataUtils.showContent();
        closeMore();
        hideRefresh(true);
        if (list.size() == 0) {
            if (mLoadDataUtils != null && isShowEmpty) {
                mLoadDataUtils.showEmpty(TextUtils.isEmpty(emptyStr) ? "空空如也~" : emptyStr, emptyRes);
                return;
            }
        }
        if (mRecyclerView.getAdapter() == null) {
            if (mAdapter == null) {
                Logger.e("初始化adapter");
            }
            setAdapter();
        }
        mAdapter.replaceData(list);
    }

    public SuperRecyclerViewUtils setAdapter() {
        mRecyclerView.setAdapter(mAdapter);
        return this;
    }

    @Override
    public void onReload(View v, int status) {
        if (status == LoadDataLayout.NO_Login) {
            if (iResterLoginCallBack != null)
                iResterLoginCallBack.goToLogin();
        } else {
            if (mLoadDataUtils != null)
                mLoadDataUtils.showLoading("拼命加载中");
            onRefresh(mRefreshLayout);
        }
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        mPage = 1;
        if (interceptRefresh == null) {
            if (mCallBack != null) {
                mCallBack.onRefresh(mPage);
                recyclePresenter.getDataList(mCallBack.initRequestInfo(mPage));
            }
        } else {
            interceptRefresh.interceptRefresh();
        }
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        mPage++;
        if (mCallBack != null) {
            mCallBack.onLoadMore(mPage);
            recyclePresenter.getDataList(mCallBack.initRequestInfo(mPage));
        }
    }

    public abstract static class CallBack<T extends BaseListBean> {

        public void onRefresh(int page) {
        }

        public void onLoadMore(int page) {
        }

        public abstract HashMap initRequestInfo(int page);

        public abstract boolean isPaging();

        /**
         * 自定义转换数据,线程内转换
         */
        public BaseListBean onConvertData(BaseResponseBean response) {
            return null;
        }

        /**
         * 自定义转换类 必须重写此方法 回传此时basebean的子类,线程内转换
         */
        public abstract Class onConvertClass();

        /**
         * 从数据获取数据
         */
        public List getGreenDaoList() {
            return null;
        }

        /**
         * 用来返回原始数据
         */
        public void onReturnData(Object response) {

        }

        /**
         * 保存数据到数据库
         */
        public void onSaveToGreenDao(Object baseBean) {

        }

    }


    public static class RecyclerViewHttpCallBack implements OnRequestListener<BaseResponseBean> {
        private SuperRecyclerViewUtils mRecyclerViewUtils;
        private boolean isPaging;

        public RecyclerViewHttpCallBack(SuperRecyclerViewUtils SuperRecyclerViewUtils) {
            mRecyclerViewUtils = SuperRecyclerViewUtils;
            this.isPaging = mRecyclerViewUtils.mCallBack.isPaging();
            if (mRecyclerViewUtils.mCallBack.getGreenDaoList() != null && mRecyclerViewUtils.mCallBack.getGreenDaoList().size() > 0) {
                mRecyclerViewUtils.onInitGreenDaoList(mRecyclerViewUtils.mCallBack.getGreenDaoList());
            }
        }


        @Override
        public void requestSuccess(BaseResponseBean bean) {
            try {
                mRecyclerViewUtils.mCallBack.onReturnData(bean);
                mRecyclerViewUtils.mCallBack.onSaveToGreenDao(bean);
                if (isPaging) {
                    BaseListBean baseBean = mRecyclerViewUtils.mCallBack.onConvertData(bean);
                    if (baseBean == null) {
                        if (mRecyclerViewUtils.mCallBack.onConvertClass() == null) {
                            Logger.e("请重写SuperRecyclerViewUtils里面onConvertClass方法");
                            return;
                        }
                        baseBean = (BaseListBean) bean.parseObject(mRecyclerViewUtils.mCallBack.onConvertClass());
                    }
                    mRecyclerViewUtils.onSuccess(baseBean);
                } else if (!isPaging) {
                    if (mRecyclerViewUtils.mCallBack.onConvertClass() == null) {
                        Logger.e("请重写SuperRecyclerViewUtils里面onConvertClass方法");
                        return;
                    }
                    List list = bean.parseList(mRecyclerViewUtils.mCallBack.onConvertClass());
                    mRecyclerViewUtils.onSuccessArray(list);
                }
            } catch (Exception e) {
                mRecyclerViewUtils.onFail(0, e, "数据异常");
            }

        }

        @Override
        public void requestFailed(BaseResponseBean bean, int errorCode, Exception exception, String error) {
            mRecyclerViewUtils.onFail(errorCode, exception, error);
        }

        @Override
        public void requestFinish() {

        }

    }

    public void removeItem(int pos) {
        mAdapter.remove(pos);
        if (mAdapter.getData().size() == 0 && isShowEmpty) {
            mLoadDataUtils.showEmpty(TextUtils.isEmpty(emptyStr) ? "空空如也~" : emptyStr, emptyRes);
        } else {
            getmLoadDataUtils().showContent();
        }
    }

    public void addNewItem(List o) {
        if (o == null) {
            return;
        }
        mAdapter.getData().clear();
        getmLoadDataUtils().showContent();
        mAdapter.addData(o);
    }

    public void addItem(Object obj) {
        if (obj == null) {
            return;
        }
        mAdapter.addData(obj);
        getmLoadDataUtils().showContent();
    }

    public GridLayoutManager getGridLayoutManager() {
        return gridLayoutManager;
    }

    public void setGridLayoutManager(GridLayoutManager gridLayoutManager) {
        this.gridLayoutManager = gridLayoutManager;
    }

    public LinearLayoutManager getLinearLayoutManager() {
        return linearLayoutManager;
    }

    public void setLinearLayoutManager(LinearLayoutManager linearLayoutManager) {
        this.linearLayoutManager = linearLayoutManager;
    }

    public void notifyDataSetChanged() {
        mAdapter.notifyDataSetChanged();
    }
}
