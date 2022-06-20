package team.seven.ticketsquery.configuration;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import team.seven.ticketsquery.domain.ResultVO;
import team.seven.ticketsquery.enums.ResultStatusEnum;

/**
 * @description: API相应
 * @author: ZhouLe
 * @create: 2022-06-10
 * @version: 1.0
 */
@RestControllerAdvice(annotations = RestController.class)
public class GlobalExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    public <T> ResultVO<T> handleAllException(Exception e) {
        e.printStackTrace();
        return new ResultVO<>(ResultStatusEnum.BAD_REQUEST); //默认异常处理 返回code为400
    }

}
