package xyz.coolestme.community.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import xyz.coolestme.community.dto.PageinationDTO;
import xyz.coolestme.community.dto.QuestionDTO;
import xyz.coolestme.community.mapper.QuestionMapper;
import xyz.coolestme.community.mapper.UserMapper;
import xyz.coolestme.community.model.Question;
import xyz.coolestme.community.model.User;
import xyz.coolestme.community.service.QuestionService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class IndexController {

//    @Autowired
//    private UserMapper userMapper;

    @Autowired
    private QuestionService questionService;

    @GetMapping("/")
    public String index(HttpServletRequest request,
                        Model model,
                        @RequestParam(name = "page",defaultValue = "1") Integer page,
                        @RequestParam(name = "size",defaultValue = "5") Integer size){
/*
        Cookie[] cookies = request.getCookies();
        //if (cookies != null &&cookies.length!= 0)
        for (Cookie cookie : cookies){
            if (cookie.getName().equals("token")){
                String token = cookie.getValue();
                System.out.println(token);
                //token = "123";
                User user = userMapper.findByToken(token);
                if (user != null){
                    request.getSession().setAttribute("user",user);
                    System.out.println(token);
                }
                break;
            }
        }
*/

        PageinationDTO pageination = questionService.list(page,size);
        model.addAttribute("pageination",pageination);
        return "index";
    }
}
