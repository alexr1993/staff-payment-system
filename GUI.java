
import java.net.URL;
import java.util.Hashtable;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


/*
 * GUI.java also uses these additional files
 * 	create.png
 * 	edit.png
 * 	save.gif
 */
public class GUI extends JFrame implements ListSelectionListener {

	/* 
	 * The JList and paymanager are for showing the employees and managing the
	 * employee objects respectively
	 */
	private JList employeelist;
	private static PayManager p;
	
	private JTextField namefield;
	private JTextField addressfield;
	private JTextField telephonefield;
	private JLabel idlabel;
	private JLabel titlelabel;
	
	private JButton create;
	private JButton delete;
	private JButton edit;
	private JButton save;
	
	private JButton pay;
	
	private JRadioButton admin;
	private JRadioButton researcher;
	private JRadioButton lecturer;
	
	private ButtonGroup radiogroup;
	
	private JComboBox hours;
	private JButton clockhours;
	private JLabel perflabel;
	private JSlider performance;

	private JPanel mainpanel;
	private JPanel buttonpanel;
	
	private DefaultListModel listmodel;
	
    public GUI() {

        initUI();

    }

    public final void initUI() {

    	/* The JFrame itself uses a border layout */
        setLayout(new BorderLayout());
        
        /* 
         * EAST FRAME CODE
         * The list of employees takes up the larger part of the east
         * frame
         */
        
        JPanel eastpanel = new JPanel();
        eastpanel.setLayout(new BoxLayout(eastpanel, BoxLayout.Y_AXIS));
        eastpanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        eastpanel.setPreferredSize(new Dimension(190, 600));

        
        /* INITIATE JLIST */
        
        
        listmodel = generateListModel();
        employeelist = new JList(listmodel);
        employeelist.setSelectedIndex(-1); //Initialise with nothing selected
        
        employeelist.addListSelectionListener(this);
        
        
        JScrollPane listscrollpane = new JScrollPane(employeelist);
        listscrollpane.setPreferredSize(new Dimension(180, 525));
        
        eastpanel.add(listscrollpane);
        
        
        /* 
         * BUTTON PANEL CODE (including event handlers)
         * 
         * edit, create and save buttons are added beneath the list
         * 
         * this section of code also contains actionlisteners and event
         * handlers for the buttons
         */
        
        buttonpanel = new JPanel();
        buttonpanel.setLayout(new GridLayout(1,3));
        
        edit = new JButton(createImageIcon("edit.png", "edit"));
        buttonpanel.add(edit);
		edit.setEnabled(false);
        /* The edit button toggles edit mode */
        edit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
        		System.out.println("edit");
        		if(namefield.isEditable()) {
        			setEditMode(false);
        		}
        		else { setEditMode(true); }

            }
        });
        
        
        save = new JButton(createImageIcon("save.gif", "save"));
        buttonpanel.add(save);
		save.setEnabled(false);
        save.addActionListener(new ActionListener() {
        	
        	/**
        	 * If save is clicked when an entry is being edited, then it
        	 * will update the employee's informations.
        	 * 
        	 * If save is clicked after a new entry has been made (using
        	 * the create button), then it will add the new entry to the
        	 * hashtable in paymanager.
        	 */
        	public void actionPerformed(ActionEvent event) {
        		
        		System.out.println("save");
        		/* If create has just been clicked */
        		if(employeelist.getSelectedIndex() == -1) {
        			String jobtitle;
        			
        			if (admin.isSelected()) {
        				jobtitle = "Administrator";
        			}
        			else if (researcher.isSelected()) {
        				jobtitle = "Researcher";
        			}
        			else {
        				jobtitle = "Lecturer";
        			}
        			
        			int newid = p.createEmployee(namefield.getText(), 
        										 addressfield.getText(),
        										 telephonefield.getText(),
        										 jobtitle);
        			
        			/* Add the new employee to the end of the list model
        			 * using the standard representation of name + hash
        			 * + id
        			 */
        			listmodel.add(listmodel.size(), 
        						  namefield.getText() + " #" +
        						  String.valueOf(newid));
        			
        			mainpanel.remove(admin);
        			mainpanel.remove(researcher);
        			mainpanel.remove(lecturer);
        			mainpanel.revalidate();
        			mainpanel.repaint();
        			
        			int last = employeelist.getLastVisibleIndex();
        			employeelist.setSelectedIndex(last);
        			//toggleEditMode(); //this is a workaround
        		}
        		
        		/* if we are in edit mode */
        		else {
            		Employee current = getCurrentEmployee();
            		current.setName(namefield.getText());
            		current.setAddress(addressfield.getText());
            		current.setTelephone(telephonefield.getText());
        		}
        		
        		/* save always causes us to leave edit mode */
        		setEditMode(false);
        		
        	}
        });
        
        create = new JButton(createImageIcon("create.png", "create"));
        buttonpanel.add(create);
        create.addActionListener(new ActionListener() {
        	//TODO implement 
            public void actionPerformed(ActionEvent event) {
        		System.out.println("create");
            	clearAllFields();
            	setEditable(false); //clears 
            	employeelist.clearSelection();
            	setEditable(true);
            	
            	edit.setEnabled(false);
            	save.setEnabled(true);
            	create.setEnabled(false);
            	delete.setEnabled(false);
            	
            	pay.setVisible(false);

                mainpanel.add(admin, createConstraints(0,5));
                mainpanel.add(researcher, createConstraints(0,6));
                mainpanel.add(lecturer, createConstraints(0,7));
                mainpanel.revalidate();
    			mainpanel.repaint();

                
                /* Clear selection so the save listener knows create 
                 * has been called.
                 */
                employeelist.clearSelection();
                
            }
        });
        
        delete = new JButton(createImageIcon("delete.png", "delete"));
        buttonpanel.add(delete);
		delete.setEnabled(false);

        delete.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent event) {
        		System.out.println("delete");
        		
            	clearAllFields();

        		/* Takes the String jlist element */
        		String element = (String) employeelist.getSelectedValue();
        		
            	/* Find the index of the hash sign which immediately precedes the id */
            	int hash = element.indexOf('#');
            	
            	/* Cast the String after hash as an int */
            	int id = Integer.parseInt(element.substring(hash + 1));
            	p.removeEmployee(id); //Employee removed from database
            	
            	/*Remove current employee from jlist */
            	listmodel.remove(employeelist.getSelectedIndex());
            	
            	setEditMode(false);
            	
    			edit.setEnabled(false);
    			save.setEnabled(false);
    			delete.setEnabled(false);
        	}
        });
        
        eastpanel.add(buttonpanel); //add the buttonpanel to the eastpanel

        
        add(eastpanel, BorderLayout.EAST);

        /* MAIN PANEL CODE */
        
        mainpanel = new JPanel();
        mainpanel.setLayout(new GridBagLayout());
        mainpanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        
        JLabel name = new JLabel("Name");
        mainpanel.add(name, createConstraints(0, 0));
        namefield = new JTextField(20);
        mainpanel.add(namefield, createConstraints(1, 0));
        
        JLabel address = new JLabel("Address");
        mainpanel.add(address, createConstraints(0, 1));
        addressfield = new JTextField(20);
        mainpanel.add(addressfield, createConstraints(1, 1));

        JLabel telephone = new JLabel("Telephone");
        mainpanel.add(telephone, createConstraints(0, 2));
        telephonefield = new JTextField(20);
        mainpanel.add(telephonefield, createConstraints(1, 2));

        JLabel id = new JLabel("ID"); 
        mainpanel.add(id, createConstraints(0, 3));
        idlabel = new JLabel(); //TODO change to a jlabel
        mainpanel.add(idlabel, createConstraints(1, 3));
        
        JLabel title = new JLabel("Title");
        mainpanel.add(title, createConstraints(0, 4));
        titlelabel = new JLabel();
        mainpanel.add(titlelabel, createConstraints(1, 4));
        

        /* make sure edit mode starts off */
        setEditable(false);
        add(mainpanel, BorderLayout.CENTER);
        
    	/* RADIO BUTTONS FOR SELECTING TYPE OF EMPLOYEE TO CREATE */
        admin = new JRadioButton("Administrator");
        researcher = new JRadioButton("Researcher");
        lecturer = new JRadioButton("Lecturer");

        radiogroup = new ButtonGroup();
        radiogroup.add(admin);
        radiogroup.add(researcher);
        radiogroup.add(lecturer); 
        admin.setSelected(true);
        
        /* PAY BUTTON FOR WHEN EMPLOYEE IS BEING VIEWED */
        
        pay = new JButton("Pay");
    	mainpanel.add(pay, createConstraints(0, 8));
        /* pay is disabled at startup as no employee is selected */
        pay.setVisible(false);  
        
        pay.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent Event) {
        		Employee current = getCurrentEmployee();
        		String message = current.getName() + " is due pounds" + current.getPay();
        		JOptionPane.showMessageDialog(mainpanel, message);
        		
        		
        	}
        });
        
        /*
         *  COMBOBOX, BUTTON AND SLIDER FOR CLOCKING HOURS AND RATING PERF
         * IN THE MAINPANEL
         * 
         */
        Integer[] numbers = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
        
        hours = new JComboBox();
        hours.setVisible(false); //visible only in edit mode
        
        for (int i = 0; i < 12; i++) {
        	hours.addItem(numbers[i]);
        }
        mainpanel.add(hours, createConstraints(2, 7));

        clockhours = new JButton("Clock Hours");
        clockhours.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent Event) {
        		String message;
        		
        		if (isLecturer()) {
        			Lecturer current = (Lecturer) getCurrentEmployee();
        			current.clockHours((Integer) hours.getSelectedItem());
            		message = current.getName() + " has now clocked " + 
            				  current.getConsultancyHours() + " hours since last reset.";

        		}
        		else {
        			Administrator current = (Administrator) getCurrentEmployee();
        			current.clockHours((Integer) hours.getSelectedItem());
            		message = current.getName() + " has now clocked " + 
            				  current.getOvertime() + " hours since last reset.";
        		}
        		
        		JOptionPane.showMessageDialog(mainpanel, message);

        	}
        });
        clockhours.setVisible(false); //visible only in edit mode
        mainpanel.add(clockhours, createConstraints(3, 7));
        
        perflabel = new JLabel("Performance");
        perflabel.setVisible(false);
        mainpanel.add(perflabel, createConstraints(0, 7));
        
        performance = new JSlider(JSlider.HORIZONTAL, 0, 5, 0);
        performance.setMajorTickSpacing(5);
        performance.setMinorTickSpacing(1);
        performance.setPaintTicks(true);
        performance.setPaintLabels(true);
        performance.setVisible(false); //starts invisible, visible during edit mode
        performance.addChangeListener(new ChangeListener() {
        	public void stateChanged(ChangeEvent Event) {
        		int value = performance.getValue();
        		Lecturer current = (Lecturer) getCurrentEmployee();
        		current.setPerformance(value);
        	}
        });
		mainpanel.add(performance, createConstraints(1, 7));
        
        /* JFRAME SETTINGS */
        
        setTitle("Staff Payment");
        setSize(800, 600);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

    }

    public static void main(String[] args) {
        
        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                GUI gui = new GUI();
            }
        });
        
        p = new PayManager();
        
    }
    
    /**
     * This method provides gridbagconstraints which aligns the
     * component west if it's y coordinate is 0, otherwise east
     * 
     * @author StackOverflow user "Hovercraft full of eels"
     * @param x cell location
     * @param y cell location
     * @return the constraints for desired cell location
     */
    private GridBagConstraints createConstraints(int x, int y) {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.ipadx = 10;
        constraints.ipady = 10;
        constraints.insets = new Insets(10,10,10,10);
        constraints.gridx = x;
        constraints.gridy = y;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.anchor = (x == 0) ? GridBagConstraints.WEST : GridBagConstraints.EAST;
        constraints.fill = (x == 0) ? GridBagConstraints.BOTH
              : GridBagConstraints.HORIZONTAL;

        return constraints;
     }
    
    /** 
     * Returns an ImageIcon, or null if the path was invalid. 
     * 
     * code from http://docs.oracle.com/javase/tutorial/uiswing/components/icon.html 
     */
    private ImageIcon createImageIcon(String path, String description) {
        URL imgURL = getClass().getResource(path);
        
        if (imgURL != null) {
            return new ImageIcon(imgURL, description);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }
    
    /**
     * Gets the list of employees from the paymanager and returns
     * a DefaultListModel with their fullname, so that it can be
     * shown in the JList. Used to initialise the GUI
     * 
     * @param     listmodel
     * @return    the refreshed listmodel
     */
    private DefaultListModel generateListModel() {
    	DefaultListModel listmodel = new DefaultListModel();
    	Hashtable<Integer, Employee> employees = p.getRoster();
    	
    	for(int i = 1; i < employees.size() + 1; i++) {
    		
    		/* The name and employee number of each employee are added
    		 * to the list
    		 */
    		listmodel.addElement(employees.get(i).getName() + " #" +
    							 String.valueOf
    							 (employees.get(i).getEmployeeNum()));
    		
    	}
    	return listmodel;
    }
    
    /**
     * Gets the data for the selected employee and fills it into the fields
     */
    private void showInfo() {
    	
    	Employee selected = getCurrentEmployee();
    	
    	/* Sets the text fields */
    	namefield.setText(selected.getName());
    	addressfield.setText(selected.getAddress());
    	telephonefield.setText(selected.getTelephone());
    	idlabel.setText(String.valueOf(selected.getEmployeeNum()));
    	titlelabel.setText(String.valueOf(selected.getTitle()));
    	
    	
    }
    
    /**
     * Provides the show info method with the id of the member of staff
     * 
     * @param    element The String value of the list element
     * @return   the employee from the id which is appended to the end 
     * 			 of the element
     */
    private Employee getCurrentEmployee() {
    	
    	String element = (String) employeelist.getSelectedValue();
    	/* Find the index of the hash sign which immediately precedes the id */
    	int hash = element.indexOf('#');
    	
    	/* Cast the String after hash as an int */
    	int id = Integer.parseInt(element.substring(hash + 1));
    	
    	
    	/* Gets the hashtable and gets the employee from it */
    	Hashtable<Integer, Employee> employeetable = p.getRoster();
    	Employee selected = employeetable.get(id);
    	
    	return selected;
    }
    
    /**
     * Sets the editability of the three textfields
     * @param edit true if you want to edit the textfields
     */
    private void setEditable(boolean edit) {
    	namefield.setEditable(edit);
    	addressfield.setEditable(edit);
    	telephonefield.setEditable(edit);

    }
    
    /**
     * clears the three JTextFields
     */
    private void clearAllFields() {
    	namefield.setText(null);
    	addressfield.setText(null);
    	telephonefield.setText(null);
    	idlabel.setText(null);
    	titlelabel.setText(null);
    }

    /**
     * In edit mode, employee information can be changed via the fields, and
     * the user may also delete the employee. The edit button will get you out
     * of edit mode without saving changed, whilst the save button leaves edit
     * mode and saves the changes
     * 
     * @param edit True if you want to turn on edit mode.
     */
    private void setEditMode(boolean edit) {
        if(edit == false) {
        	setEditable(false);
        	save.setEnabled(false);
        	create.setEnabled(true);
        	delete.setEnabled(false);
        	
        	pay.setVisible(true);
        	
		/* after a delete the jlist will have none selected, so we cannot use
         * isLecturer() etc. We must make sure there are no component left
         * around from the previous employee that was deleted
         */
            if(employeelist.getSelectedIndex() == -1) {
                perflabel.setVisible(false);
                performance.setVisible(false); 
                hours.setVisible(false);
                clockhours.setVisible(false);
                pay.setVisible(false);
            }

            else {
            	if (isLecturer()) {
            		perflabel.setVisible(true);
            		performance.setVisible(true);
            	}
            	else {
            		perflabel.setVisible(false);
            		performance.setVisible(false);
            	}
            	
            	if (!isResearcher()) {
            		hours.setVisible(true);
            		clockhours.setVisible(true);
            	}
            	else {
            		hours.setVisible(false);
            		clockhours.setVisible(false);
            	}
            }
        }
        else {
        	setEditable(true);
        	save.setEnabled(true);
        	delete.setEnabled(true);
        	create.setEnabled(false);
        	
        	pay.setVisible(false);
        	
    		perflabel.setVisible(false);
        	performance.setVisible(false);
        	hours.setVisible(false);
        	clockhours.setVisible(false);
        }
        
    }
    
    /**
     * This is used to determine if the selected element is an administrator
     * or a lecturer, if it is, then the clock hours interface must be visible
     * 
     * @return true if the employee selected is an admin or lecturer
     */
    private boolean isClockable() {
    	Employee current = getCurrentEmployee();
    	boolean clockable = (current.getTitle() == "Researcher") ? false : true;
    	System.out.println("clockable =" + clockable);
    	return clockable;
    }
    
    /**
     * Tells you wheter the employee currently selected in the JList is
     * a lecturer.
     * 
     * @return true if the employee is a lecturer
     */
    private boolean isLecturer() {
    	Employee current = getCurrentEmployee();
    	if (current.getTitle().equals("Lecturer")) {
    		return true;
    		}
    	else { return false; }
    }
    
    /**
     * Tells you wheter the employee currently selected in the JList is
     * a researcher.
     * 
     * @return true if the employee is a researcher
     */
    private boolean isResearcher() {
    	Employee current = getCurrentEmployee();
    	if (current.getTitle().equals("Researcher")) {
    		return true;
    		}
    	else { return false; }
    }
    
    /**
     * If the value changes to a number other than -1, an employee will be
     * selected from the JList and its info will be shown.
     */
    public void valueChanged(ListSelectionEvent e) {
    	if(!e.getValueIsAdjusting()) {
    		if(employeelist.getSelectedIndex() == -1) {

    		}
    		else {
        		setEditMode(false); //IMPORTANT
    			edit.setEnabled(true);
    			save.setEnabled(false);
    			create.setEnabled(true);
    			delete.setEnabled(false);
    			
    			isClockable();
    			
    			pay.setVisible(true);
    			//TODO remove scaffolding
	        	String f = (String) employeelist.getSelectedValue();
	        	System.out.println(f);	
	        	
	        	showInfo();
    		}
    	}

    }
}

