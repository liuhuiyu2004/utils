package com.liuhuiyu.util.web;

import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.Query;
import java.lang.management.ManagementFactory;
import java.util.Iterator;
import java.util.Optional;
import java.util.Set;

/**
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2022-09-29 10:50
 */
public class TomcatUtil {
    /**
     * 获取tomcat启动端口
     *
     * @return java.util.Optional<java.lang.Integer>
     * @author LiuHuiYu
     * Created DateTime 2022-09-29 10:51
     */
    public static Optional<Integer> getTomcatPort() {
        MBeanServer beanServer = ManagementFactory.getPlatformMBeanServer();
        try {
            Set<ObjectName> objectNames = beanServer.queryNames(new ObjectName("*:type=Connector,*"), Query.match(Query.attr("protocol"), Query.value("HTTP/1.1")));
            Iterator<ObjectName> iterator = objectNames.iterator();
            if (!iterator.hasNext()) {
                return Optional.empty();
            }
            else {
                return Optional.of(Integer.getInteger(objectNames.iterator().next().getKeyProperty("port")));
            }
        }
        catch (MalformedObjectNameException e) {
            return Optional.empty();
        }
    }
}
