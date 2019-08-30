package com.example.framwork.base;

import java.io.Serializable;
import java.util.List;

public abstract class BaseListBean implements Serializable {
    public abstract List<?> getList();
}
