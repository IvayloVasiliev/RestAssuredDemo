package models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class Author {

    private int id;
    private String idBook;
    private String firstName;
    private String lastName;


    public boolean isValid() {
        return id > 0 && firstName != null && !firstName.isEmpty() && lastName != null && !lastName.isEmpty();
    }

    public boolean hasMinimalFields() {
        return firstName != null && !firstName.isEmpty() && lastName != null && !lastName.isEmpty();
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    @Override
    public String toString() {
        return "Author{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", idBook='" + idBook + '\'' +
                '}';
    }
}

