package pl.put.backendoctodisco.entity.requests;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;

public class ListContentRequest {
    @ApiModelProperty(notes = "List ID", example = "1", required = true)
    @Column(name = "list_id", nullable = false)
    public Long list_id;
}
