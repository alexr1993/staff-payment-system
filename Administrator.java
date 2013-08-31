
public class Administrator extends Employee {
	private int overtime;
	private static final int SALARY = 15000;
	private static final int overtimerate = 10;
	
	public Administrator(String name, String address, String telephone,
						int employeenum) {
		
		super(name, address, telephone, "Administrator", employeenum);		
	}
	
	/**
	 * 
	 * @param hours - increments their overtime by the number of hours
	 * 				  have just worked so that getPay() calls will take
	 * 				  into account all the hours they have worked since
	 * 				  last reset
	 */
	public void clockHours(int hours) {
		overtime += hours;
	}
	
	public int getOvertime() {
		return overtime;
	}
	
	public void resetOvertime() {
		overtime = 0;
	}
	
	@Override
	int getPay() {
		return (SALARY / 12) + (overtimerate * overtime);
	}
	
}
