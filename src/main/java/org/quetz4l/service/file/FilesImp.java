package org.quetz4l.service.file;


import org.quetz4l.repository.Files;

import java.util.List;

public class FilesImp implements IFiles {

    @Override
    public List<String> readInputTXT() {
        return Files.readInputTXT();
    }

    @Override
    public List<String> readInputCSV() {
        return Files.readInputCSV();
    }

    @Override
    public void writeOutputTXT(List<String> list) {
        Files.writeOutputTXT(list);
    }
}
