package gordon.scdemo.zuulgateway.apig.filter.classloader;

public class FilterUpdateWatcher {

    public static void startWatch(FilterLoader filterLoader) {
        new Thread(() -> {
            while(true) {
                if (false) {
                    filterLoader.updateFilters();
                }
                try {
                    Thread.sleep(10_000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
