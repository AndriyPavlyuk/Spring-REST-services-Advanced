package it.discovery.mapping;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Component
public class DebugHandler {

    public void logRequest(HttpServletRequest request, HttpServletResponse response) {
      log.info("New request :" + request.getRequestURI());
    }
}
