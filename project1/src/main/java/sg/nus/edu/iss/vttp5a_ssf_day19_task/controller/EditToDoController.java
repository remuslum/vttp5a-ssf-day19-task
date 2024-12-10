package sg.nus.edu.iss.vttp5a_ssf_day19_task.controller;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import jakarta.validation.Valid;
import sg.nus.edu.iss.vttp5a_ssf_day19_task.constants.Constants;
import sg.nus.edu.iss.vttp5a_ssf_day19_task.model.ToDo;
import sg.nus.edu.iss.vttp5a_ssf_day19_task.service.ToDoService;

@Controller
@RequestMapping("/edit")
public class EditToDoController {
    
    @Autowired 
    ToDoService toDoService;

    @GetMapping("/{toDo-id}")
    public ModelAndView getEditPage(@PathVariable("toDo-id") String toDoID){
        ModelAndView mav = new ModelAndView();
        ToDo toDo = toDoService.getToDo(Constants.REDISKEY, toDoID);
        System.out.println(toDo.getId());
        System.out.println(toDo.getName());
        System.out.println(toDo.getDescription());

        mav.addObject("toDo", toDo);
        mav.setViewName("edit");
        return mav;
    }

    @PostMapping("")
    public ModelAndView editToDo(@Valid @ModelAttribute("toDo") ToDo toDo, BindingResult bindingResult){
        ModelAndView mav = new ModelAndView();
        if(bindingResult.hasErrors()){
            mav.setViewName("edit");
        } else {
            long daysBetween = ChronoUnit.DAYS.between(LocalDate.now(), toDo.getDueDate());
            if(daysBetween < 0){
                FieldError error = new FieldError("toDo", "dueDate", "Due date cannot be in the past");
                bindingResult.addError(error);
                mav.setViewName("form");
            } else {
                toDoService.create(Constants.REDISKEY, toDo.getId(), toDoService.createJSONString(toDo));
                mav.setViewName("redirect:/list");
            }
        }
        return mav;
    }
}
