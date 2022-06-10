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
    SERVICE_UNAVAILABLE(503, "Service Unavailable");
    private final Integer code;
    private final String msg;
}
