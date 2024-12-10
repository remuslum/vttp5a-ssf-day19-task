package sg.nus.edu.iss.vttp5a_ssf_day19_task.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpSession;
import sg.nus.edu.iss.vttp5a_ssf_day19_task.constants.Constants;
import sg.nus.edu.iss.vttp5a_ssf_day19_task.model.ToDo;
import sg.nus.edu.iss.vttp5a_ssf_day19_task.service.ToDoService;

@Controller
@RequestMapping("/list")
public class ToDoController {

    @Autowired
    ToDoService toDoService;
    
    @GetMapping()
    public ModelAndView getToDoList(HttpSession httpSession){
        ModelAndView mav = new ModelAndView();
        if(httpSession.getAttribute("username") == null || httpSession.getAttribute("password") == null){
            mav.setViewName("refused");
        } else {
            List<ToDo> toDoList = toDoService.getToDoListFromRedis(Constants.REDISKEY);
            mav.addObject("toDoList", toDoList);
            mav.setViewName("listing");
        }
        
        return mav;
    }

    @PostMapping()
    public ModelAndView filterToDoList(@RequestParam String priority){
        System.out.println(priority);
        ModelAndView mav = new ModelAndView();
        List<ToDo> filteredList = toDoService.getFilteredList(Constants.REDISKEY, priority);
        System.out.println(filteredList);
        mav.addObject("toDoList", filteredList);
        mav.setViewName("listing");
        return mav;
    }
}
