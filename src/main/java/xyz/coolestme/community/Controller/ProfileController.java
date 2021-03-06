package xyz.coolestme.community.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import xyz.coolestme.community.dto.PageinationDTO;
import xyz.coolestme.community.mapper.UserMapper;
import xyz.coolestme.community.model.User;
import xyz.coolestme.community.service.NotificationService;
import xyz.coolestme.community.service.QuestionService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class ProfileController {

//    @Autowired
//    private UserMapper userMapper;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/profile/{action}")
    public String profile(@PathVariable(name = "action") String action,
                          Model model,
                          HttpServletRequest request,
                          @RequestParam(name = "page",defaultValue = "1") Integer page,
                          @RequestParam(name = "size",defaultValue = "5") Integer size){

//        User user = null;
//        Cookie[] cookies = request.getCookies();
//        //if (cookies != null &&cookies.length!= 0)
//        for (Cookie cookie : cookies){
//            if (cookie.getName().equals("token")){
//                String token = cookie.getValue();
//                System.out.println(token);
//                //token = "123";
//                user = userMapper.findByToken(token);
//                if (user != null){
//                    request.getSession().setAttribute("user",user);
//                    System.out.println(token);
//                }
//                break;
//            }
//        }

        User user = (User) request.getSession().getAttribute("user");

        if (user == null){
            return "redirect:/";
        }

        if ("question".equals(action)){
            model.addAttribute("section","question");
            model.addAttribute("sectionName","我的提问");

            PageinationDTO pageinationDTO  = questionService.list(user.getId(),page,size);
            model.addAttribute("pageination",pageinationDTO);
        }else if ("replies".equals(action)){
            PageinationDTO pageinationDTO = notificationService.list(user.getId(),page,size);
            Long unreadCount = notificationService.unreadCount(user.getId());

            model.addAttribute("section","replies");
            model.addAttribute("pagnation",pageinationDTO);
            model.addAttribute("sectionName","最新回复");
        }
        return "profile";
    }
}
