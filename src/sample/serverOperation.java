package sample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class serverOperation implements Runnable
{
    private Connection connection;
    
    //** Change the following variables into your own desired MySQL Server Properties            **//
    //** Do not change the character encoding otherwise the program might not function Properly  **//
	public final static String databaseConfig = "?useUnicode=yes&characterEncoding=UTF-8";
    public final static String databaseName = "171.22.26.135/svopir_temp";
    public final static String databaseUsername = "svopir_application";
    public final static String databasePassword = "QdSh14Mt";

	//public final static String databaseName = "localhost/svopir_temp";
	//public final static String databaseUsername = "root";
    //public final static String databasePassword = "";

    public static String URL = "jdbc:mysql://"+databaseName + databaseConfig;

	@Override
	public void run() {
		try {
			connection = DriverManager.getConnection("jdbc:mysql://"+databaseName+databaseConfig,databaseUsername,databasePassword);
			System.out.println("Connection to database Successfully created!");
		} catch (Exception e){
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}

    /*** Constructor : Loads MySQL Driver ***/
    public serverOperation() throws SQLException,ClassNotFoundException
    {   
        Class.forName("com.mysql.cj.jdbc.Driver");
        System.out.println("MySQL Driver Succesfully Loaded!");
    }

    public void createConnection() throws SQLException /* Creates new Database Connection */
    {
        connection = DriverManager.getConnection("jdbc:mysql://"+databaseName + databaseConfig, databaseUsername, databasePassword);
        System.out.println("Connection to database Successfully created!");   
    }
    
    public void closeConnection() throws SQLException /* Closes current database Connection */
    {
        connection.close();
        System.out.println("Connection closed!");   
    }
    
    public boolean dateValidate(String dateString) /* Checks if Input string is in yyyy-mm-dd Format */
    {                                              /* Using regular Expression */
    	String regex = "[\\d]{4}[-][\\d]{2}[-][\\d]{2}";
    	return dateString.matches(regex);
    }
    
    public String getNumberofTeachers() throws SQLException  /* Returns current number of teachers */
    {                                                        /* From Database */
         	Statement st = connection.createStatement();
     		ResultSet resultset = st.executeQuery("select count(*) from teachers;");
     		resultset.next();
     		return resultset.getString(1);
    }
    														
    /** Retrieves the number of Teachers in the database  **/
    /** And generates unique personId which will be PRIMAY KEY in database **/
    public String teacherPersonIdGenerate() throws SQLException
    {
		ArrayList<String> numbers=new ArrayList<>();
		String result="";
		String currentYear= getYear();
		String teacherCode= "002";                       /* Code for Teachers is 002 */
		String numberOfTeachers= getNumberofTeachers();  /* It is 001 for Admins     */

		int B=Integer.parseInt(numberOfTeachers)+1;

		int NoZeroes=6-Integer.toString(B).length();
		String s=Integer.toString(B);

		numbers.add(currentYear);
		numbers.add(teacherCode);

		for(int i=0; i<NoZeroes; i++)
			numbers.add("0");

		numbers.add(s);

		for(int i=0; i<numbers.size(); i++)
			result+=numbers.get(i);

		return result;
     }
    
    public String getYear() throws SQLException /* Retrieves current year from database (yy)*/
    {        									/* example = 98 */
        	Statement st = connection.createStatement();
    		ResultSet resultset = st.executeQuery("select * from time;");
    		resultset.next();
    		return resultset.getString(1).substring(2,4);
    }
    public String getDay() throws SQLException      /* Retrieves current day from database (mm)*/
    {                                               /* example = 31 */
        	Statement st = connection.createStatement();
    		ResultSet resultset = st.executeQuery("select * from time;");
    		resultset.next();
    		return resultset.getString(1).substring(8,10);
    }
    public String getMonth() throws SQLException      /* Retrieves current Month from database (dd)*/
    {                                                 /* example = 07 */
        	Statement st = connection.createStatement();
    		ResultSet resultset = st.executeQuery("select * from time;");
    		resultset.next();
    		return resultset.getString(1).substring(5,7); 
    }
    
    public String getCurrentTime() throws SQLException   /* Retrieves current Time from database (yyyy-mm-dd)*/
    {
        	Statement st = connection.createStatement();
    		ResultSet resultset = st.executeQuery("select * from time;");
    		resultset.next();
    		return resultset.getString(1); 
    }
    
    public void updateMinorInformation(String personId,String firstName,String lastName, String fatherName, String nationalId,String password) throws SQLException
    {
    	Statement st = connection.createStatement();
    	String query = String.format("update teachers set firstName='%s', "
				+ "lastName='%s', fatherName='%s', nationalId='%s', "
				+ "password='%s' where personId='%s';",firstName,lastName,fatherName,nationalId,password,personId);
		st.executeUpdate(query);
    }

    public boolean Authenticate(String personId, String Password) throws SQLException
    {
        	Statement st = connection.createStatement();
    		ResultSet resultset = st.executeQuery(String.format("select count(*) from teachers "
    				+ "where personId='%s';", personId));
    		resultset.next();
    		String returnedEmployeeResult = resultset.getString(1);
    		
    		
    		if(returnedEmployeeResult.contentEquals("0")) /* There is NO user with that personId */
    		{
    			System.out.println("User Not found!");
    			return false;
    		}
    		else if(returnedEmployeeResult.contentEquals("1"))
    		{
    			ResultSet loginResult = st.executeQuery(String.format("select password from teachers where personId='%s' ", personId));
    			loginResult.next();
    			String passwordRetrieved = loginResult.getString(1);
    			
    			if(passwordRetrieved.contentEquals(Password)) /* Users do exist Now Check if passes MATCH */
        		{
    				System.out.println("Access Granted!");
        			return true;
        		}
    			else
    			{
    				System.out.println("Password is wrong!");
    				return false;
    			}
    		}
    	return false;
    }
    
    public boolean AuthenticateAdmin(String personId, String Password) throws SQLException
    {
        	Statement st = connection.createStatement();
    		ResultSet resultset = st.executeQuery(String.format("select count(*) from admins "
    				+ "where personId='%s';", personId));
    		resultset.next();
    		String returnedEmployeeResult = resultset.getString(1);
    		
    		if(returnedEmployeeResult.contentEquals("0") ) /* There is NO user with that personId */
    		{
    			System.out.println("User Not found!");
    			return false;
    		}
    		else if(returnedEmployeeResult.contentEquals("1"))
    		{
    			ResultSet loginResult = st.executeQuery(String.format("select password from admins where personId='%s' ", personId));
    			loginResult.next();
    			String passwordRetrieved = loginResult.getString(1);
    			
    			if(passwordRetrieved.contentEquals(Password)) /* Users do exist Now Check if passes MATCH */
        		{
    				System.out.println("Access Granted!");
        			return true;
        		}
    			else
    			{
    				System.out.println("Password is wrong!");
    				return false;
    			}
    		}
    	return false;
    }
    
    public ObservableList<String> getProvinces() throws SQLException
    {
    	ObservableList<String> provinceNames = FXCollections.observableArrayList();
    	Statement st = connection.createStatement();
    	String query = "select name from provinces;";
 		ResultSet resultset = st.executeQuery(query);
 		while(resultset.next())
 		{
 			provinceNames.add(resultset.getString(1));
 		}

    	return provinceNames;
    }

    //returns persons Id
    public ObservableList<String> getEmployees() throws SQLException
    {
    	ObservableList<String> personIds = FXCollections.observableArrayList();
    	Statement st = connection.createStatement();
    	String query = "select personId from teachers;";
 		ResultSet resultset = st.executeQuery(query);
 		while(resultset.next())
 		{
 			personIds.add(resultset.getString(1));
 		}

    	return personIds;
    }
    
    public ObservableList<String> getCities(String province) throws SQLException
    {
    	ObservableList<String> cityNames = FXCollections.observableArrayList();
    	
    	Statement st = connection.createStatement();
    	String query = String.format("select provinceId from provinces where name='%s';", province);
 		ResultSet resultset = st.executeQuery(query);
 		resultset.next();
 		String id = resultset.getString(1);
 		query = String.format("select name from cities where inProvince='%s';", id);
 		resultset = st.executeQuery(query);
 		
 		while(resultset.next())
 		{
 			cityNames.add(resultset.getString(1));
 		}

    	return cityNames;
    }
    
    public ResultSet getTeacherData(String personId) throws SQLException
    {
    	Statement st = connection.createStatement();
    	String query = String.format("select * from teachers where personId='%s';", personId);
 		ResultSet resultset = st.executeQuery(query);
 		resultset.next();
 		
 		return resultset;
    }
    
    public String getTeacherCity(String personId) throws SQLException
    {
    	Statement st = connection.createStatement();
    	String query = String.format("select city from teachers where personId='%s';", personId);
 		ResultSet resultset = st.executeQuery(query);
 		resultset.next();
 		String cityNumber = resultset.getString(1);
 		query = String.format("select name from cities where cityId='%s';", cityNumber);
 		resultset = st.executeQuery(query);
 		resultset.next();
 		
 		return resultset.getString(1);
    }

	public String getTeacherName(String personId) throws SQLException
	{
		Statement st = connection.createStatement();
		String query = String.format("select firstName, lastName from teachers where personId='%s';", personId);
		ResultSet resultset = st.executeQuery(query);
		resultset.next();
		String name = resultset.getString("firstName");
		String family = resultset.getString("lastName");

		return name + " " + family;
	}
    
    public String getTeacherCityId(String personId) throws SQLException
    {
    	Statement st = connection.createStatement();
    	String query = String.format("select city from teachers where personId='%s';", personId);
 		ResultSet resultset = st.executeQuery(query);
 		resultset.next();
 		
 		return resultset.getString(1);
    }
    
    public boolean isNumber(String string)
    {
    	String ENregex = "[\\d]+";
		String FAregex = "[۱۲۳۴۵۶۷۸۹۰]+";
		
		return (string.matches(ENregex) || string.matches(FAregex) );
    }
    
    public boolean isPersian(String string)
    {
    	String regex = "[ابپتثجچحخدذرزژسشصضطظعغفقکگلمنوهی ]+";
    	return string.trim().matches(regex);
    }
    
    private String getCityIdByName(String name) throws SQLException
    {
    	Statement st = connection.createStatement();
    	String query = String.format("select cityId from cities where name='%s';", name);
 		ResultSet resultset = st.executeQuery(query);
 		resultset.next();
 		return resultset.getString(1);
    }
    
    public String createUser(String firstName,String lastName,String fatherName,String numberOfChildren,
    String nationalId,String phone,String birth,String married,String gender,
    String education,String city,String password) throws SQLException
    {
    	String personId = teacherPersonIdGenerate();
    	String registrationDate = getCurrentTime();
    	String cityId = getCityIdByName(city);
    	String query = "INSERT INTO teachers "
    			+ "(personId, nationalId, firstName, lastName, fatherName, "
    			+ "jobTitle, birth, gender, married, numberOfChildren, "
    			+ "registrationDate, education, city, password, courseHours,"
    			+ " phone) VALUES "
    			+ "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
    	
    	PreparedStatement st = connection.prepareStatement(query,  Statement.RETURN_GENERATED_KEYS);
    	st.setString(1, personId);
    	st.setString(2, nationalId);
    	st.setString(3, firstName);
    	st.setString(4, lastName);
    	st.setString(5, fatherName);
    	st.setString(6,  "دبیر");
    	st.setString(7, birth);
    	st.setString(8, gender);
    	st.setString(9, married);
    	st.setString(10, numberOfChildren);
    	st.setString(11, registrationDate);
    	st.setString(12, education);
    	st.setString(13, cityId);
    	st.setString(14, password);
    	st.setString(15, "0");
    	st.setString(16, phone);
    	
    	st.executeUpdate();
    	//Testing Purpose
    	createAction(personId,registrationDate);
    	//Testing Purpose/

    	return personId;
    	
    }
    
    public int getWorkExperience(String personId) throws SQLException /* Returns the number of years the user with*/
    {													                    /* the given person Id has worked           */
    	Statement st = connection.createStatement();
		ResultSet resultset = st.executeQuery("select * from time;");
		resultset.next();
		
		int currentYear = Integer.parseInt(resultset.getString(1).substring(0,4));
		
		String query = String.format("select registrationDate from teachers where personId='%s'", personId);
		resultset = st.executeQuery(query);
		resultset.next();
		
		int registrationDate = Integer.parseInt(resultset.getString(1).substring(0,4));
		
    	return (currentYear-registrationDate);
    }
    
    public String getJobClass(String personId) throws SQLException
    {
    	Statement st = connection.createStatement();
    	String query = String.format("select education from teachers where personId='%s';", personId);
		ResultSet resultset = st.executeQuery(query);
		resultset.next();
		String education = resultset.getString(1);
		
		int jobclass = 0;
		
		int yearsExperience = Math.min(25, getWorkExperience(personId) );
		
		switch(education)
		{
		case "دیپلم":
			jobclass+=3;
			yearsExperience = (int) (yearsExperience/5);
			jobclass+=yearsExperience;
			break;
		case "فوق دیپلم":
			jobclass+=4;
			yearsExperience = (int) (yearsExperience/5);
			jobclass+=yearsExperience;
			break;
		case "لیسانس":
			jobclass+=5;
			yearsExperience = (int) (yearsExperience/4);
			jobclass+=yearsExperience;
			break;
		case "فوق لیسانس":
			jobclass+=6;
			yearsExperience = (int) (yearsExperience/4);
			jobclass+=yearsExperience;
			break;
		case "دکتری":
			jobclass+=7;
			yearsExperience = (int) (yearsExperience/4);
			jobclass+=yearsExperience;
			break;
		}
		return String.format("%s", jobclass);
    }
    
    public String getJobRank(String personId) throws SQLException
    {
    	Statement st = connection.createStatement();
    	String query = String.format("select education from teachers where personId='%s';", personId);
		ResultSet resultset = st.executeQuery(query);
		resultset.next();
		String education = resultset.getString(1);
		
    	int yearsExperience = getWorkExperience(personId);
    	
    	if(education.contentEquals("دیپلم") || education.contentEquals("فوق دیپلم"))
    	{
	    	if(yearsExperience < 8)
	    	{
	    		return "مقدماتی";
	    	}
	    	else if(yearsExperience < 20)
	    	{
	    		return "پایه";
	    	}
	    	else
	    	{
	    		return "ارشد";
	    	}
    	}
	    	else
	    	{
	    	if(yearsExperience < 6)
	    	{
	    		return "مقدماتی";
	    	}
	    	else if(yearsExperience < 12)
	    	{
	    		return "پایه";
	    	}
	    	else if(yearsExperience < 18)
	    	{
	    		return "ارشد";
	    	}
	    	else if(yearsExperience < 24)
	    	{
	    		return "خبره";
	    	}
	    	else
	    	{
	    		return "عالی";
	    	}
    	}
    }
    
    public ObservableList<Action> getActions(String personId) throws SQLException
    {
    	ObservableList<Action> actionList = FXCollections.observableArrayList();
    	
    	Statement st = connection.createStatement();
    	String query = String.format("select * from employmentAction where owner='%s';", personId);
		ResultSet resultset = st.executeQuery(query);
		while(resultset.next())
		{
			actionList.add(new Action(resultset.getString(2),resultset.getString(3),resultset.getString(4),
					resultset.getString(5),resultset.getString(6),resultset.getString(7),
					resultset.getString(8),resultset.getString(9),resultset.getString(10),
					resultset.getString(11),resultset.getString(12),resultset.getString(13),
					resultset.getString(14)));
		}
		
    	return actionList;
    }
    
    //money
    public int getJobAllowance(String personId) throws SQLException
    {
    	String jobRank = getJobRank(personId);
    	String jobClass = getJobClass(personId);
    	String columnInDatabase = "";
    	
    	switch(jobRank)
		{
		case "مقدماتی":
			columnInDatabase = "starterRank";
			break;
		case "پایه":
			columnInDatabase = "basicRank";
			break;
		case "ارشد":
			columnInDatabase = "seniorRank";
			break;
		case "خبره":
			columnInDatabase = "expertRank";
			break;
		case "عالی":
			columnInDatabase = "superiorRank";
			break;
		}
    	
    	Statement st = connection.createStatement();
    	String query = String.format("select %s from jobAllowanceTable where jobClass='%s';",columnInDatabase, jobClass);
		ResultSet resultset = st.executeQuery(query);
		resultset.next();
		
    	int jobAllowanceScore = Integer.parseInt(resultset.getString(1));
    	
    	query = "select yearConstant from systemConstants;";
    	resultset = st.executeQuery(query);
    	resultset.next();
    	
    	int yearConstant =(int) Float.parseFloat(resultset.getString(1));
    	
    	return (jobAllowanceScore * yearConstant);
    }
    
    //Money
    public int getEmployeeAllowance(String personId) throws SQLException
    {
    	int money = 0 ;
    	
    	Statement st = connection.createStatement();
    	String query = String.format("select education from teachers where personId='%s';",personId);
		ResultSet resultset = st.executeQuery(query);
		resultset.next();
		String education = resultset.getString(1);
		
		query = String.format("select educationScore,eachYearScore from employeeAllowanceTable where education='%s';", education);
		resultset = st.executeQuery(query);
		resultset.next();
		
		int educationScore = Integer.parseInt(resultset.getString(1));
		int eachyearScore = Integer.parseInt(resultset.getString(2));
		int workYears = getWorkExperience(personId);
		
		query = "select yearConstant from systemConstants;";
    	resultset = st.executeQuery(query);
    	resultset.next();
    	
    	int yearConstant =(int) Float.parseFloat(resultset.getString(1));
    	int totalScore = educationScore + (workYears*eachyearScore);
    	money+= (totalScore*yearConstant);
		return money;
    }
    
    //Money (Array) first elements is BadWeatherAllowance and the second is NotDevelopedRegionAllowance
    public int[] getNotDevelopedRegionAllowanceAndBadWeather(String personId) throws SQLException
    {
    	int constantSalary = getJobAllowance(personId)+getEmployeeAllowance(personId);
    	int notDevelopedRegionAllowance = 0 ;
    	int badWeatherAllowance = 0 ;
    	
    	String cityId = getTeacherCityId(personId);
    	
    	Statement st = connection.createStatement();
    	String query = String.format("select weatherDegree,notDevelopedregionConstant from cities where cityId='%s';", cityId);
 		ResultSet resultset = st.executeQuery(query);
 		resultset.next();
 		
 		String weatherDegree = resultset.getString(1);
 		String notDevelopedregionConstant = resultset.getString(2);
 		
 		switch(weatherDegree)
 		{
 		case "0":
 			break;
 		case "1":
 			badWeatherAllowance = (int) (constantSalary*0.05);
 			break;
 		case "2":
 			badWeatherAllowance = (int) (constantSalary*0.06);
 			break;
 		case "3":
 			badWeatherAllowance = (int) (constantSalary*0.07);
 			break;
 		case "4":
 			badWeatherAllowance = (int) (constantSalary*0.08);
 			break;
 		}
 		
 		switch(notDevelopedregionConstant)
 		{
 		case "0":
 			break;
 		case "5":
 			notDevelopedRegionAllowance = (int) (constantSalary*0.05);
 			break;
 		case "6":
 			notDevelopedRegionAllowance = (int) (constantSalary*0.06);
 			break;
 		case "7":
 			notDevelopedRegionAllowance = (int) (constantSalary*0.08);
 			break;
 		case "8":
 			notDevelopedRegionAllowance = (int) (constantSalary*0.10);
 			break;
 		case "9":
 			notDevelopedRegionAllowance = (int) (constantSalary*0.12);
 			break;
 		}
 		
 		int[] resultArray = {badWeatherAllowance, notDevelopedRegionAllowance};
 		
    	
    	return resultArray;
    }
    
    public void updateTime(String newTime) throws SQLException
    {
    	Statement st = connection.createStatement();
    	String query = String.format("update time set currentTime='%s' WHERE time.variable='currentTime';",newTime);
    	st.executeUpdate(query);
    }
    
    public void createAction(String personId,String currentTime) throws SQLException
    {
    	updateTime(currentTime);
    	String issueDate = getCurrentTime();
    	
    	Statement st = connection.createStatement();
    	String query = "select yearConstant,familyConstantAllowance,childrenConstantMoney,"
    			+ "jobDifficultnessScore,jobAllowanceScore from systemConstants;";
		ResultSet resultset = st.executeQuery(query);
		resultset.next();
		
		int jobAllowance = getJobAllowance(personId);
		int employeeAllowance = getEmployeeAllowance(personId);
		int[] notDevelopedAndWeather = getNotDevelopedRegionAllowanceAndBadWeather(personId);
		int badWeatherRegionAllowance = notDevelopedAndWeather[0];
		int notDevelopedRegionAllowance = notDevelopedAndWeather[1];
		int yearConstant = (int) Float.parseFloat(resultset.getString(1));
		int familyHelp = (int)Float.parseFloat(resultset.getString(2));
		int childrenHelp = (int)Float.parseFloat(resultset.getString(3));
		int jobDifficultnessAllowance = (int)(Float.parseFloat(resultset.getString(4)))*yearConstant;
		int jobSpecial = (int)(Float.parseFloat(resultset.getString(5)))*yearConstant;
		
		query = String.format("select numberOfChildren ,married from teachers where personId='%s';", personId);
		resultset = st.executeQuery(query);
		resultset.next();
		int numberOfChildren = Integer.parseInt(resultset.getString(1));
		String married = resultset.getString(2);
		
		int salary = 0;
		childrenHelp = (int) Math.min(3, (numberOfChildren * childrenHelp) );
		
		switch(married)
		{
		case "مجرد":
			familyHelp = 0;
			break;
		case "متاهل":
			break;
		}
		salary = jobAllowance+employeeAllowance+badWeatherRegionAllowance+
				notDevelopedRegionAllowance+familyHelp+childrenHelp+jobDifficultnessAllowance+jobSpecial;

		String jobClass = getJobClass(personId);
		String jobrank = getJobRank(personId);
		
    	query = "INSERT INTO employmentAction "
    			+ "(owner, jobAllowance, employeeAllowance,"
    			+ " notDevelopedRegionAllowance, badWeatherRegionAllowance,"
    			+ " familyHelp, childrenHelp, jobDifficultnessAllowance,"
    			+ " jobSpecial, salary, issueDate, jobClass, jobrank) VALUES"
    			+ " (?,?,?,?,?,?,?,?,?,?,?,?,?);";

    	PreparedStatement pst = connection.prepareStatement(query,  Statement.RETURN_GENERATED_KEYS);
    	pst.setString(1, personId);
    	pst.setString(2, Integer.toString(jobAllowance));
    	pst.setString(3, Integer.toString(employeeAllowance));
    	pst.setString(4, Integer.toString(notDevelopedRegionAllowance));
    	pst.setString(5, Integer.toString(badWeatherRegionAllowance));
    	pst.setString(6,  Integer.toString(familyHelp));
    	pst.setString(7, Integer.toString((childrenHelp*numberOfChildren)));
    	pst.setString(8, Integer.toString(jobDifficultnessAllowance));
    	pst.setString(9, Integer.toString(jobSpecial));
    	pst.setString(10,Integer.toString(salary));
    	pst.setString(11, issueDate);
    	pst.setString(12, jobClass);
    	pst.setString(13, jobrank);
    	
    	pst.executeUpdate();
    }

    public void createActionAll(String date) throws SQLException
    {
    	ObservableList<String> employees = getEmployees();
    	
    	for(String employee : employees)
    	{
    		createAction(employee, date);
    	}
    }
    
    public ResultSet getConstants() throws SQLException
    {
    	Statement st = connection.createStatement();
    	String query = "select * from systemConstants;";
 		ResultSet resultset = st.executeQuery(query);
 		resultset.next();
 		return resultset;
    }
    
    public void setSystemConstants(String yearC,String familyAllowanceC,String childrenC,String jobHardC,String jobSpecialC) throws SQLException
    {
    	Statement st = connection.createStatement();
    	String query = String.format("update systemConstants set yearConstant='%s', "
				+ "familyConstantAllowance='%s', childrenConstantMoney='%s', jobDifficultnessScore='%s', "
				+ "	jobAllowanceScore='%s';",yearC,familyAllowanceC,childrenC,jobHardC,jobSpecialC);
		st.executeUpdate(query);
    }


}
