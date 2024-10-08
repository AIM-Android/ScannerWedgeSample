package com.advantech.scannerwedgedemo.module.mifare;


public class MifareSector {
	
	/**
	 * default block size is 4.
	 */
	public final static int BLOCKCOUNT=4;
	
	/**
	 * the index of the sector.
	 */
	public int sectorIndex;
	
	/**
	 * blocks in this sector.
	 */
	public MifareBlock[] blocks = new MifareBlock[BLOCKCOUNT];
	
	/**
	 * key A for this sector.
	 */
	public MifareKey keyA;
	
	/**
	 * key B for this sector.
	 */
	public MifareKey keyB;
	
	/**
	 * authorized or not.
	 */
	public boolean authorized;
	
	
}
