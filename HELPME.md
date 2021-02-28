## Do the right things
## Prepare secrets for k8s 
Encode string to base64 and save it in github secrets, then save it in k8s secrets
```
echo -n 'https://titsonfire.store/web' | base64
```
Build docker image and run test
````
podman build -t maxmorev/e-shop-web-v2 .
podman run -p 8080:8080 maxmorev/e-shop-web-v2
````
### Execute shell 'sh' in a pod
```
kubectl --namespace example get pod
shell-demo-pod-name
kubectl exec --stdin --tty shell-demo-pod-name -- /bin/sh
```
