package admin;
import javax.swing.*;
import javax.swing.tree.*;
import java.awt.*;
import java.awt.event.*;
import pub.Welcome;
import student.GetStu;
public class AdminClient extends JFrame
{
     private String host;
     private String admin_id;
     
     private DefaultMutableTreeNode dmtnRoot = new DefaultMutableTreeNode(new MyNode("����ѡ��","0"));
     private DefaultMutableTreeNode dmtn1    = new DefaultMutableTreeNode(new MyNode("ѧ����Ϣ����","1"));
     private DefaultMutableTreeNode dmtn2 	 = new DefaultMutableTreeNode(new MyNode("��Ŀ����","2"));
     private DefaultMutableTreeNode dmtn3 	 = new DefaultMutableTreeNode(new MyNode("Ժϵ����","3"));
     private DefaultMutableTreeNode dmtn4    = new DefaultMutableTreeNode(new MyNode("ϵͳѡ��","4"));
     private DefaultMutableTreeNode dmtn11   = new DefaultMutableTreeNode(new MyNode("ѧ����Ϣ��ѯ","11"));
     private DefaultMutableTreeNode dmtn13   = new DefaultMutableTreeNode(new MyNode("���ѧ��","13"));     
     private DefaultMutableTreeNode dmtn15   = new DefaultMutableTreeNode(new MyNode("ɾ��ѧ��","15"));
     private DefaultMutableTreeNode dmtn20   = new DefaultMutableTreeNode(new MyNode("��Ŀ��Ϣ��ѯ","20"));
     private DefaultMutableTreeNode dmtn21   = new DefaultMutableTreeNode(new MyNode("�����Ŀ","21"));
     private DefaultMutableTreeNode dmtn22   = new DefaultMutableTreeNode(new MyNode("ɾ����Ŀ","22"));
     private DefaultMutableTreeNode dmtn30   = new DefaultMutableTreeNode(new MyNode("������Ϣ��ѯ","30"));
     private DefaultMutableTreeNode dmtn31   = new DefaultMutableTreeNode(new MyNode("���Ӷ���","31"));
     private DefaultMutableTreeNode dmtn32   = new DefaultMutableTreeNode(new MyNode("ɾ������","32"));
     private DefaultMutableTreeNode dmtn41   = new DefaultMutableTreeNode(new MyNode("�����޸�","41"));
     private DefaultMutableTreeNode dmtn42   = new DefaultMutableTreeNode(new MyNode("�˳�","42"));
     private DefaultMutableTreeNode dmtn5    = new DefaultMutableTreeNode(new MyNode("��ѯ����","5"));
     private DefaultMutableTreeNode dmtn51   = new DefaultMutableTreeNode(new MyNode("ģ������","51"));
     private DefaultMutableTreeNode dmtn52   = new DefaultMutableTreeNode(new MyNode("�߼�����","52"));
     private DefaultTreeModel dtm = new DefaultTreeModel(dmtnRoot);
     private JTree jt = new JTree(dtm);
     private JScrollPane jsp = new JScrollPane(jt);
     private JPanel jpy = new JPanel();
     private JSplitPane jspl = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,jsp,jpy);
     CardLayout cl;
     
     private ChangePwdAdmin changepwdadmin;
     private NewStu  newstu;
     private GetStu getstu;
     private GetItem getitem;
     private GetTeam getteam;
     private Welcome welcome;
     private NewItem newitem;
     private NewTeam newteam;
     private KeySelect keyselect;
     private AdvancedSelect advancedselect;
     private DelStu delstu;
     private DelItem delitem;
     private DelTeam delteam;
     
     public AdminClient(String host,String admin_id)
     {
    	 this.host = host;
    	 this.admin_id = admin_id;
         this.initialTree();
    	 this.initialPanel();
         this.initialJpy();
         this.initialFrame();
         this.addListener();
     }
     public void initialPanel()
     {
    	    welcome = new Welcome();
    	    changepwdadmin = new ChangePwdAdmin(host,this);
    	    newstu = new NewStu(host);
    	    getstu = new GetStu(host, admin_id);
    	    getitem = new GetItem(host);
    	    getteam = new GetTeam(host);
    	    newitem = new NewItem(host);
    	    newteam  = new NewTeam(host);
    	    keyselect = new KeySelect(host);
    	    advancedselect = new AdvancedSelect(host);
            delstu = new DelStu(host);
            delitem = new DelItem(host);
            delteam = new DelTeam(host);
     }
     public void initialTree()
     {
    	 dmtnRoot.add(dmtn1);
    	 dmtnRoot.add(dmtn2);
    	 dmtnRoot.add(dmtn3);
    	 dmtnRoot.add(dmtn5);
    	 dmtnRoot.add(dmtn4);
    	 dmtn1.add(dmtn11);
    	 dmtn1.add(dmtn13);
    	 dmtn1.add(dmtn15);
    	 dmtn2.add(dmtn20);
    	 dmtn2.add(dmtn21);
    	 dmtn2.add(dmtn22);
    	 dmtn3.add(dmtn30);
    	 dmtn3.add(dmtn31);
    	 dmtn3.add(dmtn32);
    	 dmtn4.add(dmtn41);
    	 dmtn4.add(dmtn42);
    	 dmtn5.add(dmtn51);
    	 dmtn5.add(dmtn52);
     }
     public void initialJpy()
     {
    	   jpy.setLayout(new CardLayout());//��Ƭ����,ͬһʱ��ֻ�ܿ�������һ�����
    	   cl = (CardLayout)jpy.getLayout();
    	   jpy.add(welcome,"welcome");
    	   jpy.add(changepwdadmin,"changepwdadmin");
           jpy.add(newstu,"newstu");
           jpy.add(getstu,"getstu");
           jpy.add(getitem,"getitem");
           jpy.add(getteam,"getteam");
           jpy.add(newitem,"newitem");
           jpy.add(newteam,"newteam");
           jpy.add(keyselect,"keyselect");
           jpy.add(advancedselect,"advancedselect");
           jpy.add(delstu,"delstu");
           jpy.add(delitem,"delitem");
           jpy.add(delteam, "delteam");
     }
     public void initialFrame()
     {
    	 this.add(jspl);
    	 jspl.setDividerLocation(200);
    	 jspl.setDividerSize(4);
    	 this.setTitle("����Ա�ͻ���");
    	 Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
    	 int centerx = screensize.width/2;
    	 int centery =screensize.height/2;
    	 int w = 900;
    	 int h = 650;
    	 this.setBounds(centerx-w/2,centery-h/2-30,w,h);
    	 this.setVisible(true);
    	 this.setExtendedState(JFrame.MAXIMIZED_BOTH);
     }
     public void addListener()
     {
    	 jt.addMouseListener
    	 (
    			 new MouseAdapter()
    			 {
    				 //��д���������
					public void mouseClicked(MouseEvent e) 
					{
				        DefaultMutableTreeNode dmtntemp  =(DefaultMutableTreeNode)jt.getLastSelectedPathComponent();
				        MyNode myNode = (MyNode)dmtntemp.getUserObject();
				        String id = myNode.getId();
					    if(id.equals("0"))
					    {
	                        cl.show(jpy, "welcome");				       
					    }
					    else if(id.equals("42"))
					    {
		                      int i = JOptionPane.showConfirmDialog(jpy,"��ȷ���˳�?","ѯ��",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);			    	
					          if(i==0)
					          {
					        	  System.exit(0);
					          }  
					    }
					    else if(id.equals("41"))
					    {
					    	cl.show(jpy, "changepwdadmin");
					    }
					    else if(id.equals("13"))
					    {
					    	cl.show(jpy,"newstu");
					    }
					    else if(id.equals("11"))
					    {
					    	cl.show(jpy,"getstu");
					    }
					    else if(id.equals("15"))
					    {
					    	cl.show(jpy,"delstu");
					    }
					    else if(id.equals("20"))
					    {
					    	cl.show(jpy,"getitem");
					    }
					    else if(id.equals("21"))
					    {
					    	cl.show(jpy,"newitem");
					    }
					    else if(id.equals("22"))
					    {
					    	cl.show(jpy,"delitem");
					    }
					    else if(id.equals("30"))
					    {
					    	cl.show(jpy,"getteam");
					    }
					    else if(id.equals("31"))
					    {
					    	cl.show(jpy,"newteam");
					    }
					    else if(id.equals("32"))
					    {
					    	cl.show(jpy,"delteam");
					    }
					    else if(id.equals("51"))
					    {
					    	cl.show(jpy, "keyselect");
					    }
					    else if(id.equals("52"))
					    {
					    	cl.show(jpy, "advancedselect");
					    }
					}
    				 
    			 }
    	 );
     }
     class MyNode
     {
		 private String id;
		 private String values;
		 public MyNode(String values,String id)
		 {
			 
			 this.values = values;
			 this.id = id;
		 }
		 public String toString()
		 {
			 return this.values;
		 }
		 public String getId()
		 {
			 return this.id;
		 }
	 }
}
