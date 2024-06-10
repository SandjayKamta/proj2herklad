package com.technode.QueryResolution;

 public record QueryResolutionResult<R>(R resultData) {

    @Override
    public R resultData() {
        Class<?> dataType = resultData.getClass();
        System.out.println("Data Type: " + dataType.getName());
        return resultData;
    }
}

