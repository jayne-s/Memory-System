package edu.utdallas.cs4348;

import org.junit.jupiter.api.Test;

public class TestAddressConversion {

    @Test
    public void testSimpleConversion() {
        PageTable pageTable = new PageTable(16);
        MainMemory mainMemory = Factory.createMainMemory(TestUtil.SIXTY_FOUR_KB);
        mainMemory.addPageToMemory(pageTable.getEntry(1));


        TestUtil.testLookup(4096, pageTable, mainMemory, 0);
        TestUtil.testLookup(4098, pageTable, mainMemory, 2);

        // Doesn't exist
        TestUtil.testLookup(0, pageTable, mainMemory, -1);
    }

}
