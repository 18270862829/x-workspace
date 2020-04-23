package com.afiona.center.user.config;

import com.afiona.common.util.BizException;
import com.deepexi.util.IdGenerator;
import org.springframework.stereotype.Component;

/**
 * author      : dormi330
 * date        : 2018/6/30
 * project     : mybatis
 * description : 应用运行上下文
 * 遇到过很多这样的场景, 与领域无关的东西, 渗透到领域模型里面去
 * 想通过某种方式解决这个问题,
 */
@Component
public class AppRuntimeEnv {

    // 租户编码
    private ThreadLocal<String> tenantId = ThreadLocal.withInitial(() -> null);
    // token信息
    private ThreadLocal<String> token = ThreadLocal.withInitial(() -> null);
    // 登陆用户名
    private ThreadLocal<String> username = ThreadLocal.withInitial(() -> null);


    public AppRuntimeEnv ensureToken(String token) throws Exception {
        if (null == token) {
        	throw new BizException("token不能为空！");
        }
        this.token.set(token);
        return this;
    }

    public AppRuntimeEnv ensureTenantId(String tenantId) throws Exception {
        if (null == tenantId) {
        	throw new BizException("tenantId不能为空！");
        }
        this.tenantId.set(tenantId);
        return this;
    }

    public AppRuntimeEnv ensureUsername(String username) throws Exception {
        if (null == username) {
        	throw new BizException("username不能为空！");
        }
        this.username.set(username);
        return this;
    }

    public void setTenantId(String tenantId) {
        this.tenantId.set(tenantId);
    }

    public void setToken(String token) {
        this.token.set(token);
    }

    public String getTenantId() {
        String tenantIdStr = tenantId.get();
        if (tenantIdStr == null) {
            return "yanli123";
        }
        return tenantIdStr;
    }

    public String getToken() {
        return token.get();
    }

    public void setUsername(String username) {
        this.username.set(username);
    }

    public String getUsername() {
        String usernameStr = username.get();
        if (usernameStr == null) {
            return "ANONYMOUS";
        }
        return usernameStr;
    }

    // requestId
    private ThreadLocal<String> requestId = ThreadLocal.withInitial(IdGenerator::getUuId);

    public String getRequestId() {
        return requestId.get();
    }

}