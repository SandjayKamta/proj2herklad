package com.technode.QueryResolution;

public class TextToTextFactory implements QueryResolutionStrategyFactory {
    @Override
    public QueryResolutionStrategy createQueryResolutionStrategy() {
        return (QueryResolutionStrategy) new TextToText();
    }
}
