package by.javaguru.je.jdbc.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@WebServlet("/dispatcher")
public class DispatcherServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        forwardTravel(req, resp);
//        includeTravel(req,resp);
        redirectTravel(req,resp);
    }

    public void forwardTravel(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var dispatcher = req.getRequestDispatcher("/flights");
        dispatcher.forward(req,resp);
    }

    /*Если ничего не менять, то будет проблема с кодировкой
    так как в includeTravel используется кодировка по умолчанию(то есть /dispatcher отвечает)
    а при использовании forwardTravel кодировка по умолчанию не используется, а используется кодировка
    которая установлена в /flights (и соответственно /flights отвечает на запрос)
    (смотри картинку из папки images)
     */
    public void includeTravel(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());
        var dispatcher = req.getRequestDispatcher("/flights");
        dispatcher.include(req,resp);
        //ничего не напишется, так как writer будет закрыт во flightServlet
        var writer = resp.getWriter();
        writer.println("<h1>DispatcherServletINCLUDE</h1>");
    }

    public void redirectTravel(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect("https://ya.ru/");
    }
}
