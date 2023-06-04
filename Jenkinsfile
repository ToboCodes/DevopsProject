pipeline {
    agent any

    tools { 
        maven 'jenkinsmaven' 
        jdk 'JAVA17' 
    }

    stages {
        stage('Checkout') {
            steps {
                checkout([
                    $class: 'GitSCM', 
                    branches: [[name: 'main']],
                    userRemoteConfigs: [[url: 'git@github.com:ToboCodes/DevopsProject.git']]
                ])
            }

            post {
                failure {
                    sendSlackNotification("Quality Gate", currentBuild.currentResult)
                }
            }
        }

        stage('Build') {
            steps {
                sh 'mvn -B -DskipTests clean package'
            }
            
            post {
                failure {
                    sendSlackNotification("Quality Gate", currentBuild.currentResult)
                }
            }
        }

        stage('Archive') {
            steps {
                archiveArtifacts artifacts: "**/target/*.jar", fingerprint: true
            }
            
            post {
                failure {
                    sendSlackNotification("Quality Gate", currentBuild.currentResult)
                }
            }
        }

        stage('Test') {
            steps {
                sh 'mvn test'
                junit '**/target/surefire-reports/TEST-*.xml'
            }
            
            post {
                failure {
                    sendSlackNotification("Quality Gate", currentBuild.currentResult)
                }
            }
        }

        stage('Sonar Scanner') {
            steps {
                withSonarQubeEnv('SonarQube') {
                    sh 'mvn sonar:sonar -Dsonar.projectKey=GS -Dsonar.sources=src/main/java/com/kibernumacademy/devops -Dsonar.tests=src/test/java/com/kibernumacademy/devops -Dsonar.language=java -Dsonar.java.binaries=.'
                }
            }
            
            post {
                failure {
                    sendSlackNotification("Quality Gate", currentBuild.currentResult)
                }
            }
        }

        stage('Quality Gate'){
            steps{
                timeout(time:1, unit:'HOURS'){
                    waitForQualityGate abortPipeline:true
                }
            }
            
            post {
                failure {
                    sendSlackNotification("Quality Gate", currentBuild.currentResult)
                }
            }
        }
        
        stage('Nexus Upload') {
            steps {
                nexusArtifactUploader(
                    nexusVersion: 'nexus3',
                    protocol: 'http',
                    nexusUrl: 'nexus:8081',
                    groupId: 'cl.awakelab.junitapp',
                    version: '0.0.1-SNAPSHOT',
                    repository: 'maven-snapshots',
                    credentialsId: 'NexusCredentials',
                    artifacts: [
                        [artifactId: 'proyectoFinal',
                        classifier: '',
                        file: 'target/devops-0.0.1-SNAPSHOT.jar',
                        type: 'jar'],
                        [artifactId: 'proyectoFinal',
                        classifier: '',
                        file: 'pom.xml',
                        type: 'pom']
                    ]
                )
            }
            
            post {
                success {
                    sendSlackNotification("Pipeline", currentBuild.currentResult)
                }
                failure {
                    sendSlackNotification("Quality Gate", currentBuild.currentResult)
                }
            }
        }
    }
}

def sendSlackNotification(String stageName, String currentResult) {
    def color = currentResult == 'SUCCESS' ? 'good' : 'danger'
    slackSend (
        channel: '#jenkins-integration',
        color: color,
        message: "${currentResult} (${stageName}): Job ${env.JOB_NAME} build ${env.BUILD_NUMBER}"
    )
}