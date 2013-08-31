
public class Lecturer extends Employee {

	private int performance;
	private int consultancyhours;
	private static final int SALARY = 20000;
	private static final int consultancyrate = 20;
	
	public Lecturer(String name, String address, String telephone,
			int employeenum) {

		super(name, address, telephone, "Lecturer", employeenum);	
		performance = 0;
	}
	
	public void clockHours(int hours) {
		consultancyhours += hours;
	}
	
	public int getHours() {
		return consultancyhours;
	}
	
	public void resetConsultancyHours() {
		consultancyhours = 0;
	}
	
	public int getConsultancyHours() {
		return consultancyhours;
	}
	
	public void setPerformance(int perf) {
		performance = perf;
	}
	
	public int getPerformance() {
		return performance;
	}

	@Override
	/**
	 * Lecturer pay is currently done monthly, with performance being 1000 times
	 * the performance slider value
	 * 
	 */
	int getPay() {
		return (SALARY / 12) + (consultancyhours * consultancyrate)
				+ (performance * 1000/ 12);
	}
}
