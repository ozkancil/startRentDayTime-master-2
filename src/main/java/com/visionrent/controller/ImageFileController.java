package com.visionrent.controller;

import com.visionrent.domain.ImageFile;
import com.visionrent.dto.ImageFileDTO;
import com.visionrent.dto.response.ImageSavedResponse;
import com.visionrent.dto.response.ResponseMessage;
import com.visionrent.dto.response.VRResponse;
import com.visionrent.service.ImageFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

//TODO -> please check and implement an easy SOAP web service client.
// 1. find a public web service
// 2. connect to this
// 3 . create a SOAP web service and make a call from postman.
@RestController
@RequestMapping("/files")
public class ImageFileController {


    @Autowired
    private ImageFileService imageFileService;
    /**
     *
     * @param file image to save
     * @return
     * Controller to save an image. Limited to admin.
     */
    //http://localhost:8080/files/upload
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/upload")
    public ResponseEntity<ImageSavedResponse> uploadFile(@RequestParam("file") MultipartFile file){
        String imageId = imageFileService.saveImage(file);
        //TODO we do not have any function to check if the file already exist in DB.
        // how we can check this?
        ImageSavedResponse response =
                new ImageSavedResponse(imageId, ResponseMessage.IMAGE_SAVED_RESPONSE_MESSAGE,true);

        return ResponseEntity.ok(response);
    }

    //http://localhost:8080/files/display/089ff884-00b0-443d-a82c-00a113c683ab
    @GetMapping("/display/{id}")
    public ResponseEntity<byte[]>displayImage(@PathVariable String id){
        ImageFile imageFile = imageFileService.getImageById(id);
        /**
         * another way of initialising the Http header.
         * We are injecting this instance into ResponseEntity constructor.
         */
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.IMAGE_PNG);
        return new ResponseEntity<>(imageFile.getImageData().getData(), header, HttpStatus.OK);
    }

    /**
     * @param id unique ID of the image
     * @return image from DB
     */
    //http://localhost:8080/files/download/089ff884-00b0-443d-a82c-00a113c683ab
    @GetMapping("/download/{id}")
    public ResponseEntity<byte[]>downloadImage(@PathVariable String id) {
        ImageFile imageFile = imageFileService.getImageById(id);
        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename="+imageFile.getName())
                .body(imageFile.getImageData().getData());
    }

    //http://localhost:8080/files
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ImageFileDTO>> getAllImages(){
        List<ImageFileDTO> allImageDTO = imageFileService.getAllImages();
        return ResponseEntity.ok(allImageDTO);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<VRResponse> deleteImageFile(@PathVariable String id){
        imageFileService.removeById(id);
        VRResponse response = new VRResponse(ResponseMessage.IMAGE_DELETE_RESPONSE_MESSAGE,true);
        return ResponseEntity.ok(response);
        /*
         * the same usage
         * return ResponseEntity.ok().body(response);
         */





    }



}
