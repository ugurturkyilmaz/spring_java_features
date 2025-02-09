pipeline {
    agent any
    tools {
        // Eğer Jenkins'e global olarak bir Gradle tanımladıysan, burada kullanabilirsin
        gradle 'Gradle_7.4'  // Global Tool Configuration'da tanımladığın isimle aynı olmalı
    }

    environment {
        SONAR_ORGANIZATION = 'ugurturkyilmaz'  // SonarCloud organizasyon adın
        SONAR_PROJECT_KEY = 'spring_java_features'          // SonarCloud proje anahtarın
        SONAR_TOKEN = credentials('sonar_token')
    }

    stages {
        stage('Clone Repo') {
            steps {
                git branch: 'master', url: 'https://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@github.com/ugurturkyilmaz/spring_java_features.git'

            }
        }
        stage('Build') {
            steps {
                // Eğer projede gradlew varsa, aşağıdaki komutu kullan:
                sh './gradlew clean build'


            }
        }
        stage('SonarCloud Analysis') {
            steps {
                withSonarQubeEnv('SonarCloud') {  // Configure System'de tanımladığın SonarCloud adı
                    sh """
                        ./gradlew sonarqube \
                        -Dsonar.projectKey=$SONAR_PROJECT_KEY \
                        -Dsonar.organization=$SONAR_ORGANIZATION \
                        -Dsonar.host.url=https://sonarcloud.io \
                        -Dsonar.login=$SONAR_TOKEN
                    """
                }
            }
        }
    }
}
