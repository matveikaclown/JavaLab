import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.io.Writer;
import java.util.NoSuchElementException;

public class JediSquad implements Squad, Serializable, Iterable<MyPare> {
    private  final int size;
    private String squadName;
    private final MyPare[] jedi;
    public static final JediFactory jediFactory = new JediFactory();

    public JediSquad() {
        this.size = 5;
        this.squadName = "Light Side";
        this.jedi = new MyPare[5];
        this.jedi[0] = new MyPare("Anakin Skywalker", 55);
        this.jedi[1] = new MyPare("Obi-Wan Kenobi", 40);
        this.jedi[2] = new MyPare("Mace Windu", 40);
        this.jedi[3] = new MyPare("Revan", 45);
        this.jedi[4] = new MyPare("Yoda", 40);
    }

    public JediSquad(int size, String squadName, MyPare... pairs) throws JediClassException {
        if (size < 0) throw new JediClassException("Squad size is less than zero");
        if (size < pairs.length) throw new JediClassException("The number of pairs is greater than the size of the squad");
        if (size > pairs.length) throw new JediClassException("The number of pairs is less than the size of the squad");
        this.size = size;
        this.squadName = squadName;
        this.jedi = new MyPare[size];
        int i = 0;
        for (MyPare pair : pairs) {
            if (pairs[i] == null || pairs[i].getKey() == null || pairs[i].getKey().isEmpty() || pairs[i].getValue() <= 0)
                throw new JediClassRuntimeException("Invalid value/values of pair");
            this.jedi[i++] = pair;
        }
    }

    @Override
    public int getSize() {
        return this.size;
    }

    @Override
    public String getSquadName() {
        return this.squadName;
    }

    @Override
    public MyPare getUnit(int index) {
        return this.jedi[index];
    }

    @Override
    public void setSquadName(String squadName) {
        if (squadName == null || squadName.isEmpty()) throw new JediClassRuntimeException("Invalid squad name");
        this.squadName = squadName;
    }

    @Override
    public void setUnit(int index, MyPare jedi) {
        if (index >= this.size || index < 0) throw new JediClassRuntimeException("Index out of bound");
        if (jedi == null || jedi.getKey() == null || jedi.getKey().isEmpty() || jedi.getValue() <= 0)
            throw new JediClassRuntimeException("Invalid value/values of pair");
        this.jedi[index] = jedi;
    }

    @Override
    public int valueOfMaxPower() {
        int power = 0;
        for (int i = 0; i < this.size; i++) {
            if (power < this.jedi[i].getValue())
                power = this.jedi[i].getValue();
        }
        return power;
    }

    @Override
    public int valueOfMinPower() {
        int power = this.jedi[0].getValue();
        for (int i = 1; i < this.size; i++) {
            if (power > this.jedi[i].getValue())
                power = this.jedi[i].getValue();
        }
        return power;
    }

    @Override
    public String nameOfMostPowerful() {
        int power = 0;
        String name = null;
        for (int i = 0; i < this.size; i++) {
            if (power < this.jedi[i].getValue()) {
                power = this.jedi[i].getValue();
                name = this.jedi[i].getKey();
            }
        }
        return name;
    }

    @Override
    public String nameOfLeastPowerful() {
        int power = this.jedi[0].getValue();
        String name = this.jedi[0].getKey();
        for (int i = 1; i < this.size; i++) {
            if (power > this.jedi[i].getValue()) {
                power = this.jedi[i].getValue();
                name = this.jedi[i].getKey();
            }
        }
        return name;
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        string.append(this.squadName).append("\n");
        for (int i = 0; i < this.size; i++)
            string.append(this.jedi[i].getKey()).append(" - ").append(this.jedi[i].getValue()).append("\n");
        return string.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        JediSquad squad = (JediSquad) obj;
        for (int i = 0; i < this.size; i++)
            if (!this.jedi[i].equals(squad.jedi[i])) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int hash = this.size * this.squadName.hashCode();
        for (int i = 0; i < this.size; i++)
            hash  *= this.jedi[i].hashCode();
        return hash;
    }

    @Override
    public void output(OutputStream out) {
        try {
            byte[] buffer;
            buffer = "JediSquad|".getBytes();
            out.write(buffer, 0, buffer.length);
            buffer = (this.size + "|").getBytes();
            out.write(buffer, 0, buffer.length);
            buffer = (this.squadName + "|").getBytes();
            out.write(buffer, 0, buffer.length);
            for ( MyPare squad : this.jedi) {
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
            out.write("JediSquad|");
            out.write(this.size + "|");
            out.write(this.squadName + "|");
            for ( MyPare squad : this.jedi) {
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
                return jedi[current++];
            }
        };
    }


    static class JediClassException extends Exception {
        public JediClassException(String message){
            super(message);
        }
    }

    static class JediClassRuntimeException extends RuntimeException {
        public JediClassRuntimeException(String message) {
            super(message);
        }
    }

    private static class JediFactory implements SquadFactory {
        @Override
        public Squad createInstance() {
            return new JediSquad();
        }
    }
}


