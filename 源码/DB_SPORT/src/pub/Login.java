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
    private String host;//主机IP地址
	private Connection conn;//获得数据库的连接
	private Statement stmt;//向数据库发送要执行的SQL语句
	private ResultSet rs;//数据库检索的结果集
	
	private JLabel[] jlArray =  {new JLabel("用户名"),new JLabel("密码"),new JLabel("主机地址")};
	private JTextField[] jtfArray = {new JTextField(),new JTextField()};
	private JPasswordField jpwf = new JPasswordField();
	private JRadioButton[] jrbArray = {new JRadioButton("学生",true),new JRadioButton("管理员")};
	private JButton[] jbArray = {new JButton("登录"),new JButton("注册")};
	private ButtonGroup bg=new ButtonGroup();//单选按钮生效域
    //构造函数
    public 	Login()
    {
    	this.initialFrame();
    }
    public void initialFrame()
    {
    	//标签组
    	this.setLayout(null);
    	jlArray[0].setBounds(20,20,100,30);
    	this.add(jlArray[0]);
    	jlArray[1].setBounds(20,70,100,30);
    	this.add(jlArray[1]);
    	jlArray[2].setBounds(20,120,100,30);
        this.add(jlArray[2]);
        //文本域组
        jtfArray[0].setBounds(100,120,150,30);
        jtfArray[0].setText("127.0.0.1:3306");//默认ip地址
        this.add(jtfArray[0]);
        jtfArray[1].setBounds(100,20,150,30);
        this.add(jtfArray[1]);
        //密码框
        jpwf.setBounds(100,70,150,30);
        this.add(jpwf);
        //身份按钮
        this.bg.add(jrbArray[0]);
        this.bg.add(jrbArray[1]);
        jrbArray[0].setBounds(50,170,100,30);
        this.add(jrbArray[0]);
        jrbArray[1].setBounds(150,170,100,30);
        this.add(jrbArray[1]);
        //确认重置按钮
        jbArray[0].setBounds(50,210,70,30);
        this.add(jbArray[0]);
        jbArray[1].setBounds(150,210,70,30);
        this.add(jbArray[1]);
        
        this.setTitle("登录");
        //窗口大小位置设定
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();//获取屏幕尺寸
		int centerX=screenSize.width/2;
		int centerY=screenSize.height/2;
		int w=300;//本窗体宽度
		int h=320;//本窗体高度
		this.setBounds(centerX-w/2,centerY-h/2-100,w,h);//设置窗体出现在屏幕中央
		
		this.setVisible(true);
		jbArray[0].addActionListener(this);
		jbArray[1].addActionListener(this);
    }
    //实现actionPerformed方法
	public void actionPerformed(ActionEvent e) 
	{
		//注册
	    if(e.getSource()==jbArray[1])
	    {
	    	Register register  = new Register();
	    	this.dispose();
	    }
	    //登录
	    if(e.getSource()==jbArray[0])
	    {
	    	this.host = jtfArray[0].getText().trim();//trim()除去字符串左右空格
	    	if(host.equals(""))
	    	{
	    		JOptionPane.showMessageDialog(this,"IP地址不能为空","错误",JOptionPane.ERROR_MESSAGE);
	    		return ;
	    	}
	    	String id = jtfArray[1].getText().trim();
	    	if(id.equals(""))
	    	{
	    		JOptionPane.showMessageDialog(this,"请输入用户名","错误",JOptionPane.ERROR_MESSAGE);
	    		return ;
	    	}
	    	String password = jpwf.getText().trim();
	    	if(password.equals(""))
	    	{
	    		JOptionPane.showMessageDialog(this,"请输入密码","错误",JOptionPane.ERROR_MESSAGE);
	    		return ;
	    	}
	    	int type=this.jrbArray[0].isSelected()?0:1;
	    	//连接数据库
	    	this.initialConnection();
    	    if(type==0)
    	    {
	    		try
	    		{
	    	    	String sql = "select * from student where stu_id= '"+id+"' and password = '"+password+"'";
	    	    	rs = stmt.executeQuery(sql);
	    	    	if(!rs.next())
	    	    	{
	    	    		JOptionPane.showMessageDialog(this, "用户名或密码错误","错误",JOptionPane.ERROR_MESSAGE);
	    	    		this.closeConn();
	    	    		return ;
	    	    	}
	    	    	//启动学生客户端
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
	    	    		JOptionPane.showMessageDialog(this, "用户名或密码错误","错误",JOptionPane.ERROR_MESSAGE);
	    	    		this.closeConn();
	    	    		return ;
	    	    	}
	    	    	//启动管理员客户端
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
	//数据库连接
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
	//主函数
	public static void main(String args[])
	{
		   Login login  = new Login();
	}
}
