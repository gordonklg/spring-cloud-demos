package gordon.scdemo.zuulgateway.apig.filter;


import gordon.scdemo.zuulgateway.apig.filter.impl.ApiAccessPermissionFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 临时方案。
 */
@Slf4j
public class FilterLoader {

    private static List<CoreFilter> filters;

    public static List<CoreFilter> loadFilters() {
        if(filters == null) {
            filters = new ArrayList<>();
            filters.add(new ApiAccessPermissionFilter());
        }
        return filters;
    }
}
