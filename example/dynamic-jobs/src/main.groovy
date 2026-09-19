//println("HHello World !!!")

@Grab('org.yaml:snakeyaml:1.17')

import org.yaml.snakeyaml.Yaml
import hudson.model.*

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
pipeline_file_list = searchYamlFiles(cwd)


//println("YAML files found:")
//println("----------------")


// Process each YAML file
for (current_pipeline in pipeline_file_list) {
    println("Working on: " + current_pipeline)
}