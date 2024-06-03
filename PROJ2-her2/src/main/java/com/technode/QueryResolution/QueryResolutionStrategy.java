package com.technode.QueryResolution;

public interface QueryResolutionStrategy {
    <T, R> com.technode.QueryResolution.QueryResolutionResult<R> resolve(com.technode.QueryResolution.QueryResolutionForm<T> queryForm);
}

