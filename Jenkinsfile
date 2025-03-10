pipeline {
    agent any

    environment {
        DOCKER_IMAGE = 'vinaypavan022/vinay:j1'
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: '*/jenkintest', url: 'https://github.com/vinaypavan022/jenkins.git'
            }
        }

        stage('PR Validation') {
            when {
                changeRequest()  // Only run this stage if it's a pull request
            }
            steps {
                echo "PR detected: Validating code..."
                sh './mvnw clean verify'
            }
        }

        stage('Build & Push (Main Branch Only)') {
            when {
                branch 'main'  // Only deploy when merged to main
            }
            steps {
                sh './mvnw clean package'
                sh "docker build -t $DOCKER_IMAGE ."
                sh "docker login -u your-dockerhub-username -p your-dockerhub-password"
                sh "docker push $DOCKER_IMAGE"
            }
        }

        stage('Deploy to Kubernetes') {
            when {
                branch 'main'  // Deploy only if merged to main
            }
            steps {
                sh "kubectl apply -f k8s/deployment.yaml"
            }
        }
    }

    post {
        success {
            echo "✅ Build Successful!"
        }
        failure {
            echo "❌ Build Failed!"
        }
    }
}
