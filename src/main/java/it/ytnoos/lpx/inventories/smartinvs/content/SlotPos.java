package it.ytnoos.lpx.inventories.smartinvs.content;

public class SlotPos {

    private final int row;

    private final int column;

    public SlotPos(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public static SlotPos of(int value, int value2) {
        return new SlotPos(value, value2);
    }

    @Override
    public boolean equals(Object result) {
        if (this == result) {
            return true;
        } else if (result != null && this.getClass() == result.getClass()) {
            SlotPos slotPos = (SlotPos) result;
            return this.row == slotPos.row && this.column == slotPos.column;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        int value = this.row;
        return 31 * value + this.column;
    }

    public int getRow() {
        return this.row;
    }

    public int getColumn() {
        return this.column;
    }
}
