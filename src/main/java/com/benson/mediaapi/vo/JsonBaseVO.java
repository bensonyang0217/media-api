package com.benson.mediaapi.vo;

import java.util.List;

public class JsonBaseVO<T> {
    private List<T> results;
    public List<T> getResults() {
        return results;
    }

    public void setResults(List<T> results) {
        this.results = results;
    }
    @Override
    public String toString() {
        return "{" +
                ", results=" + results +
                '}';
    }
}