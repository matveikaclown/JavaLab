import java.io.*;

public class SaveLoad {
    private static SquadFactory factory = null;

    public static void output(Squad o, OutputStream out) { o.output(out); }

    public static Squad input(InputStream in) throws Exception {
        String type;
        int size;
        String squadName;
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
        String line = bufferedReader.readLine();
        int index = line.indexOf("|");
        type = line.substring(0, index);
        size = Integer.parseInt(line.substring(index + 1, (index = line.indexOf("|", index + 1))));
        squadName = line.substring(index + 1, (index = line.indexOf("|", index + 1)));
        MyPare[] units = new MyPare[size];
        for (int i = 0; i < size; i++) {
            units[i] = new MyPare(line.substring(index + 1, (index = line.indexOf("|", index + 1))),
                    Integer.parseInt(line.substring(index + 1, (index = line.indexOf("|", index + 1)))));
        }
        bufferedReader.close();
        if (type.equals("SithSquad")) return new SithSquad(size, squadName, units);
        if (type.equals("JediSquad")) return new JediSquad(size, squadName, units);
        else throw new Exception("Проблемы с названием типа объекта в файле");
    }

    public static void write(Squad o, Writer out) { o.write(out); }

    public static Squad read(Reader in) throws IOException, JediSquad.JediClassException, SithSquad.SithClassException {
        String type;
        int size;
        String squadName;
        BufferedReader bufferedReader = new BufferedReader(in);
        String line = bufferedReader.readLine();
        int index = line.indexOf("|");
        type = line.substring(0, index);
        size = Integer.parseInt(line.substring(index + 1, (index = line.indexOf("|", index + 1))));
        squadName = line.substring(index + 1, (index = line.indexOf("|", index + 1)));
        MyPare[] units = new MyPare[size];
        for (int i = 0; i < size; i++) {
            units[i] = new MyPare(line.substring(index + 1, (index = line.indexOf("|", index + 1))),
                    Integer.parseInt(line.substring(index + 1, (index = line.indexOf("|", index + 1)))));
        }
        bufferedReader.close();
        if (type.equals("SithSquad")) return new SithSquad(size, squadName, units);
        if (type.equals("JediSquad")) return new JediSquad(size, squadName, units);
        throw new InvalidObjectException("Problems with the name of the object type in the file");
    }

    public static void serialize(Squad squad, String path) {
        try (FileOutputStream fos = new FileOutputStream(path)) {
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(squad);

        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static Squad deserialize(String path, String type) throws InvalidObjectException {
        try (FileInputStream fis = new FileInputStream(path)){
            ObjectInputStream ois = new ObjectInputStream(fis);
            if (type.equals("SithSquad")) return (SithSquad) ois.readObject();
            if (type.equals("JediSquad")) return (JediSquad) ois.readObject();
        }
        catch(IOException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        throw new InvalidObjectException("Problems with the name of the object type in the file");
    }

    Squad unmodifiable(Squad o) {
        return new Decorator(o);
    }

    public static void setSquadFactory(SquadFactory f) {
        factory = f;
    }

    public static Squad createInstance() {
        return factory.createInstance();
    }
}
