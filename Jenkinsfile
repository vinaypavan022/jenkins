pipeline {
    agent any

    environment {
        DOCKER_IMAGE = 'vinaypavan022/vinay:j1'
    }

    stages {
        stage('Checkout') {
            steps {
                git 'https://github.com/vinaypavan022/jenkins.git'
            }
        }

        stage('Build') {
            steps {
                sh './mvnw clean package'
            }
        }

        stage('Docker Build & Push') {
            steps {
                sh "docker build -t $DOCKER_IMAGE ."
                sh "docker login -u your-dockerhub-username -p your-dockerhub-password"
                sh "docker push $DOCKER_IMAGE"
            }
        }

        stage('Deploy to Kubernetes') {
            steps {
                sh "kubectl apply -f k8s/deployment.yaml"
            }
        }
    }

    post {
        success {
            echo "✅ Deployment Successful!"
        }
        failure {
            echo "❌ Deployment Failed!"
        }
    }
}
