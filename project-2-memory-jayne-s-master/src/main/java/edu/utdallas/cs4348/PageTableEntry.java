package edu.utdallas.cs4348;

/**
 * A page table entry. You will update it when assigning it to a frame (and
 * removing it from a frame). Use access() to note when this page is...
 * accessed. :)
 */
public class PageTableEntry {
    private final int processID;
    private final int pageNumber;
    private int frameNumber = -1;
    private long lastAccessed = System.nanoTime();

    public PageTableEntry(int processID, int pageNumber) {
        this.processID = processID;
        this.pageNumber = pageNumber;
    }

    public void assignToFrame(int frameNumber) {
        this.frameNumber = frameNumber;
    }

    public int getProcessID() {
        return processID;
    }

    public int getFrameNumber() {
        return frameNumber;
    }

    public long getLastAccessed() {
        return lastAccessed;
    }

    /**
     * Call to update the time when the page was last accessed.
     */
    public void access() {
        lastAccessed = System.nanoTime();
    }

    public int getPageNumber() {
        return pageNumber;
    }
}
