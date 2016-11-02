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
static final int port1=8020;//端口号
ServerSocket serversocket;//服务器
Semaphore semaphore;
   
    static Vector<String> client=new Vector<String>();//存用户姓名
    static Vector<Socket> sockets=new Vector<Socket>();
    public Talkroom() throws IOException
{

try {
 serversocket=new ServerSocket(port1);//创建服务器
 System.out.println("服务前期已经启动");
} catch (IOException e) {
JOptionPane.showMessageDialog(null,"创建服务器失败"); 
e.printStackTrace();}

try 
{while(true)
      {
   Socket socket;
   socket = serversocket.accept();//等待接收
   
   zaixian=true;//说明有用户上线，以便提示输出流把用户的在线列表更新一遍
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
Socket socket;//与客户端建立连接的纽带
String serverm="";//读取客户端输入的字符
String s="";//存储客户端的姓名
BufferedReader in;
Calendar shijian;
static int kehunum;
 
public thread1(Socket socket) throws IOException
{
this.socket=socket;
in = new BufferedReader( new InputStreamReader(socket.getInputStream()));
    s=in.readLine();
    Talkroom.client.add(s);//添加客户端姓名
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
System.out.println("客户端说："+serverm);
if(!serverm.equals("!@#$%"))//如果不是"!@#$%"则输出聊天消息
{
for(int m=0;m<Talkroom.sockets.size();m++)
{Socket ss = (Socket)Talkroom.sockets.get(m);
     PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(ss.getOutputStream())),true);
     shijian = Calendar.getInstance();
 out.println(" " + shijian.get(Calendar.HOUR_OF_DAY) + ":" + shijian.get(Calendar.MINUTE) + ":" + shijian.get(Calendar.SECOND));
     out.println(s+"说：");
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
    
break;//如果是"!@#$%"则退出循环

}

       }
}
 catch (IOException e) {}
 finally{ 
      try {
    	  
  in.close();
  socket.close();
      JOptionPane.showMessageDialog(null,s+"退出"); 
      }
      catch (IOException e) {	e.printStackTrace();}
       }
}
}