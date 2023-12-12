# msrdemo
# Running webMethods BPMS on Kubernetes

# 1. Introduction

Software AG customers have been using the webMethods BPMS suite in an on-premises environment successfully for many years. Now, a lot of customers are moving towards the modernization of their current IT landscape and, hence, are looking towards the latest technologies like containerization, cloud (Azure, AWS, private cloud), kubernetes, etc. to achieve benefits like agility, scalability, and many more. This document outlines the different concepts involved in running the webMethods BPMS suite in containers and subsequently running it in on an open source container orchestrator like Kubernetes or its conformant derivatives listed at https://www.cncf.io/certification/software-conformance/ . This document aims to serve as a kick-start guide for people who want to run wM BPMS on containers and Kubernetes platforms (for example, on the Azure Kubernetes service). It also assumes that the reader of this document is familiar with webMethods BPMS technology,and how it works in general. A basic knowledge of containerization tools (like docker) and container orchestration tools (like kubernetes) is expected as well. 

## 1.1 What it means to run something in Kubernetes?

Running something as a container can be manily divided into two main stages. The first stage is creating a container image (docker image), and the second stage is running that image as containers (pods) in Kubernetes. So, basically, we will cover these two stages in the coming sections. Before we do that, we need to understand some basic concepts, that will drive our thought process in running wM BPMS on Kubernetes. 

## 1.2 Prerequisites

Basic knowledge of below concepts is necessary and helpful to understand the sections coming in future. 

1. Containerization concepts : Any CRE will do for e.g.: docker CRE, docker File , container registry etc. 
2. Kubernetes concepts : pods, services,  Deployment, stateful sets, persistent volumes, ingress , networking in Kubernetes etc.
3. wM BPMS basic concepts : wm ABE, rules, tasks, process engine, um , mws. 

## 1.3 System requirements 

Please refer to official software AG documentation to see the system requirements. For this documentation purpose, below is what is used:

1. wM 10.15 version of product suite. For example, 10.15 UM, 10.15 MWS , 10.15 Process Engine and Rules Engine.
2. Kubernetes version 1.25.11 is used. Azure Kubernetes services (AKS) is the kubernetes provider platform. 
3. Kubernetes workeer nodes were running RHEL-8 OS. 
4. Azure Database for PostgreSQL flexible server (version 14.8) was used as the external database. 
5. Red hat's ubi8 image has been used the base OS image. 

# 2. Different webMethods components involved in wM BPMS solution

As we know, there is no single product (runtime) that we call the wM BPMS solution. In fact, the wM BPMS solution mainly consists of these Software AG products, which work together to become a BPMS solution. These products are listed below:

