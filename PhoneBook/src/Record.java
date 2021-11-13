import java.util.concurrent.atomic.AtomicLong;

public class Record {
    protected final long id;
    protected String phoneNumber;
    protected String name;

    private static final AtomicLong COUNTER = new AtomicLong(1);

    public Record(String phone, String n){
        id = COUNTER.getAndIncrement();
        phoneNumber = phone;
        name = n;
    }

    public Record(){
        id = COUNTER.getAndIncrement();
        phoneNumber = null;
        name = null;
    }

    public void getInfo(){
        String result = "";
        result += "phone: " + phoneNumber + "\n";
        result += "name: " + name + "\n";
        result += "id: " + id;
        System.out.println(result);
    }
}
