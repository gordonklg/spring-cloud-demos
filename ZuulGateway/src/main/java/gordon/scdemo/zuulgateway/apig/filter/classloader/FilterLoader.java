package gordon.scdemo.zuulgateway.apig.filter.classloader;

import gordon.scdemo.zuulgateway.apig.filter.CoreFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileFilter;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class FilterLoader {

    private List<CoreFilter> cachedFilters;

    public FilterLoader() {
        updateFilters();
        FilterUpdateWatcher.startWatch(this);
    }

    public List<CoreFilter> loadFilters() {
        return cachedFilters;
    }

    public void updateFilters() {
        ResourceFolderClassLoader cl = new ResourceFolderClassLoader();
        URL url = ResourceFolderClassLoader.class.getResource("/filters");
        cl.setRoot(url.getPath());
        List<Class<CoreFilter>> filterClassList = new ArrayList<>();
        loadAllClass("", new File(url.getPath()), cl, filterClassList);

        List<CoreFilter> filters = new ArrayList<>();
        for (Class<CoreFilter> clazz : filterClassList) {
            try {
                filters.add(clazz.newInstance());
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        log.info("updated filter list: " + filters);
        cachedFilters = filters;
    }

    private void loadAllClass(String packageName, File curFolder, ClassLoader cl, List<Class<CoreFilter>> filterClassList) {

        File[] files = curFolder.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.isDirectory() || pathname.getName().endsWith(".class");
            }
        });

        for (File file : files) {
            String fileName = file.getName();
            if (file.isDirectory()) {
                String realPackageName = "";
                if (!packageName.isEmpty()) {
                    realPackageName = packageName + "." + fileName;
                } else {
                    realPackageName = fileName;
                }
                loadAllClass(realPackageName, file, cl, filterClassList);
            } else {
                String className = packageName + "." + fileName.replace(".class", "");
                try {
                    Class clazz = cl.loadClass(className);
                    // FIXME: 要求显式实现 CoreFilter 接口
                    Type[] interfaces = clazz.getGenericInterfaces();
                    for (int i = 0; i < interfaces.length; i++) {
                        if (interfaces[i].equals(CoreFilter.class)) {
                            filterClassList.add(clazz);
                            break;
                        }
                    }
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        FilterLoader loader = new FilterLoader();
        loader.updateFilters();
    }
}
