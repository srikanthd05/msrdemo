/**
 * 
 */
package caf.war.MySecondProcessTasks.approveuserageinboxresults;

/**
 * @author adit
 *
 */

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlPanelGroup;
import javax.faces.model.SelectItem;

import com.softwareag.caf.controls.mwsx.PeoplePickerDialog;
import com.webmethods.caf.faces.component.util.ComponentUtils;
import com.webmethods.caf.faces.context.ContextUtils;
import com.webmethods.caf.faces.data.dir.PrincipalModel;
import com.webmethods.caf.faces.data.task.ITaskInfo;
import com.webmethods.caf.faces.data.task.TaskDisplayProvider;
import com.webmethods.caf.faces.data.task.TaskProviderUtils;
import com.webmethods.caf.faces.data.task.impl.TaskContentProviderExtended;
import com.webmethods.portal.service.task.TaskSearchQueryTerm;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import com.webmethods.caf.faces.annotations.ExpireWithPageFlow;
import com.webmethods.caf.faces.annotations.DTManagedBean;
import com.webmethods.caf.faces.annotations.BeanType;

@ManagedBean(name = "ApproveUserAgeInboxResultsDefaultxhtmlView")
@SessionScoped
@ExpireWithPageFlow
@DTManagedBean(displayName = "ApproveUserAgeInboxResults/default", beanType = BeanType.PAGE)
public class ApproveUserAgeInboxResultsDefaultxhtmlView extends com.webmethods.caf.faces.bean.task.BaseTaskInboxResultPageBean {

	/**
	 * Determines if a de-serialized file is compatible with this class.
	 *
	 * Maintainers must change this value if and only if the new version
	 * of this class is not compatible with old versions. See Sun docs
	 * for <a href=http://java.sun.com/j2se/1.5.0/docs/guide/serialization/spec/version.html> 
	 * details. </a>
	 */
	private static final long serialVersionUID = 1L;

	private TaskDisplayProvider taskDisplayProvider = null;

	protected TaskSearchQueryTerm m_typeID = new TaskSearchQueryTerm();
	protected TaskContentProviderExtended m_taskContentProvider;
	protected PrincipalModel m_delegatePrincipal = null;
	
	private com.webmethods.caf.faces.data.object.TableSelectItemGroupProvider taskTypeNameMapProvider = null;
	private static final String[][] TASKTYPENAMEMAPPROVIDER_PROPERTY_BINDINGS = new String[][] {
		{"#{TaskTypeNameMapProvider.labelFieldName}", "label"},
		{"#{TaskTypeNameMapProvider.array}", "#{TaskDisplayProvider.taskTypeItems}"},
		{"#{TaskTypeNameMapProvider.valueFieldName}", "value"},
	};
	
	private static final String[][] TASKDISPLAYPROVIDER_PROPERTY_BINDINGS = new String[][] {
		{"#{TaskDisplayProvider.taskInfo}", "#{row.taskInfo}"},
	};

	public TaskDisplayProvider getTaskDisplayProvider()  {
		if (taskDisplayProvider == null) {
		    taskDisplayProvider = (TaskDisplayProvider)resolveExpression("#{TaskDisplayProvider}");
		}
	
	    resolveDataBinding(TASKDISPLAYPROVIDER_PROPERTY_BINDINGS, taskDisplayProvider, "this", true, false);
		return taskDisplayProvider;
	}
	
	private com.webmethods.caf.faces.data.object.ListTableContentProvider selectedTaskInfosProvider = null;
	private static final String[][] SELECTEDTASKINFOSPROVIDER_PROPERTY_BINDINGS = new String[][] {
		{"#{SelectedTaskInfosProvider.list}", "#{ApproveUserAgeInboxResultsDefaultxhtmlView.selectedTaskInfos}"},
		{"#{SelectedTaskInfosProvider.rowVariable}", "varselectedTaskInfo"},
	};

	private com.webmethods.caf.faces.data.task.impl.TaskContentProviderExtended taskContentProviderExtended = null;
	private static final String[][] TASKCONTENTPROVIDEREXTENDED_PROPERTY_BINDINGS = new String[][] {
		{"#{TaskContentProviderExtended.taskID}", "#{row.taskID}"},
	};

	private static final String[][] TASKCONTENTPROVIDEREXTENDEDFORLIST_PROPERTY_BINDINGS = new String[][] {
		{"#{TaskContentProviderExtended.taskID}", "#{selectedTaskRow.taskID}"},
	};

