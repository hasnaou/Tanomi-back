pipeline {
    agent any

    tools {
        maven 'Maven'
        jdk 'JDK 17'
    }

    stages {
        stage('Checkout') {
            steps {
              git branch: 'jenkins', url: 'https://github.com/hasnaou/Tanomi-back'
            }
        }

        stage('Clean') {
            steps {
                bat 'mvn clean '
            }
        }

        stage('Test ProjectService') {
            steps {
                bat 'mvn test -Dtest=ProjectServiceImplTest'
            }
        }
        stage('Test TaskService') {
            steps {
                bat 'mvn test -Dtest=TaskServiceImplTest '
            }
        }
        stage('Test EventService') {
            steps {
                bat 'mvn test -Dtest=EventServiceImplTest'
            }
        }
        stage('Test mvn') {
            steps {
                bat 'mvn -v'
            }
        }
    }

    post {
        success {
            echo 'Test succeeded!'

        }

        failure {
             echo 'Test failed!'
        }
    }
}