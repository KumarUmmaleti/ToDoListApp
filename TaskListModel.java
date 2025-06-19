import java.util.ArrayList;
import javax.swing.*;

public class TaskListModel extends DefaultListModel<Task> {
    public void swap(int i, int j) {
        Task tmp = get(i);
        set(i, get(j));
        set(j, tmp);
    }

    public ArrayList<Task> toList() {
        ArrayList<Task> list = new ArrayList<>();
        for (int i = 0; i < getSize(); i++) {
            list.add(get(i));
        }
        return list;
    }
}
