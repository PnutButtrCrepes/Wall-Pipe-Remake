package com.crepes.butter.peanut.net;

import java.io.*;
import java.net.Inet4Address;
import java.net.Socket;

public class Client
{
    Socket clientSocket;
    ObjectInputStream in;
    ObjectOutputStream out;
    
    public Client()
    {
	try
	{
	    clientSocket = new Socket(Inet4Address.getLocalHost(), 42069);
	    
	    in = new ObjectInputStream(new BufferedInputStream(clientSocket.getInputStream()));
	    out = new ObjectOutputStream(new BufferedOutputStream(clientSocket.getOutputStream()));
	}
	catch (IOException e)
	{
	    e.printStackTrace();
	}
    }
}
