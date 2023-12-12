pipeline {
    agent any 

     environment {
        
        KUBECONFIG = "--kubeconfig=/opt/k8s/.kube/config"
        
       
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

         stage('deploy UM') {
            steps {
                echo "<<<<<<<<<<<<<<<<<<<<<<<<<-------------starting the deployment Process------------->>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"
                  sh """
                  cd ${workspace}
                  echo "<<<------------------Updating the Kubernetes yaml files for rules------------------------------------------------------->>>"
                  echo "-----current myDemoProject-rules-deployment.yml contents shown below------"
                  cat ${workspace}/aks-yaml/rules-my-demo-project/myDemoProject-rules-deployment.yml
                  echo "-------------------------------------------------------------------------"
                  echo "-----Updated myDemoProject-rules-deployment.yml contents shown below------"
                  sed -i 's/replicas: 1/replicas: $numberOfPods/g' ${workspace}/aks-yaml/rules-my-demo-project/myDemoProject-rules-deployment.yml
                  cat ${workspace}/aks-yaml/rules-my-demo-project/myDemoProject-rules-deployment.yml
                  echo "<<<------------------Done updating the Kubernetes yaml files for rules------------------------------------------------------->>>"
                  echo "deployment will have $numberOfPods replicas as requested"
                  echo "-------------------------------------------------------------------------"
                  echo "<<<------------------Applying the Kubernetes yaml files------------------------------------------------------->>>"
                  kubectl apply -f ${workspace}/aks-yaml/rules-my-demo-project/myDemoProject-rules-is-license-configMap.yml ${KUBECONFIG}
                  kubectl apply -f ${workspace}/aks-yaml/rules-my-demo-project/myDemoProject-business-rules-license-configMap.yml ${KUBECONFIG}
                  kubectl apply -f ${workspace}/aks-yaml/rules-my-demo-project/myDemoProject-rules-configMap-settings.yml ${KUBECONFIG}
                  kubectl apply -f ${workspace}/aks-yaml/rules-my-demo-project/myDemoProject-rules-svc-cluster-ip.yml ${KUBECONFIG}
                  kubectl apply -f ${workspace}/aks-yaml/rules-my-demo-project/myDemoProject-rules-deployment.yml ${KUBECONFIG}
                    """     
                echo "<<<<<<<<<<<<<<<<<<<<<<<<<-------------Finished the deployment Process------------->>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"

            }
        }

       
    }
}