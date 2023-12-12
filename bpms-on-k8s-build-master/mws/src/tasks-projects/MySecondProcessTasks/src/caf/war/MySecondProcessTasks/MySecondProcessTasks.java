/**
 * 
 */
package caf.war.MySecondProcessTasks;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ApplicationScoped;
import com.webmethods.caf.faces.annotations.DTManagedBean;
import com.webmethods.caf.faces.annotations.BeanType;

/**
 * @author adit
 *
 */
@ManagedBean(name = "MySecondProcessTasks")
@ApplicationScoped
@DTManagedBean(displayName = "MySecondProcessTasks", beanType = BeanType.APPLICATION)
public class MySecondProcessTasks extends com.webmethods.caf.faces.bean.BaseApplicationBean 
{
	public MySecondProcessTasks()
	{
		super();
		setCategoryName( "CafApplication" );
		setSubCategoryName( "MySecondProcessTasks" );
	}
}