import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.io.Writer;
import java.util.NoSuchElementException;

public class SithSquad implements Squad, Serializable, Iterable<MyPare> {
    private  final int size;
    private String squadName;
    private final MyPare[] sith;
    public static final SithFactory sithFactory = new SithFactory();

    public SithSquad() {
        this.size = 5;
        this.squadName = "Dark Side";
        this.sith = new MyPare[5];
        this.sith[0] = new MyPare("Darth Sidious", 50);
        this.sith[1] = new MyPare("Darth Vader", 35);
        this.sith[2] = new MyPare("Darth Maul", 25);
        this.sith[3] = new MyPare("Darth Revan", 55);
        this.sith[4] = new MyPare("Darth Nihilus", 75);
    }

    public SithSquad(int size, String squadName, MyPare... pairs) throws SithClassException {
        if (size < 0) throw new SithClassException("Squad size is less than zero");
        if (size < pairs.length) throw new SithClassException("The number of pairs is greater than the size of the squad");
        if (size > pairs.length) throw new SithClassException("The number of pairs is less than the size of the squad");
        this.size = size;
        this.squadName = squadName;
        this.sith = new MyPare[size];
        int i = 0;
        for (MyPare pair : pairs) {
            if (pairs[i] == null || pairs[i].getKey() == null || pairs[i].getKey().isEmpty() || pairs[i].getValue() <= 0)
                throw new SithClassRuntimeException("Invalid value/values of pair");
            this.sith[i++] = pair;
        }
    }

    @Override
    public int getSize() { return this.size; }

    @Override
    public String getSquadName() { return this.squadName; }

    @Override
    public MyPare getUnit(int index) { return this.sith[index]; }

    @Override
    public void setSquadName(String squadName) {
        if (squadName == null || squadName.isEmpty()) throw new SithClassRuntimeException("Invalid squad name");
        this.squadName = squadName;
    }

    @Override
    public void setUnit(int index, MyPare sith) {
        if (index >= this.size || index < 0) throw new SithClassRuntimeException("Index out of bound");
        if (sith == null || sith.getKey() == null || sith.getKey().isEmpty() || sith.getValue() <= 0)
            throw new SithClassRuntimeException("Invalid value/values of pair");
        this.sith[index] = sith;
    }

    @Override
    public int valueOfMaxPower() {
        int power = 0;
        for (int i = 0; i < this.size; i++) {
            if (power < this.sith[i].getValue())
                power = this.sith[i].getValue();
        }
        return power;
    }

    @Override
    public int valueOfMinPower() {
        int power = this.sith[0].getValue();
        for (int i = 1; i < this.size; i++) {
            if (power > this.sith[i].getValue())
                power = this.sith[i].getValue();
        }
        return power;
    }

    @Override
    public String nameOfMostPowerful() {
        int power = 0;
        String name = null;
        for (int i = 0; i < this.size; i++) {
            if (power < this.sith[i].getValue()) {
                power = this.sith[i].getValue();
                name = this.sith[i].getKey();
            }
        }
        return name;
    }

    @Override
    public String nameOfLeastPowerful() {
        int power = this.sith[0].getValue();
        String name = this.sith[0].getKey();
        for (int i = 1; i < this.size; i++) {
            if (power > this.sith[i].getValue()) {
                power = this.sith[i].getValue();
                name = this.sith[i].getKey();
            }
        }
        return name;
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        string.append(this.squadName).append("\n");
        for (int i = 0; i < this.size; i++)
            string.append(this.sith[i].getKey()).append(" - ").append(this.sith[i].getValue()).append("\n");
        return string.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        SithSquad squad = (SithSquad) obj;
        for (int i = 0; i < this.size; i++)
            if (!this.sith[i].equals(squad.sith[i])) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int hash = this.size * this.squadName.hashCode();
        for (int i = 0; i < this.size; i++)
            hash  *= this.sith[i].hashCode();
        return hash;
    }

    @Override
    public void output(OutputStream out) {
        try {
            byte[] buffer;
            buffer = "SithSquad|".getBytes();
            out.write(buffer, 0, buffer.length);
            buffer = (this.size + "|").getBytes();
            out.write(buffer, 0, buffer.length);
            buffer = (this.squadName + "|").getBytes();
            out.write(buffer, 0, buffer.length);
            for ( MyPare squad : this.sith) {
                buffer = (squad.getKey() + "|").getBytes();
                out.write(buffer, 0, buffer.length);
                buffer = (squad.getValue() + "|").getBytes();
                out.write(buffer, 0, buffer.length);
            }
            out.close();
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void write(Writer out) {
        try {
            out.write("SithSquad|");
            out.write(this.size + "|");
            out.write(this.squadName + "|");
            for ( MyPare squad : this.sith) {
                out.write(squad.getKey() + "|");
                out.write(squad.getValue() + "|");
            }
            out.close();
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public java.util.Iterator<MyPare> iterator() {
        return new Iterator() {
            int current = 0;
            @Override
            public boolean hasNext() {
                return current != size;
            }

            @Override
            public MyPare next() {
                if(!hasNext()) throw new NoSuchElementException();
                return sith[current++];
            }
        };
    }


    static class SithClassException extends Exception {
        public SithClassException(String message){ super(message); }
    }

    static class SithClassRuntimeException extends RuntimeException {
        public SithClassRuntimeException(String message) {
            super(message);
        }
    }

    private static class SithFactory implements SquadFactory {
        @Override
        public Squad createInstance() {
            return new SithSquad();
        }
    }
}