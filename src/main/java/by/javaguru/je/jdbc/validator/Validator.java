package by.javaguru.je.jdbc.validator;

public interface Validator <T>{
    ValidationResult isValid(T object);
}
