package com.crepes.butter.peanut.net;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ServerClient implements Runnable
{
    Socket clientSocket;
    ObjectInputStream in;
    ObjectOutputStream out;
    
    public ServerClient(Socket clientSocket, ObjectInputStream in, ObjectOutputStream out)
    {
	this.clientSocket = clientSocket;
	this.in = in;
	this.out = out;
    }

    @Override
    public void run()
    {
	while (true)
	{
	    try
	    {
		DataPacket inputData = (DataPacket) in.readObject();
	    }
	    catch (ClassNotFoundException | IOException e)
	    {
		e.printStackTrace();
	    }
	}
    }
}