	private TaskDisplayProvider taskDisplayProviderForDialog = null;
	private static final String[][] TASKDISPLAYPROVIDERFORDIALOG_PROPERTY_BINDINGS = new String[][] {
		{"#{TaskDisplayProviderForDialog.taskID}", "#{selectedTaskRow.taskID}"},
	};
	
	public com.webmethods.caf.faces.data.object.TableSelectItemGroupProvider getTaskTypeNameMapProvider()  {
		if (taskTypeNameMapProvider == null) {
		    taskTypeNameMapProvider = (com.webmethods.caf.faces.data.object.TableSelectItemGroupProvider)resolveExpression("#{TaskTypeNameMapProvider}");
		}
	
	    resolveDataBinding(TASKTYPENAMEMAPPROVIDER_PROPERTY_BINDINGS, taskTypeNameMapProvider, null, false, true);
		return taskTypeNameMapProvider;
	}
	
	
	/*
	 * TaskContentProvider with binding to a list control exposing 'row' 
	 */
	public com.webmethods.caf.faces.data.task.impl.TaskContentProviderExtended getTaskContentProviderExtended()  {
		if (taskContentProviderExtended == null) {
		    taskContentProviderExtended = (com.webmethods.caf.faces.data.task.impl.TaskContentProviderExtended)resolveExpression("#{TaskContentProviderExtended}");
		}
	
	    resolveDataBinding(TASKCONTENTPROVIDEREXTENDED_PROPERTY_BINDINGS, taskContentProviderExtended, "taskContentProviderExtended", false, false);
		return taskContentProviderExtended;
	}


	/*
	 * TaskContentProvider with binding to a list control exposing 'selectedTaskRow' 
	 */
	public com.webmethods.caf.faces.data.task.impl.TaskContentProviderExtended getTaskContentProviderExtendedForList()  {
		if (taskContentProviderExtended == null) {
		    taskContentProviderExtended = (com.webmethods.caf.faces.data.task.impl.TaskContentProviderExtended)resolveExpression("#{TaskContentProviderExtended}");
		}
	
		resolveDataBinding(TASKCONTENTPROVIDEREXTENDEDFORLIST_PROPERTY_BINDINGS, taskContentProviderExtended, "taskContentProviderExtended", true, false);
		return taskContentProviderExtended;
	}

	/**
	 * Getter method for the control with id='searchResultsTableControl'
	 */
	public com.webmethods.caf.faces.component.table.html.DataTable getSearchResultsTableControl() {
		return (com.webmethods.caf.faces.component.table.html.DataTable) findComponentInRoot("searchResultsTableControl");
	}

	/**
	 * Getter method for the control with id=':searchResultsForm:delegatePrincipalPicker'
	 */
	public PeoplePickerDialog getDelegatePrincipalPicker()  {
		return (PeoplePickerDialog)findComponentInRoot(":searchResultsForm:delegatePrincipalPicker");
	}


	/**
	 * Getter method for the control with id=':searchResultsForm:panelDelegateConfirm'
	 */
	public javax.faces.component.html.HtmlPanelGroup getPanelDelegateConfirm()  {
		return (javax.faces.component.html.HtmlPanelGroup)findComponentInRoot(":searchResultsForm:panelDelegateConfirm");
	}
	
	public void doDelegateCancel() {
	}
	
	protected String getDelegatePrincipalID() {
		String principalUID = null;
		if( m_delegatePrincipal != null ) {
			return m_delegatePrincipal.getPrincipalID();
		}
		return principalUID;
	}

	public void doDelegateSelectedTasks() {
		String currentUserID = TaskProviderUtils.getCurrentUserID();
		String delegateToID = getDelegatePrincipalID(); 

		List<String> selectedTaskIDs = getSelectedTaskIDs();
		TaskProviderUtils.delegateTasks(selectedTaskIDs, currentUserID, delegateToID);
		getTaskSearchProvider().refresh();
	
	}
	
	/*
	 * Build a list of selected taskIDs
     */
    @SuppressWarnings("unchecked")
    public List<String> getSelectedTaskIDs() {
        @SuppressWarnings("rawtypes")
        Collection coll = getTaskSearchProvider().getRowSelectedIds();
        return new ArrayList<String>(coll);
    }
	
    /*
	 * Get the task content provider
     */
    protected TaskContentProviderExtended getTaskContentProvider( String taskID ) {
    	if( m_taskContentProvider == null ) {
    		m_taskContentProvider = new TaskContentProviderExtended();
        	m_taskContentProvider.setAutoAccept(false);
        	m_taskContentProvider.setNeedAcceptToUpdate(false);
    	}
    	m_taskContentProvider.setTaskID( taskID );
    	return m_taskContentProvider;
    }

