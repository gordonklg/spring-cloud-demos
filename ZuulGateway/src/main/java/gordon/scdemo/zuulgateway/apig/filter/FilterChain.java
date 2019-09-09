package gordon.scdemo.zuulgateway.apig.filter;

import gordon.scdemo.zuulgateway.apig.filter.classloader.FilterLoader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class FilterChain {

    @Autowired
    private FilterLoader filterLoader;

    public void doFilter(final CoreFilterContext context) {
        List<CoreFilter> filters = filterLoader.loadFilters();
        for (CoreFilter filter : filters) {
            filter.doFilter(context);
        }
    }
}
