# 第一阶段使用 layertools 的 extract 命令将应用程序拆分为多个层  本次构建标记为builder  pivotalservices/jdk8-minimal  adoptopenjdk/openjdk8
FROM pivotalservices/jdk8-minimal as builder
ENV APP_NAME=material-classification
# 指定工作目录,当前仅流程引擎有包,如: /var/lib/jenkins/workspace/material-classification/jswy-bpm
ENV WORK_DIR=$JENKINS_HOME/workspace/$APP_NAME/jswy-bpm

WORKDIR $WORK_DIR
COPY target/$APP_NAME.jar $WORK_DIR/app.jar
#容器对外暴露的9099端口
EXPOSE 9099
ENTRYPOINT ["java","-jar","/app.jar"]