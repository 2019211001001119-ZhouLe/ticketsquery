package team.seven.ticketsquery.handler;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import team.seven.ticketsquery.domain.ResultVO;
import team.seven.ticketsquery.enums.ResultStatusEnum;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 匿名登录处理器
 */
@Component
public class UserAuthenticationEntryPoint implements AuthenticationEntryPoint {

    /**
     * 用户未登录返回信息
     * @param request
     * @param response
     * @param authException
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        ResultVO resultVO = new ResultVO(ResultStatusEnum.USER_NOT_LOGIN);
        resultVO.out(response);
    }
}
