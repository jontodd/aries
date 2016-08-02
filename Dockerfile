FROM centos:centos7.1.1503
RUN useradd -d /home/container_user -m -s /bin/bash container_user

# Install wget, tar, hostname
RUN yum install -y wget tar hostname

# Install Java 8
RUN yum install -y java-1.8.0

RUN mkdir -p /opt/aries
RUN mkdir -p /var/log/aries
RUN chown container_user /var/log/aries

ADD conf/aries.yml /opt/aries/conf/
ADD target/aries*-SNAPSHOT.jar /opt/aries/aries.jar

EXPOSE 8080 8081
USER container_user
CMD java $ARIES_JAVA_ARGS -jar /opt/aries/aries.jar server /opt/aries/conf/aries.yml
