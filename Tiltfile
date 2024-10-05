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

def mongodb():
    k8s_yaml([
        './finance-app-file-storage/devops/mongo/mongo.namespace.yaml',
        './finance-app-file-storage/devops/mongo/mongo.configmap.yaml',
        './finance-app-file-storage/devops/mongo/mongo.deployment.yaml',
        './finance-app-file-storage/devops/mongo/mongo.service.yaml'
    ])
    k8s_resource(
        'mongodb-deployment',
        port_forwards=27017
    )

def zookeeper():
    # Specify the Kubernetes manifest for the deployment
    k8s_yaml([
        './finance-app-file-storage/devops/kafka/redpanda/redpanda.namespace.yaml',
        './finance-app-file-storage/devops/kafka/zookeeper/zookeeper.service.yaml',
        './finance-app-file-storage/devops/kafka/zookeeper/zookeeper.deployment.yaml'
    ])
    k8s_resource(
        'zookeeper-deployment',
        resource_deps=['cert-manager']
    )

def kafka():
    k8s_yaml([
        './finance-app-file-storage/devops/kafka/kafka.service.yaml',
        './finance-app-file-storage/devops/kafka/kafka.deployment.yaml',
    ])
    k8s_resource(
        'kafka-deployment',
        port_forwards=29092,
        resource_deps=['zookeeper-deployment']
    )

def redpanda():
    k8s_yaml([
        './finance-app-file-storage/devops/kafka/redpanda/redpanda.service.yaml',
        './finance-app-file-storage/devops/kafka/redpanda/redpanda.deployment.yaml',
        './finance-app-file-storage/devops/kafka/redpanda/redpanda.ingress.yaml',
    ])
    k8s_resource(
        'redpanda-deployment',
        links=['redpanda.local.gd'],
        resource_deps=['kafka-deployment']
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
        links=['api.local.gd'],
        resource_deps=[
            'mongodb-deployment',
            'kafka-deployment',
        ]
    )

cert_manager()
zookeeper()
kafka()
redpanda()
mongodb()
storage()