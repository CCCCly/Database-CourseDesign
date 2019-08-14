package pub;

import javax.swing.*;

import java.util.*;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.*;
import java.sql.*;

public class Register extends JFrame implements ActionListener
{
    private String host;
	private Connection conn;
	private ResultSet rs;
	private Statement stmt;
	
	private JLabel[] jlArray =  {new JLabel("ѧ��"),new JLabel("����"),new JLabel("ȷ������"),new JLabel("����"),new JLabel("�Ա�"),new JLabel("����"),new JLabel("����")};
	private JPasswordField[] jpwf = {new JPasswordField(),new JPasswordField()};
	private JTextField[] jtfArray = {new JTextField(),new JTextField(),new JTextField()};
	private JComboBox[] jcb =  {new JComboBox(),new JComboBox()};//��ѡ��(�����˵���ʽ)
	private JButton[] jbArray = {new JButton("ȷ��"),new JButton("����")};
	private String[] gender = {"��","Ů"};
	private Map<String,String> map_team = new HashMap<String,String>();//���鼯��
	//���캯��
	public 	Register()
	{
		this.initialFrame();
    	this.initialData();
    }
	//��ʼ������
	public void initialData()
	{
		//�Ա�ѡ��
		jcb[0].addItem(gender[0]);
		jcb[0].addItem(gender[1]);
		//�������ݿ�
		this.initialConnection();
		try
		{
			String sql = "select * from team";
		    rs = stmt.executeQuery(sql); 
		    while(rs.next())
		    {
		    	String team_id = rs.getString(1).trim();
		    	String team_name = rs.getString(2).trim();
		        map_team.put(team_name,team_id);
		        jcb[1].addItem(team_name);//���鸴ѡ��
		    }
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		this.closeConn();
	}
	//��ʼ������
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
        jlArray[3].setBounds(20,170,100,30);
    	this.add(jlArray[3]);
    	jlArray[4].setBounds(20,220,100,30);
    	this.add(jlArray[4]);
    	jlArray[5].setBounds(20,270,100,30);
        this.add(jlArray[5]);
        jlArray[6].setBounds(20,320,100,30);
        this.add(jlArray[6]);
        //�û����ı���
        jtfArray[0].setBounds(100,20,150,30);
        this.add(jtfArray[0]);
        //�����
        jpwf[0].setBounds(100,70,150,30);
        this.add(jpwf[0]);
        jpwf[1].setBounds(100,120,150,30);
        this.add(jpwf[1]);
        
        jtfArray[1].setBounds(100,170,150,30);
        this.add(jtfArray[1]);
        jcb[0].setBounds(100, 220, 100, 30);
        this.add(jcb[0]);
        jtfArray[2].setBounds(100,270,150,30);
        this.add(jtfArray[2]);
        jcb[1].setBounds(100, 320,150, 30);
        this.add(jcb[1]);
        //ȷ�����ð�ť
        jbArray[0].setBounds(50,370,70,30);
        this.add(jbArray[0]);
        jbArray[1].setBounds(150,370,70,30);
        this.add(jbArray[1]);
        
        this.setTitle("ע��");
        
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int centerX=screenSize.width/2;
		int centerY=screenSize.height/2;
		int w=500;//��������
		int h=450;//������߶�
		this.setBounds(centerX-w/2,centerY-h/2-100,w,h);//���ô����������Ļ����
		
		this.setVisible(true);
		jbArray[0].addActionListener(this);
		jbArray[1].addActionListener(this);
    }
	public void actionPerformed(ActionEvent e) 
	{
		//����
	    if(e.getSource()==jbArray[1])
	    {
	    	jtfArray[0].setText("");
	    	jtfArray[1].setText("");
	    	jtfArray[2].setText("");
	    	jpwf[0].setText("");
	    	jpwf[1].setText("");
	    }
	    if(e.getSource()==jbArray[0])
	    {
	    	String stu_id = jtfArray[0].getText().trim();
	    	if(stu_id.equals(""))
	    	{
	    		JOptionPane.showMessageDialog(this,"������ѧ��","����",JOptionPane.ERROR_MESSAGE);
	    		return ;
	    	}
	    	if(stu_id.length()!=9)
	    	{
	    		JOptionPane.showMessageDialog(this,"ѧ��Ϊ9λ","����",JOptionPane.ERROR_MESSAGE);
	    		return ;
	    	}
	    	String pattern = "[0-9]{9}";
	    	if(!stu_id.matches(pattern))
	    	{
	    		JOptionPane.showMessageDialog(this,"ѧ��Ϊ9λ����","����",JOptionPane.ERROR_MESSAGE);
	    		return ;
	    	}
	    	String password = jpwf[0].getText().trim();
	    	if(password.equals(""))
	    	{
	    		JOptionPane.showMessageDialog(this,"����������","����",JOptionPane.ERROR_MESSAGE);
	    		return ;
	    	}
	    	String password2 = jpwf[1].getText().trim();
	    	if(password2.equals(""))
	    	{
	    		JOptionPane.showMessageDialog(this,"��ȷ������","����",JOptionPane.ERROR_MESSAGE);
	    		return ;
	    	}
	    	if(!password2.equals(password))
	    	{
	    		JOptionPane.showMessageDialog(this,"����ȷ�ϲ�һ��","����",JOptionPane.ERROR_MESSAGE);
	    		return ;
	    	}
	    	String stu_name = jtfArray[1].getText().trim();
	    	if(stu_name.equals(""))
	    	{
	    		JOptionPane.showMessageDialog(this,"����������","����",JOptionPane.ERROR_MESSAGE);
	    		return ;
	    	}
	    	String age = jtfArray[2].getText().trim();
	    	//�������ݿ�
	    	this.initialConnection();
    	    try{
    	    	String sql ="select * from student where stu_id = '"+stu_id+"'";
    	        rs = stmt.executeQuery(sql);
    	        if(rs.next())
    	        {
    	        	JOptionPane.showMessageDialog(this,"��ѧ����ע��","����",JOptionPane.ERROR_MESSAGE);
    	        	this.closeConn();
    	        	return ;
    	        }
    	        String sex = (String)jcb[0].getSelectedItem();
    	        String team_name = (String)jcb[1].getSelectedItem();
    	        String team_id = map_team.get(team_name);
    	        String sql2 ="insert into student values('"+stu_id+"','"+password+"','"+stu_name+"','"+sex+"','"+age+"','"+team_id+"')";
    	        int i = stmt.executeUpdate(sql2);
    	        if(i==1)
    	        {
    	        	JOptionPane.showMessageDialog(this,"ע��ɹ�","��ʾ",JOptionPane.INFORMATION_MESSAGE);
    	            new Login();
    	            this.dispose();
    	        }
    	    }
    	    catch(SQLException ea)
    	    {
    	    	ea.printStackTrace();
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
	public static void main(String args[])
	{
		Register register  = new Register();
	}
}
