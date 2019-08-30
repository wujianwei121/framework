package com.example.framwork.utils;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.example.framwork.R;
import com.example.framwork.bean.AddressInfo;
import com.example.framwork.bean.LocalCityBean;
import com.example.framwork.bean.LocalDistrictBean;
import com.example.framwork.bean.LocalProvinceBean;
import com.example.framwork.noHttp.Bean.BaseResponseBean;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by lenovo on 2018/5/11.
 */

public class PickerUtil {
    public static int YEAR_MONTH_DAY = 1;
    public static int ALL = 2;

    /**
     * 内部类实现单例模式
     * 延迟加载，减少内存开销
     *
     * @author xuzhaohu
     */
    private static class SingletonHolder {
        private static PickerUtil instance = new PickerUtil();
    }

    /**
     * 私有的构造函数
     */
    private PickerUtil() {

    }

    public static PickerUtil getInstance() {
        return PickerUtil.SingletonHolder.instance;
    }

    public TimePickerView initTimePicker(final Context context, int timeType, int color, OnTimeSelectListener onTimeSelectListener) {
        TimePickerBuilder pvTime = new TimePickerBuilder(context, onTimeSelectListener);
        if (timeType == 1)
            pvTime.setType(new boolean[]{true, true, true, false, false, false});
        else pvTime.setType(new boolean[]{true, true, true, true, true, false});
        //控制时间范围
        Calendar startDate = Calendar.getInstance();
        startDate.set(startDate.get(Calendar.YEAR) - 56, 1, 1);
        Calendar endDate = Calendar.getInstance();
        endDate.set(endDate.get(Calendar.YEAR) + 56, 12, 31);
        Calendar calendar = Calendar.getInstance();
        pvTime.setRangDate(startDate, endDate);
        pvTime.setDate(calendar);
        pvTime.isCenterLabel(true);
        pvTime.setCancelColor(ContextCompat.getColor(context, R.color.gray_9b));
        pvTime.setSubmitColor(ContextCompat.getColor(context, color));
        return pvTime.build();
    }


    public OptionsPickerView initCityPicker(final Context context, final OnCityClickListener mOnCityClickListener) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            BufferedReader bf = new BufferedReader(new InputStreamReader(context.getAssets().open("city.json")));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
            //选项选择器
            final ArrayList<LocalProvinceBean> options1Items = new ArrayList<>();
            final ArrayList<ArrayList<LocalCityBean>> options2Items = new ArrayList<>();
            final ArrayList<ArrayList<ArrayList<LocalDistrictBean>>> options3Items = new ArrayList<>();
            options1Items.addAll(BaseResponseBean.parseArray(stringBuilder.toString(), LocalProvinceBean.class));
            //选项2
            for (int i = 0; i < options1Items.size(); i++) {
                ArrayList<LocalCityBean> citys = options1Items.get(i).city_list;
                if (citys != null)
                    options2Items.add(citys);
            }
            //选项3
            if (options2Items.size() != 0) {
                for (int i = 0; i < options2Items.size(); i++) {
                    ArrayList<ArrayList<LocalDistrictBean>> options3Items_01 = new ArrayList<>();
                    if (options2Items.get(i) != null) {
                        int districts = options2Items.get(i).size();
                        for (int j = 0; j < districts; j++) {
                            options3Items_01.add(options2Items.get(i).get(j).district_list);
                        }
                        options3Items.add(options3Items_01);
                    }
                }
            }
            final OptionsPickerView pvOptions = new OptionsPickerBuilder(context, new OnOptionsSelectListener() {
                @Override
                public void onOptionsSelect(int options1, int options2, int options3, View v) {
                    //返回的分别是三个级别的选中位置
                    String tx = options1Items.get(options1).province_name + " "
                            + options2Items.get(options1).get(options2).city_name + " "
                            + options3Items.get(options1).get(options2).get(options3).district_name;

                    if (mOnCityClickListener != null) {
                        AddressInfo addressInfo = new AddressInfo();
                        addressInfo.proviceName = options1Items.get(options1).province_name;
                        addressInfo.province_id = options1Items.get(options1).province_id;
                        addressInfo.city_id = options2Items.get(options1).get(options2).city_id;
                        addressInfo.cityName = options2Items.get(options1).get(options2).city_name;
                        addressInfo.district_id = options3Items.get(options1).get(options2).get(options3).district_id;
                        addressInfo.districtName = options3Items.get(options1).get(options2).get(options3).district_name;
                        addressInfo.pcd = tx;
                        mOnCityClickListener.onCityClick(addressInfo);
                    }
                }
            }).build();
            //三级联动效果
            pvOptions.setPicker(options1Items, options2Items, options3Items);
            return pvOptions;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public interface OnCityClickListener {
        void onCityClick(AddressInfo addressInfo);
    }
}
