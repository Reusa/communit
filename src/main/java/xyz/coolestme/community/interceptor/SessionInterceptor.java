package xyz.coolestme.community.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import xyz.coolestme.community.mapper.UserMapper;
import xyz.coolestme.community.model.User;
import xyz.coolestme.community.model.UserExample;
import xyz.coolestme.community.service.NotificationService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Service
public class SessionInterceptor implements HandlerInterceptor {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private NotificationService notificationService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //请求前登陆状态
        Cookie[] cookies = request.getCookies();
        if (cookies != null &&cookies.length!= 0)
            for (Cookie cookie : cookies){
                if (cookie.getName().equals("token")){
                    String token = cookie.getValue();
                    //System.out.println("get cookie:" + token);
                    //token = "123";
                    UserExample userExample = new UserExample();
                    userExample.createCriteria().andTokenEqualTo(token);
                    List<User> users = userMapper.selectByExample(userExample);
//                    User user = userMapper.findByToken(token);
                    //System.out.println("get select:" + user.getToken());
                    if (users.size() != 0){
                        request.getSession().setAttribute("user",users.get(0));

                        Long unreadCount = notificationService.unreadCount(users.get(0).getId());
                        request.getSession().setAttribute("unreadMessage",unreadCount);
                        //System.out.println("interceptor:" + token);
                    }
                break;
                }
            }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
