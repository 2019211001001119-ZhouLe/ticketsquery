package team.seven.ticketsquery.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.NotNull;
import java.util.Collection;

@Data
@TableName("tb_administrator")
public class Admin implements UserDetails {
    @TableId
    private String adminId;
    @Length(min = 6,max = 15,message = "密码长度应该在6-15之间")
    @NotNull(message = "密码不可以为空")
    private String adminPwd;
    private String adminName;
    @NotNull(message = "权限不可以为空")
    private Integer permission;

    /**
     * 获取用户权限
     * @return
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    /**
     * 获取用户密码
     * @return
     */
    @Override
    public String getPassword() {
        return this.adminPwd;
    }

    /**
     * 获取用户账号
     * @return
     */
    @Override
    public String getUsername() {
        return this.adminId;
    }

    /**
     * 验证当前用户是否过期
     * 用户没有过期返回 true 过期返回false
     * @return
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 当前用户是否锁定
     * 没有锁定返回true 锁定了返回false
     * @return
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * 密码是否过期
     * 没有过期返回 true 过期了返回false
     * @return
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 当前用户是否启用
     * 启用返回true 禁用返回false
     * @return
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
