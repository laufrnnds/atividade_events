package com.growdev.atividade.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Autowired
    private JwtTokenStore tokenStore;

    private static final String [] PUBLIC = {"/oauth/token"};
    private static final String [] ADMIN = {"/users/**"};
    private static final String[] ROTA_ADMIN_CLIENT = {"/events/**", "/cities/**"};
    private static final String[] ROTA_CLIENT = {"/events/**"};


    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers(PUBLIC).permitAll() // LIBERANDO A ROTA DE LOGIN E SENHA PARA TODOS
                .antMatchers(ADMIN).hasRole("ADMIN") // SOMENTE O ADMIN CONSEGUE CRUD USERS
                .antMatchers(HttpMethod.POST, ROTA_ADMIN_CLIENT).hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, ROTA_ADMIN_CLIENT).hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, ROTA_ADMIN_CLIENT).hasRole("ADMIN")// TODOS PODEM ACESSAR AS ROTAS GET
                .antMatchers(HttpMethod.GET, ROTA_ADMIN_CLIENT).permitAll() // TODOS PODEM ACESSAR AS ROTAS GET
                .antMatchers(HttpMethod.POST, ROTA_CLIENT).hasAnyRole("CLIENT", "ADMIN") // LIBERA A ROTA POST DO EVENTO PARA CLIENT E TBM ADMIN
                .anyRequest().authenticated();
    }
}
