package com.rabbit.example7;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageDtoJson {

    @JsonProperty
    private String stringValue;

    @JsonProperty
    private Integer integerValue;

}
