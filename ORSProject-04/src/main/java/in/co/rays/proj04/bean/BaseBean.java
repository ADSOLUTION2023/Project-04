package in.co.rays.proj04.bean;

public class BaseBean {
	
 protected long id;
 protected String createdBy;
 protected String modifiedBy;
 protected String createdDatetime;
 protected String modifiedDatetime;
 
public long getId() {
	return id;
}
public void setId(long id) {
	this.id = id;
}
public String getCreatedBy() {
	return createdBy;
}
public void setCreatedBy(String createdBy) {
	this.createdBy = createdBy;
}
public String getModifiedBy() {
	return modifiedBy;
}
public void setModifiedBy(String modifiedBy) {
	this.modifiedBy = modifiedBy;
}
public String getCreatedDatetime() {
	return createdDatetime;
}
public void setCreatedDatetime(String createdDatetime) {
	this.createdDatetime = createdDatetime;
}
public String getModifiedDatetime() {
	return modifiedDatetime;
}
public void setModifiedDatetime(String modifiedDatetime) {
	this.modifiedDatetime = modifiedDatetime;
}
 
 
}