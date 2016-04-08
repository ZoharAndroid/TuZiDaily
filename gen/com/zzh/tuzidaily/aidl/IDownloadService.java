/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: F:\\AndroidWorkspace\\tuzidaily\\src\\com\\zzh\\tuzidaily\\aidl\\IDownloadService.aidl
 */
package com.zzh.tuzidaily.aidl;
public interface IDownloadService extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.zzh.tuzidaily.aidl.IDownloadService
{
private static final java.lang.String DESCRIPTOR = "com.zzh.tuzidaily.aidl.IDownloadService";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.zzh.tuzidaily.aidl.IDownloadService interface,
 * generating a proxy if needed.
 */
public static com.zzh.tuzidaily.aidl.IDownloadService asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.zzh.tuzidaily.aidl.IDownloadService))) {
return ((com.zzh.tuzidaily.aidl.IDownloadService)iin);
}
return new com.zzh.tuzidaily.aidl.IDownloadService.Stub.Proxy(obj);
}
@Override public android.os.IBinder asBinder()
{
return this;
}
@Override public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException
{
switch (code)
{
case INTERFACE_TRANSACTION:
{
reply.writeString(DESCRIPTOR);
return true;
}
case TRANSACTION_download:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
this.download(_arg0);
reply.writeNoException();
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.zzh.tuzidaily.aidl.IDownloadService
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
public java.lang.String getInterfaceDescriptor()
{
return DESCRIPTOR;
}
@Override public void download(java.lang.String path) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(path);
mRemote.transact(Stub.TRANSACTION_download, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
}
static final int TRANSACTION_download = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
}
public void download(java.lang.String path) throws android.os.RemoteException;
}