1. Process Engine (Running on IS or MSR)
2. Rules Engine (Running on IS or MSR)
3. Task Engine (Running on IS or MWS; MSR doesn't support IS task engines as of now)
4. MWS (for running tasks and BPM monitoring)
5. Universal Messaging or WebMethods Broker (Universal Messaging is recommended)

## Basic Runtime Components in wM BPMS solution

![BASIC-RUNTIME-COMPONENTS](https://media.github.softwareag.com/user/3204/files/4f623061-17fd-489b-88a2-e7eba58ba028)

                                                  Fig: Basic runtime components in wM BPMS solution


## Basic Runtime Components : Architecture Breakdown into Microservices


![BASIC-RUNTIME-COMPONENTS-into-microservices-arch](https://media.github.softwareag.com/user/3204/files/510bf85c-ed0e-4dd4-a2d0-240d612e45f0)

                                        Fig: Basic Architecture Breakdown into Microservices
                                        
# 3. Understanding different aspects of BPMS - what is deployed where? 

webMethods BPMS comprises different elements like the BPM process, user tasks, business rules, etc. Each of these components is deployed on different runtimes, as per the diagram shown above. Process Engine (PE) sits on top of the integration server as a layered product and is responsible for the orchestration of the different steps. The BPM project on the Integration Server (PE) is a webMethods package deployed on the Integration Server. The task project's runtime can be either MWS or IS (not MSR). The business rule runtime is either IS or MSR. Hence, when we talk about a fully fledged BPMS solution as a whole, we need to have these components running on these different runtimes. So the solution described below is built upon these principles: "What component should be present where?" and "The container image that will be launched as pods will have these respective components baked in the corresponding container image". For example, if we are using MWS as runtime for tasks, the necessary binaries for the task project will be present in the MWS image, which will be then launched as a container.

# 4. Journey of BPMS solution in Kubernetes 

As we know, the smallest deployable compute units that Kubernetes allows you to construct and control are called pods. A group of one or more containers with shared network and storage resources and operating instructions is referred to as a pod. We also know that wM BPMS comprises different elements like the BPM process, user tasks, and business rules. So we need to launch different pods, like IS, MWS, UM, etc., to host the different components of the BPMS solution, so that our different runtimes are running on Kubernetes and they work together to become a fully fledged BPMS solution.

In the current deep dive examples, we will be separating the process engine and rules engine in different IS runimes. You can very well use MSR runtime as well for the process engine (PE) and the rules engine (RE). We will have a task engine (TE) running on MWS and will use universal messaging (UM) instead of brokers.

To launch the four runtimes (pods), what we need is something called Docker images (container images), as pods are launched from images. We need four images (one each for PE, RE, MWS, and UM), which will have all the necessary code (bpm projects, rules projects, task projects, etc.), so that once these images are launched as pods on Kubernetes, we will have a fully fledged BPMS solution running on Kubernetes.

So the next question that comes to mind is: how do we create these images for these four runtimes? We will understand image creation for different runtimes in detail in the upcoming sections.

![pods-BPMS](https://media.github.softwareag.com/user/3204/files/95759dd5-8627-42f9-822c-d92611bac182)

Fig: Journey of BPMS solution in Kubernetes 
                                    

# 5. Creating container image for wM BPMS components 

In this section, we will try to understand how to create container images for different BPMS runtime components. We will use the terms Docker image, container image, and image as synonyms. Also, we will be talking about Docker as a container run-time engine in this documentation context, but what has been discussed here applies to any other OCI-compliant container run-time engine tool set as well.

The image is created from something called a Dockerfile. A Dockerfile is a text document that contains all the commands a user could call on the command line to create a docker image. The Dockerfile instructions' sequence is important. As per docker docuemntation - https://docs.docker.com/build/guide/layers/ : The order of Dockerfile instructions matters. A Docker build consists of a series of ordered build instructions. Each instruction in a Dockerfile roughly translates to an image layer. The following diagram illustrates how a Dockerfile translates into a stack of layers in a container image.

![image](https://media.github.softwareag.com/user/3204/files/2e581d74-8e02-4c09-9980-2eb88dec0723)

In a similar way, we will create Docker images for different runtime components involved in the wM BPMS solution. Our Docker image that will be launched as a container for runtimes such as Process Engine, Rules Engine, and MWS will have layers as well. Please see below one of the ways we can have layers in a Docker image. Our actual BPMS example will have only two layers, but there is no generic rule that says we should have two layers or three layers. The right number of layers depends on the usecase at hand and would depend upon the re-usability of a layer needed and its relevance in a customer's landscape.


![image-latering](https://media.github.softwareag.com/user/3204/files/97a21bc3-e991-47cb-b6eb-188f65d60ecf)

                                             Fig: Different layers in a webMethods product's image
                                             
                                             
## 5.1 Creating container image for Process Engine

As mentioned above, our final process engine solution image (which has all the customer code, etc.) will or can have two or more layers in it. The first layer is called the base image layer, which will be the layer that will have vanilla webMethods binaries packed in the Docker image. The second layer is built on top of the first layer, which will contain customer-specific assets as detailed below.

1. BPM project runtime packages. This is the package that is generated on IS when we do a build and upload of the bpm project from the Desginer.
2. Other IS packages are developed by the customer. It can have flow services, Java services, triggers, etc. that would be used by the BPM project or otherwise.

So the image creating process that wiil do image creation, will have this base idea in mind that, the image that will be produced, should have all of these present in the IS docker image file system and should be present at the right location as well. This is so that, when the image is launchded as a container or a pod, it should have all that is needed on a IS runtime, and IS will do internally the required things during the bootup. So finally a running container will have everything from process engine IS runtime perspective. 


![PE-solution-image-layers](https://media.github.softwareag.com/user/3204/files/4f81e6b6-eed1-40fa-b252-6746f2099abb)


                                             Fig: Different layers in a IS (PE) image
                                             
### 5.1.1 Creating base vanilla webMethods Process Engine image

The process to create an IS image is well documented in the Software AG official documentation. In a nutshell, below are the steps we follow:

1. Create an IS installation using the Software AG installer that will have all the necessary things needed for Process Engine.

![base-install-PE](https://media.github.softwareag.com/user/3204/files/720a2890-0534-4e85-a768-35b5ac4284d6)

2. Use the OOTB scripts (is_container.sh) provide by Software AG R&D team to create the base image docker file. Please refer to software AG Integration server administrator guide documentation for detailed description.

![script-base-is-dockkerfile](https://media.github.softwareag.com/user/3204/files/4e0f823f-2881-445a-a2e9-0af11f9daa8f)

![base-image-create-dockerfile](https://media.github.softwareag.com/user/3204/files/1ee525b6-382f-446e-b962-933c107fd73b)

3. Use the OOTB scripts (is_container.sh) provided by Software AG R&D team and the docker file generated in step 2 above, to create the base webMethods IS (PE) image. Please refer to software AG Integration server administrator guide documentation for detailed description.

![script-base-is-dockkerfile](https://media.github.softwareag.com/user/3204/files/4e0f823f-2881-445a-a2e9-0af11f9daa8f)

![base-image-create](https://media.github.softwareag.com/user/3204/files/c42fa07d-d4a5-43ab-a27e-910ff52c3496)


### 5.1.2 Creating Process engine solution image (building on top of base PE image)

Just like we created the base image with the help of a docker file, we will also create the solution image with help of a dockerfile. The dockerfile will have all the right instruction to acheive the goal of "putting the right things at the right places in the solution image, so that when the IS starts , it will have everything that it needs to have, to have proper functioning BPMS components like BPM project runtime package".

The dockerfile which is built on the above idea is present below. 

[Dockerfile_PE_Solution_image.txt](https://github.softwareag.com/adit/bpms-on-k8s/files/388/Dockerfile_PE_Solution_image.txt)

This build of the Dockerfile happens via a CI tool like Jenkins, which checks out the customer code from source code tools like GitHub, etc. The source code (webMethods BPM runtime and any other support packages) is present in a particular structure in the git hub, so that instructions in the dockerfile can make use of the source code to create the docker image properly. The CI tool builds the Docker file using simple Docker build command instructions. The role of the CI tool is just to checkout the code from GitHub and then execute the Docker build and push to container registry commands.

Reference source code structure is present here https://github.softwareag.com/adit/bpms-on-k8s-build. Look for the bpm folder inside the repo.

Below is the description of what happens in the above attached docker file.

1.  On top of the base webMethods product image, the webMethods IS packages related to BPM, other support packages and some base config (config is optional) is packed. This is all that is needed for PE solution image.  

Below is the sample output of a PE solution docker image build process , which depicts the steps described above.

[Sample-PE-solution-image-build-output.txt](https://github.softwareag.com/adit/bpms-on-k8s/files/387/Sample-PE-solution-image-build-output.txt)


## 5.2 Creating container image for Rules Engine

Similar to PE, our rules engine (RE) solution image (which has all the customer code, etc.) will or can have two or more layers in it. The first layer is called the base image layer, which will be the layer that will have vanilla webMethods binaries packed in the Docker image. The second layer is built on top of the first layer, which will contain customer-specific assets as detailed below.

1. The BPM/Rules project's runtime package. This is the package that is generated on IS when we do a build and upload of the bpm project from the desginer,  in development env. during the development stage. 
2. The IS business rules binaries that are needed for business rules in a BPMS solution.

So the image creation process that will be done will have this base idea in mind: the image that will be produced should have all of these present in the IS (RE) Docker image file system and should be present at the right location as well. This is so that, when the image is launched as a container or a pod, it should have all that is needed on an IS runtime, and IS will do internally the required things during the bootup. So finally, a running container will have everything from the rules engine's runtime perspective.


![RE-solution-image-layers](https://media.github.softwareag.com/user/3204/files/3afe5d8f-b937-4e90-9834-702c7282f612)


                                             Fig: Different layers in a IS (RE) image
                                             
### 5.2.1 Creating base vanilla webMethods Rules Engine image

The process to create IS image is well documented in the Software AG official docuementation. In nutshell, below are the steps we follow :

1. Create an IS installtion using Software AG installer that will all the necessary things needed for Rules Engine. 

![base-install-PE](https://media.github.softwareag.com/user/3204/files/720a2890-0534-4e85-a768-35b5ac4284d6)

2. Use the OOTB scripts (is_container.sh) provide by Software AG R&D team to create the base image docker file. Please refer to software AG Integration server administrator guide documentation for detailed description.

![script-base-is-dockkerfile](https://media.github.softwareag.com/user/3204/files/4e0f823f-2881-445a-a2e9-0af11f9daa8f)

![base-image-create-dockerfile](https://media.github.softwareag.com/user/3204/files/1ee525b6-382f-446e-b962-933c107fd73b)

3. Use the OOTB scripts (is_container.sh) provided by Software AG R&D team and the docker file generated in step 2 above, create the base webMethods IS (RE) image. Please refer to software AG Integration server administrator guide documentation for detailed description.

![script-base-is-dockkerfile](https://media.github.softwareag.com/user/3204/files/4e0f823f-2881-445a-a2e9-0af11f9daa8f)

![Create-base-RE-image-command](https://media.github.softwareag.com/user/3204/files/63b3976c-433d-4e31-9e9e-9cc3e841f6ef)

### 5.2.2 Creating Rules engine solution image (building on top of base RE image)

Just like we created the base image with the help of a Docker file, we will also create the solution image with the help of a Docker file. The Dockerfile will have all the right instructions to achieve the goal of "putting the right things at the right places in the solution image, so that when the IS starts, it will have everything that it needs to have to have proper functioning BPMS components like business rules".

The Dockerfile, which is built on the above idea, is presented below.

[Dockerfile_RE_Solution_image.txt](https://github.softwareag.com/adit/bpms-on-k8s/files/389/Dockerfile_RE_Solution_image.txt)

This build of the Dockerfile happens via a CI tool like Jenkins, which checks out the customer code from source code tools like GitHub, etc. The source code (webMethods packages and business rules) is present in a particular structure in the git hub, so that instructions in the dockerfile can make use of the source code to create the docker image properly. The CI tool builds the Docker file using simple Docker build command instructions. The role of the CI tool is just to checkout the code from GitHub and then execute the Docker build and push to container registry commands.

Reference source code structure is present here: https://github.softwareag.com/adit/bpms-on-k8s-build. Look for the rules folder inside the repo.

Below is a description of what happens in the above-attached Docker file.

1. A ready use Asset Build Environment (ABE) image is used to create the necessary builds/binaries (JARs) for the business rules. This happens in the first stage of the multi-stage docker file attached above. The process to create an ABE image is described in a section below. 
2. In the 2nd stage of that multistage build, on top of the base webMethods product image, the webMethods IS packages and some base config (config is optional) is packed. 
3. The rules related binaires that are created in stage 1 are copied at the right location in the 2nd stage of the multistage build. 
4. So once this dockfile is built, there is no residue of ABE left in the image and what we get as output of the build is a RE soluiton image which has "right things at the right places".  

Below is the sample output of a RE solution docker image build process , which depicts the steps described above.

[Sample-RE-solution-image-build-output.txt](https://github.softwareag.com/adit/bpms-on-k8s/files/390/Sample-RE-solution-image-build-output.txt)

## 5.3 Creating container image for Asset Build Environment (ABE)

The ABE image that is mentioned above is created using below steps. ABE is  needed to create the binaries of rules (JARs) and task (WARs) porjects of a BPMS solution. These JARs and WARs are then packed into the respective solution image. The ABE uses the build.properties that is checked-in to github to decide what to build etc. 

1. Create an ABE installtion using Software AG installer that will all the necessary things needed for AssetBuild env. 
2. Use the docker file attached below to creat the ABE docker image. 
3. You need to keep the docker file at a ABE installtion directory location. For example, if your ABE installtion directory is /opt/saoftwareag, then keep the dockerfile at the location /opt/softwareag and then run the docker build command to create the ABE image. 

[BaseDockerFile_ABE.txt](https://github.softwareag.com/adit/bpms-on-k8s/files/391/BaseDockerFile_ABE.txt)

                                             
## 5.4 Creating container image for MWS (for Task Engine and Montoring/Administration of BPM process)

As mentioned above, our final MWS solution image (which has all the customer code, etc.) will or can have two or more layers in it. The first layer is called the base image layer, which will be the layer that will have vanilla webMethods MWS binaries packed in the Docker image. The second layer is built on top of the first layer, which will contain customer-specific assets as detailed below.

1. The task project binaries that are needed for business rules in a BPMS solution.

So the image creation process that will be done will have this base idea in mind: the image that will be produced should have all of the necessary things present in the Docker image file system and should also be present at the right location inside the MWS filesystem inside the image. This is so that, when the image is launched as a container or a pod, it should have all that is needed, and MWS will do the required things internally during the bootup. So finally, a running container will have everything from an MWS runtime perspective.


![MWS-docker-image-lyer](https://media.github.softwareag.com/user/3204/files/5e55d357-4b45-4692-9f88-6a5dbb022d4e)

                                             Fig: Different layers in a MWS image
                                             
### 5.4.1 Creating base vanilla webMethods MWS image (MWS for monitoring administration and Task Engine)

The process to create an MWS image is well documented in the Software AG official documentation. In a nutshell, below are the steps we follow:

1. Create a MWS installation using the Software AG installer that will include all the necessary things needed from a BPM perspective and the Task Engine.

![mws-base-install](https://media.github.softwareag.com/user/3204/files/59bd37f4-3936-44b9-85cc-ec0aa51c1837)

2. Use the OOTB scripts (mws-docker.sh) provided by the Software AG R&D team to create the base image docker file. Please refer to the AG MWS guide for a detailed description.

![mws-baseimage-dockerfile](https://media.github.softwareag.com/user/3204/files/be8e205a-667a-4279-9374-1f3f57c53e58)

3. Using the OOTB scripts (mws-docker.sh) provided by the Software AG R&D team and the docker file generated in step 2 above, create the base webMethods MWS image. Please refer to the AG MWS guide documentation for a detailed description.

![mws-base-image-build](https://media.github.softwareag.com/user/3204/files/52bb3fd0-afb7-4df4-adf9-7faf5d79fc75)


### 5.4.2 Creating MWS solution image (building on top of base MWS image)

Just like we created the base image with the help of a docker file, we will also create the solution image with help of a dockerfile. The dockerfile will have all the right instruction to acheive the goal of "putting the right things at the right places in the solution image, so that when the MWS starts , it will have everything that it needs to have, to proper functioning BPMS components like user tasks".

The dockerfile which is built on the above idea is present below. 

[MWS_solution_image_Dockerfile.txt](https://github.softwareag.com/adit/bpms-on-k8s/files/384/MWS_solution_image_Dockerfile.txt)

This build of the Dockerfile happens via a CI tool like Jenkins, which checks out the customer code from source code tools like GitHub, etc. The source code (user taks in the BPMS) is present in a particular structure in the git hub, so that instructions in the dockerfile can make use of the source code to create the docker image properly. The CI tool builds the Docker file using simple Docker build command instructions. The role of the CI tool is just to checkout the code from GitHub and then execute the Docker build and push to container registry commands.

Reference source code structure is present here: https://github.softwareag.com/adit/bpms-on-k8s-build. Look for the mws folder inside the repo.

Below is the description of what happens in the above attached docker file.

1. A ready use Asset Build Environment (ABE) image is used to create the necessary builds/binaries (WARs) for the task project. This happens in the first stage of the multi-stage docker file attached above. The process to create an ABE image is described in above section. 
2. The tasks related binaires that are created in stage 1 are copied at the right location in the 2nd stage of the multistage build. 
3. So once this dockfile is built, there is no residue of ABE left in the image and what we get as output od the build is a MWS soluiton image, which has "right things at the right places" in the solution image.  

Below is the sample output of a solution docker image build process , which depicts the steps described above.

[MWS-solution-image-build-output.txt](https://github.softwareag.com/adit/bpms-on-k8s/files/385/MWS-solution-image-build-output.txt)


## 5.5 Creating container image for Universal Messaging (UM)

We will be using the UM image present in the official Software AG container registry (https://containers.softwareag.com/products) as the base image; hence, we don't need to install UM. Currently, if we are using UM for BPMS purposes only and using JMS messaging, then we don't need to have a solution image specially created. With the help of an IS JMS alias-related configuration setting, the needed JMS destinations (topics, queues) gets created automatically on UM, when IS comes up and connects with UM. This setting is described at url : https://documentation.softwareag.com/webmethods/integration_server/pie10-15/webhelp/pie-webhelp/#page/pie-webhelp%2Fconfigure_jms_create_admin_objects.html . A relevant IS screenshot is shown below about that setting as well. For detailed information, please refer to the  IS administrator guide from Software AG. The out-of-the-box channels that are needed for products like PE and MWS to work, gets created automatically as well, when these products connect to UM during their bootup sequence.

We will be using persistent volumes for logs and data directories in UM; hence, the channels that are created are NOT lost if a UM pod is terminated gracefully. For the same reason, they are also not lost when the UM pod or Kubernetes worker nodes crashes and the UM pod is relaunched.


![um-base-image-container-registry](https://media.github.softwareag.com/user/3204/files/cabd14f3-6de5-4fe7-b994-03a70e3547b6)

                                 Fig: UM base image at https://containers.softwareag.com/products/universalmessaging-server       
                                 
                                 
![create-jms-destination-on-demand](https://media.github.softwareag.com/user/3204/files/2bd0c156-1a65-4439-8ded-3ae7601cce7c)
 


                                 Fig: IS JMS alias-related configuration setting for creating JMS destinations automatically
                                 
                   
# 6. Launching wM BPMS runtime components as pods in kubernetes

As we saw in our journey so far, different components need to run as containers in Kubernetes. Since we have created the images, we now need to lauch them as pods in a way that they interact with each other to become a fully fledged BPMS solution. We worked on the basic ideas of what we may call "smart images and simple deployments". Running something as a pod could mean, not having much intelligence built into some sort of script that is invoked by some startup command during the pod lauch. In other words, a runtime should NOT have much complexity (building logic, executing complex commands, etc.) in its Kubernetes YAML file. The kubernetes yaml files should have just simple easy to understand kubernetes objects, like below. It might help from an operational perspective as well, where a dev team (or whoever provides images)gives simple instructions to a release managment team (or whoever does deployments). Simple instructions on how to deploy images, once those images are built by dev team; i.e "smart images and simple deployments". 

1. A contianer image (docker image) built on the idea of "having the right things at the right place", so that the respective runitme will do the necessary thing during its bootup.
2. A bunch of config maps that would have specific entries,unique to a particular environment, like DEV, UAT, PROD, etc. For example, in MSR (or an IS with an MSR license), we can do this via the application.properties file.
3. A bunch of volumes (PVs) for persistence as needed by the runtime or customer's environment. Use basic ideas around PV when deciding what to persist for a runtime or a solution.
4. Some environment variables as needed by the runtime.

Apart from above , we would have some services (clusterIP svc, Ingress etc. ) so that runtime components can talk to each other within the cluster, and if needed they can be reached from outside the cluster as well.  

All the pods that we will launch for BPMS are kind of based on these basic ideas only. For example, if we talk about launching UM pods, a sample sequence of steps that could be executed in the deployment job could be as follows: These steps can be executed in below sequence by a tool like Jenkins, etc. You can always use more mature ways of deploying the pods using package managers like Helms charts.

                   a.  kubectl apply -f ./aks-yaml/um-my-demo-project/myDemoProject-um-license-configMap.yml 
                   b.  kubectl apply -f ./aks-yaml/um-my-demo-project/myDemoProject-um-data-vol-pvc.yml 
                   c.  kubectl apply -f ./aks-yaml/um-my-demo-project/myDemoProject-um-log-vol-pvc.yml 
                   d.  kubectl apply -f ./aks-yaml/um-my-demo-project/myDemoProject-um-clusterIP-svc.yml
                   e.  kubectl apply -f ./aks-yaml/um-my-demo-project/myDemoProject-um-deployment.yml
 
Please see below different sections that gives sample deployment instructions for different runtime components. Please note, we are using IS as runtime for Process engine and Rules engine (you can always use MSR for PE and RE, the basic principles described so far will not change much); MWS as task engine and UM as the messaging provider. 

## 6.1 sample yaml file for different wM BPMS runtimes running as pods in Kubernetes. 

Please see attached the zip file for different runtime components, which contains sample YAML files for deployment of that runtime in Kubernetes. These YAML files can be applied in a particular sequence, like below.

1. Apply your config maps yaml files.
2. Apply your persistent volume or persistent volume claim yaml files.
3. Apply your service (clusterIP svc, etc.) yaml files.
4. Apply your deployment (or stateful set) yaml files.

For Process Engine running on IS, please see attached below.

[bpm-my-demo-project.zip](https://github.softwareag.com/adit/bpms-on-k8s/files/393/bpm-my-demo-project.zip)

For Rules Engine running on IS, please see attached below.

[rules-my-demo-project.zip](https://github.softwareag.com/adit/bpms-on-k8s/files/394/rules-my-demo-project.zip)

For MWS (task Engine), please see attached below.

[mws-my-demo-project.zip](https://github.softwareag.com/adit/bpms-on-k8s/files/395/mws-my-demo-project.zip)

For Universal Messaging, please see attached below.

[um-my-demo-project.zip](https://github.softwareag.com/adit/bpms-on-k8s/files/397/um-my-demo-project.zip)

Please see a sample screenshot of all components running on a kubernetes platform (Azure Kubernetes Services) once they are launched. 

![bpms-pods-on-aks](https://media.github.softwareag.com/user/3204/files/2ddedb8a-c3a4-4868-a06e-9dac4d035c24)


# 7. Some pointers about configurations needed in BPMS components

Please see below points about configuring a particular runtime and how we can do that in the Kubernetes world for the BPMS usecase.

1. We are familiar with the fact by now, that different runtime components interact with each other to become a BPMS solution. So these components need to be configured at the appropriate places, so they know how to reach each other. These settings could include things like configuring the PE and RE urls and credentials in MWS. The UM URL in MWS and IS, and so on.
2. There are some other settings as well in each individual runtime that control certain aspects of their runtime behavior. For example, jdbc pools settings in the IS connection, which is mandatory for running PE.

So based on the type of configuration, you can decide how to externalize that configuration. For example, if you are using an IS with MSR, you can externalize many configurations such as JMS url, jdbc pool connection etc. using the application.properties file. MWS supports some externalization of configuration (like the custom_Wrapper.conf file) as well, which you can read more about in the MWS documentation. Please see the below section to learn how it has been done in this POC, for a runtime component.

## 7.1  Configurations in Process Engine 

In our case, we have taken a slightly different approach to externalizing configuration for PE. This is mainly due to the fact that we are not using MSR (or MSR license in this case) for PE. So what we have done is that we have launched a temporary base webMethods IS (having PE) as a container, and then, via the IS admin web UI console, we have configured the necessary aspects of IS like the UM connection, the JDBC pool, etc. Then the config directory present in the instance folder of the IS has been extracted and checked-in to SCM like github. The Docker image building process of the PE solution image, takes care of baking that configuration into the image. Please refer to the source code repo and the Docker file in earlier sections for this. This approach serves well if you are only going to have one environment (say, PROD), which would not happen in reality. So different aspects of those configurations might need to be overridden based on environment (like in SIT, UAT, PROD, etc.). So the overriding of those configs has been done via the config map concept of Kubernetes. Some samples are present in the zip folder for PE Yaml files attached in the above section. Please note that the config that is baked in the image only aims to serve as some base starter config, so you don't have to use the OOTB base wM image config, which has default values. The best way to control these configurations is through the application.properties file (a MSR license is needed).

## 7.2  Configurations in MWS

MWS supports some externalization of configuration as well, which you can read more about in the MWS documentation. These configurations can be supplied as config maps in kubernetes world.  However, there might be some configurations which could be needed to be done via UI, only once, as they go in MWS database directly ( for e.g : configuring UM url in MWS cluster settings. If we don't want to scale up MWS on demamnd , then we can externlize this as well today). But for most of the configurations, we can externalize it very easily via config maps. You might needed a reference running MWS containers to extract some of the configs that can be checked-in as config maps in Git hub. Some samples are present in the zip folder for MWS Yaml files attached in the above section.

## 7.3  Configurations in Rules Engine

If we are using Rules engine only for invoking rules, then we don't need many external configurations, apart from changing default user credentials; which you can do during base image creation itself, as installer while isntalling IS (RE), gives you an option now to NOT install with default user credentials. We only need an external database for rules engine, if you want to use rules auditing or do a hot deployment of rules via MWS. Hot deployment of rules is not recommended in container world. So in our RE case, we don't need an external database as a must for BPMS usecase. How to configure a RE's IS and externalize it, you can refer to the details mentioned in the process engine section above, named as "Configurations in Process Engine".


## 7.2  Configurations in Universal Messaging

We are controlling some configuration of UM via OOTB supported env variables. The different UM assets like JMS destinations etc. for this POC are created on demand by the IS using the settings described in the official documentation : https://documentation.softwareag.com/webmethods/integration_server/pie10-15/webhelp/pie-webhelp/#page/pie-webhelp%2Fconfigure_jms_create_admin_objects.html. You can refer to UM image creation process for some more details as well. 


# 8. Conclusion

Using the above principles and ideas, we saw how we can run wM BPMS components on Kubernetes. Since this is a kick-start guide only, it doesn't consider all aspects, like the best security practices of using only SSl/TLS-based connections, best code managament practices (like no userID, password in github) etc. It attempts to give us a  glimpse of how we can run BPMS on Kubernetes in a green field customer landscape; as well as, it attempts to give some guidance on how we can go to the container/Kubernetes world from an existing on-prem world. 




Thanks,
Aditya,
Software AG, 
Email : aditya.aditya@softwareag.com








                                    




                                         
