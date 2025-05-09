package edu.utdallas.cs4348;

public class JayneTLB extends TLB{

    @Override
    public void addEntry(PageTableEntry entry) {
        int emptyLoc = -1;
        for(int i = 0; i < entries.length; i++){
            if(entries[i] == null) {
                emptyLoc = i;
                break;
            }
        }
        if(emptyLoc != -1) {
            entries[emptyLoc] = entry;
            //entries[emptyLoc].assignToFrame(entry.getFrameNumber());
            entries[emptyLoc].access();
        } else {
            long ptr = Long.MAX_VALUE;
            int index = 0;
            for(int i = 0; i < entries.length; i++){
                if(entries[i].getLastAccessed() < ptr) {
                    ptr = entries[i].getLastAccessed();
                    index = i;
                }
            }
            entries[index] = entry;
            //entries[index].assignToFrame(entry.getFrameNumber());
            entries[index].access();
        }
    }

    @Override
    public int lookup(int pageInTable, PageTable pageTable) {
        for(int i = 0; i < entries.length; i++){
            if(entries[i] != null) {
                if(entries[i].getProcessID() == pageTable.getProcessID() && entries[i].getPageNumber() == pageInTable) {
                    entries[i].access();
                    return entries[i].getFrameNumber();
                }
            }
        }

        return -1;
    }
}
