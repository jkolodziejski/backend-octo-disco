package pl.put.backendoctodisco.entity.requests;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;

public class FlashcardListRequest {
    @ApiModelProperty(notes = "Name of the flashcard list", example = "Monday exam", required = true)
    @Column(name = "name", nullable = false)
    public String name;

    @ApiModelProperty(notes = "Description of the flashcard list", example = "Food and drinks", required = false)
    @Column(name = "description")
    public String description;
}
