package com.example.pkglab3.controller;

import com.example.pkglab3.service.ImageProcessingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@CrossOrigin(origins = "http://localhost:63342")
@RequiredArgsConstructor
@RequestMapping("/api/image")
public class ImageProcessingController {

    private final ImageProcessingService imageProcessingService;

    @PostMapping("/linear-contrast")
    public String linearContrast(
            @RequestParam("file") MultipartFile file,
            @RequestParam("alpha") double alpha,
            @RequestParam("beta") double beta) throws IOException {

        File result = imageProcessingService.applyLinearContrast(file, alpha, beta);
        return result.getName();
    }

    @PostMapping("/local-threshold-mean")
    public String localThresholdMean(
            @RequestParam("file") MultipartFile file,
            @RequestParam("blockSize") int blockSize) throws IOException {

        File result = imageProcessingService.localThresholdMean(file, blockSize);
        return result.getName();
    }

    @PostMapping("/local-threshold-gaussian")
    public String localThresholdGaussian(
            @RequestParam("file") MultipartFile file,
            @RequestParam("blockSize") int blockSize) throws IOException {

        File result = imageProcessingService.localThresholdGaussian(file, blockSize);
        return result.getName();
    }

    @PostMapping("/adaptive-threshold")
    public String adaptiveThresholding(
            @RequestParam("file") MultipartFile file,
            @RequestParam("blockSize") int blockSize,
            @RequestParam("c") int c) throws IOException {

        File result = imageProcessingService.adaptiveThresholding(file, blockSize, c);
        return result.getName();
    }
}
