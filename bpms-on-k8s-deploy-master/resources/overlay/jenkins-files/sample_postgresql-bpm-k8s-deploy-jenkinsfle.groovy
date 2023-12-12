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

         stage('deploy the PostgreSql image for IS and BPM') {
            steps {
                echo "<<<<<<<<<<<<<<<<<<<<<<<<<-------------starting the deployment Process------------->>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"
                  sh """
                    cd ${workspace}
                    kubectl apply -f ${workspace}/aks-yaml/postgresql-my-demo-project-bpm/postgresql-configmap.yml ${KUBECONFIG}
                    kubectl apply -f ${workspace}/aks-yaml/postgresql-my-demo-project-bpm/postgresql-my-demo-project-clusterip-svc.yml ${KUBECONFIG}
                    kubectl apply -f ${workspace}/aks-yaml/postgresql-my-demo-project-bpm/postgresql-my-demo-project-deployment.yml ${KUBECONFIG}
                     """
                echo "<<<<<<<<<<<<<<<<<<<<<<<<<-------------Finished the deployment Process------------->>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"

            }
        }

       
    }
}