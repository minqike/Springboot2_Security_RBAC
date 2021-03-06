package com.min.config;

import com.min.AppSecurity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.PseudoColumnUsage;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppSecurity.class)
public class SecurityConfigTest {
    @Autowired
    PasswordEncoder passwordEncoder;
    @Test
    public void passwordEncoder() {
        System.out.println(passwordEncoder.encode("123456"));
    }
}
