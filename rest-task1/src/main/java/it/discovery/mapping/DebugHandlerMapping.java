package it.discovery.mapping;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.AbstractHandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

@Component
@RequiredArgsConstructor
public class DebugHandlerMapping extends AbstractHandlerMapping {

    private final DebugHandler debugHandler;

    @Override
    protected Object getHandlerInternal(HttpServletRequest httpServletRequest) throws Exception {
        if(httpServletRequest.getRequestURI().startsWith("/debug")) {
            Method logMethod = BeanUtils.findMethod(debugHandler.getClass(), "logRequest",
                    HttpServletRequest.class, HttpServletResponse.class);

            return new HandlerMethod(debugHandler, logMethod);
        }
        return null;
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}
