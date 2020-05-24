package paranoia.services.technical.command;

import org.json.JSONArray;
import org.json.JSONObject;
import paranoia.visuals.messages.ParanoiaError;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ACPFCommand extends ParanoiaCommand {

    public interface ParanoiaACPFListener {
        void updateProfile(String name, String gender, String[] personality, BufferedImage image);
    }

    private final String name;
    private final String gender;
    private final String[] personality;
    private final BufferedImage image;
    private final ParanoiaACPFListener listener;

    public ACPFCommand(ParanoiaACPFListener listener) {
        this("","", new String[]{}, null, listener);
    }

    private ACPFCommand(
        String name,
        String gender,
        String[] personality,
        BufferedImage image,
        ParanoiaACPFListener listener
    ) {
        super(CommandType.ACPF);
        this.listener = listener;
        this.name = name;
        this.gender = gender;
        this.personality = personality;
        this.image = image;
    }

    @Override
    public void execute() {
        listener.updateProfile(name, gender, personality, image);
    }

    /*
    BufferedImage buffered = null;
        try {
            buffered = ImageIO.read(new ByteArrayInputStream(image));
        } catch (IOException e) {
            ParanoiaError.error(e);
        }
     */

    @Override
    public JSONObject toJsonObject() {
        JSONObject body = new JSONObject();
        if(image == null) return body;  //Safe-check for image
        //Parse buffered image
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] imageRaw = null;
        try {
            ImageIO.write(image, "png", outStream);
            imageRaw = outStream.toByteArray();
        } catch (IOException e) {
            ParanoiaError.error(e);
        }
        //Create JSON
        body.put("name", name);
        body.put("gender", gender);
        body.put("personality", new JSONArray(personality));
        body.put("profile", imageRaw);
        //Wrap command
        return wrapCommand(body);
    }
}
