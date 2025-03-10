# Jenkins Deployment with Ngrok and Kubernetes using Helm

This guide outlines the steps to set up Jenkins, expose it securely using Ngrok, and deploy a Spring Boot application on Kubernetes using Helm and Jenkins pipelines.

---

## Prerequisites
Ensure you have the following installed:
- **Docker**
- **Jenkins**
- **Ngrok**
- **Kubernetes Cluster** (e.g., Minikube, K3s, or cloud-based services)
- **Helm**
- **MongoDB Compass** (for database visualization)

---

## Step 1: Jenkins Setup in Docker
1. **Create Jenkins container:**
   ```bash
   docker run -d -p 9090:8080 -v jenkins_home:/var/jenkins_home --name jenkins jenkins/jenkins:lts
   ```
2. **Access Jenkins UI:**
   - Navigate to `http://localhost:9090`
   - Retrieve the initial admin password:
   ```bash
   docker exec jenkins cat /var/jenkins_home/secrets/initialAdminPassword
   ```
3. **Install recommended plugins** during the setup process.
4. **Create a new admin user** for Jenkins.

---

## Step 2: Ngrok Setup for External Access
### 1. Create an Ngrok Account
- Sign up at [https://dashboard.ngrok.com/](https://dashboard.ngrok.com/)
- Copy your **Authtoken** from the dashboard.

### 2. Run Ngrok in Docker
```bash
docker run --rm ngrok/ngrok config add-authtoken <YOUR_AUTH_TOKEN>
```

### 3. Create Ngrok Configuration File
Create the following `ngrok.yml` file:
```yaml
version: "2"
authtoken: <YOUR_AUTH_TOKEN>

tunnels:
  jenkins:
    proto: http
    addr: 9090
```

### 4. Start Ngrok Tunnel
```bash
docker run --rm -v C:\Users\vinay\ngrok:/home/ngrok/.config/ngrok --network=host ngrok/ngrok start jenkins
```
- Copy the public URL provided by Ngrok.

---

## Step 3: Jenkins Pipeline Setup
1. **Create a new Pipeline job** in Jenkins.
2. Add the following Jenkinsfile to your repository:

```groovy
pipeline {
    agent any

    stages {
        stage('Clone Repository') {
            steps {
                git 'https://github.com/your-repo-url.git'
            }
        }

        stage('Build Docker Image') {
            steps {
                sh 'docker build -t vinaypavan022/vinay:j1 .'
            }
        }

        stage('Push to Docker Hub') {
            steps {
                withDockerRegistry([url: '', credentialsId: 'docker-hub-credentials']) {
                    sh 'docker push vinaypavan022/vinay:j1'
                }
            }
        }

        stage('Deploy to Kubernetes') {
            steps {
                sh 'helm upgrade --install my-app ./helm-chart --set image.repository=vinaypavan022/vinay --set image.tag=j1'
            }
        }
    }
}
```

3. Add your **Docker Hub credentials** in Jenkins under `Manage Jenkins` → `Credentials`.

4. Set your repository's webhook to trigger the Jenkins build on every push.

---

## Step 4: Kubernetes Deployment with Helm
1. Create a Helm chart for your application:
```bash
helm create my-app
```

2. Update `values.yaml` in the Helm chart with your Docker image details:
```yaml
image:
  repository: vinaypavan022/vinay
  tag: j1
```

3. Deploy the application using Helm:
```bash
helm upgrade --install my-app ./helm-chart
```

---

## Step 5: Final Testing
1. Confirm Jenkins is accessible via the Ngrok URL.
2. Trigger a Git push to observe the automated build and deployment pipeline.
3. Verify the application is running successfully in your Kubernetes cluster.

---

## Troubleshooting
- **Ngrok auth token error**: Ensure your Ngrok config file is saved properly with `.yml` extension.
- **Docker permission errors**: Run Docker commands with `sudo` if required.
- **Pipeline failure**: Review Jenkins logs for detailed error information.

---

## Conclusion
Following these steps ensures a streamlined CI/CD process for your Spring Boot application using Jenkins, Ngrok, Docker, and Kubernetes with Helm.

