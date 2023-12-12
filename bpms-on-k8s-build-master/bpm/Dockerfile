#FROM registry-144fd7c1.nip.io/v2/wm1015bpm:v1
FROM registry-144fd7c1.nip.io/v2/wm1015bpm_re_fix:v1

LABEL "Image.maintainer"="Aditya_POC"
LABEL "Image.Usage"="POC"

USER 1724

COPY  --chown=1724:1724 ./src/instance/config ${SAG_HOME}/IntegrationServer/instances/${INSTANCE_NAME}/config
COPY  --chown=1724:1724 ./src/instance/packages ${SAG_HOME}/IntegrationServer/instances/${INSTANCE_NAME}/packages




