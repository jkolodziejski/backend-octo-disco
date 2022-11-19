package pl.put.backendoctodisco.entity.requests;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;

public class AddToFlashcardListRequest {
    @ApiModelProperty(notes = "ID of the flashcard", example = "1", required = true)
    public Long flashcard_id;

    @ApiModelProperty(notes = "ID of the flashcard list", example = "1", required = true)
    public Long list_id;
}
