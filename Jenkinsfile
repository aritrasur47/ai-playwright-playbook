pipeline {
    agent any

    options {
        buildDiscarder(logRotator(numToKeepStr: '5'))
    }

    parameters {
        choice(name: 'BROWSER', choices: ['chromium', 'firefox'], description: 'Browser to run the suite against')
        string(name: 'CUCUMBER_TAGS', defaultValue: 'not @demo-failure', description: 'Cucumber tag expression to filter scenarios')
    }

    environment {
        PATH = "/opt/homebrew/bin:/opt/homebrew/opt/openjdk@21/bin:${env.PATH}"
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build') {
            steps {
                sh 'mvn -B test-compile'
            }
        }

        stage('Resolve Classpath') {
            steps {
                sh 'mvn -B dependency:build-classpath -Dmdep.outputFile=target/cp.txt -q'
            }
        }

        stage('Test') {
            steps {
                catchError(buildResult: 'UNSTABLE', stageResult: 'UNSTABLE') {
                    sh '''
                        CP=$(cat target/cp.txt):target/classes:target/test-classes
                        java -Dbrowser=${BROWSER} \
                             -Dheadless=true \
                             -Dcucumber.filter.tags="${CUCUMBER_TAGS}" \
                             -cp "$CP" org.testng.TestNG -testclass runner.TestRunner
                    '''
                }
            }
        }

        stage('Allure Report') {
            steps {
                sh 'mvn -B io.qameta.allure:allure-maven:2.12.0:report'
            }
        }
    }

    post {
        always {
            testNG(reportFilenamePattern: 'test-output/testng-results.xml')
            archiveArtifacts artifacts: 'target/allure-results/**, target/site/allure-maven-plugin/**', allowEmptyArchive: true
        }
    }
}
