package com.yu;

import com.yu.filter.JwtTokenBasedSecurityFilter;
import com.yu.model.Permission;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${property-service.api-base-url}")
    protected String apiBaseUrl;

    @Value("${property-service.options.enable-debug-endpoint}")
    protected boolean enableDebugEndpoint;

    @Value("${property-service.options.disable-permission-check}")
    protected boolean disablePermissionCheck;

    private static final Logger logger = LoggerFactory.getLogger(AppSecurityConfig.class);

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // ensure the service is completely stateless (and never set cookie)
        http.sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.csrf().disable();

        // access control for debug endpoints
        if (this.enableDebugEndpoint) {
            http.authorizeRequests()
                    .antMatchers(apiBaseUrl+"/debug/**").permitAll();
        }

        // access control for endpoints
        if (disablePermissionCheck) {
            logger.warn("app started with disablePermissionCheck.");
            http.authorizeRequests().anyRequest().permitAll();
        } else {
            http
                .authorizeRequests()
                    .antMatchers(apiBaseUrl+"/about")
                        .permitAll()

                    .antMatchers(HttpMethod.GET, apiBaseUrl+"/property")
                        .hasAnyAuthority(Permission.ANYTHING.name(), Permission.PROPERTY_SEARCH.name())
                    .antMatchers(HttpMethod.POST, apiBaseUrl+"/property")
                        .hasAnyAuthority(Permission.ANYTHING.name(), Permission.PROPERTY_ADD.name())
                    .antMatchers(HttpMethod.GET, apiBaseUrl+"/property/*")
                        .hasAnyAuthority(Permission.ANYTHING.name(), Permission.PROPERTY_GET.name())
                    .antMatchers(HttpMethod.PUT, apiBaseUrl+"/property/*")
                       .hasAnyAuthority(Permission.ANYTHING.name(), Permission.PROPERTY_UPDATE.name())

                    .anyRequest().denyAll()
            ;
        }

        http.addFilterBefore(new JwtTokenBasedSecurityFilter(), UsernamePasswordAuthenticationFilter.class);
    }

}
