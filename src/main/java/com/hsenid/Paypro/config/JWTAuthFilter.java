package com.hsenid.Paypro.config;


import com.hsenid.Paypro.service.JWTUtils;
import com.hsenid.Paypro.service.UserDetailService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
public class JWTAuthFilter extends OncePerRequestFilter{


@Autowired
    private JWTUtils jwtUtils;
@Autowired
    private UserDetailService userDetailService;
@Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authorizationHeader = request.getHeader("Authorization");
        String jwtToken = null;
        String userEmail = null;

        try{
            if (authorizationHeader != null && authorizationHeader.isBlank() && authorizationHeader.startsWith("Bearer ")) {
                jwtToken = authorizationHeader.substring(7);
                userEmail=jwtUtils.extractUsername(jwtToken);
            }
            if (userEmail != null && SecurityContextHolder.getContext().getAuthentication()==null) {
                UserDetails userDetails = userDetailService.loadUserByUsername(userEmail);

                if(jwtUtils.isTokenValid(jwtToken,userDetails)) {
                    UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
        } catch(Exception e){
            e.printStackTrace();
        }
        filterChain.doFilter(request, response);
    }

}