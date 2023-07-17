package com.fwl.unmannedstore.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
@AttributeOverrides({
        @AttributeOverride(
                name = "name",
                column = @Column(name="cat_name")
        ),
        @AttributeOverride(
                name = "description",
                column = @Column(name="cat_description")
        )
})
public class Category {
    private String name; // max 255 chars
    private String description; // max 255 chars
}
