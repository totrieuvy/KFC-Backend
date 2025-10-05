package com.example.Order_Project.config;

import com.example.Order_Project.entity.Account;
import com.example.Order_Project.exception.AuthException;
import com.example.Order_Project.service.TokenService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
import java.util.List;

@Component
public class Filter extends OncePerRequestFilter {

    private final TokenService tokenService;

    @Autowired
    @Qualifier("handlerExceptionResolver")
    HandlerExceptionResolver resolver; //giúp quăng ra lỗi

    private final List<String> AUTH_PERMISSION = List.of(
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/v3/api-docs/**",
            "/swagger-resources/**",
            "/swagger-ui/index.html",
            "/api/login",
            "/api/register",
            "/api/refresh-token"
    );


    public Filter(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    public String getToken(HttpServletRequest request){
        String authHeader = request.getHeader("Authorization");
        if(authHeader == null) {
            return null;
        }
        return authHeader.substring(7);
    }

    public boolean checkIsPublicAPI(HttpServletRequest request) {
        String uri = request.getRequestURI();
        String method = request.getMethod(); // GET, POST, PUT, DELETE
        AntPathMatcher patchMatch = new AntPathMatcher();

        // cho phép GET /api/categories (không cần token)
        if ("GET".equalsIgnoreCase(method) && patchMatch.match("/api/products", uri)) {
            return true;
        }

        // các public API khác
        return AUTH_PERMISSION.stream().anyMatch(pattern -> patchMatch.match(pattern, uri));
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //kiểm tra trước khi vô controller
//        filterChain.doFilter(request, response);

        //check api ng dùng yêu cầu có phải là 1 public api k?
        boolean isPublicAPI = checkIsPublicAPI(request);

        if(isPublicAPI) { //ko cần token vẫn truy cập dc
            filterChain.doFilter(request, response);
        }else{ //khi truy cập api phãi check token
            String token = getToken(request);

            if(token == null){ //ko có token => ko dc truy cập
                resolver.resolveException(request, response, null, new AuthException("Empty token!"));
                return;
            }
            //có token
            //check token có đúng ko
            Account account;
            try {
                account = tokenService.getAccountByToken(token);
            } catch (ExpiredJwtException e) {
                //response token hết hạn
                resolver.resolveException(request, response, null, new AuthException("Expired token!"));
                return;
            } catch (MalformedJwtException malformedJwtException) {
                //response token sai
                resolver.resolveException(request, response, null, new AuthException("Invalid token!"));
                return;
            }

            //token chuẩn => cho phép truy cập và lưu thông tin account
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(account, token, account.getAuthorities());
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            //token ok cho vào
            filterChain.doFilter(request, response);
        }
    }
}
