package team.seven.ticketsquery.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import team.seven.ticketsquery.domain.Admin;
import team.seven.ticketsquery.enums.ResultStatusEnum;
import team.seven.ticketsquery.mapper.AdminMapper;

/**
 * User: 谢礼治
 * Date: 2022/6/13
 * Time: 9:06
 * Description: No Description
 */
@Service
public class AdminService extends ServiceImpl<AdminMapper, Admin> implements UserDetailsService {

    @Autowired
    private AdminMapper adminMapper;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Admin adminDetail=null;
        try {
            adminDetail = this.getById(username);
        } catch (Exception e) {
            throw  new UsernameNotFoundException(ResultStatusEnum.ERROR.getMsg());
        }
        if(adminDetail==null){
            throw  new UsernameNotFoundException(ResultStatusEnum.USER_NOT_EXIST.getMsg());
        }
        return adminDetail;
    }
}
