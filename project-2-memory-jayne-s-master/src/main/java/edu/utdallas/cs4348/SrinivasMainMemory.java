package edu.utdallas.cs4348;

public class SrinivasMainMemory extends MainMemory {

    TLB tlb; //can be null
    int ptr = -1;
    protected SrinivasMainMemory(int bytes, TLB tlb) {
        super(bytes/Util.SIZE_OF_FRAME);
        this.tlb = tlb;
    }

    @Override
    public void getPhysicalAddress(LookupInfo lookupInfo) {
        int logicalAddress = lookupInfo.getLogicalAddress();
        int pageNumber = logicalAddress >> Util.NUM_BITS_WITHIN_FRAME;
        int offset = logicalAddress & Util.LOCATION_WITHIN_PAGE_OR_FRAME_MASK;
        int frameNumber = -1;
        if(tlb != null) {
            frameNumber = tlb.lookup(pageNumber, lookupInfo.getProcess());
            if(frameNumber != -1) {
                lookupInfo.setTlbHit(true);
                lookupInfo.setPhysicalAddress((frameNumber << Util.NUM_BITS_WITHIN_FRAME) | offset);
                return;
            }
        }

        PageTableEntry entry = lookupInfo.getProcess().getEntry(pageNumber);
        frameNumber = entry.getFrameNumber();
        if(frameNumber == -1) {
           lookupInfo.setPhysicalAddress(-1);
           return;
        }
        if(tlb != null) {
            tlb.addEntry(entry);
        }
        lookupInfo.setPhysicalAddress((frameNumber << Util.NUM_BITS_WITHIN_FRAME) | offset);
    }

    @Override
    public void addPageToMemory(PageTableEntry pageTableEntry) {
        int emptyLoc = -1;
        for(int i = 0; i < frames.length; i++){
            if(frames[i] == null) {
                emptyLoc = i;
                break;
            }
        }
        if(emptyLoc != -1) {
            frames[emptyLoc].assignFrame(pageTableEntry);
        } else {
            ptr = (ptr + 1) % frames.length;
            if(!frames[ptr].isEmpty()) {
                frames[ptr].clearFrame();
            }
            frames[ptr].assignFrame(pageTableEntry);
        }
    }
}
