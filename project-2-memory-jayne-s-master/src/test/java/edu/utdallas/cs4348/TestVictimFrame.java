package edu.utdallas.cs4348;

import org.junit.jupiter.api.Test;

public class TestVictimFrame {

    @Test
    public void testFrameVictimization() {
        PageTable pageTable = new PageTable(16);
        PageTable pageTable2 = new PageTable(16);
        MainMemory mainMemory = Factory.createMainMemory(TestUtil.THIRTY_TWO_KB);

        // Fill up the memory system with a mix of pages from two processes
        mainMemory.addPageToMemory(pageTable.getEntry(0));
        mainMemory.addPageToMemory(pageTable.getEntry(1));
        mainMemory.addPageToMemory(pageTable.getEntry(2));
        mainMemory.addPageToMemory(pageTable.getEntry(3));
        mainMemory.addPageToMemory(pageTable2.getEntry(0));
        mainMemory.addPageToMemory(pageTable2.getEntry(1));
        mainMemory.addPageToMemory(pageTable2.getEntry(2));
        mainMemory.addPageToMemory(pageTable2.getEntry(3));

        TestUtil.testLookup(123, pageTable, mainMemory, 123);
        TestUtil.testLookup(5000, pageTable, mainMemory, 5000);
        TestUtil.testLookup(10000, pageTable, mainMemory, 10000);
        TestUtil.testLookup(14000, pageTable, mainMemory, 14000);

        // Not in memory
        TestUtil.testLookup(17000, pageTable, mainMemory, -1);

        TestUtil.testLookup(123, pageTable2, mainMemory, (123 + TestUtil.SIXTEEN_KB));
        TestUtil.testLookup(5000, pageTable2, mainMemory, (5000 + TestUtil.SIXTEEN_KB));
        TestUtil.testLookup(10000, pageTable2, mainMemory, (10000 + TestUtil.SIXTEEN_KB));
        TestUtil.testLookup(14000, pageTable2, mainMemory, (14000 + TestUtil.SIXTEEN_KB));

        // Doesn't exist
        TestUtil.testLookup(17000, pageTable2, mainMemory, -1);

        // Force the first page in memory out
        mainMemory.addPageToMemory(pageTable2.getEntry(4));
        TestUtil.testLookup((123 + TestUtil.SIXTEEN_KB), pageTable2, mainMemory, 123);

        // Not in memory
        TestUtil.testLookup(100, pageTable, mainMemory, -1);

        // Force all original pages to be kicked out, plus 1 to check the circular motion
        mainMemory.addPageToMemory(pageTable.getEntry(5));
        mainMemory.addPageToMemory(pageTable.getEntry(6));
        mainMemory.addPageToMemory(pageTable.getEntry(7));
        mainMemory.addPageToMemory(pageTable.getEntry(8));
        mainMemory.addPageToMemory(pageTable.getEntry(9));
        mainMemory.addPageToMemory(pageTable.getEntry(10));
        mainMemory.addPageToMemory(pageTable.getEntry(11));

        TestUtil.testLookup(22000, pageTable, mainMemory, (22000 - TestUtil.SIXTEEN_KB));
        TestUtil.testLookup(26000, pageTable, mainMemory, (26000 - TestUtil.SIXTEEN_KB));
        TestUtil.testLookup(30000, pageTable, mainMemory, (30000 - TestUtil.SIXTEEN_KB));
        TestUtil.testLookup(34000, pageTable, mainMemory, (34000 - TestUtil.SIXTEEN_KB));
        TestUtil.testLookup(38000, pageTable, mainMemory, (38000 - TestUtil.SIXTEEN_KB));
        TestUtil.testLookup(42000, pageTable, mainMemory, (42000 - TestUtil.SIXTEEN_KB));

        mainMemory.addPageToMemory(pageTable.getEntry(0));
        TestUtil.testLookup(100, pageTable, mainMemory, 100);

        // Not in memory
        TestUtil.testLookup((123 + TestUtil.SIXTEEN_KB), pageTable2, mainMemory, -1);
    }
}
