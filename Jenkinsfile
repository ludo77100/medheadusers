pipeline {
    agent any
    environment {
        DOCKERHUB_CREDENTIALS = credentials('dockerhub-credentials-id')
        IMAGE_NAME = 'fr0d0n/medhead-users'
        SONAR_TOKEN = credentials('sonarcloud-token')
        SONAR_PROJECT_KEY = 'ludo77100_medheadusers'
    }
    stages {
        stage('Check Docker') {
            steps {
                sh 'docker --version'  // Vérifie la version de Docker
                sh 'docker images'     // Liste les images Docker existantes
            }
        }
        stage('Build') {
            steps {
                sh 'mvn -B -DskipTests clean package'
            }
        }
        stage('Test') {
            steps {
                sh 'mvn verify'
            }
            post {
                always {
                    junit '**/target/surefire-reports/*.xml'
                    jacoco execPattern: '**/target/jacoco.exec'
                }
            }
        }
        stage('SonarCloud Analysis') {
            steps {
                script {
                    // Exécuter l'analyse avec SonarCloud
                    sh "mvn sonar:sonar \
                        -Dsonar.projectKey=${env.SONAR_PROJECT_KEY} \
                        -Dsonar.organization=ludo77100 \
                        -Dsonar.host.url=https://sonarcloud.io \
                        -Dsonar.login=${env.SONAR_TOKEN} \
                        -Dsonar.coverage.jacoco.xmlReportPaths=target/site/jacoco/jacoco.xml"

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
                    withCredentials([usernamePassword(credentialsId: 'dockerhub-credentials-id', passwordVariable: 'DOCKERHUB_PASSWORD', usernameVariable: 'DOCKERHUB_USERNAME')]) {
                        // Effectuer le login de manière sécurisée
                        sh 'echo $DOCKERHUB_PASSWORD | docker login -u $DOCKERHUB_USERNAME --password-stdin'
                        // Pousser l'image vers Docker Hub
                        sh "docker push ${env.IMAGE_NAME}:${env.BUILD_NUMBER}"
                        //sh "docker push ${env.IMAGE_NAME}:latest"
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

