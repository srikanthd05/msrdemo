FROM redhat/ubi8

LABEL "Image.maintainer"="Aditya_POC"
LABEL "Image.Usage"="POC-demo"

ENV SAG_HOME /opt/webm1015BPM
ENV LANG C.utf8
RUN yum install -y procps && yum clean all
RUN groupadd -g 1724 sagadmin; useradd -u 1724 -m -g 1724 -d ${SAG_HOME} sagadmin
USER 1724
RUN mkdir -p ${SAG_HOME}/jvm ${SAG_HOME}/common ${SAG_HOME}/install

ENV JAVA_HOME ${SAG_HOME}/jvm/jvm/
ENV JRE_HOME ${SAG_HOME}/jvm/jvm/

COPY --chown=1724:1724 ./jvm/jvm/ ${SAG_HOME}/jvm/jvm/
COPY --chown=1724:1724 ./install/jars/ ${SAG_HOME}/install/jars/
COPY --chown=1724:1724 ./install/profile/ ${SAG_HOME}/install/profile/
COPY --chown=1724:1724 ./install/products/ ${SAG_HOME}/install/products/

COPY --chown=1724:1724 ./Licenses/ ${SAG_HOME}/Licenses/
COPY --chown=1724:1724 ./common/bin/ ${SAG_HOME}/common/bin/
COPY --chown=1724:1724 ./common/conf/ ${SAG_HOME}/common/conf/
COPY --chown=1724:1724 ./common/lib/ ${SAG_HOME}/common/lib/
COPY --chown=1724:1724 ./common/runtime/ ${SAG_HOME}/common/runtime/
COPY --chown=1724:1724 ./common/AssetBuildEnvironment/ ${SAG_HOME}/common/AssetBuildEnvironment/
COPY --chown=1724:1724 ./install/bin/setenv.sh ${SAG_HOME}/install/bin/setenv.sh