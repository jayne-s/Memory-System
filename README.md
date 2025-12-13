# Memory System Simulation with Paging and TLB

**Course:** CE 4348 - Operating Systems\
**Semester:** Spring 2025\
**Project:** Memory System Simulation\
**Language:** Java

## Overview

This project simulates a virtual memory system that translates logical addresses into physical addresses using 
paging, page tables, frames, and an optional Translation Lookaside Buffer (TLB). The simulation mirrors how 
modern operating systems manage memory and optimize address translation performance. 

## System Components

**Main Memory**
* Physical memory divided into fixed size frames
* Stores page table entries currently resident in memory

**Page Table**
* Maintains per-process mappings from pages to frames

**Translation Lookaside Buffer (TLB)**
* Cache for recent page table entries
* Uses least-recently-used (LRU) replacement

**Logical to Physical Address Translation**
* Uses bit manipulation to extract page number and offset
* Supports detection of page faults and TLB hits

## Project Components

| **Class** | **Description** |
| --------- | --------------- |
| MainMemory | Implements frame allocation and eviction |
| TLB |Implements entry lookup and replacement |
| Factory | Instantiates custom memory and TLB implementations |

## Memory Management Behavior

* **Frame Allocation:**
  * Pages are added to the first available frame
  * When the memory is full, the least-recently-added page is evicted and the new page replaces the evicted page 
* **Address Translation:**
  * Extract page number and offset from the logical address
  * Check the TLB.
    * On TLB hit, use the cached frame number and record the hit.
    * On TLB miss, look up the page table.
    * If page is not in memory, return -1 as the physical address (page fault).

## TLB Policy
* Fixed Size TLB
* On insert, fill the first available slot and if full, evict the least-recently-used (LRU) entry.
* Access timestamps tracked using nano seconds for precision. 
  
## How to Run

* Clone repository and open the project in IntelliJ IDEA.
* Create JUnit configuration with package ```edu.utdallas.cs4348```
* Run all tests to validate functionality

## Possible Extensions

* Different page replacement policies.
* Variable page sizes and multi-level page tables
* Performance analysis of TLB hit rates
