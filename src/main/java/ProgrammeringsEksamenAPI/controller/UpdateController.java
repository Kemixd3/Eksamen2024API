package ProgrammeringsEksamenAPI.controller;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import java.util.Optional;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/updates")
public class UpdateController {

    private final String updatesDirectory = "C:/Users/SurfaceLaptop/Desktop/Eksamen2024API/src/updates"; // Adjust this path

    @GetMapping("/RELEASES")
    public ResponseEntity<InputStreamResource> downloadLatestUpdate() throws IOException {
        Path latestUpdateDir = getLatestUpdateDirectory();
        if (latestUpdateDir == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        File latestDirFile = latestUpdateDir.toFile();
        if (!latestDirFile.isDirectory()) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new InputStreamResource(new ByteArrayInputStream("Expected a directory but found a file".getBytes())));
        }

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try (ZipOutputStream zipOutputStream = new ZipOutputStream(byteArrayOutputStream)) {
            File[] files = latestDirFile.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isFile()) {
                        try (InputStream fileInputStream = new FileInputStream(file)) {
                            ZipEntry zipEntry = new ZipEntry(file.getName());
                            zipOutputStream.putNextEntry(zipEntry);

                            byte[] buffer = new byte[1024];
                            int len;
                            while ((len = fileInputStream.read(buffer)) > 0) {
                                zipOutputStream.write(buffer, 0, len);
                            }
                            zipOutputStream.closeEntry();
                        }
                    }
                }
            }
        } catch (IOException e) {
            throw new IOException("Error creating ZIP file: " + e.getMessage());
        }

        InputStreamResource resource = new InputStreamResource(new ByteArrayInputStream(byteArrayOutputStream.toByteArray()));

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"latest-update.zip\"")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .contentLength(byteArrayOutputStream.size())
                .body(resource);
    }

    private Path getLatestUpdateDirectory() throws IOException {
        Path directoryPath = Paths.get(updatesDirectory);

        try (Stream<Path> paths = Files.walk(directoryPath)) {
            Optional<Path> latestDirectory = paths
                    .filter(Files::isDirectory)
                    .max((p1, p2) -> Long.compare(p1.toFile().lastModified(), p2.toFile().lastModified()));

            return latestDirectory.orElse(null);
        } catch (IOException e) {
            throw new IOException("Error reading directory: " + e.getMessage());
        }
    }
}
