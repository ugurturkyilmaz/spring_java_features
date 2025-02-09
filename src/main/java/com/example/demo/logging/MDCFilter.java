package com.example.demo.logging;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;

@Component
public class MDCFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Gerekirse init işlemleri
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        // Örnek olarak rastgele bir requestId oluşturuyoruz
        String requestId = UUID.randomUUID().toString();

        // MDC’ye (ThreadContext) put ediyoruz:
        ThreadContext.put("requestId", requestId);

        HttpServletRequest httpRequest = (HttpServletRequest) request;

        ThreadContext.put("firstName", httpRequest.getHeader("firstName"));

        try {
            chain.doFilter(request, response);
        } finally {
            // İşlem bittiğinde, MDC’yi temizlemek önemli:
            ThreadContext.clearAll();
        }
    }

    @Override
    public void destroy() {
        // Gerekirse destroy işlemleri
    }
}