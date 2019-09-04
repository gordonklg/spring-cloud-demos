package gordon.scdemo.zuulgateway.filter;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class FilterChain {

    private List<CoreFilter> filters;

    public void doFilter(final CoreFilterContext context) {
        for (CoreFilter filter : filters) {
            filter.doFilter(context);
        }
    }
}
