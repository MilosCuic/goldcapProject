package com.goldcap.security;


import com.goldcap.model.GoldcapUser;
import com.goldcap.model.Role;
import com.goldcap.service.impl.GoldcapUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.goldcap.util.Constants.HEADER_STRING;
import static com.goldcap.util.Constants.TOKEN_PREFIX;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private GoldcapUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {

        String jwt = getJWTFromRequest(request);

        if (StringUtils.hasText(jwt) && tokenProvider.isTokenValid(jwt)){
            Long userId = tokenProvider.getUserIdFromJWT(jwt);

            GoldcapUser userDetails = userDetailsService.loadGoldcapUserById(userId);

            List<SimpleGrantedAuthority> authorities = new ArrayList<>();


            if (userDetails.getRoles().size() > 0){
                for (Role role : userDetails.getRoles()) {
                    SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role.getName());
                    authorities.add(authority);
                }
            }else {
                SimpleGrantedAuthority authority = new SimpleGrantedAuthority("GUEST");
                authorities.add(authority);
            }


            UsernamePasswordAuthenticationToken auth  = new UsernamePasswordAuthenticationToken(
                    userDetails , null , authorities);

            auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(auth);
        }

        }catch (Exception e){
            e.printStackTrace();
        }

        filterChain.doFilter(request , response);

    }

    private String getJWTFromRequest(HttpServletRequest request){
        String bearerToken = request.getHeader(HEADER_STRING);
        System.out.println(bearerToken);
        //has text thats not blank and starts with
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(TOKEN_PREFIX)){
            return bearerToken.substring(7 , bearerToken.length());
        }else{
            return null;
        }
    }
}
