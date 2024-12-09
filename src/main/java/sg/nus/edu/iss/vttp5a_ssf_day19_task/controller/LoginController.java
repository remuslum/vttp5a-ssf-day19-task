package sg.nus.edu.iss.vttp5a_ssf_day19_task.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/login")
public class LoginController {

    @GetMapping 
    public String getLoginPage(){
        return "login";
    }

    @PostMapping
    public ModelAndView loginUser(@RequestBody MultiValueMap<String, String> params, HttpSession httpSession){
        ModelAndView mav = new ModelAndView();
        String username = params.getFirst("username");
        String password = params.getFirst("password");
        String dateOfBirth = params.getFirst("dateOfBirth");
        System.out.println(dateOfBirth);

        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(dateOfBirth, format);
        
        if(ChronoUnit.YEARS.between(date,LocalDate.now()) < 10){
            mav.setViewName("underage");
        } else {
            httpSession.setAttribute("username", username);
            httpSession.setAttribute("password", password);

            mav.setViewName("redirect:/list");
        }
        return mav;

    }
}
