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
    job(job_config.get_job_name()) {
      steps {
        shell('echo Hello World!')
       }
    }
}