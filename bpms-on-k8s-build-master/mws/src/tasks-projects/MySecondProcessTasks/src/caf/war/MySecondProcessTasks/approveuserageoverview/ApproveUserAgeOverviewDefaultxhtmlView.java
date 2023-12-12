/**
 * 
 */
package caf.war.MySecondProcessTasks.approveuserageoverview;

/**
 * @author adit
 *
 */

import com.webmethods.caf.faces.data.task.TaskDisplayProvider;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import com.webmethods.caf.faces.annotations.ExpireWithPageFlow;
import com.webmethods.caf.faces.annotations.DTManagedBean;
import com.webmethods.caf.faces.annotations.BeanType;

@ManagedBean(name = "ApproveUserAgeOverviewDefaultxhtmlView")
@SessionScoped
@ExpireWithPageFlow
@DTManagedBean(displayName = "ApproveUserAgeOverview/default", beanType = BeanType.PAGE)
public class ApproveUserAgeOverviewDefaultxhtmlView extends com.webmethods.caf.faces.bean.BasePageBean {


	private static final long serialVersionUID = 1L;
	private static final String[][] INITIALIZE_PROPERTY_BINDINGS = new String[][] {
		{"#{activePreferencesBean.currentTab}", "TaskData"},
	};

	// binding the Task Display Provider to the current taskID (passed to the Portlet Bean via the URL)
	private TaskDisplayProvider taskDisplayProvider = null;
	private static final String[][] TASKDISPLAYPROVIDER_PROPERTY_BINDINGS = new String[][] { {
			"#{TaskDisplayProvider.taskID}", "#{ApproveUserAgeOverview.taskID}" }, };
	private transient caf.war.MySecondProcessTasks.approveuserageoverview.ApproveUserAgeOverview approveUserAgeOverview = null;
	private caf.war.MySecondProcessTasks.taskclient.ApproveUserAge approveUserAge = null;
	private static final String[][] APPROVEUSERAGE_PROPERTY_BINDINGS = new String[][] {
		{"#{ApproveUserAge.taskID}", "#{ApproveUserAgeOverview.taskID}"},
	};

	/**
	 * Initialize page
	 */
	public String initialize() {
		try {
		    resolveDataBinding(INITIALIZE_PROPERTY_BINDINGS, null, "initialize", true, false);
		} catch (Exception e) {
			error(e);
			log(e);
		}
		return null;	
	}

	/*
	 * Get the Task Display Provider for the current taskID
	 */
	public TaskDisplayProvider getTaskDisplayProvider() {
		if (taskDisplayProvider == null) {
			taskDisplayProvider = (TaskDisplayProvider) resolveExpression("#{TaskDisplayProvider}");
		}
		resolveDataBinding(TASKDISPLAYPROVIDER_PROPERTY_BINDINGS,
				taskDisplayProvider, "taskDisplayProvider", false, false);
		return taskDisplayProvider;
	}

	public caf.war.MySecondProcessTasks.approveuserageoverview.ApproveUserAgeOverview getApproveUserAgeOverview()  {
		if (approveUserAgeOverview == null) {
		    approveUserAgeOverview = (caf.war.MySecondProcessTasks.approveuserageoverview.ApproveUserAgeOverview)resolveExpression("#{ApproveUserAgeOverview}");
		}
		return approveUserAgeOverview;
	}

	public caf.war.MySecondProcessTasks.taskclient.ApproveUserAge getApproveUserAge()  {
		if (approveUserAge == null) {
		    approveUserAge = (caf.war.MySecondProcessTasks.taskclient.ApproveUserAge)resolveExpression("#{ApproveUserAge}");
		}
	
	    resolveDataBinding(APPROVEUSERAGE_PROPERTY_BINDINGS, approveUserAge, "approveUserAge", false, false);
		return approveUserAge;
	}

}