package xyz.coolestme.community.exception;

public enum CustomizeErrorCode implements ICustomizeErrorCode {

    QUESTION_NOT_FOUND(2001,"该问题不存在，请确认或查看其他问题"),
    TARGET_PARAM_NOT_FOUND(2002,"未选中任何问题或评论回复"),
    NO_LOGIN(2003,"请登陆后重试"),
    SYSTEM_ERROR(2004,"服务器错误"),
    TYPE_PARAM_WRONG(2005,"评论类型错误或不存在"),
    COMMENT_NOT_FOUND(2006,"回复的评论不存在"),
    CONTENT_ID_EMPTY(2007,"输入内容不能为空"),
    READ_NOTIFICATION_FAIL(2008,"只能查看自己的消息"),
    NOTIFICATION_NOT_FOUND(2009,"消息没有找到")
    ;

    private Integer code;
    private String message;

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    CustomizeErrorCode(Integer code, String message) {
        this.message = message;
        this.code = code;
    }
}
