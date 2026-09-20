//println("HHello World !!!")

//@Grab('org.yaml:snakeyaml:1.17')
//import org.yaml.snakeyaml.Yaml

import hudson.model.*
import utils.JobUtils

// Function to search for all files ending with .yaml or .yml
ArrayList searchYamlFiles(String dirPath) {
    ArrayList file_list = []
    File dir = new File(dirPath)
    if (dir.isDirectory()) {
        dir.eachFileRecurse { File file ->
            if (file.isFile() &&
                (file.name.endsWith(".yaml") || file.name.endsWith(".yml"))) {
                file_list.add(file.absolutePath)
            }
        }
    } else {
        println("Error: $dirPath is not a valid directory.")
    }
    return file_list
}


// Get the current Jenkins workspace
def cwd = hudson.model.Executor.currentExecutor()
        .getCurrentWorkspace()
        .absolutize()

// Search for YAML files
pipeline_file_list = searchYamlFiles(cwd.toString())


//println("YAML files found:")
//println("----------------")


// Process each YAML file
for (current_pipeline in pipeline_file_list) {
    println("Working on: " + current_pipeline)
    //parsing the yaml content
    //parsed_job_config = new Yaml().load((current_pipeline as File).text)
    JobUtils job_config = new JobUtils(current_pipeline)

    println("jobname is : "+job_config.get_job_name() )
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



    pipelineJob(job_config.get_job_name()) {
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