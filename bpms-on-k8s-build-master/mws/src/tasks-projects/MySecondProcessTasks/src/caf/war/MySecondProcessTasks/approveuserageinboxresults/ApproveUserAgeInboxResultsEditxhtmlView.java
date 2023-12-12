/**
 * 
 */
package caf.war.MySecondProcessTasks.approveuserageinboxresults;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import com.webmethods.caf.faces.annotations.ExpireWithPageFlow;
import com.webmethods.caf.faces.annotations.DTManagedBean;
import com.webmethods.caf.faces.annotations.BeanType;

/**
 * @author adit
 *
 */
@ManagedBean(name = "ApproveUserAgeInboxResultsEditxhtmlView")
@SessionScoped
@ExpireWithPageFlow
@DTManagedBean(displayName = "ApproveUserAgeInboxResults/edit", beanType = BeanType.PAGE)
public class ApproveUserAgeInboxResultsEditxhtmlView extends com.webmethods.caf.faces.bean.search.BaseSearchResultOptionsPageBean {

	/**
	 * Determines if a de-serialized file is compatible with this class.
	 *
	 * Maintainers must change this value if and only if the new version
	 * of this class is not compatible with old versions. See Sun docs
	 * for <a href=http://java.sun.com/j2se/1.5.0/docs/guide/serialization/spec/version.html> 
	 * details. </a>
	 */
	private static final long serialVersionUID = 1L;
	private transient caf.war.MySecondProcessTasks.approveuserageinboxresults.ApproveUserAgeInboxResults approveUserAgeInboxResults = null;

	/**
	 * @return
	 */
	protected String getSearchResultControlId() {
		return "searchResultsTableControl";
	}

	public caf.war.MySecondProcessTasks.approveuserageinboxresults.ApproveUserAgeInboxResults getApproveUserAgeInboxResults()  {
		if (approveUserAgeInboxResults == null) {
		    approveUserAgeInboxResults = (caf.war.MySecondProcessTasks.approveuserageinboxresults.ApproveUserAgeInboxResults)resolveExpression("#{ApproveUserAgeInboxResults}");
		}
		return approveUserAgeInboxResults;
	}

}