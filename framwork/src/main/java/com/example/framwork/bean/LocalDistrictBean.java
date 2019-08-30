package com.example.framwork.bean;

import com.contrarywind.interfaces.IPickerViewData;

import java.io.Serializable;

/**
 * Created by wanjingyu on 2016/6/6.
 */


public class LocalDistrictBean implements IPickerViewData {
    public String district_id;
    public String district_name;
    public boolean isSelected;

    @Override
    public String getPickerViewText() {
        return district_name;
    }
}