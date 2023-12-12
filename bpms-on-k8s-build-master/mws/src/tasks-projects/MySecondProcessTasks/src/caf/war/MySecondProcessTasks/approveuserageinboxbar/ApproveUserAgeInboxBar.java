/**
 * 
 */
package caf.war.MySecondProcessTasks.approveuserageinboxbar;

/**
 * @author adit
 *
 */

import javax.portlet.PortletPreferences;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import com.webmethods.caf.faces.annotations.ExpireWithPageFlow;
import com.webmethods.caf.faces.annotations.DTManagedBean;
import com.webmethods.caf.faces.annotations.BeanType;

@ManagedBean(name = "ApproveUserAgeInboxBar")
@SessionScoped
@ExpireWithPageFlow
@DTManagedBean(displayName = "ApproveUserAgeInboxBar", beanType = BeanType.PORTLET)
public class ApproveUserAgeInboxBar  extends   com.webmethods.caf.faces.bean.BaseFacesPreferencesBean {

	private transient caf.war.MySecondProcessTasks.MySecondProcessTasks mySecondProcessTasks = null;
	/**
	 * List of portlet preference names
	 */
	public static final String[] PREFERENCES_NAMES = new String[] {
		"initialSearchTab", "initialSelectedSavedSearch", "runSearchOnDisplay", "noMaxResults", "maxResults", "lastSearchState", "savedSearchMap"
	};
	/**
	 * Create new preferences bean with list of preference names
	 */
	public ApproveUserAgeInboxBar() {
		super(PREFERENCES_NAMES);
	}
	
	/**
	 * Call this method in order to persist
	 * Portlet preferences
	 */
	public void storePreferences() throws Exception {
		updatePreferences();
		PortletPreferences preferences = getPreferences();
		preferences.store();
	}

	public caf.war.MySecondProcessTasks.MySecondProcessTasks getMySecondProcessTasks()  {
		if (mySecondProcessTasks == null) {
		    mySecondProcessTasks = (caf.war.MySecondProcessTasks.MySecondProcessTasks)resolveExpression("#{MySecondProcessTasks}");
		}
		return mySecondProcessTasks;
	}

	/**
	 * The algorithm for this 'smart' preference getter is:
	 * 1) Check the Request Map (skip this step if it isn't a 'smart' preference)
	 * 2) Check the Member variable
	 * 3) Fall back to the PortletPreferences
	 */
	public String getInitialSearchTab() throws Exception {
		return (String) getPreferenceValue("initialSearchTab", String.class);
	}

	/**
	 * Invoke {@link #storePreferences} to persist these changes
	 */
	public void setInitialSearchTab(String initialSearchTab) throws Exception {
		setPreferenceValue("initialSearchTab", initialSearchTab);
	}

	/**
	 * The algorithm for this 'smart' preference getter is:
	 * 1) Check the Request Map (skip this step if it isn't a 'smart' preference)
	 * 2) Check the Member variable
	 * 3) Fall back to the PortletPreferences
	 */
	public String getInitialSelectedSavedSearch() throws Exception {
		return (String) getPreferenceValue("initialSelectedSavedSearch", String.class);
	}

	/**
	 * Invoke {@link #storePreferences} to persist these changes
	 */
	public void setInitialSelectedSavedSearch(String initialSelectedSavedSearch) throws Exception {
		setPreferenceValue("initialSelectedSavedSearch", initialSelectedSavedSearch);
	}

	/**
	 * The algorithm for this 'smart' preference getter is:
	 * 1) Check the Request Map (skip this step if it isn't a 'smart' preference)
	 * 2) Check the Member variable
	 * 3) Fall back to the PortletPreferences
	 */
	public Boolean getRunSearchOnDisplay() throws Exception {
		return (Boolean) getPreferenceValue("runSearchOnDisplay", Boolean.class);
	}

	/**
	 * Invoke {@link #storePreferences} to persist these changes
	 */
	public void setRunSearchOnDisplay(Boolean runSearchOnDisplay) throws Exception {
		setPreferenceValue("runSearchOnDisplay", runSearchOnDisplay);
	}

	/**
	 * The algorithm for this 'smart' preference getter is:
	 * 1) Check the Request Map (skip this step if it isn't a 'smart' preference)
	 * 2) Check the Member variable
	 * 3) Fall back to the PortletPreferences
	 */
	public Boolean getNoMaxResults() throws Exception {
		return (Boolean) getPreferenceValue("noMaxResults", Boolean.class);
	}

	/**
	 * Invoke {@link #storePreferences} to persist these changes
	 */
	public void setNoMaxResults(Boolean noMaxResults) throws Exception {
		setPreferenceValue("noMaxResults", noMaxResults);
	}

	/**
	 * The algorithm for this 'smart' preference getter is:
	 * 1) Check the Request Map (skip this step if it isn't a 'smart' preference)
	 * 2) Check the Member variable
	 * 3) Fall back to the PortletPreferences
	 */
	public Integer getMaxResults() throws Exception {
		return (Integer) getPreferenceValue("maxResults", Integer.class);
	}

	/**
	 * Invoke {@link #storePreferences} to persist these changes
	 */
	public void setMaxResults(Integer maxResults) throws Exception {
		setPreferenceValue("maxResults", maxResults);
	}

	/**
	 * The algorithm for this 'smart' preference getter is:
	 * 1) Check the Request Map (skip this step if it isn't a 'smart' preference)
	 * 2) Check the Member variable
	 * 3) Fall back to the PortletPreferences
	 */
	public String getLastSearchState() throws Exception {
		return (String) getPreferenceValue("lastSearchState", String.class);
	}

	/**
	 * Invoke {@link #storePreferences} to persist these changes
	 */
	public void setLastSearchState(String lastSearchState) throws Exception {
		setPreferenceValue("lastSearchState", lastSearchState);
	}

	/**
	 * The algorithm for this 'smart' preference getter is:
	 * 1) Check the Request Map (skip this step if it isn't a 'smart' preference)
	 * 2) Check the Member variable
	 * 3) Fall back to the PortletPreferences
	 */
	public String getSavedSearchMap() throws Exception {
		return (String) getPreferenceValue("savedSearchMap", String.class);
	}

	/**
	 * Invoke {@link #storePreferences} to persist these changes
	 */
	public void setSavedSearchMap(String savedSearchMap) throws Exception {
		setPreferenceValue("savedSearchMap", savedSearchMap);
	}
}