package com.workintech.zoo.exceptions;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ZooErrorResponse {
    private int status;
    private String message;
    private long timestamp;
}
