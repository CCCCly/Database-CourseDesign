package admin;
import javax.swing.*;

import pub.Login;

import java.awt.event.*;
import java.sql.*;
public class ChangePwdAdmin extends JPanel implements ActionListener
{
    private String host;
    private AdminClient ac;
    private Connection conn;
    private Statement stmt;
	private ResultSet rs;
	
	private JLabel[] jlArray = {new JLabel("用户ID"),new JLabel("原始密码"),new JLabel("新密码"),new JLabel("确认新密码")};
	private JTextField jtf = new JTextField();
	private JPasswordField[] jpfArray = {new JPasswordField(),new JPasswordField(),new JPasswordField()};
	private JButton[] jbArray = {new JButton("确认"),new JButton("重置")};
    public ChangePwdAdmin(String host,AdminClient ac)
    {
    	this.host = host;
    	this.ac=ac;
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
    		if(i==0)
    		{
    			  jtf.setBounds(130, 20+50*i, 150, 30);
    			  this.add(jtf);	
    		}
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
	//重写actionPerformed()方法
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
			     JOptionPane.showMessageDialog(this,"请输入用户名","错误",JOptionPane.ERROR_MESSAGE);
				 return ;
			 }
			 String oldPwd = jpfArray[0].getText().trim();
             if(oldPwd.equals(""))
             {
            	 JOptionPane.showMessageDialog(this,"请输入旧密码","错误",JOptionPane.ERROR_MESSAGE);
            	 return ;
             }		     
		    String newPwd = jpfArray[1].getText().trim();
		    if(newPwd.equals(""))
		    {
		    	 JOptionPane.showMessageDialog(this,"请输入新密码","错误",JOptionPane.ERROR_MESSAGE);
            	 return ;
            }
		    String newPwd1 = jpfArray[2].getText().trim();
		    if(!newPwd1.equals(newPwd))
		    {
		    	 JOptionPane.showMessageDialog(this,"密码确认与新密码不同","错误",JOptionPane.ERROR_MESSAGE);
            	 return ;
            }
		    try
		    {
	        	  this.initialConnection();
	        	  String sql =  "update admin set password = '"+newPwd+"' where admin_id = '"+user_name+"' and password = '"+oldPwd+"'";
	              int i = stmt.executeUpdate(sql);
	              if(i==0)
	              {
	            	  JOptionPane.showMessageDialog(this,"修改失败，请检查您的用户名和密码是否正确","错误",JOptionPane.ERROR_MESSAGE);
	            	  return ;
	              }
	              else
	              {
	            	  JOptionPane.showMessageDialog(this,"密码修改成功","提示",JOptionPane.INFORMATION_MESSAGE);
	            	  new Login();
	            	  ac.dispose();
	              }
		    }
		    catch(Exception ea)
		    {
	        	  ea.printStackTrace();
	        }
		 }
	}
	//连接数据库
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
			JOptionPane.showMessageDialog(this,"连接失败，请检查连接是否正确","错误",JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}
	//关闭数据库连接
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