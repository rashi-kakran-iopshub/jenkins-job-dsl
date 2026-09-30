package utils

@Grab('org.yaml:snakeyaml:1.17')
import org.yaml.snakeyaml.Yaml

class JobUtils {
    def job_config
    def file_path

    JobUtils(current_pipeline){
        //parsing the yaml content
        def parsed_job_config = new Yaml().load((current_pipeline as File).text)
        this.job_config = parsed_job_config
        this.file_path = current_pipeline
    } 

    String get_job_name(){
        String slash_split = this.file_path.split("/")[-1]
        def job_name = slash_split.split(".yaml")[0].split(".yml")[0]
        return job_name
    }

   
    String get_build_command(){
        return this.job_config.build_command
    }

    ArrayList get_environments(){
        return this.job_config.env
    }

    String get_job_type(){
        return this.job_config.job_type
    }
    
}