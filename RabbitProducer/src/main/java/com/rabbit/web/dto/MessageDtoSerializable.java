package com.rabbit.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageDtoSerializable implements Serializable {

    private String stringValue;
    private Integer integerValue;

}
