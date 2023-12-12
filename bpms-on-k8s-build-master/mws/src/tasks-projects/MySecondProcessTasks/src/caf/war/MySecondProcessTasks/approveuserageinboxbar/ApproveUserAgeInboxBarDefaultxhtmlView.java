/**
 * 
 */
package caf.war.MySecondProcessTasks.approveuserageinboxbar;

/**
 * @author adit
 *
 */

import com.webmethods.caf.faces.data.object.DateRange;
import com.webmethods.caf.faces.search.saved.DboSavedSearchProvider;
import com.webmethods.caf.faces.search.saved.ISavedSearchProvider;
import com.webmethods.caf.faces.search.query.ISearchQuery;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import com.webmethods.caf.faces.annotations.ExpireWithPageFlow;
import com.webmethods.caf.faces.annotations.DTManagedBean;
import com.webmethods.caf.faces.annotations.BeanType;

@ManagedBean(name = "ApproveUserAgeInboxBarDefaultxhtmlView")
@SessionScoped
@ExpireWithPageFlow
@DTManagedBean(displayName = "ApproveUserAgeInboxBar/default", beanType = BeanType.PAGE)
public class ApproveUserAgeInboxBarDefaultxhtmlView extends com.webmethods.caf.faces.bean.task.BaseTaskInboxBarPageBean {
 
	/**
	 * Determines if a de-serialized file is compatible with this class.
	 *
	 * Maintainers must change this value if and only if the new version
	 * of this class is not compatible with old versions. See Sun docs
	 * for <a href=http://java.sun.com/j2se/1.5.0/docs/guide/serialization/spec/version.html> 
	 * details. </a>
	 */
	private static final long serialVersionUID = 1L;

	private com.webmethods.caf.faces.data.object.TableSelectItemGroupProvider taskPriorityMapProvider = null;

	private static final String[][] TASKPRIORITYMAPPROVIDER_PROPERTY_BINDINGS = new String[][] {
		{"#{TaskPriorityMapProvider.labelFieldName}", "label"},
		{"#{TaskPriorityMapProvider.array}", "#{TaskDisplayProvider.taskPriorityItems}"},
		{"#{TaskPriorityMapProvider.valueFieldName}", "value"},
	};
	
	public static String[][] SEARCH_QUERY_PROPERTIES = new String[][] {};
	
	public com.webmethods.caf.faces.data.object.TableSelectItemGroupProvider getTaskPriorityMapProvider()  {
		if (taskPriorityMapProvider == null) {
		    taskPriorityMapProvider = (com.webmethods.caf.faces.data.object.TableSelectItemGroupProvider)resolveExpression("#{TaskPriorityMapProvider}");
		}
	
	    resolveDataBinding(TASKPRIORITYMAPPROVIDER_PROPERTY_BINDINGS, taskPriorityMapProvider, null, false, true);
		return taskPriorityMapProvider;
	}

	/* (non-Javadoc)
	 * @see com.webmethods.caf.faces.bean.search.BaseSearchBarPageBean#getSavedSearchProvider()
	 */
	public ISavedSearchProvider getSavedSearchProvider() {
		if (fSavedSearchProvider == null) {
			fSavedSearchProvider = new DboSavedSearchProvider(getSearchQueryFactory(), "ApproveUserAge Saved Searches" );
		}
		return fSavedSearchProvider;
	}
	
	public DateRange getCreatedRange() {
		return (DateRange)getActiveSearchQuery().getRefineFields().get( "createdDateRange" );
	}

	public void setCreatedRange( DateRange newVal ) {
		getActiveSearchQuery().getRefineFields().put( "createdDateRange", newVal );
	}

	public DateRange getModifiedRange() {
		return (DateRange)getActiveSearchQuery().getRefineFields().get( "modifiedDateRange" );
	}
	
	public void setModifiedRange( DateRange newVal ) {
		getActiveSearchQuery().getRefineFields().put( "modifiedDateRange", newVal );
	}
	
	public DateRange getExpiredRange() {
		return (DateRange)getActiveSearchQuery().getRefineFields().get( "expirationDateRange" );
	}
	
	public void setExpiredRange( DateRange newVal ) {
		getActiveSearchQuery().getRefineFields().put( "expirationDateRange", newVal );
	}
	
	public Boolean getAcceptedByCurrentUser() {
		Boolean retVal = null;
		Object o = getActiveSearchQuery().getRefineFields().get( "acceptedByCurrentUser" ); //$NON-NLS-1$
		if( o != null && o instanceof Boolean ) {
			retVal = ((Boolean)o);
		}
		return retVal;
	}
	
	public void setAcceptedByCurrentUser( Boolean newVal ) {
		// Only store a true value, false value is stored as null
		// (to prevent only displaying search results where acceptedByCurrent is false)
		if( newVal != null && newVal.booleanValue() ) {
			// 
			getActiveSearchQuery().getRefineFields().put( "acceptedByCurrentUser", newVal ); //$NON-NLS-1$
		}
		else {
			getActiveSearchQuery().getRefineFields().remove( "acceptedByCurrentUser" ); //$NON-NLS-1$
		}
	}
	
	protected boolean isDateRangeSet( DateRange range ) {
		if( range == null ) {
			return false;
		}
		if( range.getRelativeRange() == null ) {
			return false;
		}
		// range is non empty if it isn't set to "All"
		boolean setToAll = range.getRelativeRange() == DateRange.FIXED && range.getFixedRange() == 0L;
		return !setToAll;
	}
	
	public String doSearch() {
		return getSearchBarControl().getControlBean().constructSearchQueryString();
	}

	public String getPortletResource( String key ) {
	    return (String)getActivePreferencesBean().getPortletResourcesProvider().getValue( key );
	}
	
	public void loadSearchQueryDefaults(ISearchQuery searchQuery) {
		super.loadSearchQueryDefaults(searchQuery);
		// assign default search query properties
		for (int i = 0; i < SEARCH_QUERY_PROPERTIES.length; i++) {
			String[] properties = SEARCH_QUERY_PROPERTIES[i];
			searchQuery.getRefineFields().put(properties[0], properties[1]);
		}
	}	
	
	private static final String[][] INITIALIZE_PROPERTY_BINDINGS = new String[][] {
	};

	private transient caf.war.MySecondProcessTasks.approveuserageinboxbar.ApproveUserAgeInboxBar approveUserAgeInboxBar = null;

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

	public caf.war.MySecondProcessTasks.approveuserageinboxbar.ApproveUserAgeInboxBar getApproveUserAgeInboxBar()  {
		if (approveUserAgeInboxBar == null) {
		    approveUserAgeInboxBar = (caf.war.MySecondProcessTasks.approveuserageinboxbar.ApproveUserAgeInboxBar)resolveExpression("#{ApproveUserAgeInboxBar}");
		}
		return approveUserAgeInboxBar;
	}
	
}