package com.nhlshop.dto;

import java.util.ArrayList;
import java.util.Collection;

public class Page<T> {
    private int page;
    private int totalpage;
    private Collection<T> listResult = new ArrayList<>();

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotalpage() {
        return totalpage;
    }

    public void setTotalpage(int totalpage) {
        this.totalpage = totalpage;
    }

    public Collection<T> getList() {
        return listResult;
    }

    public void setList(Collection<T> list) {
        this.listResult = list;
    }

}
