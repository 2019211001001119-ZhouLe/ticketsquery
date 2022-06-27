package team.seven.ticketsquery.configuration;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import team.seven.ticketsquery.domain.ResultVO;
import team.seven.ticketsquery.enums.ResultStatusEnum;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

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

    @ExceptionHandler(value = UsernameNotFoundException.class)
    public <T> ResultVO<T> handleUserNameNotFoundException(Exception e) {
        //e.printStackTrace();
        return new ResultVO<>(ResultStatusEnum.USER_NOT_EXIST); //默认异常处理 返回code为400
    }

    /**
     * 全局异常处理 处理参数校验异常
     * @param be
     * @return
     */
    @ExceptionHandler(value = BindException.class)
    public ResultVO handleValidationException(BindException be){
        //创建集合保存错误描述
        List<String> data=new ArrayList<>();
        //用于接收bean的校验信息
        BindingResult bindingResult = be.getBindingResult();
        //获取到校验错误的字段信息集合
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        //循环获取错误提示信息
        for(FieldError error:fieldErrors){
            //获取错误提示信息
            data.add(error.getDefaultMessage());
        }
        return new ResultVO(ResultStatusEnum.VALIDATE_FAILED,data);
    }

    /**
     * 全局异常处理 处理参数校验异常
     * @param cve
     * @return
     */
    @ExceptionHandler(value = ConstraintViolationException.class)
    public ResultVO handleValidationException(ConstraintViolationException cve){
        List<String> data=new ArrayList<>();
        //获取校验错误信息集合
        for(ConstraintViolation violation:cve.getConstraintViolations()){
            data.add(violation.getMessage());
        }
        return new ResultVO(ResultStatusEnum.VALIDATE_FAILED,data);
    }
}
