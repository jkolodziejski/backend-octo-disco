package pl.put.backendoctodisco.entity.requests;

import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;

public class AddListToFlashcardListRequest {
    @ApiModelProperty(notes = "Requests for every flashcard to be added to the list", required = true)
    public Long list_id;

    @ApiModelProperty(notes = "Requests for every flashcard to be added to the list", required = true)
    public ArrayList<Long> flashcard_ids;
}
