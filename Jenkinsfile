// pipeline{
//     agent any
//     tools{
//         maven 'mvn-3.5.4'
//     }
//     stages{
//         stage('Build'){
//             steps{
//                 sh "mvn clean package spring-boot:repackage"
//                 sh "printenv"
//             }
//         }
//     }
// }
// pipeline{
//     agent any
//     stages{
//         stage('deploy'){
//             steps{
//                 input message: 'start or stop'
//             }
//         }
//     }
// }

pipeline{
    agent any
    // post{
    //     always{
    //         cleanWs()
    //     }
    // }
    parameters {
        booleanParam(defaultValue: true, description: '', name: 'userFLag')
        string(name: 'DEPLOY_ENV', defaultValue: 'staging', description: '')
    }
    triggers {
        cron('0 0 * * *')
        pollSCM('H/1 * * * *')
        upstream(upstreamProjects: 'job1, job2', threshold: hudson.model.Result.SUCCESS)
    }
    stages{
        stage('performance test'){
            when{
                changelog '**/*.js'
                environment name: 'DEPLOY', value: 'production'
                equals expected: 2, actual: currentBuild.numer
                buildingTag()
                tag "release-*"
                expression{return readFIle('pom.xml').contains('mycomponent')}
            }
            steps{
                build(
                    job: "parameters-example",
                    parameters: [
                        booleanParam(name: 'userFlag', value: true)
                    ]
                )
                // script{
                //     def browsers = ['chrome', 'firefox']
                //     for (int i =0; i<browsers.size(); ++i){
                //         echo "Testing the ${browsers[i]} browser"
                //     }
//                     echo "Running ${BUILD_NUMBER} on ${JENKINS_URL}"
                    bzt params: 'blaze_exist_jmeter_config.yml'
                }
            }
        }
        stage('deploy to test'){
            steps{
                script{
                    if(env.GIT_BRANCH == 'master'){
                        echo 'deploy to test env'
                    }
                }
            }
        }
    }
    pipeline {
        agent any

        stages {
            stage('pre deploy') {
                steps {
                    script{
                        approvalMap = input(
                            message: 'ready?',
                            ok: 'sure',
                            parameters: [
                                choice(choices: 'dev\ntest\nprod', description: 'where', name: 'ENV'),
                                string(defaultValue: '', description: '', name: 'myparam')
                                ],
                                submitter: 'admin, admin2, releaseGroup',
                                submitterParameter: 'APPROVER'
                            )
                    }
                }
            }

            stage('deploy'){
                steps{
                    echo "operators ${approvalMap['APPROVER']}"
                }
            }
        }
    }

pipeline{
    agent any

    stages{
        stage('build'){
            failFast true
            parallel{
                stage('test on chrome'){
                    agent {label 'chrome'}
                    steps{

                    }

                }
                stage('test on firefox'){
                    agent {label 'firefox'}
                    steps{
                    echo 'firefox ui test'
                    }
                }           }
        }
    }
}

// share library
@library(['global-shared-library']) _
generatePipeline('go')