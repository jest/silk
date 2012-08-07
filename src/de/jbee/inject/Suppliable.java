package de.jbee.inject;

/**
 * Describing data of something that can be supplied.
 * 
 * It describes WHAT is supplied, HOW to supply it and how stable it is and WHERE it came from.
 * 
 * @author Jan Bernitt (jan.bernitt@gmx.de)
 */
public final class Suppliable<T> {

	public final Resource<T> resource;
	public final Supplier<? extends T> supplier;
	public final Repository repository;
	public final Source source;
	public final Stability stability;

	public Suppliable( Resource<T> resource, Supplier<? extends T> supplier, Repository repository,
			Stability stability, Source source ) {
		super();
		this.resource = resource;
		this.supplier = supplier;
		this.repository = repository;
		this.stability = stability;
		this.source = source;
	}

	@Override
	public String toString() {
		return source + " / " + resource + " / " + supplier;
	}
}
