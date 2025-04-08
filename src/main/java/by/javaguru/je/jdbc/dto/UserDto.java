package by.javaguru.je.jdbc.dto;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto {
    private Long id;
    private String email;
}
