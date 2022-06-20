package team.seven.ticketsquery.handler;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;
import team.seven.ticketsquery.domain.ResultVO;
import team.seven.ticketsquery.enums.ResultStatusEnum;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 退出登录成功处理器
 */
@Component
public class UserLogoutSuccessHandler implements LogoutSuccessHandler {

    /**
     * 退出登录方法
     * @param request
     * @param response
     * @param authentication
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        //清楚登录信息
        SecurityContextHolder.clearContext();
        ResultVO resultVO = new ResultVO(ResultStatusEnum.USER_NOT_LOGIN);
        resultVO.out(response);
    }
}
