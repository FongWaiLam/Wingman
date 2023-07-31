package com.fwl.unmannedstore.security.config;

import com.fwl.unmannedstore.security.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@Configuration
//@EnableWebSecurity
@EnableMethodSecurity
//(securedEnabled = true,
//jsr250Enabled = true,
//prePostEnabled = true) // by default
public class WebSecurityConfiguration {
    @Autowired
    private UserRepository repository;

    @Autowired
    private AuthEntryPointJwt unauthorizedHandler;

    @Bean
    public JwtAuthenticationFilter authenticationJwtTokenFilter() {
        return new JwtAuthenticationFilter();
    }

    // All beans should be public
    // User extends UserDetails, so User includes UserDetails
    // To return an object of interface UserDetailService,
    // must override "UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;"
    // parameter: username, method expression repo.findByEmail(username) [return Optional]
    @Bean
    public UserDetailsService userDetailsService() {
        return userEmail
                -> repository
                .findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + userEmail));
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        // Even login path stated in formlogin, it still should state in requestMatchers
        // All login path, signin postRequest and resources should be stated in the requestMatchers.
        // After logged in, a JWTCookie will be sent to the browser
        http
                .authorizeHttpRequests(authConfig -> {
                    authConfig.requestMatchers(HttpMethod.GET,  "/checkout", "/usms/login", "/error", "/css/**", "/js/**", "/images/**", "/product_photos").permitAll();
                    authConfig.requestMatchers(HttpMethod.POST,   "/usms/signin", "/usms/logout", "/checkout/getProduct").permitAll();
//                    authConfig.requestMatchers(HttpMethod.GET, "/usms").hasRole("USER");
//                    authConfig.requestMatchers(HttpMethod.GET, "/admin").hasRole("ADMIN");
                    authConfig.anyRequest().authenticated();
                })
                .formLogin(login -> {
                            login.loginPage("/usms/login");
                            login.defaultSuccessUrl("/usms");
                            login.failureUrl("/usms/login-error");
                        }
                );
//                .logout(logout -> {
//                    logout.logoutRequestMatcher(new AntPathRequestMatcher("/usms/signout"));
//                    logout.logoutSuccessUrl("/usms/login");
//                    logout.deleteCookies("usms");
//                    logout.clearAuthentication(true);
//                    logout.invalidateHttpSession(true);
//                });

        http    .csrf(csrf -> csrf.disable())
//                .cors(cors -> cors.disable())
                .exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
//        http
//                .csrf(csrf -> csrf.disable());
//                .exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler))
//                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .authorizeHttpRequests(auth ->
//                        auth.requestMatchers("/usms/auth/**").permitAll()
//                                .requestMatchers("/usms/**","/css/**", "/images/**","/js/**").permitAll()
//                                .anyRequest().authenticated()
//                );

        http.authenticationProvider(authenticationProvider());

        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
