/*
 * This file is auto-generated.  DO NOT MODIFY.
 */
package com.advantech.peripheralmanager;
// Declare any non-default types here with import statements
public interface IComPortManager extends android.os.IInterface
{
  /** Default implementation for IComPortManager. */
  public static class Default implements IComPortManager
  {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    @Override public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws android.os.RemoteException
    {
    }
    /**
     * get comport  dev list
     * 
     * @return dev list
     */
    @Override public java.util.List<String> listPorts() throws android.os.RemoteException
    {
      return null;
    }
    /**
     * open port
     * 
     * @param port /dev/ttyACM0
     * @param baudrate 9000
     * @return fd
     */
    @Override public int open(String port, int baudrate) throws android.os.RemoteException
    {
      return 0;
    }
    /**
     * setSerialPortSettings
     * 
     * @param baudrate 波特率
     * @param databits 数据位
     * @param parity 校验位
     * @param stopbits 停止位
     * @param flowcontrol 流控制
     * 
     * @return status
     */
    @Override public int setSerialPortSettings(int baudrate, int databits, int parity, int stopbits, int flowcontrol) throws android.os.RemoteException
    {
      return 0;
    }
    /**
     * close port
     * 
     * @return status
     */
    @Override public int close() throws android.os.RemoteException
    {
      return 0;
    }
    /**
     * send data to port
     * 
     * @param data
     * @return status
     */
    @Override public int write(byte[] data) throws android.os.RemoteException
    {
      return 0;
    }
    /**
     * recv port data
     * 
     * @return byte[] data
     */
    @Override public byte[] read() throws android.os.RemoteException
    {
      return null;
    }
    @Override
    public android.os.IBinder asBinder() {
      return null;
    }
  }
  /** Local-side IPC implementation stub class. */
  public static abstract class Stub extends android.os.Binder implements IComPortManager
  {
    /** Construct the stub at attach it to the interface. */
    public Stub()
    {
      this.attachInterface(this, DESCRIPTOR);
    }
    /**
     * Cast an IBinder object into an com.advantech.peripheralmanager.IComPortManager interface,
     * generating a proxy if needed.
     */
    public static IComPortManager asInterface(android.os.IBinder obj)
    {
      if ((obj==null)) {
        return null;
      }
      android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
      if (((iin!=null)&&(iin instanceof IComPortManager))) {
        return ((IComPortManager)iin);
      }
      return new Proxy(obj);
    }
    @Override public android.os.IBinder asBinder()
    {
      return this;
    }
    @Override public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException
    {
      String descriptor = DESCRIPTOR;
      if (code >= android.os.IBinder.FIRST_CALL_TRANSACTION && code <= android.os.IBinder.LAST_CALL_TRANSACTION) {
        data.enforceInterface(descriptor);
      }
      switch (code)
      {
        case INTERFACE_TRANSACTION:
        {
          reply.writeString(descriptor);
          return true;
        }
      }
      switch (code)
      {
        case TRANSACTION_basicTypes:
        {
          int _arg0;
          _arg0 = data.readInt();
          long _arg1;
          _arg1 = data.readLong();
          boolean _arg2;
          _arg2 = (0!=data.readInt());
          float _arg3;
          _arg3 = data.readFloat();
          double _arg4;
          _arg4 = data.readDouble();
          String _arg5;
          _arg5 = data.readString();
          this.basicTypes(_arg0, _arg1, _arg2, _arg3, _arg4, _arg5);
          reply.writeNoException();
          break;
        }
        case TRANSACTION_listPorts:
        {
          java.util.List<String> _result = this.listPorts();
          reply.writeNoException();
          reply.writeStringList(_result);
          break;
        }
        case TRANSACTION_open:
        {
          String _arg0;
          _arg0 = data.readString();
          int _arg1;
          _arg1 = data.readInt();
          int _result = this.open(_arg0, _arg1);
          reply.writeNoException();
          reply.writeInt(_result);
          break;
        }
        case TRANSACTION_setSerialPortSettings:
        {
          int _arg0;
          _arg0 = data.readInt();
          int _arg1;
          _arg1 = data.readInt();
          int _arg2;
          _arg2 = data.readInt();
          int _arg3;
          _arg3 = data.readInt();
          int _arg4;
          _arg4 = data.readInt();
          int _result = this.setSerialPortSettings(_arg0, _arg1, _arg2, _arg3, _arg4);
          reply.writeNoException();
          reply.writeInt(_result);
          break;
        }
        case TRANSACTION_close:
        {
          int _result = this.close();
          reply.writeNoException();
          reply.writeInt(_result);
          break;
        }
        case TRANSACTION_write:
        {
          byte[] _arg0;
          _arg0 = data.createByteArray();
          int _result = this.write(_arg0);
          reply.writeNoException();
          reply.writeInt(_result);
          break;
        }
        case TRANSACTION_read:
        {
          byte[] _result = this.read();
          reply.writeNoException();
          reply.writeByteArray(_result);
          break;
        }
        default:
        {
          return super.onTransact(code, data, reply, flags);
        }
      }
      return true;
    }
    private static class Proxy implements IComPortManager
    {
      private android.os.IBinder mRemote;
      Proxy(android.os.IBinder remote)
      {
        mRemote = remote;
      }
      @Override public android.os.IBinder asBinder()
      {
        return mRemote;
      }
      public String getInterfaceDescriptor()
      {
        return DESCRIPTOR;
      }
      /**
       * Demonstrates some basic types that you can use as parameters
       * and return values in AIDL.
       */
      @Override public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeInt(anInt);
          _data.writeLong(aLong);
          _data.writeInt(((aBoolean)?(1):(0)));
          _data.writeFloat(aFloat);
          _data.writeDouble(aDouble);
          _data.writeString(aString);
          boolean _status = mRemote.transact(Stub.TRANSACTION_basicTypes, _data, _reply, 0);
          _reply.readException();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
      }
      /**
       * get comport  dev list
       * 
       * @return dev list
       */
      @Override public java.util.List<String> listPorts() throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        java.util.List<String> _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          boolean _status = mRemote.transact(Stub.TRANSACTION_listPorts, _data, _reply, 0);
          _reply.readException();
          _result = _reply.createStringArrayList();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      /**
       * open port
       * 
       * @param port /dev/ttyACM0
       * @param baudrate 9000
       * @return fd
       */
      @Override public int open(String port, int baudrate) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        int _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeString(port);
          _data.writeInt(baudrate);
          boolean _status = mRemote.transact(Stub.TRANSACTION_open, _data, _reply, 0);
          _reply.readException();
          _result = _reply.readInt();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      /**
       * setSerialPortSettings
       * 
       * @param baudrate 波特率
       * @param databits 数据位
       * @param parity 校验位
       * @param stopbits 停止位
       * @param flowcontrol 流控制
       * 
       * @return status
       */
      @Override public int setSerialPortSettings(int baudrate, int databits, int parity, int stopbits, int flowcontrol) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        int _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeInt(baudrate);
          _data.writeInt(databits);
          _data.writeInt(parity);
          _data.writeInt(stopbits);
          _data.writeInt(flowcontrol);
          boolean _status = mRemote.transact(Stub.TRANSACTION_setSerialPortSettings, _data, _reply, 0);
          _reply.readException();
          _result = _reply.readInt();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      /**
       * close port
       * 
       * @return status
       */
      @Override public int close() throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        int _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          boolean _status = mRemote.transact(Stub.TRANSACTION_close, _data, _reply, 0);
          _reply.readException();
          _result = _reply.readInt();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      /**
       * send data to port
       * 
       * @param data
       * @return status
       */
      @Override public int write(byte[] data) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        int _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeByteArray(data);
          boolean _status = mRemote.transact(Stub.TRANSACTION_write, _data, _reply, 0);
          _reply.readException();
          _result = _reply.readInt();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      /**
       * recv port data
       * 
       * @return byte[] data
       */
      @Override public byte[] read() throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        byte[] _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          boolean _status = mRemote.transact(Stub.TRANSACTION_read, _data, _reply, 0);
          _reply.readException();
          _result = _reply.createByteArray();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
    }
    static final int TRANSACTION_basicTypes = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
    static final int TRANSACTION_listPorts = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
    static final int TRANSACTION_open = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
    static final int TRANSACTION_setSerialPortSettings = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
    static final int TRANSACTION_close = (android.os.IBinder.FIRST_CALL_TRANSACTION + 4);
    static final int TRANSACTION_write = (android.os.IBinder.FIRST_CALL_TRANSACTION + 5);
    static final int TRANSACTION_read = (android.os.IBinder.FIRST_CALL_TRANSACTION + 6);
  }
  public static final String DESCRIPTOR = "com.advantech.peripheralmanager.IComPortManager";
  /**
   * Demonstrates some basic types that you can use as parameters
   * and return values in AIDL.
   */
  public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws android.os.RemoteException;
  /**
   * get comport  dev list
   * 
   * @return dev list
   */
  public java.util.List<String> listPorts() throws android.os.RemoteException;
  /**
   * open port
   * 
   * @param port /dev/ttyACM0
   * @param baudrate 9000
   * @return fd
   */
  public int open(String port, int baudrate) throws android.os.RemoteException;
  /**
   * setSerialPortSettings
   * 
   * @param baudrate 波特率
   * @param databits 数据位
   * @param parity 校验位
   * @param stopbits 停止位
   * @param flowcontrol 流控制
   * 
   * @return status
   */
  public int setSerialPortSettings(int baudrate, int databits, int parity, int stopbits, int flowcontrol) throws android.os.RemoteException;
  /**
   * close port
   * 
   * @return status
   */
  public int close() throws android.os.RemoteException;
  /**
   * send data to port
   * 
   * @param data
   * @return status
   */
  public int write(byte[] data) throws android.os.RemoteException;
  /**
   * recv port data
   * 
   * @return byte[] data
   */
  public byte[] read() throws android.os.RemoteException;
}
