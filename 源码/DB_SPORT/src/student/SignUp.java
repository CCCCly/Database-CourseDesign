package student;
import javax.swing.*;

import java.awt.event.*;
import java.sql.*;
import java.util.*;
import javax.swing.table.*;
public class SignUp extends JPanel implements ActionListener
{
    private String host;
    private String stu_id;
    
    private Connection conn;
    private Statement stmt;
	private ResultSet rs;
	
	private JLabel jl=  new JLabel("请输入项目号");
	private JTextField[] jtfArray = {new JTextField(),new JTextField()};
	private JComboBox jcb = new JComboBox();
	private JButton jb = new JButton("确认");
	private JTable jt;
    private JScrollPane jsp;
    private JTable jt2;
    private JScrollPane jsp2;
    private Vector<String> v_head = new Vector<String>();//表头数组
    private Vector<Vector> v_data = new Vector<Vector>();//表数据数组
    private Vector<String> v_itemid = new Vector<String>();//项目号数组
    private Map<String,String> map_cate = new HashMap<String,String>();//项目类集合
    private String selectCate_id = "21";
    public SignUp(String host,String stu_id)
    {
    	this.host = host;
    	this.stu_id = stu_id;
        this.initialFrame();
        this.addListener();
    }
    public void initialData()
    {
    	v_head.clear();
    	v_data.clear();
    	v_itemid.clear();
    	v_head.add("项目号");
    	v_head.add("项目名称");
    	v_head.add("比赛时间");
    	v_head.add("规则");
    	v_head.add("晋级下一轮人数");
    	v_head.add("地点");
    	try
    	{
    		String sql  = "select * from item where cate_id = '"+map_cate.get(jcb.getSelectedItem())+"' order by item_id desc";
    		rs = stmt.executeQuery(sql);
    	    while(rs.next())
    	    {
    	    	String item_id =  rs.getString(1).trim();
    	        String item_name = rs.getString(2).trim();
    	        String item_time  = rs.getString(3);
    	        String rule = rs.getString(4);
    	        String promotion_num = rs.getString(5);
    	        String place = rs.getString(6);
    	        	
    	        Vector<String> v = new Vector<String>();
    	        	v.add(item_id);
    	        	v.add(item_name);
    	        	v.add(item_time);
    	        	v.add(rule);
    	        	v.add(promotion_num);
    	        	v.add(place);
    	        v_data.add(v);
    	        v_itemid.add(item_id);
    	    }
    	    rs.close();
    	}
    	catch(SQLException e)
    	{
    		e.printStackTrace();
    	}
    	catch(NullPointerException e)
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
    	 this.initialConnection();
    	 try
    	 {
    		 String  sql = "select * from  category ";
    	     rs  =  stmt.executeQuery(sql);
    	     jcb.removeAllItems();
    	     while(rs.next())
    	     {
    	    	 String	cate_id = rs.getString(1).trim();
    	    	 String cate_name = rs.getString(2).trim();
    	    	 map_cate.put(cate_name, cate_id);
    	         jcb.addItem(cate_name);        
    	     }
    	     jcb.setSelectedItem(selectCate_id);//选中项目分类
    	     this.initialData();
    	 }
    	 catch(SQLException e)
    	 {
    		 e.printStackTrace();
    	 }
    	 catch(Exception e)
    	 {
    		 e.printStackTrace();
    	 }
    	 jcb.setBounds(20,13,100,30);
    	 this.add(jcb);
    	 DefaultTableModel dtm = new DefaultTableModel(v_data,v_head);
    	 jt = new JTable(dtm);
    	 jsp = new JScrollPane(jt);
    	 jsp.setBounds(20,60,650,200);
    	 this.add(jsp);
    	 //输入项目号
    	 jl.setBounds(100,510,190,30);
    	 this.add(jl);
    	 jtfArray[1].setBounds(200,510,130,30);
    	 this.add(jtfArray[1]);
    	 
    	 jb.setBounds(350,510,150,30);
    	 this.add(jb); 
    }
    public void addListener()
    {
    	jcb.addActionListener(this);
   	    jb.addActionListener(this);    
    }
	//重写actionPerformed()方法
	public void actionPerformed(ActionEvent e) 
	{
		//选择项目分类号
		if(e.getSource()==jcb)
		{
			selectCate_id = (String)jcb.getSelectedItem();
			this.removeAll();
			this.initialFrame();
			this.repaint();
		}
		//选择确定报名
		if(e.getSource()==jb)
		{
			String item_id = jtfArray[1].getText().trim();
			if(item_id.equals(""))
			{
				JOptionPane.showMessageDialog(this,"项目号不得为空","错误",JOptionPane.ERROR_MESSAGE);
			    return ;
			}
			if(!v_itemid.contains(item_id))
			{
				JOptionPane.showMessageDialog(this,"项目号不在列表中","错误",JOptionPane.ERROR_MESSAGE);
			    return ;
			}
          	try
          	{
			  this.initialConnection();	
		      String sql2 = "select * from grade where stu_id = '"+stu_id+"' and item_id = '"+item_id+"'";
			  rs  = stmt.executeQuery(sql2);
			  if(rs.next())
			  {
				    JOptionPane.showMessageDialog(this,"你已经报过此项目","错误",JOptionPane.ERROR_MESSAGE);  
				    return ;  	  
			  }
			  rs.close();
			  //插入成绩表（暂时无成绩）
			  String sql3 = "insert into grade(stu_id,item_id) values('"+stu_id+"','"+item_id+"')";
			  int i = stmt.executeUpdate(sql3);
			  if(i==1)
			  {
			    	  JOptionPane.showMessageDialog(this,"报名成功","提示",JOptionPane.INFORMATION_MESSAGE);    	  
			  }
			  else
			  {
			    	  JOptionPane.showMessageDialog(this,"报名失败","错误",JOptionPane.ERROR_MESSAGE);  
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
          	finally
          	{
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
