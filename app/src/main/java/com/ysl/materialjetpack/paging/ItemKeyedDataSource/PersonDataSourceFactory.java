package com.ysl.materialjetpack.paging.ItemKeyedDataSource;

import androidx.annotation.NonNull;
import androidx.paging.DataSource;

public class PersonDataSourceFactory extends DataSource.Factory<Integer, Person> {

    @NonNull
    @Override
    public DataSource<Integer, Person> create() {
        return new CustomItemDataSource(new DataRepository());
    }
}
