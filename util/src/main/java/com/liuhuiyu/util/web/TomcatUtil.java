package com.liuhuiyu.util.web;

import javax.management.*;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
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
    public static Optional<Integer> getTomcatPort1() {
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
        catch (Exception e) {
            return Optional.empty();
        }
    }
    /**
     * 获取tomcat启动端口
     *
     * @return java.util.Optional<java.lang.Integer>
     * @author LiuHuiYu
     * Created DateTime 2022-09-29 10:51
     */
    public static Optional<Integer> getTomcatPort2() {
        MBeanServer mBeanServer = null;
        ArrayList<MBeanServer> mBeanServers = MBeanServerFactory.findMBeanServer(null);
        if (mBeanServers.size() > 0) {
            mBeanServer = mBeanServers.get(0);
        }
        if (mBeanServer == null) {
            return Optional.empty();
        }
        Set<ObjectName> objectNames = null;
        try {
            objectNames = mBeanServer.queryNames(new ObjectName("Catalina:type=Connector,*"), null);
            if (objectNames == null || objectNames.size() <= 0) {
                throw new IllegalStateException("没有发现JVM中关联的MBeanServer : " + mBeanServer.getDefaultDomain() + " 中的对象名称.");
            }
            for (ObjectName objectName : objectNames) {
                String protocol = (String) mBeanServer.getAttribute(objectName, "protocol");
                if ("HTTP/1.1".equals(protocol)
                        || "org.apache.coyote.http11.Http11Nio2Protocol".equals(protocol)
                        || "org.apache.coyote.http11.Http11NioProtocol".equals(protocol)) {
                    return Optional.of((Integer) mBeanServer.getAttribute(objectName, "port"));
                }
            }
        }
        catch (Exception e) {
            return Optional.empty();
        }
        return Optional.empty();
    }
    /**
     * 获取tomcat启动端口
     *
     * @return java.util.Optional<java.lang.Integer>
     * @author LiuHuiYu
     * Created DateTime 2022-09-29 10:51
     */
    public static Optional<Integer> getTomcatPort(){
        final Optional<Integer> tomcatPort1 = getTomcatPort1();
        if(tomcatPort1.isPresent()){
            return tomcatPort1;
        }else{
            return getTomcatPort2();
        }
    }
}
