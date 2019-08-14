package student;
import javax.swing.*;

import pub.Login;
import pub.Register;

import java.awt.event.*;
import java.sql.*;
public class ChangePwdStu extends JPanel implements ActionListener
{
    private String host;
    private String stu_id;
    private StuClient sc;
    
    private Connection conn;
    private Statement stmt;
	private ResultSet rs;
	
	private JLabel[] jlArray = {new JLabel("ѧ��"),new JLabel("ԭʼ����"),new JLabel("������"),new JLabel("ȷ��������")};
	private JTextField jtf = new JTextField();
	private JPasswordField[] jpfArray = {new JPasswordField(),new JPasswordField(),new JPasswordField()};
	private JButton[] jbArray = {new JButton("ȷ��"),new JButton("����")};
    public ChangePwdStu(String host,String stu_id,StuClient sc)
    {
    	this.host = host;
    	this.stu_id = stu_id;
    	this.sc=sc;
    	this.addListener();
    	this.initialFrame();
    }
    public void addListener()
    {
    	jbArray[0].addActionListener(this);
    	jbArray[1].addActionListener(this);
    }
    public void initialFrame()
    {
    	this.setLayout(null);  
    	for(int i = 0;i<jlArray.length;i++)
    	{
    		jlArray[i].setBounds(30,20+50*i,150,30);
    		this.add(jlArray[i]);
    		//�û����ı���
    		if(i==0)
    		{
    			  jtf.setBounds(130, 20+50*i, 150, 30);
    			  this.add(jtf);
    		}
    		//�����
    		else
    		{
    			jpfArray[i-1].setBounds(130, 20+50*i,150, 30);
    			this.add(jpfArray[i-1]);
    		}
    	}
    	jbArray[0].setBounds(40,230,100,30);
    	this.add(jbArray[0]);
    	jbArray[1].setBounds(170,230,100,30);
    	this.add(jbArray[1]);
    }
    //��д����
	public void actionPerformed(ActionEvent e) 
	{
		 if(e.getSource()==jtf)
		 {
			 jpfArray[0].requestFocus(true);
		 }
		 else if(e.getSource()==jpfArray[0])
		 {
			 jpfArray[1].requestFocus(true);
		 }
		 else if(e.getSource()==jpfArray[1])
		 {
			 jpfArray[2].requestFocus(true);
		 }
		 else if(e.getSource()==jpfArray[2])
		 {
			 jbArray[0].requestFocus(true);
		 }
		 //����
		 else if(e.getSource()==jbArray[1])
		 {
			 for(int i = 0;i<jpfArray.length;i++)
			 {
				 jpfArray[i].setText("");
			 }
			 jtf.setText("");
		 }
		 else if(e.getSource()==jbArray[0])
		 {
			 String user_name = jtf.getText().trim();
			 if(user_name.equals(""))
			 {
			     JOptionPane.showMessageDialog(this,"�������û���","����",JOptionPane.ERROR_MESSAGE);
				 return ;
			 }
			 String oldPwd = jpfArray[0].getText().trim();
             if(oldPwd.equals(""))
             {
            	 JOptionPane.showMessageDialog(this,"�����������","����",JOptionPane.ERROR_MESSAGE);
            	 return ;
             }		     
		     String newPwd = jpfArray[1].getText().trim();
		     if(newPwd.equals(""))
		     {
		        	 JOptionPane.showMessageDialog(this,"������������","����",JOptionPane.ERROR_MESSAGE);
            	     return ;
             }
		    String newPwd1 = jpfArray[2].getText().trim();
		    if(!newPwd1.equals(newPwd))
		    {
		    	 JOptionPane.showMessageDialog(this,"����ȷ���������벻ͬ","����",JOptionPane.ERROR_MESSAGE);
            	 return ;
            }
		    this.initialConnection();
		    try
		    {
	        	  String sql =  "update student set password = '"+newPwd+"' where stu_id = '"+user_name+"' and password = '"+oldPwd+"'";
	              int i = stmt.executeUpdate(sql);
	              if(i==0)
	              {
	            	  JOptionPane.showMessageDialog(this,"�޸�ʧ�ܣ����������û����������Ƿ���ȷ","����",JOptionPane.ERROR_MESSAGE);
	            	  return  ;	
	              }
	              else
	              {
	            	  JOptionPane.showMessageDialog(this,"�����޸ĳɹ�","��ʾ",JOptionPane.INFORMATION_MESSAGE);
	            	  new Login();
	            	  sc.dispose();
	              }
		    }
		    catch(Exception ea)
		    {
	        	  ea.printStackTrace();
	        }
		    this.closeConn();
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
}