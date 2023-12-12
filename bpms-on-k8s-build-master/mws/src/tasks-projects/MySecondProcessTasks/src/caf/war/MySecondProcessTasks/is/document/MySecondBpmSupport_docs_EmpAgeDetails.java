package caf.war.MySecondProcessTasks.is.document;

 import java.io.Serializable;

/**
 * IS document wrapper
 */
public  class MySecondBpmSupport_docs_EmpAgeDetails extends java.lang.Object implements Serializable {

	
	private static final long serialVersionUID = 1L;
	// IS Document type used to generate this class
	public static final String DOCUMENT_TYPE = "MySecondBpmSupport.docs:EmpAgeDetails";

	// Used by Designer to access the source document.
	 @SuppressWarnings("unused")
	private static final String DOCUMENT_SRC = "http://localhost:5555";
	public static String[][] FIELD_NAMES = new String[][] {{"dateOfBirth", "dateOfBirth"},
	};
	private java.lang.String dateOfBirth;

	
	public MySecondBpmSupport_docs_EmpAgeDetails() {
	}


	public java.lang.String getDateOfBirth()  {
		
		return dateOfBirth;
	}


	public void setDateOfBirth(java.lang.String dateOfBirth)  {
		this.dateOfBirth = dateOfBirth;
	}
	
	

}