	public String getPortletResource( String key ) {
	    return (String)getActivePreferencesBean().getPortletResourcesProvider().getValue( key );
	}

	/*
	 * Build a list of selected ITasksInfo
     */
	public List<ITaskInfo> getSelectedTaskInfos() {

		List<ITaskInfo> selectedTasks = new ArrayList<ITaskInfo>(0x10);
		Collection<String> coll = getTaskSearchProvider().getRowSelectedIds();
		if (coll != null) {
			try {
				for( String rowID : coll ) {
					TaskContentProviderExtended tp = getTaskContentProvider(rowID);
					selectedTasks.add(tp.getTaskInfo());
				}
			} catch (Exception ex) {
				String s = getPortletResource( "getselectedids.error");
				ContextUtils.error( s, ex );
			}
		}
		return selectedTasks;
	}


	public com.webmethods.caf.faces.data.object.ListTableContentProvider getSelectedTaskInfosProvider()  {
		if (selectedTaskInfosProvider == null) {
		    selectedTaskInfosProvider = (com.webmethods.caf.faces.data.object.ListTableContentProvider)resolveExpression("#{SelectedTaskInfosProvider}");
		}
	
	    resolveDataBinding(SELECTEDTASKINFOSPROVIDER_PROPERTY_BINDINGS, selectedTaskInfosProvider, "selectedTaskInfosProvider", false, false);
		return selectedTaskInfosProvider;
	}

	/**
	 * Action Event Handler for the control with id='commandDelegate'
	 */
	public String commandDelegate_action() {
	    doDelegateSelectedTasks();
	    return null;
	}
	
	public PrincipalModel getDelegatePrincipal() {
		return m_delegatePrincipal;
	}
	
	public void setDelegatePrincipal( PrincipalModel newVal ) {
		m_delegatePrincipal = newVal;
	}
	
	public List<SelectItem> getDelegatePrincipalDisplayList() {
		// can currently only select one principal
		List<SelectItem> displayNameList = new ArrayList<SelectItem>();
		PrincipalModel principalModel = getDelegatePrincipal();
		if( principalModel != null ) {
			String principalUID = principalModel.getPrincipalID();
			displayNameList.add( new SelectItem(principalUID, principalUID) );
		}
		return displayNameList;
	}
	
	public void setDelegatePrincipalDisplayList( List<SelectItem> newVal ) {
		// do nothing
	}
	
	public TaskDisplayProvider getTaskDisplayProviderForDialog()  {
		if (taskDisplayProviderForDialog == null) {
		    taskDisplayProviderForDialog = (TaskDisplayProvider)resolveExpression("#{TaskDisplayProviderForDialog}");
		}
	
	    resolveDataBinding(TASKDISPLAYPROVIDERFORDIALOG_PROPERTY_BINDINGS, taskDisplayProviderForDialog, "taskDisplayProviderForDialog", true, false);
		return taskDisplayProviderForDialog;
	}
	
	/*
	 * Copy the confirmation list of selected tasks to the bottom of
	 * the principal picker dialog, as well as add action listeners to the 'applyButton' of the picker
	 * (non-Javadoc)
	 * @see com.webmethods.caf.faces.bean.search.BaseSearchResultPageBean#beforeRenderResponse()
	 */
	protected void beforeRenderResponse() {
		super.beforeRenderResponse();
		try {
			PeoplePickerDialog dialog = getDelegatePrincipalPicker(); 
			if( !dialog.getAttributes().containsKey("principalPickerCopied") ) {
				dialog.ensureEmbeddedViewIsLoaded();
				HtmlPanelGroup panel = getPanelDelegateConfirm();
				UIComponent copyPanel = ComponentUtils.copyComponent( getFacesContext(), panel);
				copyPanel.setRendered(true);
				dialog.getChildren().add( copyPanel );
				dialog.getAttributes().put( "principalPickerCopied", true );
			}
		}
		catch (Exception ex) {
			String s = getPortletResource( "task.delegation.prerender.error" );
			ContextUtils.error( s, ex );
		}
		
		// reset the display providers so they reload fresh task info 
		// if we ask for task info that the provider is alreayd aligned on
		getTaskDisplayProvider().reset();
		getTaskDisplayProviderForDialog().reset();
	}

	/**
	 * Action Event Handler for the control with id='commandUndelegate'
	 */
	public String commandUndelegate_action() {
		String currentUserID = TaskProviderUtils.getCurrentUserID();
		List<String> selectedTaskIDs = getSelectedTaskIDs();
		TaskProviderUtils.undelegateTasks(selectedTaskIDs, currentUserID);
		getTaskSearchProvider().refresh();

	    return null;
	}


