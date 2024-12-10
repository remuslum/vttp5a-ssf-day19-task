package sg.nus.edu.iss.vttp5a_ssf_day19_task2.controller.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sg.nus.edu.iss.vttp5a_ssf_day19_task2.service.FileReaderService;
import sg.nus.edu.iss.vttp5a_ssf_day19_task2.util.Constants;

@RestController
@RequestMapping("/load")
public class LoadProductsDataRestController {
    
    @Autowired
    FileReaderService fileReaderService;

    @GetMapping
    public ResponseEntity<String> loadJSONToRedis(){
        fileReaderService.readJSONFile(Constants.REDISKEY);
        return ResponseEntity.status(HttpStatusCode.valueOf(201)).body("File successfully uploaded");
    }
}
