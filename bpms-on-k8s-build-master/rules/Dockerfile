FROM registry-144fd7c1.nip.io/v2/wm1015abe:v1 as build_base

LABEL "Image.maintainer"="Aditya_POC"
LABEL "Image.Usage"="POC"

USER 1724

RUN mkdir -p ${SAG_HOME}/webm_builds ${SAG_HOME}/webm_builds/source_code ${SAG_HOME}/webm_builds/BPM_builds
RUN chown -R 1724:1724 ${SAG_HOME}/webm_builds/

COPY  --chown=1724:1724 ./resources/overlay ${SAG_HOME}/
COPY  --chown=1724:1724 ./resources/overlay/init/config/build.properties ${SAG_HOME}/common/AssetBuildEnvironment/master_build/build.properties
COPY  --chown=1724:1724 ./resources/overlay/init/config/build.xml ${SAG_HOME}/common/AssetBuildEnvironment/master_build/build.xml
COPY  --chown=1724:1724 ./src/business-rules-projects ${SAG_HOME}/webm_builds/source_code/business-rules-projects

RUN chmod +x ${SAG_HOME}/common/AssetBuildEnvironment/bin/build.sh
RUN ${SAG_HOME}/common/AssetBuildEnvironment/bin/build.sh


FROM registry-144fd7c1.nip.io/v2/wm1015bre_base:v1

LABEL "Image.maintainer"="Aditya_POC"
LABEL "Image.Usage"="POC"

ENV SAG_HOME /opt/softwareag
ENV ABE_HOME /opt/webm1015BPM
ENV LANG C.utf8
ENV INSTANCE_NAME default
ENV JAVA_HOME ${SAG_HOME}/jvm/jvm/
ENV JRE_HOME ${SAG_HOME}/jvm/jvm/ 

USER 1724

RUN mkdir -p ${SAG_HOME}/IntegrationServer/instances/default/packages/WmBusinessRules/projects

COPY  --chown=1724:1724 ./src/instance/config ${SAG_HOME}/IntegrationServer/instances/${INSTANCE_NAME}/config
COPY  --chown=1724:1724 ./src/instance/packages ${SAG_HOME}/IntegrationServer/instances/${INSTANCE_NAME}/packages

COPY --chown=1724:1724 --from=build_base ${ABE_HOME}/webm_builds/BPM_builds/RULES/MyNewRuleTest.jar ${SAG_HOME}/IntegrationServer/instances/default/packages/WmBusinessRules/projects/
COPY --chown=1724:1724 --from=build_base ${ABE_HOME}/webm_builds/BPM_builds/RULES/MyNewRuleTest.acdl ${SAG_HOME}/IntegrationServer/instances/default/packages/WmBusinessRules/projects/