	/**
	 * Action Event Handler for the control with id='buttonManageDelegate'
	 */
	public String buttonManageDelegate_action() {
	    return null;
	}

	/**
	 * Opens task details
	 */
	public void openTask() throws Exception {
		super.openTask();
	}	
	
	/**
	 * Accepts and opens task
	 */
	public void acceptAndOpenTask() throws Exception {
		super.acceptAndOpenTask();
	}	
	
	private static final String[][] INITIALIZE_PROPERTY_BINDINGS = new String[][] {
	};

	private transient caf.war.MySecondProcessTasks.approveuserageinboxresults.ApproveUserAgeInboxResults approveUserAgeInboxResults = null;

	private caf.war.MySecondProcessTasks.approveuserageinboxresults.ApproveUserAgeSearchProvider taskSearchProvider = null;

	private static final String[][] TASKSEARCHPROVIDER_PROPERTY_BINDINGS = new String[][] {
		{"#{ApproveUserAgeSearchProvider.searchQuery.taskTypeID.value}", "DC7A2A18-760F-8C98-648C-93A129E077C0"},
		{"#{ApproveUserAgeSearchProvider.rowIdExpression}", "#{row.taskInfo.taskID}"},
		{"#{ApproveUserAgeSearchProvider.searchQuery.taskID.value}", "#{ApproveUserAgeInboxResultsDefaultxhtmlView.activeSearchQuery.refineFields.taskID}"},
		{"#{ApproveUserAgeSearchProvider.searchQuery.priority.value}", "#{ApproveUserAgeInboxResultsDefaultxhtmlView.activeSearchQuery.refineFields.priority}"},
		{"#{ApproveUserAgeSearchProvider.searchQuery.createdDateRange}", "#{ApproveUserAgeInboxResultsDefaultxhtmlView.activeSearchQuery.refineFields.createdDateRange}"},
		{"#{ApproveUserAgeSearchProvider.searchQuery.modifiedDateRange}", "#{ApproveUserAgeInboxResultsDefaultxhtmlView.activeSearchQuery.refineFields.modifiedDateRange}"},
		{"#{ApproveUserAgeSearchProvider.searchQuery.expirationDateRange}", "#{ApproveUserAgeInboxResultsDefaultxhtmlView.activeSearchQuery.refineFields.expirationDateRange}"},
		{"#{ApproveUserAgeSearchProvider.searchQuery.acceptedByCurrent.value}", "#{ApproveUserAgeInboxResultsDefaultxhtmlView.activeSearchQuery.refineFields.acceptedByCurrentUser}"},
		{"#{ApproveUserAgeSearchProvider.searchQuery.invocationID}", "#{ApproveUserAgeInboxResultsDefaultxhtmlView.activeSearchQuery.invocationID}"},
		{"#{ApproveUserAgeSearchProvider.searchQuery.maxResults}", "#{ApproveUserAgeInboxResultsDefaultxhtmlView.activeSearchQuery.maxResults}"},
		{"#{ApproveUserAgeSearchProvider.autoRefresh}", "#{not empty ApproveUserAgeInboxResultsDefaultxhtmlView.activeSearchQueryString}"},
		{"#{ApproveUserAgeSearchProvider.searchQuery.invocationID}", "#{ApproveUserAgeInboxResultsDefaultxhtmlView.activeSearchQuery.invocationID}"},
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


	public caf.war.MySecondProcessTasks.approveuserageinboxresults.ApproveUserAgeInboxResults getApproveUserAgeInboxResults()  {
		if (approveUserAgeInboxResults == null) {
		    approveUserAgeInboxResults = (caf.war.MySecondProcessTasks.approveuserageinboxresults.ApproveUserAgeInboxResults)resolveExpression("#{ApproveUserAgeInboxResults}");
		}
		return approveUserAgeInboxResults;
	}


	public caf.war.MySecondProcessTasks.approveuserageinboxresults.ApproveUserAgeSearchProvider getTaskSearchProvider()  {
		if (taskSearchProvider == null) {
		    taskSearchProvider = (caf.war.MySecondProcessTasks.approveuserageinboxresults.ApproveUserAgeSearchProvider)resolveExpression("#{ApproveUserAgeSearchProvider}");
		}
	
	    resolveDataBinding(TASKSEARCHPROVIDER_PROPERTY_BINDINGS, taskSearchProvider, "taskSearchProvider", false, false);
		return taskSearchProvider;
	}
	
}	