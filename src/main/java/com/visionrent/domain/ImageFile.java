package com.visionrent.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "t_imagefile")
@Entity
public class ImageFile {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid",strategy = "uuid2")
    private String id;

    private String name;

    private String type;


    private long length;

    /**
     *  Defines the set of cascadable operations that are propagated
     *  to the associated entity.
     */
    //TODO please read about cascade types.
    //https://www.baeldung.com/jpa-cascade-types
    @OneToOne(cascade = CascadeType.ALL)
    private ImageData imageData;

    public ImageFile (String name, String type, ImageData imageData){
        this.name = name;
        this.type = type;
        this.imageData = imageData;
        this.length = imageData.getData().length;
    }


}
