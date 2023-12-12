package caf.war.MySecondProcessTasks.approveuserageinboxresults;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import com.webmethods.caf.faces.annotations.ExpireWithPageFlow;
import com.webmethods.caf.faces.annotations.DTManagedBean;
import com.webmethods.caf.faces.annotations.BeanType;


/**
 * Task Search bean for 'ApproveUserAge' task.
 */
@ManagedBean(name = "ApproveUserAgeSearchProvider")
@SessionScoped
@ExpireWithPageFlow
@DTManagedBean(beanType = BeanType.DEFAULT)
public class ApproveUserAgeSearchProvider extends com.webmethods.caf.faces.data.task.impl.TaskInboxSearchContentProvider {

	private static final long serialVersionUID = 1177464092246614016L;
    
	private static final String TASK_TYPE_ID = "DC7A2A18-760F-8C98-648C-93A129E077C0";

	public ApproveUserAgeSearchProvider() {
		super(); // task type id to search
		m_searchQuery = new CustomInboxSearchQuery(); 
	}

	/**
	 * Typed ITaskData getter
	 * @return current task data
	 */
	public caf.war.MySecondProcessTasks.taskclient.ApproveUserAge.TaskData getTaskData() {
		return (caf.war.MySecondProcessTasks.taskclient.ApproveUserAge.TaskData)getValue(PROPERTY_TASKDATA);
	}

	/**
	 * Typed custom search query
	 */
	public CustomInboxSearchQuery getSearchQuery() {  
		return (CustomInboxSearchQuery)m_searchQuery;
	}

	/**
	 * Custom inbox search query that can be extended
	 **/
	public class CustomInboxSearchQuery extends com.webmethods.caf.faces.data.task.impl.TaskInboxSearchContentProvider.InboxSearchQuery {
		private static final long serialVersionUID = 7027797307024826368L;
		
		public CustomInboxSearchQuery() {
			super();
		}

	}				

}
