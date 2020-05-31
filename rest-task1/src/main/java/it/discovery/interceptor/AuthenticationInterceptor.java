package it.discovery.interceptor;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AuthenticationInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {
//        if(StringUtils.isBlank(request.getHeader(HttpHeaders.AUTHORIZATION))) {
//            response.setStatus(HttpStatus.UNAUTHORIZED.value());
//            response.getOutputStream().flush();
//            return false;
//        }
        return true;
    }
}
