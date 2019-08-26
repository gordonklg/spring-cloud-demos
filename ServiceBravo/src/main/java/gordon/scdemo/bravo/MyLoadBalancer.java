package gordon.scdemo.bravo;

import com.netflix.loadbalancer.Server;
import com.netflix.loadbalancer.ZoneAwareLoadBalancer;

public class MyLoadBalancer extends ZoneAwareLoadBalancer {

    @Override
    public Server chooseServer(Object key) {
        System.out.println("In my load balancer");
        return super.chooseServer(key);
    }
}
