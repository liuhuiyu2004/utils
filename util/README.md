# util 简介
My util, Common tools collection  
工具集合类
# 包说明
|包|说明|  
|---|---|  
|asserts|断言|
|constant.enums|常量枚举|
|exception|异常|
|spring|spring工具|
|web|web工具|
#导入方法
1.将项目maven打jar包
Maven->Util->生命周期->package  
2.在要引用的项目中建立lib文件夹  
3.复制jar包到lib文件夹
4.在pom.xml中  
dependencies 标签中加入
~~~
<dependency>
    <groupId>com.liuhuiyu</groupId>
    <artifactId>util</artifactId>
    <version>1.0-SNAPSHOT</version>
    <scope>system</scope>
    <systemPath>${project.basedir}/lib/util-1.0-SNAPSHOT.jar</systemPath>
</dependency>
~~~
build 标签中加入
~~~
<resources>
    <resource>
        <directory>lib</directory>
        <targetPath>/BOOT-INF/lib/</targetPath>
        <includes>
            <include>**/*.jar</include>
        </includes>
    </resource>
</resources>
~~~
本地仓库安装
mvn install:install-file -Dfile=util-1.0.0.jar -DgroupId=com.liuhuiyu -DartifactId=util -Dversion=1.0-SNAPSHOT -Dpackaging=jar
