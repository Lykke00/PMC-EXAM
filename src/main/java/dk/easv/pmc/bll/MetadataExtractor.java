package dk.easv.pmc.bll;

import dk.easv.pmc.be.ShowAlerts;
import org.apache.tika.Tika;
import org.apache.tika.metadata.Metadata;

import java.io.File;
import java.io.IOException;

public class MetadataExtractor {
    public static String getDuration(String filePath) {
        String projectDir = System.getProperty("user.dir");
        filePath = filePath.replace("\\", "/");

        Tika tika = new Tika();
        Metadata metadata = new Metadata();

        File file = null;
        if (filePath.contains(":")){
            file = new File(filePath);
        }
        else{
            file = new File(projectDir + "/" + filePath);
        }

        try {
            tika.parse(file, metadata);

            for (String name : metadata.names()) {
                //System.out.println(name + ": " + metadata.get(name));
            }


            String duration = metadata.get("xmpDM:duration");
            return duration != null ? duration : "Ukendt varighed";
        } catch (IOException e) {
            ShowAlerts.displayError(e.getMessage());
            return "Fejl ved metadataudtræk";
        }
    }

}
