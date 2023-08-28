import java.util.ArrayList;
import java.util.List;

public class Listdemo {
    public static void main(String[] args) {
        List<String> itemList = new ArrayList<>();
        itemList.add("Harry");
        itemList.add("Liam");
        itemList.add("Louis");
        itemList.add("Zayn");

        for (String item : itemList) {
            System.out.println(item);
        }
    }
}
