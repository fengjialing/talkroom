package talkroom;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

import javax.swing.*;
import javax.swing.RowFilter.Entry;
class Semaphore{}
public class Talkroom 
{

static boolean zaixian=false;
static final int port1=8020;//�˿ں�
ServerSocket serversocket;//������
Semaphore semaphore;
   
    static Vector<String> client=new Vector<String>();//���û�����
    static Vector<Socket> sockets=new Vector<Socket>();
    public Talkroom() throws IOException
{

try {
 serversocket=new ServerSocket(port1);//����������
 System.out.println("����ǰ���Ѿ�����");
} catch (IOException e) {
JOptionPane.showMessageDialog(null,"����������ʧ��"); 
e.printStackTrace();}

try 
{while(true)
      {
   Socket socket;
   socket = serversocket.accept();//�ȴ�����
   
   zaixian=true;//˵�����û����ߣ��Ա���ʾ��������û��������б����һ��
   sockets.add(socket);
   thread1 user=new thread1(socket);
   user.start();
      }   
}
catch (IOException e1) {

e1.printStackTrace();
}
finally{
serversocket.close();
}

}	

public static void main(String []args) throws IOException
{
Talkroom t=new Talkroom();
}
}

class thread1 extends Thread
{
Socket socket;//��ͻ��˽������ӵ�Ŧ��
String serverm="";//��ȡ�ͻ���������ַ�
String s="";//�洢�ͻ��˵�����
BufferedReader in;
Calendar shijian;
static int kehunum;
 
public thread1(Socket socket) throws IOException
{
this.socket=socket;
in = new BufferedReader( new InputStreamReader(socket.getInputStream()));
    s=in.readLine();
    Talkroom.client.add(s);//��ӿͻ�������
 }
public void run()
{
try {
     while(true)
{
if(kehunum!=Talkroom.client.size())
{ 
for(int m=0;m<Talkroom.sockets.size();m++)
{Socket ss = (Socket)Talkroom.sockets.get(m);
     PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(ss.getOutputStream())),true);
                 out.println("^&*()");
     out.flush();
     for( int i = 0; i < Talkroom.client.size(); i++)
  {
       
   out.println(Talkroom.client.get(i));
   out.flush();
  }
     out.println("^&*()");
     out.flush();
    }
 kehunum=Talkroom.client.size();
 
 }
serverm=in.readLine();
System.out.println("�ͻ���˵��"+serverm);
if(!serverm.equals("!@#$%"))//�������"!@#$%"�����������Ϣ
{
for(int m=0;m<Talkroom.sockets.size();m++)
{Socket ss = (Socket)Talkroom.sockets.get(m);
     PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(ss.getOutputStream())),true);
     shijian = Calendar.getInstance();
 out.println(" " + shijian.get(Calendar.HOUR_OF_DAY) + ":" + shijian.get(Calendar.MINUTE) + ":" + shijian.get(Calendar.SECOND));
     out.println(s+"˵��");
     out.flush();
     out.println(serverm);
 out.flush();
 }
 
 }
else if(serverm.equals("!@#$%"))
{      Talkroom.sockets.remove(socket);
       Talkroom.client.remove(s);
       for(int m=0;m<Talkroom.sockets.size();m++)
       { Socket ss = (Socket)Talkroom.sockets.get(m);
             PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(ss.getOutputStream())),true);
         out.println("^&*()");
             out.flush();
                   for( int i = 0; i < Talkroom.client.size(); i++)
                 {  out.println(Talkroom.client.get(i));
                    out.flush();
                 }
                 out.println("^&*()");
                 out.flush();
           }
    kehunum=Talkroom.client.size();
    
break;//�����"!@#$%"���˳�ѭ��

}

       }
}
 catch (IOException e) {}
 finally{ 
      try {
    	  
  in.close();
  socket.close();
      JOptionPane.showMessageDialog(null,s+"�˳�"); 
      }
      catch (IOException e) {	e.printStackTrace();}
       }
}
}