package paranoia.visuals.custom;

import javax.swing.filechooser.FileFilter;
import java.io.File;

public class ParanoiaImageFilter extends FileFilter {

    @Override
    public boolean accept(File f) {
        if(f.isDirectory()) return true;
        String path = f.getAbsolutePath();
        return path.endsWith(".png") ||
            path.endsWith(".jpg") ||
            path.endsWith(".jpeg") ||
            path.endsWith(".svg") ||
            path.endsWith(".bmp") ||
            path.endsWith(".gif");
    }

    @Override
    public String getDescription() {
        return "Alpha Complex compatible images (PNG | JPG | BMP | GIF | SVG)";
    }
}
