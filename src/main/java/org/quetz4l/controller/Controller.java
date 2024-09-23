package org.quetz4l.controller;

import org.quetz4l.service.file.FilesImp;

import java.util.List;

import static org.quetz4l.service.text.TextPrettifier.prettifier;


public class Controller {
    public static void start() {
        FilesImp filesImp = new FilesImp();

        List<String> stringsFromTxt = filesImp.readInputTXT();
        List<String> stringsFromCSV = filesImp.readInputCSV();

        List<String> prettyText = prettifier(stringsFromTxt, stringsFromCSV);

        filesImp.writeOutputTXT(prettyText);
    }


}
