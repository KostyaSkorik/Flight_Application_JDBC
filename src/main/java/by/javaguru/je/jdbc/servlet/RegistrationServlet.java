package by.javaguru.je.jdbc.servlet;

import by.javaguru.je.jdbc.dto.CreateUserDto;
import by.javaguru.je.jdbc.entity.Gender;
import by.javaguru.je.jdbc.entity.Role;
import by.javaguru.je.jdbc.exceptions.ValidationException;
import by.javaguru.je.jdbc.service.UserService;
import by.javaguru.je.jdbc.utils.JSPHelper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/registration")
public class RegistrationServlet extends HttpServlet {
    private final UserService userService = UserService.getInstance();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("roles", Role.values());
        req.setAttribute("genders", Gender.values());
        req.getRequestDispatcher(JSPHelper.getPath("registration")).forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CreateUserDto userDto = CreateUserDto.builder()
                .name(req.getParameter("name"))
                .birthday(req.getParameter("birthday"))
                .email(req.getParameter("email"))
                .password(req.getParameter("pwd"))
                .role(req.getParameter("role")==null ? "":req.getParameter("role"))
                .gender(req.getParameter("gender")==null ? "":req.getParameter("gender"))
                .build();
        try {
            Integer id = userService.createUser(userDto);
            resp.sendRedirect("/login?userId=%d".formatted(id));
        }catch (ValidationException e){
            req.setAttribute("errors", e.getErrors());
            req.setAttribute("roles", Role.values());
            req.setAttribute("genders", Gender.values());
            req.getRequestDispatcher(JSPHelper.getPath("registration")).forward(req,resp);
        }

    }
}
