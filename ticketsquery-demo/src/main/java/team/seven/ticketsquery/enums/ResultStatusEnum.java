package team.seven.ticketsquery.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * @description: 返回枚举
 * @author: ZhouLe
 * @create: 2022-06-10
 * @version: 1.0
 */
@Getter
@AllArgsConstructor
public enum ResultStatusEnum {

    SUCCESS(200, "OK"),
    CREATE_SUCCESS(201, "Created"),
    DELETE_SUCCESS(204, "No Content"),
    BAD_REQUEST(400,"Bad Request"),
    UNAUTHORIZED(401, "Unauthorized"),
    FORBIDDEN(403, "Forbidden"),
    NOT_FOUND(404, "Not Found"),
    METHOD_NOT_ALLOWED(405, "Method Not Allowed"),
    GONE(410, "Gone"),
    UNSUPPORTED_MEDIA_TYPE(415, "Unsupported Media Type"),
    UNPROCESSABLE_ENTITY(422, "Unprocessable Entity"),
    TOO_MANY_REQUESTS(429, "Too Many Requests"),
    INTERNAL_SERVER_ERROR(500,"Internal Server Error"),
    SERVICE_UNAVAILABLE(503, "Service Unavailable"),

    //1000系列  通用错误
    FAILED(1001,"接口错误"),
    VALIDATE_FAILED(1002,"参数校验失败"),
    ERROR(1003,"未知错误"),
    FILE_SIZE_OVERFLOW(1004,"上传文件字节被限制"),

    //2000系列 用户错误
    USER_NOT_EXIST(2000,"用户不存在"),
    USER_LOGIN_FAIL(2001,"用户名或密码错误"),
    USER_NOT_LOGIN(2002,"用户还未登录，请先登录"),
    NO_PERMISSION(2003,"权限不足，请联系管理员"),
    USER_ADD_FAILED(2004,"用户添加失败"),
    USER_UPDATE_FAILED(2005,"用户更新失败"),
    USER_REMOVE_FAILED(2006,"用户删除失败"),

    //3000系列 新闻错误
    NEWS_ADD_FAILED(3001,"新闻添加失败"),
    NEWS_UPDATE_FAILED(3002,"新闻更新失败"),
    NEWS_REMOVE_FAILED(3003,"新闻删除失败");

    private final Integer code;
    private final String msg;
}
