
public class Pair<T1, T2> {

    private T1 _key;
    private T2 _value;

    public Pair(T1 key, T2 value){
        this._key = key;
        this._value = value;
    }

    public T1 getKey() {
        return _key;
    }

    public T2 getValue() {
        return _value;
    }

    @Override
    public String toString(){
        return String.format("(%s, %s)",  _key, _value);
    }
}
