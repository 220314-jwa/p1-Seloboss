package Models;

public class Reimbursement {
	private int requestid;
	private int userid;
	private String description;
	private double costs;
	private String status; 
	
	public Reimbursement() {
		requestid = 10;
		userid = 5;
		description = "";
		costs = 120.0;
		setStatus("Granted");
		
	}

	public int getRequestid() {
		return requestid;
	}

	public void setRequestid(int requestid) {
		this.requestid = requestid;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getCost() {
		return costs;
	}

	public void setCost(double cost) {
		this.costs = cost;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(costs);
		result =prime*result +(int)(temp ^(temp>>>32));
		result = prime*result +((description == null)?0:description.hashCode());
		result = prime*result +userid;
		result = prime*result + requestid;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if(this ==obj)
			return true;
		if(obj ==null)
			return false;
		if(getClass() != obj.getClass())
			return false;
		Reimbursement other =(Reimbursement) obj;
		if (Double.doubleToLongBits(costs) != Double.doubleToLongBits(other.costs))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (userid != other.userid)
			return false;
		if (requestid != other.requestid)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Reimbursement [requestid=" + requestid + ", userid=" + userid + ", description="
				+ description + ", costs=" + costs + "]";
	}
	
	
}
