package xyz.coolestme.community.service;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.coolestme.community.dto.NotificationDTO;
import xyz.coolestme.community.dto.PageinationDTO;
import xyz.coolestme.community.dto.QuestionDTO;
import xyz.coolestme.community.enums.NotificationStatusEnum;
import xyz.coolestme.community.enums.NotificationTypeEnum;
import xyz.coolestme.community.exception.CustomizeErrorCode;
import xyz.coolestme.community.exception.CustomizeException;
import xyz.coolestme.community.mapper.NotificationMapper;
import xyz.coolestme.community.mapper.UserMapper;
import xyz.coolestme.community.model.*;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class NotificationService {

    @Autowired
    private NotificationMapper notificationMapper;
    @Autowired
    private UserMapper userMapper;

    public PageinationDTO list(Long userId, Integer page, Integer size) {

        PageinationDTO<NotificationDTO> pageinationDTO = new PageinationDTO<>();

        NotificationExample notificationExample = new NotificationExample();
        notificationExample.createCriteria().andReceiverEqualTo(userId);
        Integer totalCount = (int)notificationMapper.countByExample(notificationExample);
        pageinationDTO.setPageination(totalCount,page,size);
        if (page < 1){
            page = 1;
        }
        if (page > pageinationDTO.getTotalPage()){
            page = pageinationDTO.getTotalPage();
        }

        //size*(page-1)
        Integer offset = size * (page - 1);

        NotificationExample example = new NotificationExample();
        example.createCriteria().andReceiverEqualTo(userId);
        example.setOrderByClause("gmt_create desc");
        List<Notification> notifications = notificationMapper.selectByExampleWithRowbounds(example,new RowBounds(offset,size));

        if (notifications.size() == 0){
            return pageinationDTO;
        }
        List<NotificationDTO> notificationDTOS = new ArrayList<>();
        for (Notification notification : notifications) {
            NotificationDTO notificationDTO = new NotificationDTO();
            BeanUtils.copyProperties(notification,notificationDTO);
            notificationDTO.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));
            notificationDTOS.add(notificationDTO);
        }

        pageinationDTO.setData(notificationDTOS);
        return pageinationDTO;
    }

    public Long unreadCount(Long userId) {
        NotificationExample notificationExample = new NotificationExample();
        notificationExample.createCriteria().andReceiverEqualTo(userId).andStatusEqualTo(NotificationStatusEnum.UNREAD.getStatus());
        return notificationMapper.countByExample(notificationExample);
    }

    public NotificationDTO read(Long id, User user) {
        Notification notification = notificationMapper.selectByPrimaryKey(id);
        if (notification == null){
            throw new CustomizeException(CustomizeErrorCode.NOTIFICATION_NOT_FOUND);
        }
        if (!Objects.equals(notification.getReceiver(),user.getId())){
            throw new CustomizeException(CustomizeErrorCode.READ_NOTIFICATION_FAIL);
        }
        notification.setStatus(NotificationStatusEnum.READ.getStatus());
        notificationMapper.updateByPrimaryKey(notification);

        NotificationDTO notificationDTO = new NotificationDTO();
        BeanUtils.copyProperties(notification,notificationDTO);
        notificationDTO.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));

        return notificationDTO;
    }
}
