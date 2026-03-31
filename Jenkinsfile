pipeline {
    agent any
    tools {
        maven 'Maven3'
        jdk 'JDK21'
    }
    environment {
        PATH = "/usr/local/bin:${env.PATH}"
        DOCKER_CREDS_ID = 'docker_hub' 
        DOCKER_REPO = 'suph03/shopping-cartUI'
        DOCKER_TAG = 'latest'
    }
    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/suwaiphyoe-beatriz/ShoppingCartUI.git'
            }
        }
        stage('Build') {
            steps {
                sh 'mvn clean install'
            }
        }
        stage('Test & Coverage') {
            steps {
                sh 'mvn test jacoco:report'
            }
        }
        stage('Publish Reports') {
            steps {
                junit '**/target/surefire-reports/*.xml'
                jacoco execPattern: '**/target/jacoco.exec'
            }
        }
        stage('Build Docker Image') {
            steps {
                sh "docker build -t ${DOCKER_REPO}:${DOCKER_TAG} ."
            }
        }
        stage('Push to Docker Hub') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'docker_hub', 
                                 usernameVariable: 'DOCKER_USER', 
                                 passwordVariable: 'DOCKER_PASS')]) {
                    
                    sh 'echo "${DOCKER_PASS}" | docker login -u "${DOCKER_USER}" --password-stdin'
                    sh 'docker push suph03/shopping-cartUI:latest'
                }
            }
        }
    }
    post {
        always {
            cleanWs()
        }
    }
}
