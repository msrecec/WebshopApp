package com.example.webshop.command.customer;

import lombok.*;

import javax.validation.constraints.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class CustomerCommand {
    @NotNull(message = "Customer id must not be null")
    @Positive(message = "Customer id must be a positive number")
    private Long id;
    @NotNull(message = "Customer first name must not be null")
    @NotBlank(message = "Customer first name must not be blank")
    @Size(min = 1, max = 50, message = "Customer first name must be between 1 and 50 characters")
    private String firstName;
    @NotNull(message = "Customer last name must not be null")
    @NotBlank(message = "Customer last name must not be blank")
    @Size(min = 1, max = 50, message = "Customer last name must be between 1 and 50 characters")
    private String lastName;
    @NotNull(message = "Customer email must not be null")
    @NotBlank(message = "Customer email must not be blank")
    @Size(min = 4, max = 50, message = "Customer e-mail must be between 4 and 50 characters")
    @Email(message = "Email should be valid")
    private String email;
}
