package by.javaguru.je.jdbc.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;

import java.io.IOException;
import java.util.Arrays;

@WebFilter("/*")
public class LoggingFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        servletRequest.getParameterMap().forEach((key,value) ->
                System.out.println("Request parameter: " + key + "=" + Arrays.toString(value)));
        filterChain.doFilter(servletRequest,servletResponse);
    }
}
