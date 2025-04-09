package by.javaguru.je.jdbc.validator;

import lombok.Value;
/*
Аннотация @Value - позволяет генерировать конструкторы и геттеры для полей класса.
 */
@Value(staticConstructor = "of")
public class Error {
    String code;
    String message;
}
