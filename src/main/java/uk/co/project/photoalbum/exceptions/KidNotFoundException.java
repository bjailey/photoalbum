package uk.co.nbrown.photoalbum.exceptions;

public class KidNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -7446074162191875295L;

	@Override
	public String toString() {
		return "Kid not found";
	}
}