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
public class Book {

    private int id;
    private String title;
    private String description;
    private int pageCount;
    private String excerpt;
    private String publishDate;

    /**
     * Validate if Book object has required fields
     * @return true if all required fields are present
     */
    public boolean isValid() {
        return id > 0 && title != null && !title.isEmpty();
    }

    /**
     * Validate if Book has minimal required fields for creation
     * @return true if minimal fields are present
     */
    public boolean hasMinimalFields() {
        return title != null && !title.isEmpty() && pageCount > 0;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", pageCount=" + pageCount +
                ", publishDate='" + publishDate + '\'' +
                '}';
    }
}

