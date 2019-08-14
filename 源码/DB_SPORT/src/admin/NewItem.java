package admin;
import javax.swing.*;

import java.awt.event.*;
import java.sql.*;
import java.util.*;
public class NewItem extends JPanel implements ActionListener
{
	private String host;
	
	private JLabel[] jlArray = {new JLabel("项目编号"),new JLabel("项目名称"),new JLabel("时间"),new JLabel("晋级人数"),new JLabel("项目分类"),new JLabel("规则")};
	private JTextField[] jtfArray = {new JTextField(),new JTextField(),new JTextField(),new JTextField(),new JTextField()};
	private JTextArea  jta = new JTextArea();//规则文本域
	private JComboBox jcb  = new JComboBox();//项目分类下拉框
	private JButton   jb  = new JButton("确认");
	
	private Connection conn;
	private Statement stmt;
	private ResultSet rs;
	private Map<String,String> map_cate = new HashMap<String,String>();//比赛项目分类集合
	public NewItem(String host)
	{
		this.host = host;
		this.initialData();
		this.initialFrame();
	}
	public void initialData()
	{
		try
		{
			this.initialConnection();
			String sql = "select * from category ";
			rs = stmt.executeQuery(sql);
			int i  = 0;
			while(rs.next())
			{
				String cate_id = rs.getString(1).trim();
				String cate_name = rs.getString(2).trim();
	            map_cate.put(cate_name,cate_id);
			    if(i==0)
			    {
			    	jcb.setSelectedItem(cate_name);
			    }
	            jcb.addItem(cate_name);
			    i++;
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();	
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		this.closeConn();
	}
	public void initialFrame()
	{
		this.setLayout(null);
		jlArray[0].setBounds(20,20,100,30);
		this.add(jlArray[0]);
		jlArray[1].setBounds(20,60,100,30);
		this.add(jlArray[1]);
		jlArray[2].setBounds(20,100,100,30);
		this.add(jlArray[2]);
		jlArray[3].setBounds(20,140,100,30);
		this.add(jlArray[3]);
		jlArray[4].setBounds(20,180,100,30);
		this.add(jlArray[4]);
		jlArray[5].setBounds(20,220,100,30);
		this.add(jlArray[5]);
		
		jtfArray[0].setBounds(150,20,150,30);
		this.add(jtfArray[0]);
		jtfArray[1].setBounds(150,60,150,30);
		this.add(jtfArray[1]);
		jtfArray[2].setBounds(150,100,150,30);
		this.add(jtfArray[2]);
		jtfArray[3].setBounds(150,140,50,30);
		this.add(jtfArray[3]);
		
		jcb.setBounds(150,180,80,30);
		this.add(jcb);
		
		JScrollPane jsp  = new JScrollPane(jta);
		jsp.setBounds(150,220,250,100);
		this.add(jsp);
		
		jb.setBounds(150,400,100,30);
		this.add(jb);
	    jb.addActionListener(this);
	}
	//重写方法
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getSource()==jb)
		{
			String item_id = jtfArray[0].getText().trim();
			String item_name = jtfArray[1].getText().trim();
			String time = jtfArray[2].getText().trim();
			String promotion_num = jtfArray[3].getText().trim();
			String cate_name = ((String)jcb.getSelectedItem()).trim();
			String cate_id = map_cate.get(cate_name);
			String rule = jta.getText().trim();
			String pattern  = "[0-9]{3}";
			if(item_id.equals(""))
			{
				JOptionPane.showMessageDialog(this, "项目编号不得为空","错误",JOptionPane.ERROR_MESSAGE);
				return ;
			}
			if(item_id.length()!=3 || !item_id.matches(pattern))
			{
				JOptionPane.showMessageDialog(this,"项目编号为3位整数","错误",JOptionPane.ERROR_MESSAGE);
				return ;
			}
			if(item_name.equals(""))
			{
				JOptionPane.showMessageDialog(this,"项目名称不得为空","错误",JOptionPane.ERROR_MESSAGE);
				return ;
			}
			if(time.equals(""))
			{
				JOptionPane.showMessageDialog(this, "时间不得为空","错误",JOptionPane.ERROR_MESSAGE);
				return ;
			}
			if(promotion_num.equals(""))
			{
				JOptionPane.showMessageDialog(this, "晋级人数不得为空","错误",JOptionPane.ERROR_MESSAGE);
				return ;
			}
			if(rule.equals(""))
			{
				JOptionPane.showMessageDialog(this, "规则不得为空","错误",JOptionPane.ERROR_MESSAGE);
				return ;
			}
			this.initialConnection();
			try
			{
				String sql = "select * from item where item_id = '"+item_id+"'";
			    rs = stmt.executeQuery(sql);
			    if(rs.next())
			    {
			       JOptionPane.showMessageDialog(this,"该项目已经存在","错误",JOptionPane.ERROR_MESSAGE);
                   this.closeConn();
			       return ;
			    }
			    rs.close();
			    String sql2  = "select * from item where item_name = '"+item_name+"'";
			    rs = stmt.executeQuery(sql2);
			    if(rs.next())
			    {
			    	   JOptionPane.showMessageDialog(this,"项目名称与已有项目名称重复","错误",JOptionPane.ERROR_MESSAGE);
	                   this.closeConn();
				       return ;
				}
			    rs.close();
		        String sql3 = "insert into item values('"+item_id+"','"+item_name+"','"+time+"','"+rule+"','"+promotion_num+"','"+cate_id+"') "; 	    
			    int i  = stmt.executeUpdate(sql3);
			    if(i==1)
			    {
			    	JOptionPane.showMessageDialog(this,"添加成功","提示",JOptionPane.INFORMATION_MESSAGE);	
			    }
			    else
			    {	
			    	JOptionPane.showMessageDialog(this,"添加失败","错误",JOptionPane.INFORMATION_MESSAGE);
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
