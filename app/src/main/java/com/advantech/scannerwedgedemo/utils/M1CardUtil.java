package com.advantech.scannerwedgedemo.utils;

import android.nfc.Tag;
import android.nfc.tech.MifareClassic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.advantech.scannerwedgedemo.module.mifare.MifareBlock;
import com.advantech.scannerwedgedemo.module.mifare.MifareClassCard;
import com.advantech.scannerwedgedemo.module.mifare.MifareSector;

public class M1CardUtil {
    public static ArrayList<String> readBlock(MifareClassic mifareClassic) throws IOException {
        MifareClassCard mifareClassCard = null;
        try {
            mifareClassic.connect();
            boolean auth = false;
            int secCount = mifareClassic.getSectorCount();
            mifareClassCard= new MifareClassCard(secCount);
            int bCount = 0;
            int bIndex = 0;

            for (int j = 0; j < secCount; j++) {
                MifareSector mifareSector = new MifareSector();
                mifareSector.sectorIndex = j;
                auth = mifareClassic.authenticateSectorWithKeyA(j, MifareClassic.KEY_DEFAULT);
                mifareSector.authorized = auth;
                if (auth) {
                    bCount = mifareClassic.getBlockCountInSector(j);
                    bCount = Math.min(bCount, MifareSector.BLOCKCOUNT);
                    bIndex = mifareClassic.sectorToBlock(j);
                    for (int i = 0; i < bCount; i++) {

                        byte[] data = mifareClassic.readBlock(bIndex);
                        MifareBlock mifareBlock = new MifareBlock(data);
                        mifareBlock.blockIndex = bIndex;

                        bIndex++;
                        mifareSector.blocks[i] = mifareBlock;
                    }
                    mifareClassCard.setSector(mifareSector.sectorIndex, mifareSector);
                }
            }
            ArrayList<String> blockData = new ArrayList<String>();
            int blockIndex=0;
            for (int i = 0; i < secCount; i++){
                MifareSector mifareSector = mifareClassCard.getSector(i);
                for (int j = 0; j < MifareSector.BLOCKCOUNT; j++) {
                    MifareBlock mifareBlock = mifareSector.blocks[j];
                    byte[] data = mifareBlock.getData();
                    blockData.add("Block " + blockIndex++ + " : " + StringUtil.bytesToHexString(data));
                }
            }

            return blockData;
        } catch (IOException e) {
            throw new IOException(e);
        } finally {
            try {
                if (mifareClassic != null) {
                    mifareClassic.close();
                }
            } catch (IOException e) {
                throw new IOException(e);
            }
        }
    }

    public static Map<Integer,List<byte[]>> readBlock(MifareClassic mifareClassic, byte[] keyA) throws IOException {
        try {
            Map<Integer,List<byte[]>> result = new HashMap<Integer,List<byte[]>>();
            mifareClassic.connect();
            int type = mifareClassic.getType();
            int sectorCount = mifareClassic.getSectorCount();
            String typeS = getMifareClassicType(type);
            for (int j = 0; j < sectorCount; j++) {
                if (m1AuthByKey(mifareClassic, j,keyA) || m1Auth(mifareClassic, j)) {
                    List<byte[]> dataList = new ArrayList<byte[]>();
                    int bCount;
                    int bIndex;
                    bCount = mifareClassic.getBlockCountInSector(j);
                    bIndex = mifareClassic.sectorToBlock(j);
                    for (int i = 0; i < bCount; i++) {
                        byte[] data = mifareClassic.readBlock(bIndex);
                        String hexString = StringUtil.bytesToHexString(data);
                        dataList.add(data);
                        bIndex++;
                    }
                    result.put(j, dataList);
                }
            }
            return result;
        } catch (Exception e) {
            throw new IOException(e);
        } finally {
            try {
                if (mifareClassic != null) {
                    mifareClassic.close();
                }
            } catch (IOException e) {
                throw new IOException(e);
            }
        }
    }

    private static String getMifareClassicType(int type) {
        String result = null;
        switch (type) {
            case MifareClassic.TYPE_CLASSIC:
                result = "TYPE_CLASSIC";
                break;
            case MifareClassic.TYPE_PLUS:
                result = "TYPE_PLUS";
                break;
            case MifareClassic.TYPE_PRO:
                result = "TYPE_PRO";
                break;
            case MifareClassic.TYPE_UNKNOWN:
                result = "TYPE_UNKNOWN";
                break;
        }
        return result;
    }

    public static boolean writeBlock(Tag tag, int sectorIndex,int blockIndex,byte[] blockByte) throws IOException {
        MifareClassic mifareClassic = MifareClassic.get(tag);
        try {
            mifareClassic.connect();
            if (m1Auth(mifareClassic, sectorIndex)) {
                mifareClassic.writeBlock(blockIndex, blockByte);
            } else {
                return false;
            }
        } catch (IOException e) {
            throw new IOException(e);
        } finally {
            try {
                mifareClassic.close();
            } catch (IOException e) {
                throw new IOException(e);
            }
        }
        return true;

    }

    private static boolean m1Auth(MifareClassic mifareClassic, int position) throws IOException {
        if (mifareClassic.authenticateSectorWithKeyA(position, MifareClassic.KEY_DEFAULT)) {
            return true;
        } else return mifareClassic.authenticateSectorWithKeyB(position, MifareClassic.KEY_DEFAULT);
    }

    private static boolean m1AuthByKey(MifareClassic mifareClassic, int position,byte[] keyA) throws IOException {
        if (keyA != null && keyA.length == 6){
            return mifareClassic.authenticateSectorWithKeyA(position, keyA);
        }
        return false;
    }
}