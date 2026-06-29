package com.amit.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateMeassageRequest {

    private String content;
    private  Long projectId;
    private Long senderId;

}
