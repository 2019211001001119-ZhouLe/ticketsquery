package team.seven.ticketsquery.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("tb_administrator")
public class Admin {
    @TableId
    private String adminId;
    private String adminPwd;
    private String adminName;
    private Integer permission;
}
