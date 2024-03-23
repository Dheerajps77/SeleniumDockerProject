pipeline {
  
   agent any

   stages{
      stage('Setting Up Selenium Grid') {
         steps{        
            sh "docker network create ${network}"
            sh "docker run -d -p 4444:4444 --name ${seleniumHub} --network ${network} selenium/hub"
            sh "docker run -d -e HUB_PORT_4444_TCP_ADDR=${seleniumHub} -e HUB_PORT_4444_TCP_PORT=4444 --network ${network} --name ${chrome} selenium/node-chrome"
            sh "docker run -d -e HUB_PORT_4444_TCP_ADDR=${seleniumHub} -e HUB_PORT_4444_TCP_PORT=4444 --network ${network} --name ${firefox} selenium/node-firefox"
         }
         script {
                    sh "docker build -t YOUR_APP_NAME:TAG_VERSION -f Dockerfile ."
                    // below the line to create container name
                    sh "docker create --name auto-test-result YOUR_APP_NAME:TAG_VERSION" 
                    // create directory to paste result build from container 
                    sh "mkdir extent-reports"
                    // co	py paste test result to dir in Jenkins
                    sh "docker cp auto-test-result:/usr/app/extent-reports/extent-report.html extent-reports/extent-report.html"
                }
      }
      stage('Run Test') {
         steps{
            parallel(
               "search-module":{
                  sh "docker run --rm -e SELENIUM_HUB=${seleniumHub} -e BROWSER=firefox -e MODULE=search-module.xml -v ${WORKSPACE}/search:/usr/share/tag/test-output --network ${network} vinsdocker/containertest"
                  archiveArtifacts artifacts: 'search/**', fingerprint: true
               },
               "order-module":{
                  sh "docker run --rm -e SELENIUM_HUB=${seleniumHub} -e BROWSER=chrome -e MODULE=order-module.xml -v ${WORKSPACE}/order:/usr/share/tag/test-output  --network ${network} vinsdocker/containertest"
                  archiveArtifacts artifacts: 'order/**', fingerprint: true
               }               
            ) 
         }
          success {
                    publishHTML([
                        allowMissing: false, 
                        alwaysLinkToLastBuild: false, 
                        keepAll: false, 
                        reportDir: 'extent-reports', 
                        reportFiles: 'extent-report.html', 
                        reportName: 'HTML Report', 
                        reportTitles: '', 
                        useWrapperFileDirectly: true
                    ])
                }
      }
    }
    post{
      always {
         sh "docker rm -vf ${chrome}"
         sh "docker rm -vf ${firefox}"
         sh "docker rm -vf ${seleniumHub}"
         sh "docker network rm ${network}"
      }   
   }
}