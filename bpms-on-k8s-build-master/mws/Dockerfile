FROM registry-144fd7c1.nip.io/v2/wm1015abe:v1 as build_base

LABEL "Image.maintainer"="Aditya_POC"
LABEL "Image.Usage"="POC"

USER 1724

RUN mkdir -p ${SAG_HOME}/webm_builds ${SAG_HOME}/webm_builds/source_code ${SAG_HOME}/webm_builds/MWS_builds
RUN chown -R 1724:1724 ${SAG_HOME}/webm_builds/

COPY  --chown=1724:1724 ./resources/overlay ${SAG_HOME}/
COPY  --chown=1724:1724 ./resources/overlay/init/config/build.properties ${SAG_HOME}/common/AssetBuildEnvironment/master_build/build.properties
COPY  --chown=1724:1724 ./resources/overlay/init/config/build.xml ${SAG_HOME}/common/AssetBuildEnvironment/master_build/build.xml
COPY  --chown=1724:1724 ./src ${SAG_HOME}/webm_builds/source_code

RUN chmod +x ${SAG_HOME}/common/AssetBuildEnvironment/bin/build.sh
RUN ${SAG_HOME}/common/AssetBuildEnvironment/bin/build.sh



FROM registry-144fd7c1.nip.io/v2/mws-image:v1
 
LABEL "Image.maintainer"="Aditya_POC"
LABEL "Image.Usage"="POC"

ENV ABE_HOME /opt/webm1015BPM
ENV SAG_HOME /sag/mws1015

ENV assets_cfg_CONFIGS_DIR=$CONFIGS_DIR/assets_cfg \
    cluster_cfg_CONFIGS_DIR=$CONFIGS_DIR/cluster_cfg  \
    instance_cfg_CONFIGS_DIR=$CONFIGS_DIR/instance_cfg \
    jvm_cfg_CONFIGS_DIR=$CONFIGS_DIR/jvm_cfg \
    profile_cfg_CONFIGS_DIR=$CONFIGS_DIR/profile_cfg 

RUN mkdir -p $assets_cfg_CONFIGS_DIR $cluster_cfg_CONFIGS_DIR $instance_cfg_CONFIGS_DIR $jvm_cfg_CONFIGS_DIR $profile_cfg_CONFIGS_DIR && chown 1724:1724 $assets_cfg_CONFIGS_DIR && chown 1724:1724 $cluster_cfg_CONFIGS_DIR && chown 1724:1724 $instance_cfg_CONFIGS_DIR && chown 1724:1724 $jvm_cfg_CONFIGS_DIR && chown 1724:1724 $profile_cfg_CONFIGS_DIR

COPY  --chown=1724:1724 ./resources/overlay ${SAG_HOME}/

RUN chmod -R 744 $SAG_HOME/init/scripts 

COPY --chown=1724:1724 --from=build_base $ABE_HOME/webm_builds/MWS_builds/MWS/MySecondProcessTasks.war $SAG_HOME/MWS/volumes/apps/