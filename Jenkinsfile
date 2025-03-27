pipeline {
    agent any
    stages {
        stage('Checkout') {
            steps {
                git branch: 'master', url: 'https://github.com/andyflight/java-spring-jenkins-example'
            }
        }
        stage('Test') {
            steps {
                sh './gradew test'
            }
            post {
                success {
                    junit 'build/test-results/test/*.xml'
                    step([$class: 'JaCoCoPublisher', execPattern: 'build/jacoco/test.exec', classPattern: 'build/classes/*', sourcePattern: 'src/main/*'])
                }
            }
        }
        stage('Build') {
            steps {
                sh './gradew bootJar'
            }
        }
        stage('Build Image') {
            steps {
                sh 'docker build -t simpleblog-image .'
            }
        }
        stage('Run') {
            steps {
                sh 'docker stop simpleblog-container || true'
                sh 'docker rm simpleblog-container || true'
                sh 'docker run -d -p 8000:8000 --name simpleblog-container simpleblog-image'
            }
        }
    }
}