package by.javaguru.je.jdbc;

import by.javaguru.je.jdbc.dao.TicketDao;
import by.javaguru.je.jdbc.entity.Ticket;

import java.math.BigDecimal;

public class JdbcRunnerWithDao {
    public static void main(String[] args) {

        TicketDao ticketDao = TicketDao.getInstance();
        ticketDao.delete(13L);
    }
}
