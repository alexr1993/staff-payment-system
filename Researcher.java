
public class Researcher extends Employee {
	
	private static final int SALARY = 10000;
	
	public Researcher(String name, String address, String telephone,
			int employeenum) {

		super(name, address, telephone, "Researcher", employeenum);		
	}
	
	int getPay() {
		return SALARY / 12;
	}
}
