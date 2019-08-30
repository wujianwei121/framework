package com.example.framwork.bean;


import com.contrarywind.interfaces.IPickerViewData;

import java.util.ArrayList;

/**
 * Created by wanjingyu on 2016/6/6.
 */
public class LocalCityBean implements IPickerViewData {
    public String city_id;
    public String city_name;
    public ArrayList<LocalDistrictBean> district_list;
    public boolean isSelected;

    //这个用来显示在PickerView上面的字符串,PickerView会通过getPickerViewText方法获取字符串显示出来。
    @Override
    public String getPickerViewText() {
        return city_name;
    }

}
