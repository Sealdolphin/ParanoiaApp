package paranoia.helper;

import paranoia.core.Clone;
import paranoia.core.Computer;
import paranoia.core.SecurityClearance;

import java.awt.image.BufferedImage;
import java.util.Random;

public class TestClone extends Clone {

    public static final String testName = "Test";
    public static final String testSector = "TST";
    public static final String testGender = "NULL";
    public static final SecurityClearance testClearance = Computer.randomItem(SecurityClearance.values());

    public TestClone(){
        this(new Random().nextInt(10));
    }

    public TestClone(int id) {
        this(testName, testSector, testClearance, testGender, id, null);
    }

    private TestClone(String name, String sector, SecurityClearance clearance, String gender, int playerId, BufferedImage image) {
        super(name, sector, clearance, gender, playerId, image);
    }
}
