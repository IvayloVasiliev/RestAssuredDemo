pipeline {
    agent any

    options {
        buildDiscarder(logRotator(numToKeepStr: '10'))
        timestamps()
        timeout(time: 30, unit: 'MINUTES')
    }

    environment {
        JAVA_HOME = tool 'JDK21'
        MAVEN_HOME = tool 'MAVEN3'
        PATH = "${MAVEN_HOME}/bin:${JAVA_HOME}/bin:${PATH}"
    }

    parameters {
        choice(
            name: 'TEST_SUITE',
            choices: ['all', 'books-happy', 'books-edge', 'authors'],
            description: 'Select which test suite to run'
        )
        string(
            name: 'TIMEOUT_MINUTES',
            defaultValue: '15',
            description: 'Test execution timeout in minutes'
        )
    }

    stages {
        stage('Checkout') {
            steps {
                echo '═══════════════════════════════════════════════════════'
                echo 'Stage: Checkout Code'
                echo '═══════════════════════════════════════════════════════'
                checkout scm
                sh 'echo "Checking out branch: ${GIT_BRANCH}"'
            }
        }

        stage('Build') {
            steps {
                echo '═══════════════════════════════════════════════════════'
                echo 'Stage: Building Project'
                echo '═══════════════════════════════════════════════════════'
                sh 'mvn clean compile'
                echo '✓ Build completed successfully'
            }
        }

        stage('Test') {
            steps {
                echo '═══════════════════════════════════════════════════════'
                echo 'Stage: Running API Tests'
                echo '═══════════════════════════════════════════════════════'
                timeout(time: Integer.parseInt(params.TIMEOUT_MINUTES), unit: 'MINUTES') {
                    sh 'mvn test -DsuiteXmlFile=src/test/resources/testng.xml'
                }
                echo '✓ Test execution completed'
            }
        }

        stage('Report Generation') {
            steps {
                echo '═══════════════════════════════════════════════════════'
                echo 'Stage: Generating Test Reports'
                echo '═══════════════════════════════════════════════════════'
                sh '''
                    echo "Generating Allure Report..."
                    mvn allure:report || true
                    echo "✓ Allure Report generated"
                '''
            }
        }

        stage('Publish Results') {
            steps {
                echo '═══════════════════════════════════════════════════════'
                echo 'Stage: Publishing Test Results'
                echo '═══════════════════════════════════════════════════════'

                // Publish TestNG results
                junit testResults: 'target/surefire-reports/*.xml', allowEmptyResults: true

                // Archive Allure report
                archiveArtifacts artifacts: 'target/site/allure-maven-plugin/**', allowEmptyArchive: true

                // Archive surefire reports
                archiveArtifacts artifacts: 'target/surefire-reports/**', allowEmptyArchive: true
            }
        }
    }

    post {
        always {
            echo '═══════════════════════════════════════════════════════'
            echo 'Post-Build Actions'
            echo '═══════════════════════════════════════════════════════'

            // Clean workspace
            cleanWs(
                deleteDirs: true,
                patterns: [
                    [pattern: '**/target/surefire-reports', type: 'INCLUDE']
                ]
            )
        }

        success {
            echo '✓ Pipeline execution completed successfully'
            mail(
                subject: "Build Success: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                body: "The API tests passed successfully. Check Jenkins for details.",
                to: "${env.CHANGE_AUTHOR_EMAIL}",
                recipientProviders: [developers(), requestor()]
            )
        }

        failure {
            echo '✗ Pipeline execution failed'
            mail(
                subject: "Build Failure: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                body: "Some tests failed. Please check the build log and test reports.",
                to: "${env.CHANGE_AUTHOR_EMAIL}",
                recipientProviders: [developers(), requestor()]
            )
        }

        unstable {
            echo '⚠ Pipeline has unstable results'
        }
    }
}

