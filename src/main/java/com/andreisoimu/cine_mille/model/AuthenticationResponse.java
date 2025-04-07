package com.andreisoimu.cine_mille.model;

public class AuthenticationResponse {

	private String jwt;

	public AuthenticationResponse(String jwt) {
		this.jwt = jwt;
	}

	public AuthenticationResponse() { }

	public String getJwt() {
		return jwt;
	}

	public void setJwt(String jwt) {
		this.jwt = jwt;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((jwt == null) ? 0 : jwt.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AuthenticationResponse other = (AuthenticationResponse) obj;
		if (jwt == null) {
			if (other.jwt != null)
				return false;
		} else if (!jwt.equals(other.jwt))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "AuthenticationResponse [jwt=" + jwt + ", hashCode()=" + hashCode() + "]";
	}

}
