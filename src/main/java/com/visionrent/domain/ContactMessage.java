package com.visionrent.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "t_cmessage")
public class ContactMessage {

    // TODO please read and learn strategy types
    //! https://www.baeldung.com/hibernate-identifiers
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50,nullable = false)
    private String name;

    @Column(length = 50,nullable = false)
    private String subject;

    @Column(length = 200,nullable = false)
    private String body;

    @Column(length = 50,nullable = false)
    private String email;




}
