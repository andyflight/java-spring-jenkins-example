pipeline {
    agent any
    stages {
        stage('Checkout') {
            steps {
                git branch: 'master', url: 'https://github.com/andyflight/java-spring-jenkins-example.git'
            }
        }
        stage('Test') {
            steps {
                sh './gradlew test'
            }
            post {
                success {
                    junit 'build/test-results/test/*.xml'
                    recordCoverage(
                        coverageFiles: 'build/reports/jacoco/test/jacocoTestReport.xml',
                        sourceFiles: 'src/main/java/**',
                        tool: 'JaCoCo'
                    )
                }
            }
        }
        stage('Build') {
            steps {
                sh './gradlew bootJar'
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