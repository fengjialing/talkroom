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
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.util.Date;




import javax.swing.*;

public class Client 
{

public String ss;//获取用户输入的内容
String loginname=null;//登录名

static Socket kehu;//与服务器建立连接的纽带
BufferedReader in;//输入流
PrintWriter out;//输出流

static JTextArea text1=new JTextArea();
static JTextArea text5=new JTextArea();
JLabel lab1=new JLabel("客户端名:",JLabel.LEFT);//客户端姓名


JTextArea text2=new JTextArea();
JLabel lab4=new JLabel("聊天记录",JLabel.LEFT);//聊天记录
JLabel lab5=new JLabel("请输入聊天内容",JLabel.LEFT);//请输入聊天内容
final Button button=new Button("发送");//发送
final Button buttonkehu=new Button("发送");
JLabel lab6=new JLabel("在线用户",JLabel.LEFT);//在线用户
final JTextArea text6=new JTextArea();//显示在线用户的框
JScrollPane scr=new JScrollPane(text1,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);//为聊天信息添加滚动轴 
JScrollPane scr3=new JScrollPane(text6,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);//为用户输入内容添加滚动轴
final JFrame frame=new JFrame("客户端窗口");

public Client() throws IOException
{
connect();//建立与服务器端的连接
System.out.println("已经连接上");

frame.setLayout(null);
//组件
lab1.setBounds(8, 25, 100, 20); 
text2.setBounds(65, 25, 200,20);
buttonkehu.setBounds(375,25,70,30);
lab4.setBounds(8, 115, 100, 20);
scr.setBounds(8, 150, 440, 350);
scr3.setBounds(8, 500, 440, 90);
button.setBounds(375,600,70,30);
lab5.setBounds(8, 500, 100, 20);
    lab6.setBounds(470, 115, 100, 20);
    text5.setBounds(470, 150, 180, 440);
    
    
text1.setLineWrap(true);
text1.setEnabled(false);


text5.setEnabled(false);
text5.setLineWrap(true);
text6.setEnabled(true);
text6.setLineWrap(true);

    frame.add(text5);
frame.add(lab6);
frame.add(scr3);
frame.add(lab1);    
    frame.add(text2);
frame.add(lab4);
frame.add(button);
frame.add(buttonkehu);
frame.add(scr);
frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
frame.add(lab5);

button.addActionListener(new ActionListener()//当点击确定时，把消息传给服务器
{
public void actionPerformed(ActionEvent e)
{

if(button==e.getSource())
{

  
ss=text6.getText();
out.println(ss);
    out.flush();
    text6.setText("");  
    
}
}
});
buttonkehu.addActionListener(new ActionListener()//当点击确定时，把消息传给服务器
{
public void actionPerformed(ActionEvent e)
{

if(buttonkehu==e.getSource())
{

  
ss=text2.getText();
out.println(ss);
    out.flush();
    System.out.println(ss+"点击了buttonkehu");
      
    
}
}
});


frame.addWindowListener(new WindowAdapter(){//关闭对话框的时候执行的操作
   public void windowClosing(WindowEvent e){
int answer=JOptionPane.showConfirmDialog(null,"是否关闭窗口？","窗口消息",JOptionPane.YES_NO_OPTION);
if(answer==JOptionPane.YES_OPTION)
        {
disconnect();
        System.exit(0);
        }
   }
  });
frame.setSize(690,670);
frame.setLocation(100,50);
frame.setVisible(true);

}


public void connect()	
{
try {
kehu=new Socket("127.0.0.1",Talkroom.port1);

 in=new BufferedReader(new InputStreamReader(kehu.getInputStream()));
 out=new PrintWriter(new BufferedWriter(new OutputStreamWriter(kehu.getOutputStream())),true);
    

} catch (UnknownHostException e) {
JOptionPane.showMessageDialog(null,"连接服务器失败！","错误",JOptionPane.ERROR_MESSAGE); 
e.printStackTrace();
} catch (IOException e) {
JOptionPane.showMessageDialog(null,"连接服务器失败！","错误",JOptionPane.ERROR_MESSAGE); 
e.printStackTrace();
}

}
public void disconnect()
{
  try {
  out.println("!@#$%");
      out.close(); 
      in.close();
      kehu.close();
      } 
  catch (IOException e) 
  {
   e.printStackTrace();
  }
}
public void communicate()
{String serverm;
try {
while(true)
{


serverm=in.readLine();
System.out.println("服务器发："+serverm);
if(!serverm.equals("^&*()"))//当发送内容不为“……&*（）”时表示输出聊天消息
{
text1.append(serverm+"\n");

}
else if(serverm.equals("^&*()"))//当发送内容为“……&*（）”时表示服务器要传递在线用户的名字
{
text5.setText("");
while(!(serverm=in.readLine()).equals("^&*()"))
    {

text5.append(serverm+"在线"+"\n");
    }
}
}
} catch (IOException e) {
JOptionPane.showMessageDialog(null,"服务器关闭"); 
disconnect();
}
}
public static void main(String []args)
 {

try {
Client login= new Client();

login.communicate();
} catch (IOException e) {
// TODO Auto-generated catch block
e.printStackTrace();
}


 }
}	

