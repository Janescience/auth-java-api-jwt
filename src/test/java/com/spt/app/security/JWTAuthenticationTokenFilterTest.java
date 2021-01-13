package com.spt.app.security;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.Authentication;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class JWTAuthenticationTokenFilterTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain chain;

    private JWTAuthenticationTokenFilter uut;

    @Before
    public void init() {
        uut = new JWTAuthenticationTokenFilter();
    }

    @Test
    public void throwsWithoutToken() throws Exception {

        JWTAuthenticationTokenFilter spy = Mockito.spy(uut);
        when(request.getServletPath()).thenReturn("/api");

        spy.doFilter(request, response, chain);
    }


}
