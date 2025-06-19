import java.awt.*;
import java.awt.event.*;
import java.util.List;
import javax.swing.*;

public class Main {
    private JFrame frame;
    private TaskListModel taskModel;
    private JList<Task> taskList;
    private JTextField taskField;
    private JComboBox<String> filterBox;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::new);
    }

    public Main() {
        frame = new JFrame("To-Do List Manager");
        frame.setSize(600, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Task list
        taskModel = new TaskListModel();
        taskList = new JList<>(taskModel);
        taskList.setCellRenderer(new TaskRenderer());
        taskList.setFixedCellHeight(30);
        taskList.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(taskList);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Top input + button panel
        taskField = new JTextField(20);
        taskField.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        JButton addBtn = new JButton("Add");
        JButton editBtn = new JButton("Edit");
        JButton deleteBtn = new JButton("Delete");

        Font btnFont = new Font("Segoe UI", Font.BOLD, 13);
        addBtn.setFont(btnFont);
        editBtn.setFont(btnFont);
        deleteBtn.setFont(btnFont);

        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        inputPanel.add(taskField);
        inputPanel.add(addBtn);
        inputPanel.add(editBtn);
        inputPanel.add(deleteBtn);

        // Bottom view filter
        String[] filters = {"All", "Pending", "Completed"};
        filterBox = new JComboBox<>(filters);
        filterBox.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        bottomPanel.add(new JLabel("🔎 View:"));
        bottomPanel.add(filterBox);

        // Frame setup
        frame.setLayout(new BorderLayout());
        frame.add(inputPanel, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(bottomPanel, BorderLayout.SOUTH);
        frame.setLocationRelativeTo(null); // center window
        frame.setVisible(true);


        loadTasks();

        // Listeners
        addBtn.addActionListener(e -> addTask());
        editBtn.addActionListener(e -> editTask());
        deleteBtn.addActionListener(e -> deleteTask());
        filterBox.addActionListener(e -> updateList());

        taskList.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int index = taskList.locationToIndex(e.getPoint());
                if (index >= 0) {
                    Task task = taskModel.get(index);
                    task.toggleCompleted();
                    updateList();
                    saveTasks();
                }
            }
        });

        // Drag-and-drop support
        taskList.setDragEnabled(true);
        taskList.setDropMode(DropMode.INSERT);
        taskList.setTransferHandler(new TaskReorderHandler(taskModel));

        frame.setVisible(true);
    }

    private void addTask() {
        String text = taskField.getText().trim();
        if (!text.isEmpty()) {
            taskModel.addElement(new Task(text));
            taskField.setText("");
            saveTasks();
        }
    }

    private void deleteTask() {
        int selected = taskList.getSelectedIndex();
        if (selected != -1) {
            taskModel.remove(selected);
            saveTasks();
        }
    }

    private void editTask() {
        int selected = taskList.getSelectedIndex();
        if (selected != -1) {
            Task task = taskModel.get(selected);
            String newText = JOptionPane.showInputDialog(frame, "Edit Task:", task.getText());
            if (newText != null && !newText.trim().isEmpty()) {
                task.setText(newText.trim());
                updateList();
                saveTasks();
            }
        }
    }

    private void updateList() {
        String filter = (String) filterBox.getSelectedItem();
        TaskListModel filteredModel = new TaskListModel();
        for (int i = 0; i < taskModel.size(); i++) {
            Task t = taskModel.get(i);
            if ("All".equals(filter)
                    || ("Pending".equals(filter) && !t.isCompleted())
                    || ("Completed".equals(filter) && t.isCompleted())) {
                filteredModel.addElement(t);
            }
        }
        taskList.setModel(filteredModel);
    }

    private void loadTasks() {
        List<Task> loaded = CSVStorage.loadTasks();
        for (Task t : loaded) {
            taskModel.addElement(t);
        }
    }

    private void saveTasks() {
        CSVStorage.saveTasks(taskModel.toList());
    }
}
