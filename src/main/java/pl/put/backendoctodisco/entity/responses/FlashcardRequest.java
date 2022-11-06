package pl.put.backendoctodisco.entity.responses;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;

public class FlashcardRequest {
    @ApiModelProperty(notes = "Language of the word", allowableValues = "pl, en", example = "pl", required = true)
    @Column(name = "language", nullable = false)
    private String language;

    @ApiModelProperty(notes = "Word to learn", example = "Poland", required = true)
    @Column(name = "word", nullable = false)
    private String word;

    @ApiModelProperty(notes = "Translation of the word to polish", example = "Polska", required = true)
    @Column(name = "translation", nullable = false)
    private String translation;

    @ApiModelProperty(notes = "ID of the user who created the flashcard", example = "1", required = true)
    @Column(name = "user_id", nullable = false)
    private Long userId;

    public String getLanguage() {return language;}
    public String getWord() {return word;}
    public String getTranslation() {return translation;}
    public Long getUserId() {return userId;}
}
