package com.visionrent.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Table(name = "t_imagedata")
@Entity
public class ImageData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Specifies that a persistent property or field should be
     * persisted as a large object to a database-supported large
     * object type.
     */
    @JsonIgnore
    @Lob
    private byte[] data;

    /**
     * custom constructor to initialize the ImageData object
     * @param data array of bytes.
     */
    public ImageData(byte[] data){
        this.data = data;
    }

    public ImageData(Long id){
        this.id = id;
    }





}
