
/**
 * @author Alex Remedios
 * 
 * This abstract class is a root which is extended by Administrator,
 * Reasearcher and Lecturer, its constructer sets the attributes which
 * handle the personal information and its methods allow this information
 * to be manipulated.
 */
public abstract class Employee {
	
	protected String name;
	protected String address;
	protected String telephone;
	protected final String title;
	protected final int employeenum;
	
	/**
	 * The constructor is protected - it can only be called by its subclasses
	 * 
	 * @param name
	 * @param address
	 * @param telephone
	 * @param employeenum - each employee has an id supplied by the paymanager
	 * 						when they are signed up
	 */
	protected Employee(String name, String address,
					   String telephone, String title,
					   int employeenum) {
		
		this.name = name;
		this.address = address;
		this.telephone = telephone;
		this.title = title;
		this.employeenum = employeenum;
		
	}
	
	/**
	 * Sets the full name of the employee
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Returns the name of the employee
	 * @return
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Sets the address of the employee
	 * @param address
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	
	/**
	 * Gets the address of the employee
	 * @return
	 */
	public String getAddress() {
		return address;
	}
	
	/**
	 * Returns the telphone number of the employee
	 * @param telephone
	 */
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	
	/**
	 * Returns the telephone number of the employee
	 * @return
	 */
	public String getTelephone() {
		return telephone;
	}

	/**
	 * Title is set by subclasses, and whilst not necessary for payment,
	 * may be useful for the operator.
	 * 
	 * @return
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * employeenum must be accessible so that an employee can be looked
	 * up once selected in the list in the GUI
	 * 
	 * @return  The unique id of the employee
	 */
	public int getEmployeeNum() {
		return employeenum;
	}
	
	/**
	 * Overridden by each subclass
	 * 
	 * @return - wage for this month
	 */
	abstract int getPay();
}
