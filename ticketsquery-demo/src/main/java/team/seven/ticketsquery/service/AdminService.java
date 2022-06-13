package team.seven.ticketsquery.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team.seven.ticketsquery.domain.Admin;
import team.seven.ticketsquery.mapper.AdminMapper;

/**
 * User: 谢礼治
 * Date: 2022/6/13
 * Time: 9:06
 * Description: No Description
 */
@Service
public class AdminService extends ServiceImpl<AdminMapper, Admin> {

    @Autowired
    private AdminMapper adminMapper;


}
