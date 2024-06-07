package org.odds.file.io;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) throws IOException, URISyntaxException {
        // path to image files directory
        Path p = Paths.get("P:", "Real Estate Maps", "03", "I", "ROW ACQUISITON MAPS_RC", "01_ALBANY");
        System.out.println(p.toAbsolutePath().toString().concat("\n-----\n"));

        ITesseract iTess = new Tesseract();
        //iTess.setLanguage("eng");
        Path trainPath = Path.of(Main.class.getClassLoader().getResource("tessdata").toURI());
        iTess.setDatapath(trainPath.toString());

        try(Stream<Path> stream = Files.walk(Paths.get(p.toUri()))) {
            stream.filter((f) -> Files.isRegularFile(f.toAbsolutePath())).forEach((f) -> {
                System.out.println(f.toAbsolutePath());

                try {
                    String res = iTess.doOCR(f.toFile());
                    System.out.println(res);
                } catch (TesseractException e) {
                    throw new RuntimeException(e);
                }

                System.exit(0);
            });
        }
    }
}
