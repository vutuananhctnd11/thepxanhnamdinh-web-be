package com.graduate.be_txnd_fanzone.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.graduate.be_txnd_fanzone.dto.cloudinary.FileResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CloudinaryService {

    Cloudinary cloudinary;

    public String uploadFile(MultipartFile file) throws IOException {
        Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
        return uploadResult.get("secure_url").toString();
    }

    public List<FileResponse> uploadListFile(List<MultipartFile> files) throws IOException {
        List<FileResponse> uploadResults = new ArrayList<>();
        for (MultipartFile file : files) {
            String link = uploadFile(file);
            String fileType = file.getContentType();
            byte type = 1;
            assert fileType != null;
            if (fileType.contains("image")) { type = (byte) 0; }
            uploadResults.add(new FileResponse(link, type));
        }
        return uploadResults;
    }
}
