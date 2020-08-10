package com.ysl.materialjetpack.livedata;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class NameViewModel extends ViewModel {
    private static MutableLiveData<String> mutableLiveData = new MutableLiveData<>();
    public MutableLiveData<String> getMutableLiveData() {
        return mutableLiveData;
    }
    public int i = 0;
}
