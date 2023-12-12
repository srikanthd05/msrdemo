#!/bin/sh

############################################################## Start of the Script######################################################### 


echo "<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<------ Starting the script execution------>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"
echo -e "\n"
start_timestamp=`date +%s` 
script_start_time=$(date +"%d-%m-%Y_%H-%M-%S") ## get current time.
curernt_time_zone=$(date +%Z) ## get current timezone.
echo "staritng the script execution. script launch time is $script_start_time $curernt_time_zone" ## print current time to console. 
echo -e "\n"


##initialize Base variables
attempt_counter_um=0
max_attempts_counter_for_um=100
desiredNumberOfPods=1
foundMyConnectionFactory="false"
runUMTool="true"
isUMpodRunning="false"
um_svc_port=$UM_SERVICE_PORT
um_svc_name=$UM_SERVICE_NAME

##initialize assets (jms topics , queues and  wM native channels  that needs to be created)
jmsChannelList=($(<$SAG_HOME/assets/jmsChannelList.txt))
jmsTopicList=($(<$SAG_HOME/assets/jmsTopicList.txt))
wmNativeChannelList=($(<$SAG_HOME/assets/wmNativeChannelList.txt))


echo "jmsChannelList found : ${jmsChannelList[*]}"  && echo -e "\n" && echo "jmsTopicList found : ${jmsTopicList[*]}" && echo -e "\n" && echo "wmNativeChannelList found : ${wmNativeChannelList[*]}" && echo -e "\n" && echo "um_svc_name : $UM_SERVICE_NAME"&& echo -e "\n"


## check if UM server is up and running. If not running wait for it to come up for 3*100 = 300 seconds . Chnage the value ax_attempts_counter_for_um variable to configure the max waiting time. 
if [ ${runUMTool} = "true" ];then
echo "....waiting for UM pods to be in READY and RUNNING state...."
until [ $(curl -s -o /dev/null -w "%{http_code}" http://$um_svc_name:$um_svc_port/health/) -eq "200" ]; do
    if [ ${attempt_counter_um} -eq ${max_attempts_counter_for_um} ];then
      echo "Max attempts reached , UM pod could not come up"
      exit 1
    fi
    printf '....waiting for UM pods to be in READY and RUNNING state....'
    attempt_counter_um=$(($attempt_counter_um+1))
    sleep 3
done


if [ $(curl -s -o /dev/null -w "%{http_code}" http://$um_svc_name:$um_svc_port/health/) -eq "200" ];then
isUMpodRunning="true"
echo "isUMpodRunning = $isUMpodRunning"
fi

##Once Um is up, execute the UmTool scripts to create the necessary assets 

echo "-----------executing UMtool instructions-------------------"
echo "----------checking if connection factor already exists---------------------------------"
/opt/softwareag/UniversalMessaging/tools/runner/runUMTool.sh ViewConnectionFactory -rname=nsp://$um_svc_name:$um_svc_port -factoryname=local_um && foundMyConnectionFactory="true"
echo "----------------foundMyConnectionFactory value is : $foundMyConnectionFactory------------------"
echo "-------------------------------------------"
echo -e "\n"

##Create connection factory if it doesn't exists

if [ ${foundMyConnectionFactory} = "false" ];then
echo "----------------since foundMyConnectionFactory value is : $foundMyConnectionFactory, so will now create connection factory $foundMyConnectionFactory ------------------"
/opt/softwareag/UniversalMessaging/tools/runner/runUMTool.sh CreateConnectionFactory -rname=nsp://$um_svc_name:$um_svc_port -connectionurl=nsp://$um_svc_name:$um_svc_port -factoryname=local_um -durabletype=S
fi
echo -e "\n"
##check if JMS queue exists and if doesn't exist , then create accordingly

for j in ${jmsChannelList[@]}
do
myChannel=$j
echo "--------------checking if channel $myChannel exists----------"
foundMyChannel="false"
channelDetails=$(/opt/softwareag/UniversalMessaging/tools/runner/runUMTool.sh ListChannels -rname=nsp://$um_svc_name:$um_svc_port -format=json)
echo $channelDetails | grep $myChannel && foundMyChannel="true"
echo "----------value of foundMyChannel: $foundMyChannel--------------------"
 if [ ${foundMyChannel} = "false" ];then
echo "-------------- Queue $myChannel not found. Will create the channel ----------" 
/opt/softwareag/UniversalMessaging/tools/runner/runUMTool.sh CreateJMSQueue -rname=nsp://$um_svc_name:$um_svc_port -queuename=$myChannel
echo -e "\n"
else
echo "------------------Queue $myChannel already exists. Skipping creation----------------------"
echo -e "\n"
fi
done

 ##check if JMS topic exists and if doesn't exist , then create accordingly

for k in ${jmsTopicList[@]}
do
myChannel=$k
echo "--------------checking if channel $myChannel exists----------"
foundMyChannel="false"
channelDetails=$(/opt/softwareag/UniversalMessaging/tools/runner/runUMTool.sh ListChannels -rname=nsp://$um_svc_name:$um_svc_port -format=json)
echo $channelDetails | grep $myChannel && foundMyChannel="true"
echo "----------value of foundMyChannel: $foundMyChannel--------------------"
 if [ ${foundMyChannel} = "false" ];then
echo "--------------Topic $myChannel not found. Will create the channel ----------" 
/opt/softwareag/UniversalMessaging/tools/runner/runUMTool.sh CreateJMSTopic -rname=nsp://$um_svc_name:$um_svc_port -channelname=$myChannel
echo -e "\n"
else
echo "------------------Topic $myChannel already exists. Skipping creation----------------------"
echo -e "\n"
fi
done

##check if wM native channel exists and if doesn't exist , then create accordingly

for n in ${wmNativeChannelList[@]}
do
myChannel=$n
echo "--------------checking if channel $myChannel exists----------"
foundMyChannel="false"
channelDetails=$(/opt/softwareag/UniversalMessaging/tools/runner/runUMTool.sh ListChannels -rname=nsp://$um_svc_name:$um_svc_port -format=json)
echo $channelDetails | grep $myChannel && foundMyChannel="true"
echo "----------value of foundMyChannel: $foundMyChannel--------------------"
if [ ${foundMyChannel} = "false" ];then
echo "--------------wM native channel $myChannel not found. Will create the channel ----------" 
/opt/softwareag/UniversalMessaging/tools/runner/runUMTool.sh CreateChannel -rname=nsp://$um_svc_name:$um_svc_port -channelname=$myChannel
echo -e "\n"
else
echo "------------------wM native channel $myChannel already exists. Skipping creation----------------------"
echo -e "\n"
fi
done
fi

echo "<<<<<<<<<<<<<<<<<<<<<<-----------Finishing executon of the script ---------------->>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"
echo -e "\n"
script_end_time=$(date +"%d-%m-%Y_%H-%M-%S") ## get current time.
echo "Finished the script execution. script finish time is $script_end_time $curernt_time_zone" ## print current time to console. 
finish_timestamp=`date +%s`
script_runtime=$((finish_timestamp-start_timestamp))
echo -e "\n"
echo "Script execution took $script_runtime seconds." 
echo "----------------------------------------------------Sript execution Done------------------------------------------------------------"

############################################################## End of the Script######################################################### 