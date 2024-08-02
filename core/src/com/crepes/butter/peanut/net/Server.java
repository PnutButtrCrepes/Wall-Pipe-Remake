package com.crepes.butter.peanut.net;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server implements Runnable
{
    ServerSocket serverSocket;
    
    ExecutorService serverAndClientThreadPool;
    
    ArrayList<ServerClient> clients;
    
    public Server()
    {
	clients = new ArrayList<ServerClient>();
	serverAndClientThreadPool = Executors.newCachedThreadPool();
	
	try
	{
	    serverSocket = new ServerSocket(42069);
	    serverAndClientThreadPool.execute(this);
	}
	catch (IOException e)
	{
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	
	
    }

    @Override
    public void run()
    {
	try
	{
	    Socket clientSocket = serverSocket.accept();
	    
	    ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(clientSocket.getInputStream()));
	    ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(clientSocket.getOutputStream()));
	    
	    ServerClient client = new ServerClient(clientSocket, in, out);
	    clients.add(client);
	}
	catch (IOException e)
	{
	    e.printStackTrace();
	}
    }
}
