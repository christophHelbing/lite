package com.sevdesk.lite.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.security.web.SecurityFilterChain


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
class SpringSecurityConfiguration {

    @Bean
    fun encoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun userDetailsService(): UserDetailsService? {
        val user: UserDetails = User.builder()
            .username("user")
            .password(encoder().encode("password"))
            .roles("USER")
            .build()
        val admin: UserDetails = User.builder()
            .username("admin")
            .password(encoder().encode("password"))
            .roles("ADMIN", "USER")
            .build()
        return InMemoryUserDetailsManager(user, admin)
    }

    @Bean
    fun httpSecurity(httpSecurity: HttpSecurity): SecurityFilterChain {
        return httpSecurity
            .httpBasic(Customizer.withDefaults())
            .csrf().disable()
            .build()
    }
}