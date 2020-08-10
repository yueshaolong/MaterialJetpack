package com.ysl.materialjetpack.databinding;


import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.ysl.materialjetpack.BR;

/**
 * ViewModel
 */
public class User extends BaseObservable {
    private String name;
    private String password;

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }

    @Bindable
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
