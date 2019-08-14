package admin;
import javax.swing.*;

import java.sql.*;
import java.awt.event.*;
import java.util.*;
public class GetStu extends JPanel implements ActionListener
{
    private String host;
    private Connection conn;
    private Statement stmt;
    private ResultSet rs;
    
    private JTextField jtf = new JTextField();
    private JLabel[] jlArray = {new JLabel("学号"),new JLabel("姓名"),new JLabel("性别"),new JLabel("年龄"),new JLabel("所属队伍")};
    private JLabel[] jlArray2 = new JLabel[5];
	private JButton jb = new JButton("查询");
	private Map<String,String> map_team = new HashMap<String,String>();//队伍集合
	public GetStu(String host)
	{
		this.host = host;
		this.initialFrame();
		this.initialMap();
		this.addListener();
	}
	public void initialFrame()
	{
	    this.setLayout(null);
		jtf.setBounds(100,20,200,30);
	    this.add(jtf);
	    jb.setBounds(350,20,150,30);
	    this.add(jb);
	    for(int i = 0;i<5;i++)
	    {
	    	jlArray[i].setBounds(50,70+i*50,100,30);
	   	    this.add(jlArray[i]);
     	}
	}
	public void initialMap()
	{
	   try
	   {
		   this.initialConnection();
		   String sql  = "select * from team";
	       rs = stmt.executeQuery(sql);
	       while(rs.next())
	       {
	    	   String team_id = rs.getString(1).trim();
	    	   String name = rs.getString(2).trim();
	           map_team.put(team_id,name);
	       }
	   }
	   catch(SQLException e)
	   {
		   e.printStackTrace();
	   }
	   this.closeConn();
	}
	public void addListener()
	{
		jb.addActionListener(this);	
	}
    //重写方法
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getSource()==jb)
		{
			String stu_id = jtf.getText().trim();
			if(stu_id.equals(""))
			{
				JOptionPane.showMessageDialog(this,"学号不得为空","错误",JOptionPane.ERROR_MESSAGE);
				return ;
			}
			try
			{
			    this.initialConnection();
			    //查询学生单表
			    String sql  = "select * from student where stu_id = '"+stu_id+"'";
			    rs = stmt.executeQuery(sql);
			    if(rs.next())
			    {
			       this.removeAll();
			       String stu_name = rs.getString(3).trim();
			       String stu_gender = rs.getString(4).trim();
			       String stu_age = rs.getString(5).trim();
			       String team_id = rs.getString(6).trim();
			       
			       jlArray2[0] = new JLabel(stu_id);
			       jlArray2[1] = new JLabel(stu_name);
			       jlArray2[2] = new JLabel(stu_gender);
			       jlArray2[3] = new JLabel(stu_age);
			       jlArray2[4] = new JLabel(map_team.get(team_id));
			       this.initialFrame();  
			       for(int i = 0;i<5;i++)
			       {
				    	jlArray2[i].setBounds(150,70+i*50,150,30);
				   	    this.add(jlArray2[i]);
			       }
			       this.repaint();
			    }
			    else
			    {
			    	JOptionPane.showMessageDialog(this,"该学号不存在","错误",JOptionPane.ERROR_MESSAGE);
			    	for(int i = 0;i<5;i++)
				    {
					    jlArray2[i].setText("");
				    }
					this.closeConn();
					return ;
			    }
			}
			catch(SQLException ea)
			{
				ea.printStackTrace();	
			}
			this.closeConn();
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
