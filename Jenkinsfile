pipeline {
    agent any

    tools {
        maven 'Maven_3'   // Ensure this matches your Jenkins tool config
        jdk 'JDK_17'          // Ensure this matches your Jenkins JDK config
    }

    environment {
        MAVEN_OPTS = "-Dmaven.test.failure.ignore=false"
    }

    stages {
        stage('Checkout Code') {
            steps {
                git url: 'https://github.com/bhushan-salgar/FastAPI_Testing.git', branch: 'main'
            }
        }

        stage('Run Tests') {
            steps {
                sh 'mvn clean test'
            }
        }

        stage('Publish Report') {
            steps {
                // Optional: Allure Report
                sh 'mvn allure:report'
                allure includeProperties: false, jdk: '', results: [[path: 'target/allure-results']]
            }
        }
    }

    post {
        always {
			archiveArtifacts artifacts: '**/target/*.jar', allowEmptyArchive: true
            junit 'target/surefire-reports/*.xml'
            cleanWs()
        }
    }
}
