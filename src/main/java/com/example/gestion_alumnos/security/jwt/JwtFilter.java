package com.example.gestion_alumnos.security.jwt;

import com.example.gestion_alumnos.security.CustomerDetailService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private CustomerDetailService customerDetailService;

    @Autowired
    private JwtUtil jwtUtil;

    private String username;

    public Claims claims;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {


        if (request.getServletPath().matches("/api/user/login|/api/user/singup"))
        {
            filterChain.doFilter(request,response);
       }

        else
        {
            String authorization = request.getHeader("Authorization");
            String token = null;
            if(authorization != null &&  authorization.startsWith("Bearer "))
            {
                token = authorization.substring(7);
                username = jwtUtil.getUsername(token);
                claims = jwtUtil.getAllClaims(token);

            }
            if (username!=null && SecurityContextHolder.getContext().getAuthentication() == null)
            {
                UserDetails userDetails = customerDetailService.loadUserByUsername(username);
                if (Boolean.TRUE.equals(jwtUtil.validateToken(token,userDetails.getUsername())))
                {
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                            (new UsernamePasswordAuthenticationToken(userDetails,null,new ArrayList<>()));
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                    filterChain.doFilter(request,response);
                }
                else {
                    response.sendError(HttpServletResponse.SC_FORBIDDEN,"Token Expiro o es invalido");
                }

            }
            else {
                response.sendError(HttpServletResponse.SC_FORBIDDEN,"Username invalido");
            }
        }



    }


}
