package paranoia.services.hpdmc;

import paranoia.core.cpu.Skill;
import paranoia.core.cpu.Stat;
import paranoia.visuals.rnd.ActionCard;
import paranoia.visuals.rnd.EquipmentCard;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;

import static paranoia.Paranoia.getParanoiaResource;

public abstract class CardDecoder {

    public static void readDatabase(String filePath) throws IOException {
        File database = new File(getParanoiaResource(filePath));
        ParanoiaMap<Integer> data = new ParanoiaMap<>();

        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(database)));

        //Cutoff header
        reader.readLine();
        while (true) {
            String line = reader.readLine();
            if(line == null) break;
            String[] stringData = line.split(",");
            Integer[] intData = new Integer[stringData.length];
            for (int i = 0; i < stringData.length; i++) {
                intData[i] = Integer.valueOf(stringData[i]);
            }
            data.addRow(intData);
        }

        reader.close();

    }

    /**
     * Decodes an action card from the database
     * @param id the card's ID
     * @param order the action order (0 - 11). If bigger than 0, then it is considered an action card
     * @param reaction 1 if the card is a reaction card (0 otherwise)
     * @return Action card with parameters
     */
    public static ActionCard decodeActionCard(
        int id,
        int order,
        int reaction
    ) {
        return new ActionCard(id, (order > 0), (reaction == 1), order);
    }

    /**
     * Decodes an equipment card from the database
     * @param id the card's ID
     * @param order the action order it gives in addition
     * @param modifier the action order modifier (0 - 3: stat bonus, 4+: skill bonus)
     * @param level the item level
     * @param size the item's size (0 - 3)
     * @return Equipment card with parameters
     */
    public static EquipmentCard decodeEquipmentCard(
        int id,
        int order,
        int modifier,
        int level,
        int size
    ) {
        EquipmentCard.EquipmentSize eqSize =
            EquipmentCard.EquipmentSize.values()[size];

        if( modifier < Stat.values().length ) {
            Stat statModifier = Stat.values()[modifier];
            return new EquipmentCard(id, order, statModifier, level, eqSize);
        } else {
            Skill skillModifier = Skill.values()[modifier - 3];
            return new EquipmentCard(id, order, skillModifier, level, eqSize);
        }
    }

    private static class ParanoiaMap<T> {
        private LinkedList<ParanoiaRow<T>> records;

        public ParanoiaMap() {
            records = new LinkedList<>();
        }

        public void addRow(T[] items) {
            records.add(new ParanoiaRow<T>(items));
        }

        public T getItem(int row, int index) {
            return records.get(row).get(index);
        }
    }

    private static class ParanoiaRow<T> {
        private LinkedList<T> items;

        private ParanoiaRow(T[] items){
            this();
            for (T item : items) {
                addItem(item);
            }
        }

        private ParanoiaRow(){
            items = new LinkedList<>();
        }

        private void addItem(T item) {
            items.add(item);
        }

        private T get(int index) {
            return items.get(index);
        }

    }

}
