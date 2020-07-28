package com.rabbit.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageDtoJson {

    private String stringValue;
    private Integer integerValue;

}
