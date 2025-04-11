package by.javaguru.je.jdbc.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Set;

import static by.javaguru.je.jdbc.utils.UrlPath.LOGIN;
import static by.javaguru.je.jdbc.utils.UrlPath.REGISTRATION;

@WebFilter("/*")
public class AuthorizationFilter implements Filter {
    private static final Set<String> PUBLIC_PATH = Set.of(LOGIN,REGISTRATION);

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
    String URI = ((HttpServletRequest)servletRequest).getRequestURI();
        if(userIsAuthorized(servletRequest)||(isPublicPath(URI))){
            filterChain.doFilter(servletRequest,servletResponse);
        }else {
            ((HttpServletResponse)servletResponse).sendRedirect(LOGIN);
        }
    }

    public boolean userIsAuthorized(ServletRequest servletRequest) {
        var user = ((HttpServletRequest)servletRequest).getSession().getAttribute("user");
        return user!=null;
    }
    public boolean isPublicPath(String Uri){
        return PUBLIC_PATH.contains(Uri);
    }
}
