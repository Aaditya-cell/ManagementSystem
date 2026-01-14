package model;

import java.util.Stack;

public class UndoStack {
    private Stack<String> undoStack;
    
    public UndoStack() {
        this.undoStack = new Stack<>();
    }
    
    public void pushAction(String action) {
        undoStack.push(action);
    }
    
    public String popAction() {
        if (!undoStack.isEmpty()) {
            return undoStack.pop();
        }
        return null;
    }
    
    public boolean canUndo() {
        return !undoStack.isEmpty();
    }
    
    public int getUndoCount() {
        return undoStack.size();
    }
    
    public void clear() {
        undoStack.clear();
    }
}
