package io.tacsio.models;

import javax.persistence.Entity;
import javax.persistence.Table;

import io.quarkus.elytron.security.common.BcryptUtil;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.security.jpa.Password;
import io.quarkus.security.jpa.Roles;
import io.quarkus.security.jpa.UserDefinition;
import io.quarkus.security.jpa.Username;

@Entity
@Table(name = "app_user")
@UserDefinition
public class User extends PanacheEntity {

    @Username
    public String username;
    @Password
    public String password;
    @Roles
    public String role;

    public static void addUser(String username, String rawPassword, String role) {
        User user = new User();
        user.username = username;
        user.password = BcryptUtil.bcryptHash(rawPassword);
        user.role = role;

        user.persist();
    }
}
