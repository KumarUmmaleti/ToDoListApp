import java.awt.datatransfer.*;
import javax.swing.*;

public class TaskReorderHandler extends TransferHandler {
    private final TaskListModel model;
    private int fromIndex = -1;

    public TaskReorderHandler(TaskListModel model) {
        this.model = model;
    }

    @Override
    public int getSourceActions(JComponent c) {
        return MOVE;
    }

    @Override
    protected Transferable createTransferable(JComponent c) {
        fromIndex = ((JList<?>) c).getSelectedIndex();
        return new StringSelection("");
    }

    @Override
    public boolean canImport(TransferSupport support) {
        return support.getComponent() instanceof JList;
    }

    @Override
    public boolean importData(TransferSupport support) {
        try {
            JList.DropLocation dropLocation = (JList.DropLocation) support.getDropLocation();
            int toIndex = dropLocation.getIndex();
            if (fromIndex != -1 && fromIndex != toIndex) {
                model.swap(fromIndex, toIndex);
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
