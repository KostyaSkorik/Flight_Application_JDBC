package by.javaguru.je.jdbc.dao;

import java.util.List;
import java.util.Optional;

public interface Dao <K,E>{
    E save(E e);
    boolean delete(K k);
    List<E> findAll();
    Optional<E> findById(K k);
    boolean update(E e);
}
