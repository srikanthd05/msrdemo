pipeline {
    agent any 

     environment {
        
        KUBECONFIG = "--kubeconfig=/opt/k8s/.kube/config"
        mws_pod_name_template = "dhl-mws-demo-my-project"
       
       
    }

    stages {
        stage('code checkout') {
            steps {
                echo "<<<<<<<<<<<<<<<<<<<<<<<<<-------------checking out the code from repository------------->>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"
                checkout scmGit(branches: [[name: '*/main']], extensions: [], userRemoteConfigs: [[credentialsId: 'adigit', url: 'https://github.com/adihere2402/my-demo-project-k8s-deploy.git']])
                  sh """
                     ls -lrt ${workspace}              	
                     """
            }
        }

         stage('deploy the BPM image') {
            steps {
                echo "<<<<<<<<<<<<<<<<<<<<<<<<<-------------starting the deployment Process------------->>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"
                  sh """
                    cd ${workspace}
                    echo "<<<------------------Updating the Kubernetes yaml files for MWS------------------------------------------------------->>>"
                    echo "-----current myDemoProject-mws-stateful-set.yml contents shown below------"
                    cat ./aks-yaml/mws-my-demo-project/myDemoProject-mws-stateful-set.yml
                    echo "-------------------------------------------------------------------------"
                    echo "-----Updated myDemoProject-mws-stateful-set.yml contents shown below------"
                    sed -i 's/replicas: 1/replicas: $numberOfPods/g' ./aks-yaml/mws-my-demo-project/myDemoProject-mws-stateful-set.yml
                    cat ./aks-yaml/mws-my-demo-project/myDemoProject-mws-stateful-set.yml
                    echo "<<<------------------Done updating the Kubernetes yaml files for BPM------------------------------------------------------->>>"
                    echo "deployment will have $numberOfPods replicas as requested"
                    echo "-------------------------------------------------------------------------"
                    kubectl apply -f ${workspace}/aks-yaml/mws-my-demo-project/myDemoProject-mws-configMap.yml ${KUBECONFIG}
                    kubectl apply -f ${workspace}/aks-yaml/mws-my-demo-project/myDemoProject-mws-svc.yml ${KUBECONFIG}
                    kubectl apply -f ${workspace}/aks-yaml/mws-my-demo-project/myDemoProject-mws-stateful-set.yml ${KUBECONFIG}
                    kubectl ${KUBECONFIG} exec ${mws_pod_name_template}-0 -- /bin/bash -c "/sag/mws1015/init/scripts/rules.sh" 
                     """
                echo "<<<<<<<<<<<<<<<<<<<<<<<<<-------------Finished the deployment Process------------->>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"

            }
        }

       
    }
}