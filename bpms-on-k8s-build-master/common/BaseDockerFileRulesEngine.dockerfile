FROM redhat/ubi8

LABEL "Image.maintainer"="Aditya_POC"
LABEL "Image.Usage"="POC"

ENV SAG_HOME /opt/softwareag
ENV LANG C.utf8
ENV INSTANCE_NAME default
#RUN yum install -y wget && yum clean all
RUN yum install -y procps && yum clean all
RUN groupadd -g 1724 sagadmin; useradd -u 1724 -m -g 1724 -d ${SAG_HOME} sagadmin
USER 1724
RUN mkdir -p ${SAG_HOME}/jvm ${SAG_HOME}/common ${SAG_HOME}/IntegrationServer ${SAG_HOME}/IntegrationServer/instances ${SAG_HOME}/IntegrationServer/instances/${INSTANCE_NAME} ${SAG_HOME}/IntegrationServer/instances/${INSTANCE_NAME}/packages ${SAG_HOME}/IntegrationServer/instances/${INSTANCE_NAME}/cacheStore ${SAG_HOME}/IntegrationServer/instances/${INSTANCE_NAME}/db ${SAG_HOME}/profiles ${SAG_HOME}/install

ENV JAVA_HOME ${SAG_HOME}/jvm/jvm/
ENV JRE_HOME ${SAG_HOME}/jvm/jvm/

COPY --chown=1724:1724 ./jvm/jvm/ ${SAG_HOME}/jvm/jvm/
COPY --chown=1724:1724 ./install/jars/ ${SAG_HOME}/install/jars/
COPY --chown=1724:1724 ./install/profile/ ${SAG_HOME}/install/profile/
COPY --chown=1724:1724 ./install/products/ ${SAG_HOME}/install/products/
COPY --chown=1724:1724 ./Licenses/ ${SAG_HOME}/Licenses/


COPY --chown=1724:1724 ./common/bin/ ${SAG_HOME}/common/bin/
COPY --chown=1724:1724 ./common/conf/ ${SAG_HOME}/common/conf/
COPY --chown=1724:1724 ./common/db/ ${SAG_HOME}/common/db/
COPY --chown=1724:1724 ./common/DigitalEventServices/ ${SAG_HOME}/common/DigitalEventServices/
COPY --chown=1724:1724 ./common/EventTypeStore/ ${SAG_HOME}/common/EventTypeStore/
COPY --chown=1724:1724 ./common/metering/ ${SAG_HOME}/common/metering/
COPY --chown=1724:1724 ./common/lib/ ${SAG_HOME}/common/lib/
COPY --chown=1724:1724 ./common/runtime/ ${SAG_HOME}/common/runtime/

COPY --chown=1724:1724 ./WS-Stack/ ${SAG_HOME}/WS-Stack/

COPY --chown=1724:1724 ./IntegrationServer/bin/ ${SAG_HOME}/IntegrationServer/bin/
COPY --chown=1724:1724 ./IntegrationServer/lib/ ${SAG_HOME}/IntegrationServer/lib/
COPY --chown=1724:1724 ./IntegrationServer/updates/ ${SAG_HOME}/IntegrationServer/updates/
COPY --chown=1724:1724 ./IntegrationServer/docker/ ${SAG_HOME}/IntegrationServer/docker/
COPY --chown=1724:1724 ./IntegrationServer/features/ ${SAG_HOME}/IntegrationServer/features/

COPY --chown=1724:1724 ./IntegrationServer/.tc.custom.log4j2.properties ${SAG_HOME}/IntegrationServer/.tc.custom.log4j2.properties

COPY --chown=1724:1724 ./IntegrationServer/instances/${INSTANCE_NAME}/web/ ${SAG_HOME}/IntegrationServer/instances/${INSTANCE_NAME}/web/
COPY --chown=1724:1724 ./IntegrationServer/instances/${INSTANCE_NAME}/updates/ ${SAG_HOME}/IntegrationServer/instances/${INSTANCE_NAME}/updates/
COPY --chown=1724:1724 ./IntegrationServer/instances/${INSTANCE_NAME}/replicate/ ${SAG_HOME}/IntegrationServer/instances/${INSTANCE_NAME}/replicate/
COPY --chown=1724:1724 ./IntegrationServer/instances/${INSTANCE_NAME}/lib/ ${SAG_HOME}/IntegrationServer/instances/${INSTANCE_NAME}/lib/
COPY --chown=1724:1724 ./IntegrationServer/instances/${INSTANCE_NAME}/bin/ ${SAG_HOME}/IntegrationServer/instances/${INSTANCE_NAME}/bin/
COPY --chown=1724:1724 ./IntegrationServer/instances/${INSTANCE_NAME}/db/ ${SAG_HOME}/IntegrationServer/instances/${INSTANCE_NAME}/db/
COPY --chown=1724:1724 ./IntegrationServer/instances/${INSTANCE_NAME}/config/ ${SAG_HOME}/IntegrationServer/instances/${INSTANCE_NAME}/config/

