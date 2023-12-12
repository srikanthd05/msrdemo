package caf.war.MySecondProcessTasks.taskclient;

 

import com.webmethods.portal.service.task.ITaskData;
import com.webmethods.caf.faces.data.ContentProviderException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import com.webmethods.caf.faces.annotations.ExpireWithPageFlow;
import com.webmethods.caf.faces.annotations.DTManagedBean;
import com.webmethods.caf.faces.annotations.BeanType;
 
/**
 * Task Client bean for 'ApproveUserAge' task.
 */ 
@ManagedBean(name = "ApproveUserAge")
@SessionScoped
@ExpireWithPageFlow
@DTManagedBean(beanType = BeanType.DEFAULT)
public class ApproveUserAge extends com.webmethods.caf.faces.data.task.impl.TaskContentProviderExtended {

	private static final long serialVersionUID = 6376875793262963712L;
	
	/**
	 * Globally unique task type id
	 */
	private static final String TASK_TYPE_ID = "DC7A2A18-760F-8C98-648C-93A129E077C0";

	/**
	 * Default constructor
	 */
	public ApproveUserAge() {
		super();
		setTaskTypeID(TASK_TYPE_ID);
	}
	
	/**
	 * Task Data Object
	 */
	public static class TaskData extends com.webmethods.caf.faces.data.task.impl.TaskData {

		private static final long serialVersionUID = 8982645663461439488L;
		 
			
		private java.lang.String age;


		public static String[][] FIELD_NAMES = new String[][] {{"age", "age"},{"empAgeDetails", "EmpAgeDetails"},
		};


		private caf.war.MySecondProcessTasks.is.document.MySecondBpmSupport_docs_EmpAgeDetails empAgeDetails = null;


		public static final String[] INPUTS = new String[] {"age", "empAgeDetails", };


		public static final String[] OUTPUTS = new String[] {"age", "empAgeDetails", };


		public TaskData() {
		}


		public java.lang.String getAge()  {
			
			return age;
		}


		public void setAge(java.lang.String age)  {
			this.age = age;
		}


		public caf.war.MySecondProcessTasks.is.document.MySecondBpmSupport_docs_EmpAgeDetails getEmpAgeDetails()  {
			if (empAgeDetails == null) {
				empAgeDetails = new caf.war.MySecondProcessTasks.is.document.MySecondBpmSupport_docs_EmpAgeDetails();
			}
			return empAgeDetails;
		}


		public void setEmpAgeDetails(caf.war.MySecondProcessTasks.is.document.MySecondBpmSupport_docs_EmpAgeDetails empAgeDetails)  {
			this.empAgeDetails = empAgeDetails;
		}
		
		
		
	}
	
	/**
	 * Return current task data object
	 * @return current task data
	 */
	public TaskData getTaskData() {
		return (TaskData)getValue(PROPERTY_KEY_TASKDATA);
	}

	/**
	 * Creates new task data object
	 * @return newly created task data object
	 */	
	protected ITaskData newTaskData() throws ContentProviderException {
		return new TaskData();
	}

}