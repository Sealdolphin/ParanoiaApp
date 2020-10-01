package paranoia.helper;

import paranoia.core.Clone;
import paranoia.core.SecurityClearance;
import paranoia.core.cpu.ParanoiaAttribute;
import paranoia.core.cpu.Skill;
import paranoia.core.cpu.Stat;

import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

import static daiv.Computer.randomItem;

public class TestClone extends Clone {

    public static final String testName = "Test";
    public static final String testSector = "TST";
    public static final String testGender = "NULL";
    public static final SecurityClearance testClearance = randomItem(SecurityClearance.values());
    public final Set<ParanoiaAttribute> attributes = new HashSet<>();

    public TestClone(){
        this(new Random().nextInt(10));
    }

    public TestClone(int id) {
        this(testName, testSector, testClearance, testGender, id, null);
    }

    private TestClone(String name, String sector, SecurityClearance clearance, String gender, int playerId, BufferedImage image) {
        super(name, sector, clearance, gender, playerId, image);
        //Setup basic attributes

        Set<Skill> testAttributes = new HashSet<>();

        while(testAttributes.size() < 10) {
            Skill skill = randomItem(Skill.values());
            testAttributes.add(skill);
        }

        Iterator<Skill> skillIterator = testAttributes.iterator();

        for (int i = 1; i <= 5; i++) {
            attributes.add(ParanoiaAttribute.getSkill(skillIterator.next(), i));
            attributes.add(ParanoiaAttribute.getSkill(skillIterator.next(), -i));
        }

        Set<ParanoiaAttribute> skills = new HashSet<>(attributes);

        for (Stat stat : Stat.values()) {
            int statValue = (int) skills.stream().filter(attr ->
                attr.getValue() > 0 &&
                    Skill.getSkillByName(attr.getName()).getParent().equals(stat))
                .count();
            attributes.add(ParanoiaAttribute.getStat(stat, statValue));
        }
    }
}