COPY --chown=1724:1724 ./IntegrationServer/instances/${INSTANCE_NAME}/packages/Default/ ${SAG_HOME}/IntegrationServer/instances/${INSTANCE_NAME}/packages/Default/
COPY --chown=1724:1724 ./IntegrationServer/instances/${INSTANCE_NAME}/packages/WmART/ ${SAG_HOME}/IntegrationServer/instances/${INSTANCE_NAME}/packages/WmART/
COPY --chown=1724:1724 ./IntegrationServer/instances/${INSTANCE_NAME}/packages/WmARTExtDC/ ${SAG_HOME}/IntegrationServer/instances/${INSTANCE_NAME}/packages/WmARTExtDC/
COPY --chown=1724:1724 ./IntegrationServer/instances/${INSTANCE_NAME}/packages/WmAdmin/ ${SAG_HOME}/IntegrationServer/instances/${INSTANCE_NAME}/packages/WmAdmin/
COPY --chown=1724:1724 ./IntegrationServer/instances/${INSTANCE_NAME}/packages/WmCloud/ ${SAG_HOME}/IntegrationServer/instances/${INSTANCE_NAME}/packages/WmCloud/
COPY --chown=1724:1724 ./IntegrationServer/instances/${INSTANCE_NAME}/packages/WmFlatFile/ ${SAG_HOME}/IntegrationServer/instances/${INSTANCE_NAME}/packages/WmFlatFile/
COPY --chown=1724:1724 ./IntegrationServer/instances/${INSTANCE_NAME}/packages/WmISExtDC/ ${SAG_HOME}/IntegrationServer/instances/${INSTANCE_NAME}/packages/WmISExtDC/
COPY --chown=1724:1724 ./IntegrationServer/instances/${INSTANCE_NAME}/packages/WmJSONAPI/ ${SAG_HOME}/IntegrationServer/instances/${INSTANCE_NAME}/packages/WmJSONAPI/
COPY --chown=1724:1724 ./IntegrationServer/instances/${INSTANCE_NAME}/packages/WmMonitor/ ${SAG_HOME}/IntegrationServer/instances/${INSTANCE_NAME}/packages/WmMonitor/
COPY --chown=1724:1724 ./IntegrationServer/instances/${INSTANCE_NAME}/packages/WmPublic/ ${SAG_HOME}/IntegrationServer/instances/${INSTANCE_NAME}/packages/WmPublic/
COPY --chown=1724:1724 ./IntegrationServer/instances/${INSTANCE_NAME}/packages/WmRoot/ ${SAG_HOME}/IntegrationServer/instances/${INSTANCE_NAME}/packages/WmRoot/
COPY --chown=1724:1724 ./IntegrationServer/instances/${INSTANCE_NAME}/packages/WmXSLT/ ${SAG_HOME}/IntegrationServer/instances/${INSTANCE_NAME}/packages/WmXSLT/
#COPY --chown=1724:1724 ./IntegrationServer/instances/${INSTANCE_NAME}/packages/WmTaskEngine/ ${SAG_HOME}/IntegrationServer/instances/${INSTANCE_NAME}/packages/WmTaskEngine/
COPY --chown=1724:1724 ./IntegrationServer/instances/${INSTANCE_NAME}/packages/WmWin32/ ${SAG_HOME}/IntegrationServer/instances/${INSTANCE_NAME}/packages/WmWin32/
#COPY --chown=1724:1724 ./IntegrationServer/instances/${INSTANCE_NAME}/packages/WmCloudStreamsDeployer/ ${SAG_HOME}/IntegrationServer/instances/${INSTANCE_NAME}/packages/WmCloudStreamsDeployer/
#COPY --chown=1724:1724 ./IntegrationServer/instances/${INSTANCE_NAME}/packages/WmNUMDeployer/ ${SAG_HOME}/IntegrationServer/instances/${INSTANCE_NAME}/packages/WmNUMDeployer/
#COPY --chown=1724:1724 ./IntegrationServer/instances/${INSTANCE_NAME}/packages/WmPRT/ ${SAG_HOME}/IntegrationServer/instances/${INSTANCE_NAME}/packages/WmPRT/
#COPY --chown=1724:1724 ./IntegrationServer/instances/${INSTANCE_NAME}/packages/WmCDS/ ${SAG_HOME}/IntegrationServer/instances/${INSTANCE_NAME}/packages/WmCDS/
#COPY --chown=1724:1724 ./IntegrationServer/instances/${INSTANCE_NAME}/packages/WmTaskAssets/ ${SAG_HOME}/IntegrationServer/instances/${INSTANCE_NAME}/packages/WmTaskAssets/
#COPY --chown=1724:1724 ./IntegrationServer/instances/${INSTANCE_NAME}/packages/WmBrokerDeployer/ ${SAG_HOME}/IntegrationServer/instances/${INSTANCE_NAME}/packages/WmBrokerDeployer/
#COPY --chown=1724:1724 ./IntegrationServer/instances/${INSTANCE_NAME}/packages/WmTaskClient/ ${SAG_HOME}/IntegrationServer/instances/${INSTANCE_NAME}/packages/WmTaskClient/
#COPY --chown=1724:1724 ./IntegrationServer/instances/${INSTANCE_NAME}/packages/WmAgileAppsDeployer/ ${SAG_HOME}/IntegrationServer/instances/${INSTANCE_NAME}/packages/WmAgileAppsDeployer/
#COPY --chown=1724:1724 ./IntegrationServer/instances/${INSTANCE_NAME}/packages/WmDeployer/ ${SAG_HOME}/IntegrationServer/instances/${INSTANCE_NAME}/packages/WmDeployer/
#COPY --chown=1724:1724 ./IntegrationServer/instances/${INSTANCE_NAME}/packages/WmDesigner/ ${SAG_HOME}/IntegrationServer/instances/${INSTANCE_NAME}/packages/WmDesigner/
COPY --chown=1724:1724 ./IntegrationServer/instances/${INSTANCE_NAME}/packages/WmBusinessRules/ ${SAG_HOME}/IntegrationServer/instances/${INSTANCE_NAME}/packages/WmBusinessRules/
#COPY --chown=1724:1724 ./IntegrationServer/instances/${INSTANCE_NAME}/packages/WmAS400/ ${SAG_HOME}/IntegrationServer/instances/${INSTANCE_NAME}/packages/WmAS400/

