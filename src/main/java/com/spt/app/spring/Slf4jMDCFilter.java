package com.spt.app.spring;

import com.spt.app.util.AuthorizeUtil;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Component
public class Slf4jMDCFilter extends OncePerRequestFilter {

    @Autowired
    AuthorizeUtil authorizeUtil;


    private final String responseHeader = "req-id";
    private final String mdcTokenKey = "req_id";

    @Override
    protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response, final FilterChain chain)
            throws java.io.IOException, ServletException {
        try {
            final String token = UUID.randomUUID().toString().toUpperCase().replace("-", "");
            String username = authorizeUtil.getUsername();
            MDC.put(mdcTokenKey, token);
            if(username!=null) {
                MDC.put("Username", username);
            }

            if (!StringUtils.isEmpty(responseHeader)) {
                response.addHeader(responseHeader, token);
            }
            chain.doFilter(request, response);
        } finally {
            MDC.remove(mdcTokenKey);
        }
    }
}
