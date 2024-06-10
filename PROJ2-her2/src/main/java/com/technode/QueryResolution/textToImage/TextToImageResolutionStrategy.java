package com.technode.QueryResolution.textToImage;

import com.technode.QueryResolution.QueryResolutionForm;
import com.technode.QueryResolution.QueryResolutionResult;
import com.technode.QueryResolution.QueryResolutionStrategy;

public class TextToImageResolutionStrategy implements QueryResolutionStrategy {
    @Override
    public QueryResolutionResult resolve(QueryResolutionForm queryForm) {
        Object queryData = queryForm.getQueryData();
        String format = (String) queryData;

        if (format.contains("42")) {
            System.out.println("42 gevonden");
            return new QueryResolutionResult(ImagePaths.BEDRIJF42);
        } else if (format.contains("hhs")) {
            return new QueryResolutionResult(ImagePaths.HHS_AULA);
        } else if (format.contains("haagse hogeschool")) {
            return new QueryResolutionResult(ImagePaths.HHS_AULA);
        } else if (format.contains("java")) {
            return new QueryResolutionResult(ImagePaths.JAVA);
        } else if (format.contains("pikachu")) {
            return new QueryResolutionResult(ImagePaths.PIKACHU);
        } else {
            return new QueryResolutionResult(ImagePaths.NOTFOUND);
        }
    }
}
