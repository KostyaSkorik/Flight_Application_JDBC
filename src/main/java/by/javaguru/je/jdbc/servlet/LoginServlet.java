package by.javaguru.je.jdbc.servlet;

import by.javaguru.je.jdbc.dto.UserDto;
import by.javaguru.je.jdbc.service.UserService;
import by.javaguru.je.jdbc.utils.JSPHelper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    UserService userService = UserService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        req.getRequestDispatcher(JSPHelper.getPath("login")).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        userService.login(req.getParameter("email"), req.getParameter("pwd"))
                .ifPresentOrElse(userDto -> onLoginSuccess(userDto, req, resp), () -> onLoginFail(req, resp));
    }

    @SneakyThrows
    private void onLoginFail(HttpServletRequest req, HttpServletResponse resp) {
        resp.sendRedirect("/login?error&mail=" + req.getParameter("email"));
    }
    @SneakyThrows
    private void onLoginSuccess(UserDto userDto, HttpServletRequest req, HttpServletResponse resp)  {

        req.getSession().setAttribute("user", userDto);
        resp.sendRedirect("/flights");
    }
}
