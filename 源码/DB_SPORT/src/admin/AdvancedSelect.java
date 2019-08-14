package admin;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.Date;
import java.awt.event.*;
import java.sql.*;
import java.util.*;
public class AdvancedSelect extends JPanel implements ActionListener
{
    private String host;
    private Connection conn;
    private Statement stmt;
    private ResultSet rs;
    
	private JLabel[] jlArray = {new JLabel("性别"),new JLabel("出生年份"),new JLabel("所属院系"),new JLabel("报名项目")};
    private JComboBox[] jcbArray =  {new JComboBox(),new JComboBox(),new JComboBox(),new JComboBox()};//性别、出生年份、院系、项目
    private Map<String,String> map_team = new HashMap<String,String>();//队伍集合
    private Map<String,String> map_item = new HashMap<String,String>();//项目集合
    private JButton  jb = new JButton("确定");
    private JTable jt;
    private JScrollPane jsp;
    private Vector<String> v_head = new Vector<String>();
    private Vector<Vector> v_data = new Vector<Vector>();
    public AdvancedSelect(String host)
    {
    	this.host = host;
    	this.initialData();
        this.initialFrame();
        this.addActionListener();
    }
    public void initialData()
    {
	   	v_head.add("项目名称");
		v_head.add("学号");
		v_head.add("姓名");
		v_head.add("年龄");
		v_head.add("性别");
		v_head.add("所属队伍");

    	jcbArray[0].addItem("不限");
    	jcbArray[0].addItem("男");
    	jcbArray[0].addItem("女");
    	
    	jcbArray[1].addItem("不限");
    	for(int i = 1994;i < 2001;i++)
    	{
    		jcbArray[1].addItem(Integer.toString(i));
    	}
        this.initialConnection();
        try
        {
    	   String sql = "select * from team";
    	   rs = stmt.executeQuery(sql);
    	   jcbArray[2].addItem("不限");
		   while(rs.next())
		   {
    		   String team_id = rs.getString(1).trim();
    		   String team_name = rs.getString(2).trim();
    		   jcbArray[2].addItem(team_name);
    		   map_team.put(team_name, team_id);
    	   }
		   
    	   String sql2 = "select * from item";
    	   rs = stmt.executeQuery(sql2);
    	   jcbArray[3].addItem("不限");
		   while(rs.next())
		   {
    		   String item_id = rs.getString(1).trim();
    		   String item_name = rs.getString(2).trim();
    		   jcbArray[3].addItem(item_name);
    		   map_item.put(item_name, item_id);
    	   } 
       }
       catch(SQLException e)
       {
    	   e.printStackTrace();
       }
       this.closeConn();	
	}
    public void initialFrame()
    {
    	this.setLayout(null);
    	jlArray[0].setBounds(20,20,40,30);
    	this.add(jlArray[0]);
    	jcbArray[0].setBounds(60,20,70,30);
    	this.add(jcbArray[0]);
    	jlArray[1].setBounds(170,20,70,30);
    	this.add(jlArray[1]);
    	jcbArray[1].setBounds(240,20,70,30);
    	this.add(jcbArray[1]);
    	jlArray[2].setBounds(330,20,70,30);
    	this.add(jlArray[2]);
    	jcbArray[2].setBounds(400,20,70,30);
    	this.add(jcbArray[2]);
    	jlArray[3].setBounds(480,20,70,30);
    	this.add(jlArray[3]);
    	jcbArray[3].setBounds(550,20,120,30);
    	this.add(jcbArray[3]);
    	jb.setBounds(680,20,70,30);
    	this.add(jb);
    }
    public void addActionListener()
    {
    	jb.addActionListener(this);
    }
    //重写方法
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getSource()==jb)
		{
			this.removeAll();//清空容器所有组件
			v_data.clear();
			this.initialFrame();
			this.initialConnection();
			try
			{
				Calendar c = Calendar.getInstance();
				c.setTime(new Date());
				//年龄
				String sex = (String)jcbArray[0].getSelectedItem();
				String year = (String)jcbArray[1].getSelectedItem();
				String age;
				if(!year.equals("不限"))
				{
					age = Integer.toString(c.get(Calendar.YEAR)-Integer.parseInt(year));//获取的年份
				}
				else
				{
					age = "不限";    		
				}
				//队伍名称
				String team_name = (String)jcbArray[2].getSelectedItem();
				String team_id = "";
				if(!team_name.equals("不限"))
				{
					team_id = map_team.get(team_name);
				}
				else
				{
					team_id = "不限";
				}
				//参赛项目
				String item_name  = (String)jcbArray[3].getSelectedItem();
				String item_id = "";
				if(!item_name.equals("不限"))
				{
					item_id = map_item.get(item_name);
				}
				else
				{
					item_id = "不限";
				}
				String sql = "select distinct item.item_name,student.stu_id,student.stu_name,student.age,student.sex,team.team_name " +
							 "from  grade,item,student,team " +
							 "where   student.stu_id=grade.stu_id and item.item_id=grade.item_id and  student.team_id=team.team_id ";
				if(!sex.equals("不限"))
				{
					sql += " and sex = '"+sex+"'";	
				}
	            if(!age.equals("不限"))
	            {	 
	            	 sql +="and age = '"+age+"'";
	            }			 
	            if(!team_id.equals("不限"))
	            {
                	sql +="and student.team_id = '"+team_id+"'";
                }
	            if(!item_id.equals("不限"))
	            {
                	sql +="and grade.item_id = '"+item_id+"'";
                }
	            rs = stmt.executeQuery(sql);	    		
	            while(rs.next())
	            {
	            	String itemname = rs.getString(1).trim();
	            	String stu_id = rs.getString(2).trim();
	            	String stu_name = rs.getString(3).trim();
	            	String stu_age = rs.getString(4).trim();
	            	String stu_sex = rs.getString(5).trim();
	                String teamname =  rs.getString(6).trim();
	                Vector<String> v =new Vector<String>();
	                v.add(itemname);
	                v.add(stu_id);
	                v.add(stu_name);
	                v.add(stu_age);
	                v.add(stu_sex);
	                v.add(teamname);
	                v_data.add(v);
	            }
	            DefaultTableModel dtm = new DefaultTableModel(v_data,v_head);
                jt = new JTable(dtm); 
                jsp = new JScrollPane(jt);
                jsp.setBounds(20,60,800,600);
                this.add(jsp);
                this.repaint();
			}
			catch(SQLException ea)
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
