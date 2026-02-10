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

    /**
     * Validate if Author object has required fields
     * @return true if all required fields are present
     */
    public boolean isValid() {
        return id > 0 && firstName != null && !firstName.isEmpty() && lastName != null && !lastName.isEmpty();
    }

    /**
     * Validate if Author has minimal required fields for creation
     * @return true if minimal fields are present
     */
    public boolean hasMinimalFields() {
        return firstName != null && !firstName.isEmpty() && lastName != null && !lastName.isEmpty();
    }

    /**
     * Get full name of the author
     * @return Full name (FirstName LastName)
     */
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

