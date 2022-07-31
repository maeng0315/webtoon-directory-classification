import java.io.*;
import java.util.Set;
import java.util.TreeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
    private static final Logger log = LoggerFactory.getLogger(Main.class);

    private static final String originalDirPath = "C:/Users/dakma/Downloads/완결_양말도깨비_역순";
    private static final boolean isDesc = false;

    public static void main(String[] args) {

        File originalDir = new File(originalDirPath);
        File originalFiles[] = originalDir.listFiles();

        String newDirPath = originalDirPath + " (정렬)";
        File newDir = new File(newDirPath);
        if (!newDir.exists()) {
            newDir.mkdir();
        }

        for (File originalFile : originalFiles) {
            String originalFileName = originalFile.getName();
            String splitFileName[] = originalFileName.split("_");
            String episodeNumber = splitFileName[1];

            String episodeDirPath = newDirPath + "/" + episodeNumber;
            File episodeDir = new File(episodeDirPath);
            if (!episodeDir.exists()) {
                episodeDir.mkdir();
                log.debug("make episode dir : {}", episodeNumber);
            }

            String makeFilePath = episodeDirPath + "/" + originalFileName;
            File makeFile = new File(makeFilePath);
            log.debug("make File : {}", makeFile);

            try {
                FileInputStream input = new FileInputStream(originalFile);
                FileOutputStream output = new FileOutputStream(makeFile);

                byte[] buf = new byte[1024];
                int readData;
                while ((readData = input.read(buf)) > 0) {
                    output.write(buf, 0, readData);
                }
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }
    }
}
