package xyz.coolestme.community.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import xyz.coolestme.community.dto.FileDTO;

@Controller
public class FileController {

    @RequestMapping("/file/upload")
    @ResponseBody
    public Object upload(){
        FileDTO fileDTO = new FileDTO();
        fileDTO.setSuccess(1);
        fileDTO.setUrl("/image/641.jpeg");
        return fileDTO;
    }
}
