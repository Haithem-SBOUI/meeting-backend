package com.haithemsboui.meetingbackend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@AllArgsConstructor
public class StringToJsonDto {
//    @JsonProperty("blabla")
    private String message;


}
