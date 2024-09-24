pipeline {
    agent any
    environment {
        DOCKERHUB_CREDENTIALS = credentials('dockerhub-credentials-id')
        IMAGE_NAME = 'fr0d0n/medheadoc'
    }
    stage('Check Docker') {
        steps {
            sh 'docker --version'  // Vérifie la version de Docker
            sh 'docker images'     // Liste les images Docker existantes
        }
    }
    stages {
        stage('Build') {
            steps {
                sh 'mvn -B -DskipTests clean package'
            }
        }
        stage('Test') { 
            steps {
                sh 'mvn test' 
            }
            post {
                always {
                    junit 'target/surefire-reports/*.xml' 
                }
            }
        }
        stage('Build Docker Image') {
            steps {
                script {
                    echo "Starting Docker build..."
                    dockerImage = docker.build("${env.IMAGE_NAME}:${env.BUILD_NUMBER}")
                }
            }
        }
        stage('Push Docker Image') {
            steps {
                script {
                    echo "Starting Docker push..."
                    docker.withRegistry('https://registry.hub.docker.com', 'DOCKERHUB_CREDENTIALS') {
                        dockerImage.push("${env.BUILD_NUMBER}")
                        dockerImage.push("latest")
                    }
                }
            }
        }
    }
    post {
        always {
            cleanWs() // Nettoyage du workspace après l'exécution du pipeline
        }
    }
}

