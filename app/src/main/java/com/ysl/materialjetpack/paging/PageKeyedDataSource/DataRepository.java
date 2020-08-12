package com.ysl.materialjetpack.paging.PageKeyedDataSource;

import java.util.ArrayList;
import java.util.List;

// TODO 同学们，这里理解就是仓库，要有仓库的概念，后面Derry老师带大家写JetPack项目实战时 也会有仓库的概念
public class DataRepository {

    private List<Person> dataList = new ArrayList<>();

    public DataRepository() {
        for (int i = 0; i < 1000; i++) {
            Person person = new Person();
            person.setId("ID3号:" + i);
            person.setName("名3称:" + i);
            person.setSex("性3别:" + i);
            dataList.add(person);
        }
    }

    public List<Person> initData(int size) {
        return dataList.subList(0, size);
    }

    public List<Person> loadPageData(int page, int size) {
        int totalPage;
        if (dataList.size() % size == 0) {
            totalPage = dataList.size() / size;
        } else {
            totalPage = dataList.size() / size + 1;
        }

        if (page > totalPage || page < 1) {
            return null;
        }
        if (page == totalPage) {
            return dataList.subList((page - 1) * size, dataList.size());
        }
        return dataList.subList((page - 1) * size, page * size);
    }
}
