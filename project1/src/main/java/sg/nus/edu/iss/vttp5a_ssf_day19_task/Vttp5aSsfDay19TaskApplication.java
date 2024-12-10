package sg.nus.edu.iss.vttp5a_ssf_day19_task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import sg.nus.edu.iss.vttp5a_ssf_day19_task.service.FileReaderService;

@SpringBootApplication
public class Vttp5aSsfDay19TaskApplication implements CommandLineRunner{

	@Autowired
	FileReaderService fileReaderService;

	public static void main(String[] args) {
		SpringApplication.run(Vttp5aSsfDay19TaskApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		for(String arg : args){
			if(arg.startsWith("--dataDir=")){
				String dataDir = arg.substring("--dataDir=".length());
				fileReaderService.loadFileToRedis(dataDir);
			}
		}
	}

}
