package org.quetz4l.repository;

import org.quetz4l.controller.AppState;
import org.quetz4l.models.Config;
import org.quetz4l.service.text.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Files {
    //reader
    public static List<String> readInputTXT() {
        Path path = getPath("src\\main\\resources\\input\\txt\\", Config.inputFileName);
        return readFileBytes(path).orElseGet(() -> {
            Logger.printError("Input not found: ", path.toString());
            AppState.closeApp();
            return new ArrayList<>();
        });

    }

    public static List<String> readInputCSV() {
        Path path = getPath("src\\main\\resources\\input\\csv\\", Config.csvFileName);
        return readFile(path).orElseGet(() -> {
            Logger.printError("Airport lookup not found: ", path.toString());
            AppState.closeApp();
            return new ArrayList<>();
        });
    }

    private static Optional<List<String>> readFile(Path path) {
        try {
            return Optional.of(java.nio.file.Files.readAllLines(path, StandardCharsets.UTF_8));
        } catch (IOException e) {
            return Optional.empty();
        }
    }

    private static Optional<List<String>> readFileBytes(Path path) {
        try {
            String raw = new String(java.nio.file.Files.readAllBytes(path));
            String rawFormated = raw.replaceAll("[\\v\\r\\f]", "\n");

            List<String> result = new ArrayList<>();
            String lastLine = ".";
            for (String str : rawFormated.split("\\n")) {
                if (lastLine.trim().isEmpty() && str.trim().isEmpty()) {
                    continue;
                }
                result.add(str);
                lastLine = str;
            }
            return Optional.of(result);
        } catch (IOException e) {
            return Optional.empty();
        }
    }


    //writer
    public static void writeOutputTXT(List<String> list) {
        Path path = getPath("src\\main\\resources\\output\\", Config.outputFileName);
        File output = new File(path.toString());
        try {
            if (output.createNewFile()) Logger.printMessage("Created file: ", output.getName());
            else Logger.printMessage("File was rewritten: ", output.getName());
            java.nio.file.Files.write(path, list);
        } catch (IOException e) {
            System.out.println("Output not found");
        }
    }

    private static Path getPath(String path, String string) {
        if (string.startsWith("./")) string = string.substring(2);
        return Paths.get(path + string);
    }


}
