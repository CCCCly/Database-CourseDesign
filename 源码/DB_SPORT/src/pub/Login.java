package pub;
import javax.swing.*;
import student.StuClient;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.*;
import java.sql.*;

import admin.AdminClient;
public class Login extends JFrame implements ActionListener
{
    private String host;//����IP��ַ
	private Connection conn;//������ݿ������
	private Statement stmt;//�����ݿⷢ��Ҫִ�е�SQL���
	private ResultSet rs;//���ݿ�����Ľ����
	
	private JLabel[] jlArray =  {new JLabel("�û���"),new JLabel("����"),new JLabel("������ַ")};
	private JTextField[] jtfArray = {new JTextField(),new JTextField()};
	private JPasswordField jpwf = new JPasswordField();
	private JRadioButton[] jrbArray = {new JRadioButton("ѧ��",true),new JRadioButton("����Ա")};
	private JButton[] jbArray = {new JButton("��¼"),new JButton("ע��")};
	private ButtonGroup bg=new ButtonGroup();//��ѡ��ť��Ч��
    //���캯��
    public 	Login()
    {
    	this.initialFrame();
    }
    public void initialFrame()
    {
    	//��ǩ��
    	this.setLayout(null);
    	jlArray[0].setBounds(20,20,100,30);
    	this.add(jlArray[0]);
    	jlArray[1].setBounds(20,70,100,30);
    	this.add(jlArray[1]);
    	jlArray[2].setBounds(20,120,100,30);
        this.add(jlArray[2]);
        //�ı�����
        jtfArray[0].setBounds(100,120,150,30);
        jtfArray[0].setText("127.0.0.1:3306");//Ĭ��ip��ַ
        this.add(jtfArray[0]);
        jtfArray[1].setBounds(100,20,150,30);
        this.add(jtfArray[1]);
        //�����
        jpwf.setBounds(100,70,150,30);
        this.add(jpwf);
        //��ݰ�ť
        this.bg.add(jrbArray[0]);
        this.bg.add(jrbArray[1]);
        jrbArray[0].setBounds(50,170,100,30);
        this.add(jrbArray[0]);
        jrbArray[1].setBounds(150,170,100,30);
        this.add(jrbArray[1]);
        //ȷ�����ð�ť
        jbArray[0].setBounds(50,210,70,30);
        this.add(jbArray[0]);
        jbArray[1].setBounds(150,210,70,30);
        this.add(jbArray[1]);
        
        this.setTitle("��¼");
        //���ڴ�Сλ���趨
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();//��ȡ��Ļ�ߴ�
		int centerX=screenSize.width/2;
		int centerY=screenSize.height/2;
		int w=300;//��������
		int h=320;//������߶�
		this.setBounds(centerX-w/2,centerY-h/2-100,w,h);//���ô����������Ļ����
		
		this.setVisible(true);
		jbArray[0].addActionListener(this);
		jbArray[1].addActionListener(this);
    }
    //ʵ��actionPerformed����
	public void actionPerformed(ActionEvent e) 
	{
		//ע��
	    if(e.getSource()==jbArray[1])
	    {
	    	Register register  = new Register();
	    	this.dispose();
	    }
	    //��¼
	    if(e.getSource()==jbArray[0])
	    {
	    	this.host = jtfArray[0].getText().trim();//trim()��ȥ�ַ������ҿո�
	    	if(host.equals(""))
	    	{
	    		JOptionPane.showMessageDialog(this,"IP��ַ����Ϊ��","����",JOptionPane.ERROR_MESSAGE);
	    		return ;
	    	}
	    	String id = jtfArray[1].getText().trim();
	    	if(id.equals(""))
	    	{
	    		JOptionPane.showMessageDialog(this,"�������û���","����",JOptionPane.ERROR_MESSAGE);
	    		return ;
	    	}
	    	String password = jpwf.getText().trim();
	    	if(password.equals(""))
	    	{
	    		JOptionPane.showMessageDialog(this,"����������","����",JOptionPane.ERROR_MESSAGE);
	    		return ;
	    	}
	    	int type=this.jrbArray[0].isSelected()?0:1;
	    	//�������ݿ�
	    	this.initialConnection();
    	    if(type==0)
    	    {
	    		try
	    		{
	    	    	String sql = "select * from student where stu_id= '"+id+"' and password = '"+password+"'";
	    	    	rs = stmt.executeQuery(sql);
	    	    	if(!rs.next())
	    	    	{
	    	    		JOptionPane.showMessageDialog(this, "�û������������","����",JOptionPane.ERROR_MESSAGE);
	    	    		this.closeConn();
	    	    		return ;
	    	    	}
	    	    	//����ѧ���ͻ���
	    	    	else
	    	    	{
	    	    		new StuClient(host,id);
	    	    		this.dispose();
	    	    	}
	    	    	this.closeConn();
	    	    }
	    		catch(SQLException ea)
	    		{
	    	    	ea.printStackTrace();
	    	    } 
	    	}
    	    if(type==1)
    	    {
	    		try
	    		{
	    	    	String sql = "select * from admin where admin_id= '"+id+"' and password = '"+password+"'";
	    	    	rs = stmt.executeQuery(sql);
	    	    	if(!rs.next())
	    	    	{
	    	    		JOptionPane.showMessageDialog(this, "�û������������","����",JOptionPane.ERROR_MESSAGE);
	    	    		this.closeConn();
	    	    		return ;
	    	    	}
	    	    	//��������Ա�ͻ���
	    	    	else
	    	    	{
	    	    		new AdminClient(host,id);
	    	    		this.dispose();
	    	    	}
	    	    	this.closeConn();	
	    	    }
	    		catch(SQLException ea)
	    		{
	    	    	ea.printStackTrace();
	    	    } 
	    	}
	    }
	}
	//���ݿ�����
	public void initialConnection()
	{
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			conn=DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/sportmanager", "root" , "root");;
			stmt = conn.createStatement();
		}
		catch(ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (SQLException e) 
		{
			JOptionPane.showMessageDialog(this,"����ʧ�ܣ����������Ƿ���ȷ","����",JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}
	//�ر����ݿ�����
	public void closeConn()
	{  
		   try
		   {
			   if(rs!=null){rs.close();}
			   if(stmt!=null){stmt.close();}
			   if(conn!=null){conn.close();}
		   }
		   catch(SQLException e)
		   {
			   e.printStackTrace();
		   }
	}
	//������
	public static void main(String args[])
	{
		   Login login  = new Login();
	}
}
