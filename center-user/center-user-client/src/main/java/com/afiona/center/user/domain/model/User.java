package com.afiona.center.user.domain.model;

import com.afiona.center.user.constants.enums.UserType;
import com.afiona.common.model.SuperEntity;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.groups.Default;
import java.io.Serializable;
import java.util.Collection;

/**
 * 用户
 *
 * @author dengweiyi
 * @date 2020-03-09
 */
@Data
public class User extends SuperEntity implements UserDetails, Serializable {
    private static final long serialVersionUID = -1011159141909993976L;

    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空", groups = Create.class)
    private String username;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空", groups = Create.class)
    @Length(min = 6, message = "密码不能小于6位", groups = Create.class)
    private String password;

    /**
     * 用户类型
     */
    @NotNull(message = "用户类型不能为空", groups = Create.class)
    private UserType userType;

    public interface Create extends Default {
    }

    public interface Update extends Default {
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
