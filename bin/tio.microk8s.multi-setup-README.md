
## Primary Setup on Ubuntu =============================================
@see    https://microk8s.io/docs/getting-started

### Setup kubctl to use microk8s and current certificate
```
anil@oommubt24:~/.kube$ microk8s config > config
```

#### Setup DNS and Cert
https://microk8s.io/docs/services-and-ports




## Secondary Setup on Microsoft WSL Ubuntu. ============================
        @see https://microk8s.io/docs/install-wsl2

Run Reset on the new Installation to enable Addons

## 1. Adding Nodes
https://microk8s.io/docs/clustering

Note added as --worker.
```
# On Master , Run and use output to run on Slave
microk8s add-node

# Imortant cannot join as a HA Control Node , so can use only  --worker
# Limitation might be we are running as WSL Linux and IP address is not available.
microk8s join 192.168.1.205:25000/..../31....4d7859a500  --worker
```

## 2. Removing Node
```
# On Slave First
microk8s leave 

# On Master 
microk8s remove-node oommwinao23
```

## 3. Working with secondary Node
```
sudo snap stop microk8s
```

## 4. Resettings Secondary Nodes, Addons enabled before Joining the Cluster.
or else use clean install of microk8s
```
sudo microk8s reset
sudo microk8s stop
sudo microk8s start
microk8s enable dns; microk8s enable hostpath-storage; microk8s enable dashboard
echo "Enabling Required Addons ..."
microk8s enable prometheus
microk8s enable registry:size=40Gi
```
## 5. Back to adding Nodes to Master.

#####  Investigation
```
sudo microk8s inspect

# Changes Host name to lowere case on WLS Ubuntu to compliant
sudo hostnamectl set-hostname oommwinao23
```