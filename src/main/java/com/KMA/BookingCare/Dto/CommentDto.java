package com.KMA.BookingCare.Dto;

public class CommentDto {
	private Long id;
	private Long idUser;
	private Long idHandbook;
	private String content;
	private String userName;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getIdUser() {
		return idUser;
	}
	public void setIdUser(Long idUser) {
		this.idUser = idUser;
	}
	public Long getIdHandbook() {
		return idHandbook;
	}
	public void setIdHandbook(Long idHandbook) {
		this.idHandbook = idHandbook;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	
	
	

}
