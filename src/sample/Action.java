package sample;

public class Action 
{
	String actionId,
	jobAllowance,
	employeeAllowance,
	notDevelopedRegionAllowance,
	badWeatherRegionAllowance,
	familyHelp,
	childrenHelp,
	jobDifficultnessAllowance,
	jobSpecial,
	salary,
	issueDate,
	jobClass,
	jobrank;
	
	public Action()
	{
		super();
	}

	public Action(String actionId, String jobAllowance, String employeeAllowance, String notDevelopedRegionAllowance,
			String badWeatherRegionAllowance, String familyHelp, String childrenHelp, String jobDifficultnessAllowance,
			String jobSpecial, String salary, String issueDate, String jobClass, String jobrank) 
	{
		super();
		this.actionId = actionId;
		this.jobAllowance = jobAllowance;
		this.employeeAllowance = employeeAllowance;
		this.notDevelopedRegionAllowance = notDevelopedRegionAllowance;
		this.badWeatherRegionAllowance = badWeatherRegionAllowance;
		this.familyHelp = familyHelp;
		this.childrenHelp = childrenHelp;
		this.jobDifficultnessAllowance = jobDifficultnessAllowance;
		this.jobSpecial = jobSpecial;
		this.salary = salary;
		this.issueDate = issueDate;
		this.jobClass = jobClass;
		this.jobrank = jobrank;
	}
	
	public String getActionId() 
	{
		return actionId;
	}

	public void setActionId(String actionId) 
	{
		this.actionId = actionId;
	}

	public String getJobAllowance() 
	{
		return jobAllowance;
	}

	public void setJobAllowance(String jobAllowance) 
	{
		this.jobAllowance = jobAllowance;
	}

	public String getEmployeeAllowance() 
	{
		return employeeAllowance;
	}

	public void setEmployeeAllowance(String employeeAllowance) 
	{
		this.employeeAllowance = employeeAllowance;
	}

	public String getNotDevelopedRegionAllowance() 
	{
		return notDevelopedRegionAllowance;
	}

	public void setNotDevelopedRegionAllowance(String notDevelopedRegionAllowance) 
	{
		this.notDevelopedRegionAllowance = notDevelopedRegionAllowance;
	}

	public String getBadWeatherRegionAllowance() 
	{
		return badWeatherRegionAllowance;
	}

	public void setBadWeatherRegionAllowance(String badWeatherRegionAllowance) 
	{
		this.badWeatherRegionAllowance = badWeatherRegionAllowance;
	}

	public String getFamilyHelp() 
	{
		return familyHelp;
	}

	public void setFamilyHelp(String familyHelp) 
	{
		this.familyHelp = familyHelp;
	}

	public String getChildrenHelp() 
	{
		return childrenHelp;
	}

	public void setChildrenHelp(String childrenHelp) 
	{
		this.childrenHelp = childrenHelp;
	}

	public String getJobDifficultnessAllowance() 
	{
		return jobDifficultnessAllowance;
	}

	public void setJobDifficultnessAllowance(String jobDifficultnessAllowance) 
	{
		this.jobDifficultnessAllowance = jobDifficultnessAllowance;
	}

	public String getJobSpecial() {
		return jobSpecial;
	}

	public void setJobSpecial(String jobSpecial) 
	{
		this.jobSpecial = jobSpecial;
	}

	public String getSalary() 
	{
		return salary;
	}

	public void setSalary(String salary) 
	{
		this.salary = salary;
	}

	public String getIssueDate() 
	{
		return issueDate;
	}

	public void setIssueDate(String issueDate) 
	{
		this.issueDate = issueDate;
	}

	public String getJobClass() {
		return jobClass;
	}

	public void setJobClass(String jobClass) 
	{
		this.jobClass = jobClass;
	}

	public String getJobrank() {
		return jobrank;
	}

	public void setJobrank(String jobrank) 
	{
		this.jobrank = jobrank;
	}


	
	
}
