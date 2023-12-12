pipeline {
    agent any 

     environment {
        
        KUBECONFIG = "--kubeconfig=/opt/k8s/.kube/config"
        mws_pod_name_template = "mws-demo-my-project"
        namespace="bpms-load-test"
       
       
    }

    stages {
        stage('code checkout') {
            steps {
                echo "<<<<<<<<<<<<<<<<<<<<<<<<<-------------checking out the code from repository------------->>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"
                    checkout scmGit(branches: [[name: '*/master']], extensions: [], userRemoteConfigs: [[credentialsId: 'adi_sag_git_hub', url: 'https://github.softwareag.com/adit/bpms-on-k8s-deploy.git']])
                  sh """
                     ls -lrt ${workspace}              	
                     """
            }
        }

         stage('deploy the MWS image') {
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
                    echo "<<<------------------Done updating the Kubernetes yaml files for MWS------------------------------------------------------->>>"
                    echo "deployment will have $numberOfPods replicas as requested"
                    echo "-------------------------------------------------------------------------"
                    kubectl apply -f ${workspace}/aks-yaml/mws-my-demo-project/myDemoProject-mws-configMap.yml ${KUBECONFIG}
                     kubectl apply -f ${workspace}/aks-yaml/mws-my-demo-project/myDemoProject-mws-configMap-jvm-settings.yml ${KUBECONFIG}
                    kubectl apply -f ${workspace}/aks-yaml/mws-my-demo-project/myDemoProject-mws-svc.yml ${KUBECONFIG}
                    kubectl apply -f ${workspace}/aks-yaml/mws-my-demo-project/myDemoProject-mws-stateful-set.yml ${KUBECONFIG}
                     """
                echo "<<<<<<<<<<<<<<<<<<<<<<<<<-------------Finished the deployment Process------------->>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"

            }
        }

       
    }
}