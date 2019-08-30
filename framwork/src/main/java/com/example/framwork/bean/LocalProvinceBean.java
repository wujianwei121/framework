package com.example.framwork.bean;

import com.contrarywind.interfaces.IPickerViewData;

import java.util.ArrayList;

/**
 * Created by wanjingyu on 2016/6/6.
 */
public class LocalProvinceBean implements IPickerViewData {
    public String province_id;
    public String province_name;
    public ArrayList<LocalCityBean> city_list;
    public boolean isSelected;

    @Override
    public String getPickerViewText() {
        return province_name;
    }
}
