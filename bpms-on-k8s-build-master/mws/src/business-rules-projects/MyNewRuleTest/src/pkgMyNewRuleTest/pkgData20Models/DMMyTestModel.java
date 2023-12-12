package pkgMyNewRuleTest.pkgData20Models;
import com.softwareag.rules.datamodel.IRuleAnnotation;
import com.softwareag.rules.datamodel.AbstractBaseDataModel;
import com.softwareag.rules.datamodel.IDataAnnotation;
@IRuleAnnotation(RuleProjectName="MyNewRuleTest",DataModelName="MyTestModel",EventTypeName="") public class DMMyTestModel extends AbstractBaseDataModel {
  private static final long serialVersionUID=1L;
  private String slotname;
  private String slotage;
  private String slotdepartment;
  private String slotdateOfBirth;
  private DMpub_publish_envelope slot_env;
  @IDataAnnotation(OriginalFieldName="name",FieldName="slotname",SlotKey="/name;1;0",Position=0,CameFromFloat=false) public String getSlotname(){
    return this.slotname;
  }
  @IDataAnnotation(OriginalFieldName="name",FieldName="slotname",SlotKey="/name;1;0",Position=0,CameFromFloat=false) public void setSlotname(  String paramname){
    this.slotname=paramname;
  }
  @IDataAnnotation(OriginalFieldName="age",FieldName="slotage",SlotKey="/age;1;0",Position=1,CameFromFloat=false) public String getSlotage(){
    return this.slotage;
  }
  @IDataAnnotation(OriginalFieldName="age",FieldName="slotage",SlotKey="/age;1;0",Position=1,CameFromFloat=false) public void setSlotage(  String paramage){
    this.slotage=paramage;
  }
  @IDataAnnotation(OriginalFieldName="department",FieldName="slotdepartment",SlotKey="/department;1;0",Position=2,CameFromFloat=false) public String getSlotdepartment(){
    return this.slotdepartment;
  }
  @IDataAnnotation(OriginalFieldName="department",FieldName="slotdepartment",SlotKey="/department;1;0",Position=2,CameFromFloat=false) public void setSlotdepartment(  String paramdepartment){
    this.slotdepartment=paramdepartment;
  }
  @IDataAnnotation(OriginalFieldName="dateOfBirth",FieldName="slotdateOfBirth",SlotKey="/dateOfBirth;1;0",Position=3,CameFromFloat=false) public String getSlotdateOfBirth(){
    return this.slotdateOfBirth;
  }
  @IDataAnnotation(OriginalFieldName="dateOfBirth",FieldName="slotdateOfBirth",SlotKey="/dateOfBirth;1;0",Position=3,CameFromFloat=false) public void setSlotdateOfBirth(  String paramdateOfBirth){
    this.slotdateOfBirth=paramdateOfBirth;
  }
  @IDataAnnotation(OriginalFieldName="_env",FieldName="slot_env",SlotKey="/_env;4;0;pub.publish:envelope",Position=4,CameFromFloat=false) public DMpub_publish_envelope getSlot_env(){
    return this.slot_env;
  }
  @IDataAnnotation(OriginalFieldName="_env",FieldName="slot_env",SlotKey="/_env;4;0;pub.publish:envelope",Position=4,CameFromFloat=false) public void setSlot_env(  DMpub_publish_envelope param_env){
    this.slot_env=param_env;
  }
}
