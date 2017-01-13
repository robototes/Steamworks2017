package oajava.net.bytesender;

public interface ByteClientManager {
	
	public abstract void UPONRECIEVE(String ip, int port, byte[] message, int count);

}
