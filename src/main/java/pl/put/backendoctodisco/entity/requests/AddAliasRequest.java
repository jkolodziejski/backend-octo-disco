package pl.put.backendoctodisco.entity.requests;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import java.util.List;

public class AddAliasRequest {
    @ApiModelProperty(notes = "Flashcard ID", example = "1", required = false)
    @Column(name = "flashcard_id", nullable = false)
    public Long flashcard_id;

    @ApiModelProperty(notes = "Translation of the word to polish", example = "[Polska]", required = true)
    @Column(name = "translation", nullable = false)
    public List<String> translation;
}
