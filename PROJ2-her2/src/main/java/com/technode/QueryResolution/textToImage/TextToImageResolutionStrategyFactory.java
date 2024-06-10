package com.technode.QueryResolution.textToImage;


import com.technode.QueryResolution.QueryResolutionStrategy;
import com.technode.QueryResolution.QueryResolutionStrategyFactory;

public class TextToImageResolutionStrategyFactory implements QueryResolutionStrategyFactory {
    @Override
    public QueryResolutionStrategy createQueryResolutionStrategy() {
        return new TextToImageResolutionStrategy();
    }
}