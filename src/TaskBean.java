


public class TaskBean {

	private String taskName,desc,endDt,tags,priority,date,status;
	public TaskBean(){
		
	}
	
	public TaskBean(String taskName,String desc,String endDt,String tags, String priority,String status, String date){
		super();
		this.taskName=taskName;
		this.desc=desc;
		this.endDt=endDt;
		this.tags=tags;
		this.priority=priority;	
		this.status=status;
		this.date=date;
	}
	public String getStatus(){
		return status;
	}
	public void setStatus(String status){
		this.status=status;
	}
	public String getDate(){
		return date;
	}
	public void setDate(String date){
		this.date=date;
	}
	
	public String getTaskName(){
		return taskName;
	}
	public void setTaskName(String taskName){
		this.taskName=taskName;
	}
	
	public String getDesc(){
		return desc;
	}
	public void setDesc(String desc){
		this.desc=desc;
	}
	public String getEndDt(){
		return endDt;
	}
	public void setEndDt(String endDt){
		this.endDt=endDt;
	}
	
	public String getTags(){
		return tags;
		
	}
	public void setTags(String tags){
		this.tags=tags;
	}
	public String getPriority(){
		return priority;
	}
	public void setPriority(String priority){
		this.priority=priority;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TaskBean other = (TaskBean) obj;
		if (desc == null) {
			if (other.desc != null)
				return false;
		} else if (!desc.equals(other.desc))
			return false;
		if (endDt == null) {
			if (other.endDt != null)
				return false;
		} else if (!endDt.equals(other.endDt))
			return false;
		if (tags == null) {
			if (other.tags != null)
				return false;
		} else if (!tags.equals(other.tags))
			return false;
		if (priority == null) {
			if (other.priority != null)
				return false;
		} else if (!priority.equals(other.priority))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (taskName == null) {
			if (other.taskName != null)
				return false;
		} else if (!taskName.equals(other.taskName))
			return false;
		if(date == null){
			if(other.date !=null)
				return false;
		} else if(!date.equals(other.date))
			return false;
		
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((desc == null) ? 0 : desc.hashCode());
		result = prime * result + ((endDt == null) ? 0 : endDt.hashCode());
		result = prime * result + ((tags == null) ? 0 : tags.hashCode());
		result = prime * result + ((priority == null) ? 0 : priority.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result
				+ ((taskName == null) ? 0 : taskName.hashCode());
		return result;
	}

	@Override
	public String toString() {
		
		return taskName+":"+desc+":"+endDt+":"+tags+":"+priority+":"+status+":"+date;
	}
	
	
	
	
	

	

}
