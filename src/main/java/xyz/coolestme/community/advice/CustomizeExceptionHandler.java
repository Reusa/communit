package xyz.coolestme.community.advice;

import com.alibaba.fastjson.JSON;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import xyz.coolestme.community.dto.ResultDTO;
import xyz.coolestme.community.exception.CustomizeErrorCode;
import xyz.coolestme.community.exception.CustomizeException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@ControllerAdvice
public class CustomizeExceptionHandler {

    @ExceptionHandler(Exception.class)
    Object handle(HttpServletRequest request, Throwable e, Model model, HttpServletResponse response){
//        HttpStatus status = getStatus(request);

        String contentType = request.getContentType();
        if ("application/json".equals(contentType)){
            //返回json
            ResultDTO resultDTO = null;
            if (e instanceof CustomizeException){
                resultDTO = ResultDTO.errorOf((CustomizeException)e);
            }else {
                resultDTO = ResultDTO.errorOf(CustomizeErrorCode.SYSTEM_ERROR);
            }

            try {
                response.setContentType("application/json");
                response.setStatus(200);
                response.setCharacterEncoding("UTF-8");
                PrintWriter writer = response.getWriter();
                writer.write(JSON.toJSONString(resultDTO));
                writer.close();
            } catch (IOException ioe) {
            }
            return null;
        }else {
            //错误页面跳转
            if (e instanceof CustomizeException){
                model.addAttribute("message",e.getMessage());
            }else {
                model.addAttribute(CustomizeErrorCode.SYSTEM_ERROR);
            }
            return new ModelAndView("error");
        }
    }

    private HttpStatus getStatus(HttpServletRequest request){
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null){
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }
}
