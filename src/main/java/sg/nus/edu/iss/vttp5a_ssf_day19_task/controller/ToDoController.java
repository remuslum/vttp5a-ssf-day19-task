package sg.nus.edu.iss.vttp5a_ssf_day19_task.controller;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import sg.nus.edu.iss.vttp5a_ssf_day19_task.constants.Constants;
import sg.nus.edu.iss.vttp5a_ssf_day19_task.model.LocalDateConverter;
import sg.nus.edu.iss.vttp5a_ssf_day19_task.model.ToDo;
import sg.nus.edu.iss.vttp5a_ssf_day19_task.service.ToDoService;

@Controller
@RequestMapping()
public class ToDoController {

    @Autowired
    ToDoService toDoService;
    
    @GetMapping("/list")
    public ModelAndView getToDoList(){
        LocalDateConverter localDateConverter = new LocalDateConverter();
        ModelAndView mav = new ModelAndView();
        Map<String, String> entries = toDoService.getEntries(Constants.REDISKEY);
        List<ToDo> toDoList = new ArrayList<>();

        for(Entry<String, String> entry:entries.entrySet()){
            JsonReader jsonReader = Json.createReader(new StringReader(entry.getValue()));
            JsonObject jsonObject = jsonReader.readObject();

            ToDo toDo = new ToDo(jsonObject.getString("id"),jsonObject.getString("name"), 
            jsonObject.getString("description"), localDateConverter.convert(Long.parseLong(jsonObject.getString("due_date"))), 
            jsonObject.getString("priority_value"), jsonObject.getString("status"), 
            localDateConverter.convert(Long.parseLong(jsonObject.getString("created_at"))), 
            localDateConverter.convert(Long.parseLong(jsonObject.getString("updated_at"))));

            toDoList.add(toDo);
        }
        mav.addObject("toDoList", toDoList);
        mav.setViewName("listing");
        return mav;
    }
}
