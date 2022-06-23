package team.seven.ticketsquery.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import team.seven.ticketsquery.domain.Admin;
import team.seven.ticketsquery.domain.ResultVO;
import team.seven.ticketsquery.enums.ResultStatusEnum;
import team.seven.ticketsquery.service.AdminService;
import team.seven.ticketsquery.utils.RandomAdminIdUtils;

import java.util.List;

/**
 * User: 谢礼治
 * Date: 2022/6/13
 * Time: 9:09
 * Description: 管理员信息管理模块
 */

@RestController
@CrossOrigin
@RequestMapping("/admin")
@Transactional
public class AdminController {

    @Autowired
    private AdminService adminService;


    // 根据id删除管理员
    @DeleteMapping("/remove/{adminId}")
    public ResultVO removeAdmin(@PathVariable String adminId){
        boolean flag = adminService.removeById(adminId);
        return new ResultVO(flag?ResultStatusEnum.SUCCESS:ResultStatusEnum.USER_REMOVE_FAILED);
    }

    // 更新管理员信息
    @PostMapping("/update")
    public ResultVO updateAdmin(@RequestBody Admin admin){
        boolean flag = adminService.updateById(admin);
        return new ResultVO(flag?ResultStatusEnum.SUCCESS:ResultStatusEnum.USER_UPDATE_FAILED);
    }

    // 根据id获取管理员信息
    @GetMapping("/getById/{adminId}")
    public ResultVO selectAdminById(@PathVariable String adminId){
        Admin admin = adminService.getById(adminId);
        return new ResultVO(ResultStatusEnum.SUCCESS,admin);
    }

    // 获取该权限的所有管理员
    @GetMapping("/getByAccess/{permission}")
    public ResultVO selectAdminByAccess(@PathVariable Integer permission){
        QueryWrapper<Admin> wrapper = new QueryWrapper<>();
        wrapper.eq("permission",permission);
        List<Admin> admins = adminService.list(wrapper);
        return new ResultVO(ResultStatusEnum.SUCCESS,admins);
    }


    @GetMapping("/login")
    public ResultVO selectAdminByAccess(@RequestParam(value = "adminId") String adminId,@RequestParam(value = "adminPwd") String adminPwd){

        UserDetails admin = adminService.loadUserByUsername(adminId);

        if (admin!=null){
            BCryptPasswordEncoder encoder=new BCryptPasswordEncoder();
            String encode = encoder.encode(adminPwd);//加密返回密文密码
            boolean flag = encoder.matches(encode, adminPwd);
            return new ResultVO(ResultStatusEnum.SUCCESS,admin);
        }else{
            return new ResultVO(ResultStatusEnum.USER_LOGIN_FAIL);
        }
    }

    //添加管理员信息
    @PostMapping("/add")
    public ResultVO addAdmin(@RequestBody Admin admin){
        String adminId = RandomAdminIdUtils.verifyUserName(4, 6);
        String password = admin.getAdminPwd();
        //加密存储密码
        BCryptPasswordEncoder encoder=new BCryptPasswordEncoder();
        String encode = encoder.encode(password);//加密返回密文密码
        admin.setAdminId(adminId);
        admin.setAdminPwd(encode);
        boolean flag = adminService.save(admin);
        return new ResultVO(flag?ResultStatusEnum.SUCCESS:ResultStatusEnum.USER_ADD_FAILED);
    }


}
