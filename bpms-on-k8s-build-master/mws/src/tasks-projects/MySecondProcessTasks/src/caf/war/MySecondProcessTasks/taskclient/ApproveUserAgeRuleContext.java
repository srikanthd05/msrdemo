package caf.war.MySecondProcessTasks.taskclient;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import com.webmethods.caf.faces.annotations.DTManagedBean;
import com.webmethods.caf.faces.annotations.BeanType;

@ManagedBean(name = "ApproveUserAgeRuleContext")
@SessionScoped
@DTManagedBean(displayName = "ApproveUserAge Rule Context", beanType = BeanType.DEFAULT)
public class ApproveUserAgeRuleContext  extends  com.webmethods.caf.faces.data.task.impl.BaseTaskRuleContext {
}