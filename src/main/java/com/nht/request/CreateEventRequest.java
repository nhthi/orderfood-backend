package com.nht.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateEventRequest {
    private String image;
    private String location;
    private String startAt;
    private String endAt;
    private String eventName;
}
