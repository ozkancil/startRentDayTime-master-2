package com.visionrent.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ImageSavedResponse extends VRResponse{

    private String imageId;

    /**
     * good usage of parent constructor
     */
    public ImageSavedResponse(String imageId, String message, boolean success){
        super(message,success);
        this.imageId = imageId;
    }



}
