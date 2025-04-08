package by.javaguru.je.jdbc.mapper;

public interface Mapper <T, F>{
    T mapFrom(F f);
}
