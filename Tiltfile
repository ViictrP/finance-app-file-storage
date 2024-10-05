# set a variable to determine if the OS is Windows
isWindows = True if os.name == "nt" else False

# Load the helm_remote extension so we can create services from remote Helm charts
load("ext://helm_remote", "helm_remote")

# Load the kim_build extension so we can build images using nerdctl
load('ext://nerdctl', 'nerdctl_build')

# Load the Kubernetes resources defined in the YAML file
def cert_manager():
    # Specify the Kubernetes manifest for the deployment
    helm_remote(
        'cert-manager',
        repo_url='https://charts.jetstack.io',
        version='v1.14.5',
        set=[
            'installCRDs=true',  # Automatically install and manage CRDs as part of the Helm release
        ]
    )

def zookeeper():
    # Specify the Kubernetes manifest for the deployment
    k8s_yaml([
        './devops/kafka/redpanda/redpanda.namespace.yaml',
        './devops/kafka/zookeeper/zookeeper.service.yaml',
        './devops/kafka/zookeeper/zookeeper.deployment.yaml'
    ])
    k8s_resource(
        'zookeeper-deployment',
        resource_deps=['cert-manager']
    )

def kafka():
    k8s_yaml([
        './devops/kafka/kafka.service.yaml',
        './devops/kafka/kafka.deployment.yaml',
    ])
    k8s_resource(
        'kafka-deployment',
        port_forwards=29092,
        resource_deps=['zookeeper-deployment']
    )

def redpanda():
    k8s_yaml([
        './devops/kafka/redpanda/redpanda.service.yaml',
        './devops/kafka/redpanda/redpanda.deployment.yaml',
        './devops/kafka/redpanda/redpanda.ingress.yaml',
    ])
    k8s_resource(
        'redpanda-deployment',
        links=['redpanda.local.gd'],
        resource_deps=['kafka-deployment']
    )

def storage_mongodb():
    k8s_yaml([
        './finance-app-file-storage/devops/mongo/mongo.namespace.yaml',
        './finance-app-file-storage/devops/mongo/mongo.configmap.yaml',
        './finance-app-file-storage/devops/mongo/mongo.deployment.yaml',
        './finance-app-file-storage/devops/mongo/mongo.service.yaml'
    ])
    k8s_resource(
        'mongodb-storage-deployment',
        port_forwards=27017
    )

def storage():
    # Specify the Kubernetes manifest for the deployment
    nerdctl_build(
        'local/storage-app',
        context='./',
        dockerfile='./finance-app-file-storage/devops/application/Dockerfile',
    )
    k8s_yaml([
        './finance-app-file-storage/devops/application/storage.namespace.yaml',
        './finance-app-file-storage/devops/application/storage.deployment.yaml',
        './finance-app-file-storage/devops/application/storage.service.yaml',
        './finance-app-file-storage/devops/application/storage.ingress.yaml'
    ])
    k8s_resource(
        'storage-deployment',
        links=['storage.local.gd'],
        resource_deps=[
            'mongodb-storage-deployment',
            'kafka-deployment',
        ]
    )

def upload_mongodb():
    k8s_yaml([
        './finance-app-file-upload/devops/mongo/mongo.namespace.yaml',
        './finance-app-file-upload/devops/mongo/mongo.configmap.yaml',
        './finance-app-file-upload/devops/mongo/mongo.deployment.yaml',
        './finance-app-file-upload/devops/mongo/mongo.service.yaml'
    ])
    k8s_resource(
        'mongodb-upload-deployment',
        port_forwards=27018
    )

def upload():
    # Specify the Kubernetes manifest for the deployment
    nerdctl_build(
        'local/upload-app',
        context='./',
        dockerfile='./finance-app-file-upload/devops/application/Dockerfile',
    )
    k8s_yaml([
        './finance-app-file-upload/devops/application/upload.namespace.yaml',
        './finance-app-file-upload/devops/application/upload.deployment.yaml',
        './finance-app-file-upload/devops/application/upload.service.yaml',
        './finance-app-file-upload/devops/application/upload.ingress.yaml'
    ])
    k8s_resource(
        'upload-deployment',
        links=['upload.local.gd'],
        resource_deps=[
            'mongodb-upload-deployment',
            'kafka-deployment',
            'storage-deployment',
        ]
    )

cert_manager()
zookeeper()
kafka()
redpanda()
storage_mongodb()
storage()
upload_mongodb()
upload()