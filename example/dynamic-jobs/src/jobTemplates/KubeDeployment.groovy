package jobTemplates


class KubeDeployment {

    void create(pipelineJob, job_config){
        def env_list = job_config.get_environments()

    def dev_stage = ""
    def qa_stage = ""
    def prod_stage  = ""


    if ("dev" in env_list){ 
        dev_stage = """
            stage('Deploy DEV'){
                steps {
                    echo 'Deploying the project...'
                }
            }
    """
    }

    if ("qa" in env_list){
        qa_stage = """
            stage('Deploy QA'){
                steps {
                    echo 'Deploying the project...'
                }
            }
    """   
    }

    if ("prod" in env_list){
        prod_stage = """
            stage('Deploy PROD'){
                steps {
                    echo 'Deploying the project...'
                }
            }
    """   
    }



    pipelineJob.with {
    definition {
        cps {
            script(""" 
pipeline {
    agent any 

    stages{

        stage('Checkout'){
            steps {
                echo 'Checking out source code...'
            }
        }

        stage('Build'){
            steps {
                echo '${job_config.get_build_command()}'
            }
        }

        stage('Test'){
            steps {
                echo 'Running Test...'
            }
        }
     
        ${dev_stage}
        ${qa_stage}
        ${prod_stage}

    }
}
            """)
            sandbox()
        }
     }
    }
    // job(job_config.get_job_name()) {
    //   steps {
    //     shell('echo Hello World!')
    //    }
    // }
    
}

}