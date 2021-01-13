package common;

public class Schedule {
	private String title;
	private String startDate;
	private String endDate;
	private String url;
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	@Override
	public String toString() {
		return "Schedule [title=" + title + ", startDate=" + startDate + ", endDate=" + endDate + ", url=" + url + "]";
	}
	
	
}
