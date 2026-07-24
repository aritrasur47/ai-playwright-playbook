pipeline {
    agent any

    parameters {
        choice(name: 'BROWSER', choices: ['chromium', 'firefox'], description: 'Browser to run the suite against')
        string(name: 'CUCUMBER_TAGS', defaultValue: 'not @demo-failure', description: 'Cucumber tag expression to filter scenarios')
    }

    stages {
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
                sh '''
                    CP=$(cat target/cp.txt):target/classes:target/test-classes
                    java -Dbrowser=${BROWSER} \
                         -Dheadless=true \
                         -Dcucumber.filter.tags="${CUCUMBER_TAGS}" \
                         -cp "$CP" org.testng.TestNG -testclass runner.TestRunner
                '''
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
            archiveArtifacts artifacts: 'target/allure-results/**, target/site/allure-maven-plugin/**', allowEmptyArchive: true
        }
    }
}
