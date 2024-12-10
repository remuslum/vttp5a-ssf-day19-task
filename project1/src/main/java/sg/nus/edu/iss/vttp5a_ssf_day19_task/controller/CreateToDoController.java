package sg.nus.edu.iss.vttp5a_ssf_day19_task.controller;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import jakarta.validation.Valid;
import sg.nus.edu.iss.vttp5a_ssf_day19_task.constants.Constants;
import sg.nus.edu.iss.vttp5a_ssf_day19_task.model.ToDo;
import sg.nus.edu.iss.vttp5a_ssf_day19_task.service.ToDoService;

@Controller
@RequestMapping("/create")
public class CreateToDoController {
    
    @Autowired
    ToDoService toDoService;

    @GetMapping()
    public ModelAndView createToDoPage(){
        ModelAndView mav = new ModelAndView();
        mav.addObject("toDo", new ToDo());
        mav.setViewName("form");
        return mav;
    }

    @PostMapping()
    public ModelAndView createToDoObject(@Valid @ModelAttribute("toDo") ToDo toDo, BindingResult bindingResult){
        ModelAndView mav = new ModelAndView();
        // mav.addObject("toDo", toDo);
        // mav.addObject("org.spring.framework.validation.BindingResult.toDo", bindingResult);
        
        if(bindingResult.hasErrors()){
            mav.setViewName("form");
        } else {
            // First argument is always the smaller date
            long daysBetween = ChronoUnit.DAYS.between(LocalDate.now(), toDo.getDueDate());
            if(daysBetween < 0){
                FieldError error = new FieldError("toDo", "dueDate", "Due date cannot be in the past");
                bindingResult.addError(error);
                mav.setViewName("form");
            } else {
                toDo.setId(UUID.randomUUID().toString());
                toDo.setCreatedAt(LocalDate.now());
                toDo.setUpdatedAt(LocalDate.now());
                toDoService.create(Constants.REDISKEY, toDo.getId(), toDoService.createJSONString(toDo));
                mav.setViewName("redirect:/list");
            }
        }
        return mav;
    }
}
