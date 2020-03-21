package xyz.coolestme.community.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import xyz.coolestme.community.mapper.UserMapper;
import xyz.coolestme.community.model.User;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class SessionInterceptor implements HandlerInterceptor {

    @Autowired
    private UserMapper userMapper;

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
                    User user = userMapper.findByToken(token);
                    //System.out.println("get select:" + user.getToken());
                    if (user != null){
                        request.getSession().setAttribute("user",user);
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
