package com.example.shirotest.shiro;

import org.apache.shiro.authc.Account;
import org.apache.shiro.authz.Permission;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.Collection;

public class User implements Account {
    @Override
    public PrincipalCollection getPrincipals() {
        return null;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Collection<String> getRoles() {
        return null;
    }

    @Override
    public Collection<String> getStringPermissions() {
        return null;
    }

    @Override
    public Collection<Permission> getObjectPermissions() {
        return null;
    }
}
