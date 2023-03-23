# 第一阶段使用 layertools 的 extract 命令将应用程序拆分为多个层  本次构建标记为builder  pivotalservices/jdk8-minimal  adoptopenjdk/openjdk8
FROM pivotalservices/jdk8-minimal as builder
ENV APP_NAME=material-classification
#当前仅流程引擎有包,如: /var/lib/jenkins/workspace/material-classification/jswy-bpm
ENV WORK_DIR=$JENKINS_HOME/workspace/$APP_NAME/jswy-bpm
#临时目录,不指定Spring可能启动报错
ENV TMP_DIR=$JENKINS_HOME/temp

#创建目录
RUN mkdir -p $WORK_DIR
RUN mkdir -p $TMP_DIR

# 指定工作目录
WORKDIR $WORK_DIR
COPY target/*.jar $WORK_DIR/app.jar
#容器对外暴露的9099端口
EXPOSE 9099
ENTRYPOINT ["java","-jar","/app.jar"]