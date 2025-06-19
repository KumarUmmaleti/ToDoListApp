import java.awt.*;
import javax.swing.*;

public class TaskRenderer extends JCheckBox implements ListCellRenderer<Task> {
    @Override
    public Component getListCellRendererComponent(JList<? extends Task> list, Task task, int index,
                                                  boolean isSelected, boolean cellHasFocus) {
        setText(task.getText());
        setSelected(task.isCompleted());
        setBackground(isSelected ? new Color(220, 235, 245) : Color.WHITE);
        setFont(new Font("Segoe UI", Font.PLAIN, 14));
        setForeground(Color.DARK_GRAY);
        setBorderPainted(true);
        return this;
    }
}
