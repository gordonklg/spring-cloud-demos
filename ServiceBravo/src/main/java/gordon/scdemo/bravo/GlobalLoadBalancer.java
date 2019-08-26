package gordon.scdemo.bravo;

import com.netflix.loadbalancer.Server;
import com.netflix.loadbalancer.ZoneAwareLoadBalancer;

public class GlobalLoadBalancer extends ZoneAwareLoadBalancer {

    @Override
    public Server chooseServer(Object key) {
        System.out.println("In global load balancer");
        return super.chooseServer(key);
    }
}