COPY --chown=1724:1724 ./IntegrationServer/instances/is_instance.xml ${SAG_HOME}/IntegrationServer/instances/is_instance.xml
COPY --chown=1724:1724 ./profiles/IS_${INSTANCE_NAME}/configuration/custom_wrapper.conf ${SAG_HOME}/IntegrationServer/instances/custom_wrapper.conf.template

#RUN mkdir -p ${SAG_HOME}/IntegrationServer/wpm/ &&\
#        wget -q -O - https://techcommunity.softwareag.com/wpm/download/wpm.tar | tar -xzf - -C /opt/softwareag/IntegrationServer/wpm/ &&\
#        chmod +x /opt/softwareag/IntegrationServer/wpm/bin/wpm.sh &&\
#        /opt/softwareag/IntegrationServer/wpm/bin/wpm.sh install YOUR_PACKAGE_NAME -u YOUR_GITHUB_USERNAME -p CREDENTIAL -r REPO_URL &&\
#        rm -rf ${SAG_HOME}/IntegrationServer/wpm/; rm -f ${SAG_HOME}/wpm.tar

COPY --chown=1724:1724 ./IntegrationServer/instances/is_instance.sh ${SAG_HOME}/IntegrationServer/instances/is_instance.sh
RUN rm /opt/softwareag/IntegrationServer/instances/${INSTANCE_NAME}/config/repository4.cnf; cd /opt/softwareag/IntegrationServer/docker; ./is_container.sh updateDockerConfigFiles -Dinstance.name=${INSTANCE_NAME} -Ddocker.isHomeDir=${SAG_HOME}/IntegrationServer/instances/${INSTANCE_NAME} -Ddocker.rootDir=${SAG_HOME}; cd /opt/softwareag/IntegrationServer/instances; ./is_instance.sh create-osgi-profile -Dinstance.name=${INSTANCE_NAME}
COPY --chown=1724:1724 ./profiles/IS_${INSTANCE_NAME}/configuration/org.eclipse.equinox.simpleconfigurator/bundles.info.fixes ${SAG_HOME}/profiles/IS_${INSTANCE_NAME}/configuration/org.eclipse.equinox.simpleconfigurator/bundles.info
COPY --chown=1724:1724 ./profiles/IS_${INSTANCE_NAME}/configuration/security/ ${SAG_HOME}/profiles/IS_${INSTANCE_NAME}/configuration/security/
COPY --chown=1724:1724 ./profiles/IS_${INSTANCE_NAME}/configuration/event/routing/services/UniversalMessaging/service-UniversalMessaging.json ${SAG_HOME}/profiles/IS_${INSTANCE_NAME}/configuration/event/routing/services/UniversalMessaging/service-UniversalMessaging.json
COPY --chown=1724:1724 ./profiles/IS_${INSTANCE_NAME}/configuration/DigitalEventServices/services/UniversalMessaging/service-UniversalMessaging.json ${SAG_HOME}/profiles/IS_${INSTANCE_NAME}/configuration/DigitalEventServices/services/UniversalMessaging/service-UniversalMessaging.json


EXPOSE 5555
EXPOSE 9999
EXPOSE 5543

ENTRYPOINT ["/opt/softwareag/IntegrationServer/bin/startContainer.sh"]