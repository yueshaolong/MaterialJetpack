package com.ysl.materialjetpack.paging.PositionalDataSource;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

/**
 * 数据源 第二种
 * 同学们：第二种 写到另外一个Module去了，同学们去看看
 */
public class Test2 extends PageKeyedDataSource<Integer, Student> {
    // onCreate
    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, Student> callback) {

    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Student> callback) {

    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Student> callback) {

    }
}
