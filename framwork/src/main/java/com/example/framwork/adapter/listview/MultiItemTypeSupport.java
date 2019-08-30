package com.example.framwork.adapter.listview;

/**
 * 添加对多Listview item type的支持
 * @author hongyang
 */
public interface MultiItemTypeSupport<T>
{
	int getLayoutId(int position, T t);

	int getViewTypeCount();

	int getItemViewType(int position, T t);
}