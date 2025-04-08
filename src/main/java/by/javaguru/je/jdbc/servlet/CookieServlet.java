package by.javaguru.je.jdbc.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Arrays;

@WebServlet("/cookies")
public class CookieServlet extends HttpServlet {
    private final static String UNIQ_USER_ID = "UserId";


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var cookies = req.getCookies();
        if(cookies == null ||
           Arrays.stream(cookies).filter(cookie -> cookie.getName().equals(UNIQ_USER_ID)).findFirst().isEmpty()){
            Cookie cookie = new Cookie(UNIQ_USER_ID,"1");
            cookie.setMaxAge(20);
            cookie.setPath("/");
            resp.addCookie(cookie);

        }

    }

}

