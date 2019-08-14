package admin;
import javax.swing.*;

import java.awt.event.*;
import java.sql.*;
import java.util.*;
import javax.swing.table.*;
public class GetTeam extends JPanel
{
    private String host;

    private Connection conn;
    private Statement stmt;
	private ResultSet rs;
	
	private JTextField[] jtfArray = {new JTextField(),new JTextField()};

	private JButton jcb;
	private JTable jt;
    private JScrollPane jsp;
    private JTable jt2;
    private JScrollPane jsp2;
    private Vector<String> v_head = new Vector<String>();//��ͷ����
    private Vector<Vector> v_data = new Vector<Vector>();//����������
    private Vector<String> v_itemid = new Vector<String>();//��Ŀ������
    private Map<String,String> map_cate = new HashMap<String,String>();//��Ŀ�༯��
    private String selectCate_id = "21";
    public GetTeam(String host)
    {
    	this.host = host;
        this.initialFrame();
        this.repaint();
    }
    public void initialData()
    {
    	v_head.clear();
    	v_data.clear();
    	v_itemid.clear();
    	v_head.add("������");
    	v_head.add("��������");
    	try
    	{
    		String sql  = "select * from team order by team_id";
    		rs = stmt.executeQuery(sql);
    	    while(rs.next())
    	    {
    	    	String team_id =  rs.getString(1).trim();
    	        String team_name = rs.getString(2).trim();
    	       
    	        Vector<String> v = new Vector<String>();
    	        	v.add(team_id);
    	        	v.add(team_name);
    	        	
    	        v_data.add(v);
    	        v_itemid.add(team_id);
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
    	     this.initialData();
    	 }
    	 catch(Exception e)
    	 {
    		 e.printStackTrace();
    	 }
    	 
    	 DefaultTableModel dtm = new DefaultTableModel(v_data,v_head);
    	 jt = new JTable(dtm);
    	 jsp = new JScrollPane(jt);
    	 jsp.setBounds(20,60,650,600);
    	 this.add(jsp);


    }
 
	//�������ݿ�
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
