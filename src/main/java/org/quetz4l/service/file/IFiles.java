package org.quetz4l.service.file;

import java.util.List;

public interface IFiles {
    List<String> readInputTXT();

    List<String> readInputCSV();

    void writeOutputTXT(List<String> list);
}
