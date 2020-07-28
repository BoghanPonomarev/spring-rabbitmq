package com.rabbit.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SimpleMessageDto {

    private String exchange;
    private String routingKey;

    private String message;
    private Integer count;

}
