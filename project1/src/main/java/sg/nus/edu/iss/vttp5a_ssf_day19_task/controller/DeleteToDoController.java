package sg.nus.edu.iss.vttp5a_ssf_day19_task.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import sg.nus.edu.iss.vttp5a_ssf_day19_task.constants.Constants;
import sg.nus.edu.iss.vttp5a_ssf_day19_task.service.ToDoService;

@Controller
@RequestMapping("/delete")
public class DeleteToDoController {
    
    @Autowired
    ToDoService toDoService;

    @GetMapping("/{toDo-id}")
    public ModelAndView deleteToDo(@PathVariable("toDo-id") String toDoID){
        ModelAndView mav = new ModelAndView();
        toDoService.delete(Constants.REDISKEY, toDoID);
        mav.setViewName("redirect:/list");
        return mav;
    }
}
