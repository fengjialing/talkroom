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

public String ss;//��ȡ�û����������
String loginname=null;//��¼��

static Socket kehu;//��������������ӵ�Ŧ��
BufferedReader in;//������
PrintWriter out;//�����

static JTextArea text1=new JTextArea();
static JTextArea text5=new JTextArea();
JLabel lab1=new JLabel("�ͻ�����:",JLabel.LEFT);//�ͻ�������


JTextArea text2=new JTextArea();
JLabel lab4=new JLabel("�����¼",JLabel.LEFT);//�����¼
JLabel lab5=new JLabel("��������������",JLabel.LEFT);//��������������
final Button button=new Button("����");//����
final Button buttonkehu=new Button("����");
JLabel lab6=new JLabel("�����û�",JLabel.LEFT);//�����û�
final JTextArea text6=new JTextArea();//��ʾ�����û��Ŀ�
JScrollPane scr=new JScrollPane(text1,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);//Ϊ������Ϣ��ӹ����� 
JScrollPane scr3=new JScrollPane(text6,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);//Ϊ�û�����������ӹ�����
final JFrame frame=new JFrame("�ͻ��˴���");

public Client() throws IOException
{
connect();//������������˵�����
System.out.println("�Ѿ�������");

frame.setLayout(null);
//���
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

button.addActionListener(new ActionListener()//�����ȷ��ʱ������Ϣ����������
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
buttonkehu.addActionListener(new ActionListener()//�����ȷ��ʱ������Ϣ����������
{
public void actionPerformed(ActionEvent e)
{

if(buttonkehu==e.getSource())
{

  
ss=text2.getText();
out.println(ss);
    out.flush();
    System.out.println(ss+"�����buttonkehu");
      
    
}
}
});


frame.addWindowListener(new WindowAdapter(){//�رնԻ����ʱ��ִ�еĲ���
   public void windowClosing(WindowEvent e){
int answer=JOptionPane.showConfirmDialog(null,"�Ƿ�رմ��ڣ�","������Ϣ",JOptionPane.YES_NO_OPTION);
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
JOptionPane.showMessageDialog(null,"���ӷ�����ʧ�ܣ�","����",JOptionPane.ERROR_MESSAGE); 
e.printStackTrace();
} catch (IOException e) {
JOptionPane.showMessageDialog(null,"���ӷ�����ʧ�ܣ�","����",JOptionPane.ERROR_MESSAGE); 
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
System.out.println("����������"+serverm);
if(!serverm.equals("^&*()"))//���������ݲ�Ϊ������&*������ʱ��ʾ���������Ϣ
{
text1.append(serverm+"\n");

}
else if(serverm.equals("^&*()"))//����������Ϊ������&*������ʱ��ʾ������Ҫ���������û�������
{
text5.setText("");
while(!(serverm=in.readLine()).equals("^&*()"))
    {

text5.append(serverm+"����"+"\n");
    }
}
}
} catch (IOException e) {
JOptionPane.showMessageDialog(null,"�������ر�"); 
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

