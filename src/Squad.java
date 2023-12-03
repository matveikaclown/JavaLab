import java.io.OutputStream;
import java.io.Writer;
import java.util.Iterator;

public interface Squad {
    int getSize();
    String getSquadName();
    MyPare getUnit(int index);
    void setSquadName(String squadName);
    void setUnit(int index, MyPare pair);
    int valueOfMaxPower();
    int valueOfMinPower();
    String nameOfMostPowerful();
    String nameOfLeastPowerful();
    void output(OutputStream out);
    void write(Writer out);
    java.util.Iterator<MyPare> iterator();

    abstract class Iterator implements java.util.Iterator<MyPare> {
        @Override
        public abstract boolean hasNext();

        @Override
        public abstract MyPare next();
    }
}

class Decorator implements Squad {
    Squad obj;

    public Decorator(Squad obj) {
        this.obj = obj;
    }

    @Override
    public int getSize() {
        return obj.getSize();
    }

    @Override
    public String getSquadName() {
        return obj.getSquadName();
    }

    @Override
    public MyPare getUnit(int index) {
        return obj.getUnit(index);
    }

    @Override
    public void setSquadName(String squadName) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setUnit(int index, MyPare pair) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int valueOfMaxPower() {
        return obj.valueOfMaxPower();
    }

    @Override
    public int valueOfMinPower() {
        return obj.valueOfMinPower();
    }

    @Override
    public String nameOfMostPowerful() {
        return obj.nameOfMostPowerful();
    }

    @Override
    public String nameOfLeastPowerful() {
        return obj.nameOfLeastPowerful();
    }

    @Override
    public void output(OutputStream out) {
        obj.output(out);
    }

    @Override
    public void write(Writer out) {
        obj.write(out);
    }

    @Override
    public java.util.Iterator<MyPare> iterator() {
        return obj.iterator();
    }
}

interface SquadFactory {
    Squad createInstance();
}