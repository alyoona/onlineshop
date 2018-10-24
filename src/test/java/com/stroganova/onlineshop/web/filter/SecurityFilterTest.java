package com.stroganova.onlineshop.web.filter;

import com.stroganova.onlineshop.entity.Session;
import com.stroganova.onlineshop.service.SecurityService;
import org.junit.Test;
import javax.servlet.FilterChain;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class SecurityFilterTest {

    @Test
    public void testDoFilterIsNotAuth() throws Exception {
        //prepare
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain filterChain = mock(FilterChain.class);

        Cookie[] cookies = {new Cookie("user-token", "123456789")};
        when(request.getCookies()).thenReturn(cookies);

        SecurityFilter securityFilter = new SecurityFilter();
        securityFilter.setSecurityService(new SecurityService());

        //when
        securityFilter.doFilter(request, response, filterChain);

        //then
        verify(response).sendRedirect("/login" );
    }

    @Test
    public void testDoFilterIsAuth() throws Exception {
        //prepare
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain filterChain = mock(FilterChain.class);

        Cookie[] cookies = new Cookie[1];
        cookies[0] = new Cookie("user-token", "123456789");
        when(request.getCookies()).thenReturn(cookies);

        SecurityService mockSecurityService = mock(SecurityService.class);
        when(mockSecurityService.getSession("123456789")).thenReturn(java.util.Optional.of(new Session()));

        SecurityFilter securityFilter = new SecurityFilter();
        securityFilter.setSecurityService(mockSecurityService);

        //when
        securityFilter.doFilter(request, response, filterChain);

        //then
        verify(filterChain).doFilter(request,response);
    }

}