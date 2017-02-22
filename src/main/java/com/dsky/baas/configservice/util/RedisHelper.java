package com.dsky.baas.configservice.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

public class RedisHelper {

    private String projectPrefix;

    private static String KEY_SPILT = ":";

    public String getProjectPrefix() {
        return projectPrefix;
    }

    public void setProjectPrefix(String projectPrefix) {
        this.projectPrefix = projectPrefix;
    }

    public String buildCacheKey(Object... fields){
        List keyFields = new ArrayList();
        keyFields.add(this.projectPrefix);
        for(Object field : fields){
            keyFields.add(field);
        }

        return StringUtils.join(keyFields,KEY_SPILT);
    }
    
 
}
