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
    
	private JLabel[] jlArray = {new JLabel("�Ա�"),new JLabel("�������"),new JLabel("����Ժϵ"),new JLabel("������Ŀ")};
    private JComboBox[] jcbArray =  {new JComboBox(),new JComboBox(),new JComboBox(),new JComboBox()};//�Ա𡢳�����ݡ�Ժϵ����Ŀ
    private Map<String,String> map_team = new HashMap<String,String>();//���鼯��
    private Map<String,String> map_item = new HashMap<String,String>();//��Ŀ����
    private JButton  jb = new JButton("ȷ��");
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
	   	v_head.add("��Ŀ����");
		v_head.add("ѧ��");
		v_head.add("����");
		v_head.add("����");
		v_head.add("�Ա�");
		v_head.add("��������");

    	jcbArray[0].addItem("����");
    	jcbArray[0].addItem("��");
    	jcbArray[0].addItem("Ů");
    	
    	jcbArray[1].addItem("����");
    	for(int i = 1994;i < 2001;i++)
    	{
    		jcbArray[1].addItem(Integer.toString(i));
    	}
        this.initialConnection();
        try
        {
    	   String sql = "select * from team";
    	   rs = stmt.executeQuery(sql);
    	   jcbArray[2].addItem("����");
		   while(rs.next())
		   {
    		   String team_id = rs.getString(1).trim();
    		   String team_name = rs.getString(2).trim();
    		   jcbArray[2].addItem(team_name);
    		   map_team.put(team_name, team_id);
    	   }
		   
    	   String sql2 = "select * from item";
    	   rs = stmt.executeQuery(sql2);
    	   jcbArray[3].addItem("����");
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
    //��д����
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getSource()==jb)
		{
			this.removeAll();//��������������
			v_data.clear();
			this.initialFrame();
			this.initialConnection();
			try
			{
				Calendar c = Calendar.getInstance();
				c.setTime(new Date());
				//����
				String sex = (String)jcbArray[0].getSelectedItem();
				String year = (String)jcbArray[1].getSelectedItem();
				String age;
				if(!year.equals("����"))
				{
					age = Integer.toString(c.get(Calendar.YEAR)-Integer.parseInt(year));//��ȡ�����
				}
				else
				{
					age = "����";    		
				}
				//��������
				String team_name = (String)jcbArray[2].getSelectedItem();
				String team_id = "";
				if(!team_name.equals("����"))
				{
					team_id = map_team.get(team_name);
				}
				else
				{
					team_id = "����";
				}
				//������Ŀ
				String item_name  = (String)jcbArray[3].getSelectedItem();
				String item_id = "";
				if(!item_name.equals("����"))
				{
					item_id = map_item.get(item_name);
				}
				else
				{
					item_id = "����";
				}
				String sql = "select distinct item.item_name,student.stu_id,student.stu_name,student.age,student.sex,team.team_name " +
							 "from  grade,item,student,team " +
							 "where   student.stu_id=grade.stu_id and item.item_id=grade.item_id and  student.team_id=team.team_id ";
				if(!sex.equals("����"))
				{
					sql += " and sex = '"+sex+"'";	
				}
	            if(!age.equals("����"))
	            {	 
	            	 sql +="and age = '"+age+"'";
	            }			 
	            if(!team_id.equals("����"))
	            {
                	sql +="and student.team_id = '"+team_id+"'";
                }
	            if(!item_id.equals("����"))
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
