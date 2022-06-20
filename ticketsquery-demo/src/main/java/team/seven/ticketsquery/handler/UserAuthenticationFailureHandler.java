package team.seven.ticketsquery.handler;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import team.seven.ticketsquery.domain.ResultVO;
import team.seven.ticketsquery.enums.ResultStatusEnum;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录失败处理器
 */
@Component
public class UserAuthenticationFailureHandler implements AuthenticationFailureHandler {

    /**
     * 用户登录失败后返回
     * @param request
     * @param response
     * @param exception
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        ResultVO resultVO = new ResultVO(ResultStatusEnum.USER_LOGIN_FAIL);
        resultVO.out(response);
    }
}
