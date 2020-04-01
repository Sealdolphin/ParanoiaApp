package paranoia.services.hpdmc;

import paranoia.core.cpu.ParanoiaAttribute;
import paranoia.core.cpu.Skill;
import paranoia.core.cpu.Stat;
import paranoia.visuals.rnd.ActionCard;
import paranoia.visuals.rnd.EquipmentCard;
import paranoia.visuals.rnd.MutationCard;
import paranoia.visuals.rnd.ParanoiaCard;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;

import static paranoia.Paranoia.getParanoiaResource;

public class CardDecoder {

    private String filePath;
    private ParanoiaMap<Integer> data;

    public CardDecoder(String filePath){
        this.filePath = filePath;
    }

    public void readDatabase() throws IOException {
        File database = new File(getParanoiaResource(filePath));
        data = new ParanoiaMap<>();

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
     * id the card's ID
     * order the action order (0 - 11). If bigger than 0, then it is considered an action card
     * reaction 1 if the card is a reaction card (0 otherwise)
     * @return Action card with parameters
     */
    public LinkedList<ActionCard> decodeActionCards() {
        LinkedList<ActionCard> list = new LinkedList<>();

        for (ParanoiaRow<Integer> record : data.records){
            int id = record.get(0);
            int order = record.get(1);
            int reaction = record.get(2);
            list.add(new ActionCard(id, (order > 0), (reaction == 1), order));
        }

        return list;
    }

    /**
     * Decodes an equipment card from the database
     * id the card's ID
     * order the action order it gives in addition
     * modifier the action order modifier (0 - 3: stat bonus, 4+: skill bonus)
     * level the item level
     * size the item's size (0 - 3)
     * @return Equipment card with parameters
     */
    public LinkedList<EquipmentCard> decodeEquipmentCards() {

        LinkedList<EquipmentCard> list = new LinkedList<>();

        for (ParanoiaRow<Integer> record : data.records){
            int id = record.get(0);
            int order = record.get(1);
            int modifier = record.get(2);
            int level = record.get(3);
            int size = record.get(4);
            String modifierName;

            EquipmentCard.EquipmentSize eqSize =
                EquipmentCard.EquipmentSize.values()[size];

            if( modifier < Stat.values().length ) {
                Stat statModifier = Stat.values()[modifier];
                modifierName = statModifier.toString();
            } else {
                Skill skillModifier = Skill.values()[modifier - 3];
                modifierName = skillModifier.toString();
            }

            list.add(new EquipmentCard(id, order, new ParanoiaAttribute(modifierName), level, eqSize));
        }
        return list;
    }

    public LinkedList<MutationCard> decodeMutationCards() {

        LinkedList<MutationCard> list = new LinkedList<>();

        for (ParanoiaRow<Integer> record : data.records){
            int id = record.get(0);
            int order = record.get(1);

            list.add(new MutationCard(id, order));
        }

        return list;
    }

    public static <T extends ParanoiaCard>LinkedList<T> decodeCardsGeneral(int max, Class<T> clazz) {
        LinkedList<T> list = new LinkedList<>();
        try {
        for (int i = 0; i < max; i++) {
            list.add(clazz.getConstructor(Integer.class).newInstance(i));
        }
        } catch (InstantiationException | NoSuchMethodException |
            InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return list;
    }

    private class ParanoiaMap<T> {
        private LinkedList<ParanoiaRow<T>> records;

        public ParanoiaMap() {
            records = new LinkedList<>();
        }

        public void addRow(T[] items) {
            records.add(new ParanoiaRow<>(items));
        }

        public T getItem(int row, int index) {
            return records.get(row).get(index);
        }
    }

    private class ParanoiaRow<T> {
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
