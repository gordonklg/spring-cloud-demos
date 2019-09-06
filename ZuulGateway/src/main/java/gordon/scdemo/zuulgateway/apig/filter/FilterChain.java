package gordon.scdemo.zuulgateway.apig.filter;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class FilterChain {

    private List<CoreFilter> filters;

    public FilterChain() {
        filters = FilterLoader.loadFilters();
    }

    public void doFilter(final CoreFilterContext context) {
        for (CoreFilter filter : filters) {
            filter.doFilter(context);
        }
    }
}
