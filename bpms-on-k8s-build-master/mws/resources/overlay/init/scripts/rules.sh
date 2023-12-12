#!/bin/sh
attempt_counter_mws=0
max_attempts_mws=500
first_mws_pod_name="dhl-mws-demo-my-project-0"
host_name=$(hostname)

echo "-------------------------------------------------"

echo " hostname is : $host_name "
echo " first_mws_pod_name=$first_mws_pod_name "

echo "-------------------------------------------------"

if [[ "$host_name" != "$first_mws_pod_name" ]];then


echo " This is not the first MWS pod.......Skipping......"

>/sag/mws1015/init/rulesUploadDidnotHappen.log

echo "-------------------------------------------------"

else 

echo "executing on first MWS pod only"

until [ $(curl -s -o /dev/null -w "%{http_code}" http://localhost:8585/wm_rma/rest/content/projects --user "Administrator:IloveBPM@2023") -eq "200" ]; do
    if [ ${attempt_counter_mws} -eq ${max_attempts_mws} ];then
      echo "Max attempts reached , MWS container could not come up"
      exit 1
    fi

    printf '....waiting for MWS container to come up'
    attempt_counter_mws=$(($attempt_counter_mws+1))
    sleep 10
done

echo "MWS server is up. Will upload rule"


echo "-------------------------------------------------"

curl -v -X PUT -T '/sag/mws1015/init/assets/project_MWS_builds/RULES/MyNewRuleTest.jar' http://localhost:8585/wm_rma/rest/raw/project/MyNewRuleTest --user "Administrator:IloveBPM@2023"

>/sag/mws1015/init/rulesUploadHappened.log

echo "-------------------------------------------------"

fi




