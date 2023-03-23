# 第一阶段使用 layertools 的 extract 命令将应用程序拆分为多个层  本次构建标记为builder  pivotalservices/jdk8-minimal  adoptopenjdk/openjdk8
FROM pivotalservices/jdk8-minimal as builder
ADD target/*.jar /app.jar
VOLUME /tmp
EXPOSE 9099
ENTRYPOINT ["java","-jar","/app.jar"]