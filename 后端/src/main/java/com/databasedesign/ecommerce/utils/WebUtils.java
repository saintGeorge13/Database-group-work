package com.databasedesign.ecommerce.utils;

import javax.servlet.http.HttpServletRequest;

public class WebUtils {

    public static String getBaseURL(HttpServletRequest request){
        String baseURL = request.getScheme() + "://" + request.getServerName() + ":" +
                request.getServerPort() + request.getContextPath();
        return baseURL;
    }
}
