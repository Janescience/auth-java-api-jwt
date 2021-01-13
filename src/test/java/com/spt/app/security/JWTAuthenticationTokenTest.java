package com.spt.app.security;

import static org.assertj.core.api.Assertions.assertThat;


import org.junit.Before;
import org.junit.Test;


public class JWTAuthenticationTokenTest {
    private JWTAuthenticationToken uut;

    @Before
    public void setUp()  {
        uut = new JWTAuthenticationToken("<token>");
    }

    @Test
    public void shouldContainToken()  {
        assertThat(uut.getToken()).isEqualTo("<token>");
    }
}
