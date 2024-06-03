package com.technode.QueryResolution;

public class QueryResolutionForm<T> {
    private T queryData;

    public QueryResolutionForm(T queryData) {this.queryData = queryData;}

    public T getQueryData() { return queryData; }
}
