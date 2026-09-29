package jobTemplates


class MavenDeployment {
     
        void create(pipelineJob, job_config){


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
    }
}
            """)
            sandbox()
        }
     }
    }
}

}
