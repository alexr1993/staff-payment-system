
import java.util.Hashtable;

/**
 * 
 * @author Alex
 *
 */
public final class PayManager {

	/**
	 * Paymanager is responsible for creating, adding, removing, and reporting
	 * monthly payments for employees, which are stored in an arraylist.
	 * 
	 */
	
	private Hashtable<Integer, Employee> roster;
	private int id;

	public PayManager() {
		roster = new Hashtable<Integer, Employee>();
		id = 1;
		
		/* sample entries */
		createEmployee("Samuel", "Example Road", "675634563", "Lecturer");
		createEmployee("Xavier", "Example Road", "578975875", "Lecturer");
		createEmployee("Edward Norton", "Example Road", "6657564767", "Lecturer");
		createEmployee("Alan Hayes", "Example Road", "4546543977", "Lecturer");
		createEmployee("Magneto", "Example Road", "6747645765745", "Lecturer");
		createEmployee("Steve Jobs", "Example Road", "345634563546", "Lecturer");
		createEmployee("Harry Potter", "Example Road", "345643563456", "Lecturer");
		createEmployee("James Bond", "Example Road", "34563645", "Lecturer");
		createEmployee("Kate from lost", "Example Road", "235425", "Lecturer");
		createEmployee("Amy", "Example Road", "23542456", "Lecturer");
		createEmployee("Harold Crick", "Example Road", "34564767", "Lecturer");
		createEmployee("Peaches Geldof", "Example Road", "354673567547", "Lecturer");
		createEmployee("Hillary Duff", "Example Road", "564745675346", "Lecturer");
		createEmployee("Justin Bieber", "Example Road", "345634575647", "Lecturer");
		createEmployee("Hannah Montana", "Example Road", "23544254", "Lecturer");
		createEmployee("Professor McGonagall", "Example Road", "56756245", "Lecturer");
		createEmployee("Imaginative Name", "Example Road", "2345346257653", "Lecturer");
		createEmployee("Senior Lecturer A", "Example Road", "32454363435", "Lecturer");
		createEmployee("Sr Lecturer B", "Example Road", "896058776", "Lecturer");
		createEmployee("Derp Herpster", "Example Road", "78095768653", "Researcher");
		createEmployee("Herpy McDerpity", "Example Road", "794674567", "Researcher");
		createEmployee("Donald Duck", "Example Road", "3567645745763", "Researcher");
		createEmployee("Donald Trump", "Example Road", "56474568748", "Researcher");
		createEmployee("Donald Faison", "Example Road", "46784768478", "Researcher");
		createEmployee("Arne Kittlaus", "Example Road", "56735345643", "Researcher");
		createEmployee("Timmy", "Example Road", "356756363463", "Researcher");
		createEmployee("Jimmy", "Example Road", "354635465346", "Researcher");
		createEmployee("Timbo", "Example Road", "245235446546", "Researcher");
		createEmployee("Jimbo", "Example Road", "5647364356", "Researcher");
		createEmployee("Hannah Anna", "Example Road", "76856634563456", "Administrator");
		createEmployee("Jake", "Example Road", "54654365436", "Administrator");
		createEmployee("Amir", "Example Road", "5436345654", "Administrator");
		createEmployee("T Rex", "Example Road", "456345635464", "Administrator");
		createEmployee("Elton John", "Example Road", "345634564", "Administrator");
		createEmployee("Sting", "Example Road", "345653465367", "Administrator");
		createEmployee("Seal", "Example Road", "2534235345435", "Administrator");
		createEmployee("Dolphin", "Example Road", "2456425345", "Administrator");
		createEmployee("Beluga Whale", "Example Road", "25423453452", "Administrator");
		createEmployee("John Doe", "Example Road", "23545225", "Administrator");


		
		
	}
	
	public Hashtable<Integer, Employee> getRoster() {
		return roster;
	}

	/**
	 * creates an employee and adds it to the roster
	 * 
	 * @param title - The title is used in the conditional to determine
	 * 				  what kind of employee is being created.
	 * 
	 * @return id - The id of the employee which has just been created so
	 * 				the gui can append it to the name in the JList
	 */
	public int createEmployee(String name, String address,
							   String telephone, String title) {
		
		/* Conditional statement uses the last parameter of this method
		 * to determine what kind of employee to create
		 */
		
		if (title.equals("Administrator")) {
			roster.put(id, new Administrator(name, address, telephone, id));
		}
		
		else if (title.equals("Researcher")) {
			roster.put(id, new Researcher(name, address, telephone, id));
			
		}
		
		else if (title.equals("Lecturer")) {
			roster.put(id, new Lecturer(name, address, telephone, id));
		}
		
		id++; //increment id so that it serves as a primary key
		
		
		return id - 1;
	}
	
	public void removeEmployee(int id) {
		
		roster.remove(id);
	}
}
