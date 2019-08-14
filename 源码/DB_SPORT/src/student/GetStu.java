package student;
import javax.swing.*;
import java.sql.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.table.*;
public class GetStu extends JPanel 
{
    private String host;
    private String stu_id;
    
    private Connection conn;
    private Statement stmt;
    private ResultSet rs;
    
    private JTextField jtf = new JTextField();
	private JLabel[] jlArray = {new JLabel("学号"),new JLabel("姓名"),new JLabel("性别"),new JLabel("年龄"),new JLabel("所属队伍"),new JLabel("报名项目")};
    private JLabel[] jlArray2 = new JLabel[5];
	private JTable jt;
	private JScrollPane jsp = new JScrollPane();
	private Map<String,String> map_team = new HashMap<String,String>();//队伍集合
	private Vector<String> v_head = new Vector<String>();
	private Vector<Vector> v_data = new Vector<Vector>();
	//构造函数
	public GetStu(String host,String stu_id)
	{
		this.host = host;
		this.stu_id = stu_id;
		this.initialMap();
		this.initialData();
		this.initialFrame();
	}
	public void initialData()
	{
		   this.initialConnection();
		   v_head.add("项目编号");
		   v_head.add("项目名称");
		   v_head.add("比赛时间");
		   v_head.add("规则");
		   v_head.add("报名人数");
		   v_head.add("地点");
		   v_head.add("成绩");
		   try
		   {	
				//显示基本信息
			    String sql  = "select * from student where stu_id = '"+stu_id+"'";
			    rs = stmt.executeQuery(sql);
			    if(rs.next())
			    {
			       String stu_name = rs.getString(3).trim();
			       String stu_gender = rs.getString(4).trim();
			       String stu_age = rs.getString(5).trim();
			       String team_id = rs.getString(6).trim();
			       
			       jlArray2[0] = new JLabel(stu_id);
			       jlArray2[1] = new JLabel(stu_name);
			       jlArray2[2] = new JLabel(stu_gender);
			       jlArray2[3] = new JLabel(stu_age);
			       jlArray2[4] = new JLabel(map_team.get(team_id));
			       for(int i = 0;i<5;i++)
			       {
				    	 jlArray2[i].setBounds(150,70+i*50,150,30);
				   	     this.add(jlArray2[i]);
			       }
		        }
			    //显示报名信息
			   String sql2 = "select item.item_id,item_name,timel,rule1,cate_id,score " +
			   		          "from item, grade " +
			   		          "where item.item_id = grade.item_id  and grade.stu_id= '"+stu_id+"'";
			   rs =  stmt.executeQuery(sql2);
			   while(rs.next())
			   {
			    	String item_id =  rs.getString(1);
			    	String item_name =  rs.getString(2);
			    	String timel =  rs.getString(3);
			    	String rule1 =  rs.getString(4);
			    	String cate_id=rs.getString(5);
			    	String score = rs.getString(6);
			    	//查询报名人数
			    	String sql3 = "select count(*)" +
			   		              "from grade " +
			   		              "where item_id ='"+item_id+"'";
			    	Statement stmt1;
			    	stmt1 = conn.createStatement();
			    	ResultSet rs1;
			    	rs1 =  stmt1.executeQuery(sql3);
			        while(rs1.next())
			        {
			        	String signup_num=rs1.getString(1);
					    Vector<String> v = new Vector<String>();
					    v.add(item_id);
					    v.add(item_name);
					    v.add(timel);
				        v.add(rule1);
				        v.add(signup_num);
				        v.add(cate_id);
				        v.add(score);
				        v_data.add(v);
				        DefaultTableModel dtm = new DefaultTableModel(v_data,v_head);
				        jt = new JTable(dtm);
		   	    	    jsp = new JScrollPane(jt);
		   	    	    jsp.setBounds(150,320,500,300); 
				        this.add(jsp);
			        }
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
		
	    for(int i = 0;i<6;i++)
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
