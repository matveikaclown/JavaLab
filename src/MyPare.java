import java.io.Serial;
import java.io.Serializable;

public class MyPare implements Serializable {
    private String key;
    private int value;

    public MyPare(String key, int value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() { return this.key; }

    public int getValue() { return this.value; }

    public void setKey(String key) { this.key = key; }

    public void setValue(int value) { this.value = value; }

    @Override
    public String toString() {
        return this.key + " " + this.value;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        MyPare pare = (MyPare) obj;
        return this.key.equals(pare.key) && this.value == pare.value;
    }

    @Override
    public int hashCode() { return this.key.hashCode() * this.value; }
}
