package by.javaguru.je.jdbc.servlet;

import by.javaguru.je.jdbc.service.FlightService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;


@WebServlet("/flights")
public class FlightServlet extends HttpServlet {
    private static final FlightService flightService = FlightService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html");
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());

        try (PrintWriter writer = resp.getWriter()) {
            writer.println("<h1>Все перелеты</h1>");
            writer.println("<ul>");
            flightService.findAll().forEach(flightDto ->
                    writer.println("""
                            <li>
                            <a href='/tickets?flightId=%d'>%s</a>
                            </li>
                            """.formatted(flightDto.getId(),flightDto.getDescription())));
            writer.println("</ul>");
            writer.flush();
        }
    }
}
