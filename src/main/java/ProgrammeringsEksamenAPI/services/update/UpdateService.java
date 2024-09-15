package ProgrammeringsEksamenAPI.services.update;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Service
public class UpdateService {
    @Value("${updates.directory}")
    private String updatesDirectory;

    // List all available updates
    public String[] listAvailableUpdates() {
        try (Stream<Path> files = Files.list(Paths.get(updatesDirectory))) {
            return files.map(Path::getFileName)
                    .map(Path::toString)
                    .toArray(String[]::new);
        } catch (IOException e) {
            e.printStackTrace();
            return new String[]{};
        }
    }

    // Get the latest update based on file name or last modified date
/*    public Path getLatestUpdate() throws IOException {
        Path directoryPath = Paths.get(updatesDirectory);

        // Recursively search for directories
        System.out.println("Searching for directories in directory and subdirectories: " + directoryPath);
        try (Stream<Path> paths = Files.walk(directoryPath)) {
            Optional<Path> latestDirectory = paths
                    .filter(Files::isDirectory)
                    .max(Comparator.comparingLong(f -> f.toFile().lastModified()));

            if (latestDirectory.isPresent()) {
                Path latestDir = latestDirectory.get();
                if (Files.exists(latestDir)) {
                    System.out.println("Found latest directory: " + latestDir);
                    return latestDir;
                } else {
                    System.out.println("Latest directory exists but is not accessible.");
                }
            } else {
                System.out.println("No directories found.");
            }
        } catch (IOException e) {
            System.err.println("Error reading directory: " + e.getMessage());
        }

        throw new IOException("No updates available.");
    }*/




    public Path getLatestUpdateDirectory() throws IOException {
        Path directoryPath = Paths.get(updatesDirectory);

        // Recursively search for directories
        System.out.println("Searching for directories in directory and subdirectories: " + directoryPath);
        try (Stream<Path> paths = Files.walk(directoryPath)) {
            Optional<Path> latestDirectory = paths
                    .filter(Files::isDirectory)
                    .max(Comparator.comparingLong(path -> path.toFile().lastModified()));

            if (latestDirectory.isPresent()) {
                Path latestDir = latestDirectory.get();
                if (Files.exists(latestDir)) {
                    System.out.println("Found latest directory: " + latestDir);
                    return latestDir;
                } else {
                    System.out.println("Latest directory exists but is not accessible.");
                }
            } else {
                System.out.println("No directories found.");
            }
        } catch (IOException e) {
            System.err.println("Error reading directory: " + e.getMessage());
        }

        return null;
    }







    // Load a specific update as a resource
    public Resource loadUpdateAsResource(String filename) throws IOException {
        Path filePath = Paths.get(updatesDirectory).resolve(filename).normalize();
        Resource resource = new UrlResource(filePath.toUri());
        if (resource.exists() && resource.isReadable()) {
            return resource;
        } else {
            throw new IOException("Could not read file: " + filename);
        }
    }
}
