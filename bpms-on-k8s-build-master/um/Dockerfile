FROM sagcr.azurecr.io/universalmessaging-server:10.15

ENV SAG_HOME=/opt/softwareag
 
LABEL "Image.maintainer"="Aditya_POC"
LABEL "Image.Usage"="POC"

COPY  --chown=1724:1724 ./src ${SAG_HOME}/

RUN chmod -R 744 $SAG_HOME/assets 

