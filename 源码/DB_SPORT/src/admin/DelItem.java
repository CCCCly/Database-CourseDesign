package admin;
import javax.swing.*;

import java.awt.event.*;
import java.sql.*;
public class DelItem extends JPanel implements ActionListener
{
    private String host;
    private Connection conn;
    private Statement stmt;
    private ResultSet rs;
    
    private JLabel jl = new JLabel("输入项目编号");
    private JTextField jtf = new JTextField();
    private JButton jb = new JButton("删除");
	public DelItem(String host)
	{
		this.host = host;
	    this.initialFrame();	
	}
	public void initialFrame()
	{
		  this.setLayout(null);
		  jl.setBounds(20,20,100,30);
		  this.add(jl);
		  jtf.setBounds(120,20,150,30);
		  this.add(jtf);
		  jb.setBounds(280,20,100,30);
		  this.add(jb);
	      jb.addActionListener(this);
	}
	//重写方法
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getSource()==jb)
		{
			String item_id = jtf.getText().trim();
			if(item_id.equals(""))
			{
				JOptionPane.showMessageDialog(this,"编号不得为空","错误",JOptionPane.ERROR_MESSAGE );
				return ; 
			}
			int i = JOptionPane.showConfirmDialog(this, "以下操作将删除该项目的所有信息");
			if(i==0)
			{
                this.initialConnection();
                try
                {
                	String sql = "delete from item where item_id = '"+item_id+"'";
                	int j  = stmt.executeUpdate(sql);
                    if(j==1)
                    {
                    	JOptionPane.showMessageDialog(this,"删除成功","提示",JOptionPane.INFORMATION_MESSAGE);
                    }
                    else
                    {
                    	JOptionPane.showMessageDialog(this,"删除失败,请检查编号是否正确","提示",JOptionPane.INFORMATION_MESSAGE);
                    }
                }
                catch(SQLException ea)
                {
                	ea.printStackTrace();
                }
                catch(Exception ea)
                {
                	ea.printStackTrace();
                }
                this.closeConn();
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
