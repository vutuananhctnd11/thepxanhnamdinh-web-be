package com.graduate.be_txnd_fanzone.controller;

import com.graduate.be_txnd_fanzone.dto.ApiResponse;
import com.graduate.be_txnd_fanzone.dto.cloudinary.FileResponse;
import com.graduate.be_txnd_fanzone.enums.ErrorCode;
import com.graduate.be_txnd_fanzone.exception.CustomException;
import com.graduate.be_txnd_fanzone.service.CloudinaryService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/cloudinary")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CloudinaryController {

    CloudinaryService cloudinaryService;

    @PostMapping
    public ResponseEntity<ApiResponse<String>> uploadFile(@RequestParam("file") MultipartFile file){
        ApiResponse<String> apiResponse = new ApiResponse<>();
        try {
            String url = cloudinaryService.uploadFile(file);
            apiResponse.setData(url);
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            throw new CustomException(ErrorCode.UPLOAD_FAILED);
        }
    }

    @PostMapping(value = "/list-file", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<List<FileResponse>>> uploadListFile(@RequestPart("files") List<MultipartFile> files){
        ApiResponse<List<FileResponse>> apiResponse = new ApiResponse<>();
        try {
            List<FileResponse> urls = cloudinaryService.uploadListFile(files);
            apiResponse.setStatus("success");
            apiResponse.setData(urls);
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            throw new CustomException(ErrorCode.UPLOAD_FAILED);
        }
    }
}
