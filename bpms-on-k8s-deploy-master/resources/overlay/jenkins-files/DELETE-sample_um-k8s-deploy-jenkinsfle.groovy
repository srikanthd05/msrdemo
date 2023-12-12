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
                    kubectl delete -f ${workspace}/aks-yaml/um-my-demo-project/myDemoProject-um-deployment.yml ${KUBECONFIG}
                    kubectl delete -f ${workspace}/aks-yaml/um-my-demo-project/myDemoProject-um-configMap.yml ${KUBECONFIG}
                    kubectl delete -f ${workspace}/aks-yaml/um-my-demo-project/myDemoProject-um-clusterIP-svc.yml ${KUBECONFIG}
                    kubectl delete -f ${workspace}/aks-yaml/um-my-demo-project/myDemoProject-um-data-vol-pvc.yml ${KUBECONFIG}
                    kubectl delete -f ${workspace}/aks-yaml/um-my-demo-project/myDemoProject-um-log-vol-pvc.yml ${KUBECONFIG}
                echo "<<<<<<<<<<<<<<<<<<<<<<<<<-------------Finished the deployment Process------------->>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"

            }
        }

       
    }
}