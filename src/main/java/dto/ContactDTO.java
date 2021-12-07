package dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ContactDTO {
    private int id;
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
}
