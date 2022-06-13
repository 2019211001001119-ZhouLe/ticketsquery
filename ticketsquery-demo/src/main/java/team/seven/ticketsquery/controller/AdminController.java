package team.seven.ticketsquery.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import team.seven.ticketsquery.domain.Admin;
import team.seven.ticketsquery.domain.ResultVO;
import team.seven.ticketsquery.enums.ResultStatusEnum;
import team.seven.ticketsquery.service.AdminService;

import java.util.List;

/**
 * User: 谢礼治
 * Date: 2022/6/13
 * Time: 9:09
 * Description: No Description
 */

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;


    @DeleteMapping("/remove/{adminId}")
    public ResultVO removeAdmin(@PathVariable String adminId){
        boolean flag = adminService.removeById(adminId);
        return new ResultVO(ResultStatusEnum.SUCCESS);
    }

    @PostMapping("/update")
    public ResultVO updateAdmin(@RequestBody Admin admin){
        adminService.updateById(admin);
        return new ResultVO(ResultStatusEnum.SUCCESS);
    }

    @GetMapping("/getById/{adminId}")
    public ResultVO selectAdminById(@PathVariable String adminId){
        Admin admin = adminService.getById(adminId);
        return new ResultVO(ResultStatusEnum.SUCCESS,admin);
    }

    @GetMapping("/getByAccess/{permission}")
    public ResultVO selectAdminByAccess(@PathVariable Integer permission){
        QueryWrapper<Admin> wrapper = new QueryWrapper<>();
        wrapper.eq("permission",permission);
        List<Admin> admins = adminService.list(wrapper);
        return new ResultVO(ResultStatusEnum.SUCCESS,admins);
    }

    //TODO 登录功能
    @GetMapping("/login")
    public ResultVO selectAdminByAccess(@RequestParam String adminId,@RequestParam String adminPwd){
        QueryWrapper<Admin> wrapper = new QueryWrapper<>();
        wrapper.eq("admin_id",adminId);
        wrapper.eq("admin_pwd",adminPwd);
        Admin admin = adminService.getOne(wrapper);
        return new ResultVO(ResultStatusEnum.SUCCESS,admin);
    }


}
