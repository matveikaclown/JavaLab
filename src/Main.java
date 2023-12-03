import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;

public class Main {

    private static final String endl = "------------------------";

    public static void main (String[] args) {
        task1();
        task2();
        task3();
    }

    private static void task1 () {
        MyPare[] pairs1 = {new MyPare("Мол", 15), new MyPare("Вейдер", 25), new MyPare("Сидиус", 35)};
        MyPare[] pairs2 = {new MyPare("Дарт Малак", 30), new MyPare("Дарт Реван", 50), new MyPare("Дарт Малгус", 40)};
        MyPare[] pairs4 = {new MyPare("Эйла Секура", 15), new MyPare("Кит Фисто", 15), new MyPare("Шаак Ти", 40)};
        MyPare[] pairs7 = {new MyPare("Дарт Трея", 45), new MyPare("Дарт Нихилус", 65), new MyPare("Дарт Сион", 50)};
        MyPare[] pairs9 = {new MyPare("Мейс Винду", 35), new MyPare("Йода", 45), new MyPare("Оби-Ван Кеноби", 40)};
        MyPare[] pairs10 = {new MyPare("Джухани", 15), new MyPare("Реван", 55), new MyPare("Бастила Шан", 30)};
        try {
            System.out.println("\n" + endl + "\n1. Создание массива однотипных объектов\n");
            Squad[] squads = {
                    new SithSquad(3, "Первый отряд", pairs1),
                    new SithSquad(3, "Второй отряд", pairs2),
                    new JediSquad(),
                    new JediSquad(3, "Четвертый отряд", pairs4),
                    new SithSquad(),
                    new SithSquad(),
                    new SithSquad(3, "Седьмой отряд", pairs7),
                    new JediSquad(),
                    new JediSquad(3, "Девятый отряд", pairs9),
                    new JediSquad(3, "Десятый отряд", pairs10)
            };
            for (Squad squad : squads)
                System.out.println(squad);
            System.out.println(endl + "\n2. Объекты с одинаковым результатом функционального метода\n");
            int equal = 999, count = 0;
            for (Squad squad : squads) {
                if (squad.valueOfMinPower() < equal) {
                    equal = squad.valueOfMinPower();
                    count = 1;
                }
                else if (squad.valueOfMinPower() == equal)
                    count++;
            }
            Squad[] squads1 = new Squad[count];
            int i = 0;
            for (Squad squad : squads) {
                if (squad.valueOfMinPower() == equal)
                    squads1[i++] = squad;
                if (i == count) break;
            }
            for (Squad squad : squads1)
                System.out.println(squad);
            System.out.println(endl + "\n3. Два массива однотипных объектов\n");
            ArrayList<Squad> sith = new ArrayList<>();
            ArrayList<Squad> jedi = new ArrayList<>();
            for (Squad squad : squads) {
                if (squad.getClass() == SithSquad.class)
                    sith.add(squad);
                if (squad.getClass() == JediSquad.class)
                    jedi.add(squad);
            }
            System.out.println("Ситхи:\n");
            for (Squad squad : sith)
                System.out.println(squad);
            System.out.println("Джедаи:\n");
            for (Squad squad : jedi)
                System.out.println(squad);
            System.out.println(endl);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void task2() {
        try {
            Squad sq1Old = new SithSquad();
            SaveLoad.output(sq1Old, new FileOutputStream("test.bin"));
            Squad sq1New = SaveLoad.input(new FileInputStream("test.bin"));
            System.out.println(sq1New.equals(sq1Old));

            Squad sq2Old = new JediSquad();
            SaveLoad.write(sq2Old, new FileWriter("test.txt"));
            Squad sq2New = SaveLoad.read(new FileReader("test.txt"));
            System.out.println(sq2New.equals(sq2Old));

            Squad sq3Old = new SithSquad(3, "Отряд",
                    new MyPare("Ситх1", 50), new MyPare("Ситх2", 75), new MyPare("Ситх3", 60));
            SaveLoad.serialize(sq3Old, "test.data");
            Squad sq3New = SaveLoad.deserialize("test.data", "SithSquad");
            System.out.println(sq3New.equals(sq3Old));
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void task3() {
        try {
            JediSquad sq = new JediSquad(4, "SQUAD", new MyPare("Jedi1", 25), new MyPare("Jedi2", 30),
                    new MyPare("Jedi3", 35), new MyPare("jedi4", 40));
            for (MyPare out : sq) {
                System.out.println(out);
            }

            System.out.println(endl);

            Squad instance;
            SaveLoad.setSquadFactory(JediSquad.jediFactory);
            instance = SaveLoad.createInstance();
            System.out.println(instance);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}

