package edu.mondragon.webengl.seguridad;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SeguridadConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            // Fuerza HTTPS (redirige automáticamente de HTTP a HTTPS)
            .requiresChannel()
                .anyRequest()
                .requiresSecure()
            .and()
            // Permite el acceso a recursos estáticos y páginas de login sin autenticación
            .authorizeRequests()
                .antMatchers("/login", "/css/**", "/js/**", "/usuario/crear", "/api/traducir", "/forgot-password", "/verify-code", "/reset-password").permitAll()
                .anyRequest().authenticated()
            .and()
            // Indica donde se encuentra la página de login y la URL de éxito después del login
            .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/eventos/listaEventos", true)
                .permitAll()
            .and()
            // Indica donde se hace el logout y la URL de éxito después del logout
            // Invalida la sesión y elimina las cookies de sesión
            .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll();
        return http.build();
    }

}
