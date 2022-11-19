package pl.put.backendoctodisco.entity.requests;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;

public class FlashcardListRequest {
    @ApiModelProperty(notes = "Name of the flashcard list", example = "Monday exam - phrases", required = true)
    @Column(name = "name", nullable = false)
    public String name;
}
