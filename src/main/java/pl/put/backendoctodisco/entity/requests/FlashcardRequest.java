package pl.put.backendoctodisco.entity.requests;

import io.swagger.annotations.ApiModelProperty;
import lombok.NonNull;

import javax.persistence.Column;

public class FlashcardRequest {
    @ApiModelProperty(notes = "Language of the word", allowableValues = "pl, en", example = "pl", required = true)
    @Column(name = "language", nullable = false)
    public String language;

    @ApiModelProperty(notes = "Word to learn", example = "Poland", required = true)
    @Column(name = "word", nullable = false)
    public String word;

    @ApiModelProperty(notes = "Translation of the word to polish", example = "Polska", required = true)
    @Column(name = "translation", nullable = false)
    public String translation;
}
