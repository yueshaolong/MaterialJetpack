package com.ysl.materialjetpack.paging.PositionalDataSource;

import androidx.annotation.NonNull;
import androidx.paging.ItemKeyedDataSource;

/**
 * 数据源 第一种
 * 同学们：第一种 写到另外一个Module去了，同学们去看看
 */
public class Test1 extends ItemKeyedDataSource<Integer, Student> {
    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Student> callback) {

    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Student> callback) {

    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Student> callback) {

    }

    @NonNull
    @Override
    public Integer getKey(@NonNull Student item) {
        return null;
    }
}
