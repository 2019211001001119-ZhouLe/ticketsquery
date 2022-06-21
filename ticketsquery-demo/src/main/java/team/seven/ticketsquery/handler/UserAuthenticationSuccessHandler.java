package team.seven.ticketsquery.handler;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import team.seven.ticketsquery.domain.Admin;
import team.seven.ticketsquery.domain.ResultVO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录成功处理器
 */
@Component
public class UserAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    /**
     * 登录成功后处理
     * @param request
     * @param response
     * @param authentication
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        //从身份证中获取用户信息
        Admin  user = (Admin) authentication.getPrincipal();
        //把用户信息存入 安全上下文中
        SecurityContextHolder.getContext().setAuthentication(authentication);
        //返回当前登录用户的信息
        ResultVO resultVO = new ResultVO(user);
        resultVO.out(response);
    }
}
