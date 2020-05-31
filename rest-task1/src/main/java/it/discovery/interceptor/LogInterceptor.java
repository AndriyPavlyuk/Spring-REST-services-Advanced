package it.discovery.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
public class LogInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {
        if(request.getMethod().equalsIgnoreCase("POST")) {
            log.info("Method POST was called for URI: {}", request.getRequestURI());
        }
        return true;
    }
}
