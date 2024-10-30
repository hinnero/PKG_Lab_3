package com.example.pkglab3.service;

import nu.pattern.OpenCV;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class ImageProcessingService {

    static {
        OpenCV.loadLocally();
    }

    private static final String OUTPUT_DIR = "src/main/resources/static/output/";
    private static final long MAX_FILE_AGE = TimeUnit.HOURS.toMillis(24); // Макс. время хранения файла: 24 часа
    private static final long SCHEDULE_VALUE = 86400000;


    public ImageProcessingService() {
        File directory = new File(OUTPUT_DIR);
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }

    @Scheduled(fixedRate = SCHEDULE_VALUE)
    public void cleanOldFiles() {
        File directory = new File(OUTPUT_DIR);
        File[] files = directory.listFiles();

        if (files != null) {
            long currentTime = Instant.now().toEpochMilli();

            for (File file : files) {
                if (currentTime - file.lastModified() > MAX_FILE_AGE) {
                    boolean deleted = file.delete();
                    if (!deleted) {
                        System.out.println("Не удалось удалить файл: " + file.getAbsolutePath());
                    }
                }
            }
        }
    }

    private File saveResultImage(Mat image) {
        String filename = UUID.randomUUID() + ".png";
        File output = new File(OUTPUT_DIR + filename);
        Imgcodecs.imwrite(output.getAbsolutePath(), image);

        return output;
    }

    public File applyLinearContrast(MultipartFile file, double alpha, double beta) throws IOException {
        Mat src = Imgcodecs.imdecode(new MatOfByte(file.getBytes()), Imgcodecs.IMREAD_GRAYSCALE);
        Mat dst = new Mat();

        src.convertTo(dst, -1, alpha, beta);
        return saveResultImage(dst);
    }

    public File localThresholdMean(MultipartFile file, int blockSize) throws IOException {
        Mat src = Imgcodecs.imdecode(new MatOfByte(file.getBytes()), Imgcodecs.IMREAD_GRAYSCALE);
        Mat dst = new Mat();

        Imgproc.adaptiveThreshold(src, dst, 255, Imgproc.ADAPTIVE_THRESH_MEAN_C, Imgproc.THRESH_BINARY, blockSize, 10);
        return saveResultImage(dst);
    }

    public File localThresholdGaussian(MultipartFile file, int blockSize) throws IOException {
        Mat src = Imgcodecs.imdecode(new MatOfByte(file.getBytes()), Imgcodecs.IMREAD_GRAYSCALE);
        Mat dst = new Mat();

        Imgproc.adaptiveThreshold(src, dst, 255, Imgproc.ADAPTIVE_THRESH_GAUSSIAN_C, Imgproc.THRESH_BINARY, blockSize, 10);
        return saveResultImage(dst);
    }

    public File adaptiveThresholding(MultipartFile file, int blockSize, int c) throws IOException {
        Mat src = Imgcodecs.imdecode(new MatOfByte(file.getBytes()), Imgcodecs.IMREAD_GRAYSCALE);
        Mat dst = new Mat();

        Imgproc.adaptiveThreshold(src, dst, 255, Imgproc.ADAPTIVE_THRESH_GAUSSIAN_C, Imgproc.THRESH_BINARY, blockSize, c);
        return saveResultImage(dst);
    }
}
