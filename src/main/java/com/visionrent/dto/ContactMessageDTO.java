package com.visionrent.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContactMessageDTO {

    private Long id;

    private String name;

    private String subject;

    private String body;

    private String email;


}
