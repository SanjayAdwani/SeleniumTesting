pipeline {
    agent any

    stages {
        stage('Get src code') {
            steps {
                git 'https://github.com/SanjayAdwani/SeleniumTest.git'
                echo 'Hello World'
            }
        }
      stage('Build Stage') {
            steps {
              bat 'mvn compile'
            }
        }
        stage('Test Stage') {
            steps {
               bat 'mvn test -Dbrowser=localchrome'
            }
        }
        stage('Deployment Stage'){
            steps{
publishHTML([allowMissing: false, alwaysLinkToLastBuild: true, keepAll: true, reportDir: '', reportFiles: 'target/surefire-reports/SReport*.html', reportName: 'SeleniumPipeline', reportTitles: '', useWrapperFileDirectly: true])
            }
        }
        
    }
     post {
        // Clean after build
        always {
            cleanWs(cleanWhenNotBuilt: false,
                    deleteDirs: true,
                    disableDeferredWipeout: true,
                    notFailBuild: true,
                    patterns: [[pattern: '.gitignore', type: 'INCLUDE'],
                               [pattern: '.propsfile', type: 'EXCLUDE']])
        }
    }
}
