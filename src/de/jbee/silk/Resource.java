package de.jbee.silk;

/**
 * Describes WHAT can be injected and WHERE it can be injected.
 * 
 * It is an {@link Instance} with added information where the bind applies.
 * 
 * @author Jan Bernitt (jan.bernitt@gmx.de)
 */
public final class Resource<T>
		implements Comparable<Resource<T>> {

	// Object has the meaning of ANY: e.g. binding Object to a concrete instance mean use that whenever possible (and no other more precise binding applies)

	private final Instance<T> instance;

	private Resource( Instance<T> instance ) {
		super();
		this.instance = instance;
	}

	public boolean isApplicableFor( Dependency<T> dependency ) {
		//TODO what about the discriminator ? we also need to make sure that it fits
		return isAvailableFor( dependency ) && isAssignableTo( dependency );
	}

	/**
	 * Does the {@link Type} of this a valid argument for the one of the {@link Dependency} given ?
	 */
	public boolean isAssignableTo( Dependency<T> dependency ) {
		return instance.getType().isAssignableTo( dependency.getType() );
	}

	/**
	 * Does the given {@link Dependency} occur in the right package ?
	 */
	public boolean isAvailableFor( Dependency<T> dependency ) {

		return true;
	}

	public Type<T> getType() {
		return instance.getType();
	}

	public boolean morePreciseThan( Resource<T> other ) {
		return compareTo( other ) == 1;
	}

	@Override
	public int compareTo( Resource<T> other ) {
		// check type

		// check location (package)

		// check discriminator
		return 0;
	}
}
