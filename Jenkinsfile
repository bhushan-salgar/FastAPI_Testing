pipeline {
    agent any

    tools {
        maven 'Maven_3.8.5'   // Ensure this matches your Jenkins tool config
        jdk 'JDK_11'          // Ensure this matches your Jenkins JDK config
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
                allure includeProperties: false, jdk: '', results: [[path: 'target/allure-results']]
            }
        }
    }

    post {
        always {
            junit 'target/surefire-reports/*.xml'
            cleanWs()
        }
    }
}
