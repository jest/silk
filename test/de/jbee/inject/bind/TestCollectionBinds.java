package de.jbee.inject.bind;

import static de.jbee.inject.Name.named;
import static de.jbee.inject.Type.listTypeOf;
import static de.jbee.inject.Type.raw;
import static de.jbee.inject.Type.setTypeOf;
import static java.util.Arrays.asList;
import static java.util.Collections.singleton;
import static java.util.Collections.singletonList;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.junit.Test;

import de.jbee.inject.Injector;
import de.jbee.inject.Type;

public class TestCollectionBinds {

	private static class CollectionBindsModule
			extends BinderModule {

		@Override
		protected void declare() {
			bind( String.class ).to( "foobar" );
			bind( CharSequence.class ).to( "bar" );
			bind( Integer.class ).to( 42 );
			bind( named( "foo" ), Integer.class ).to( 846 );
			bind( Float.class ).to( 42.0f );
		}

	}

	private static class CollectionBindsBundle
			extends BootstrapperBundle {

		@Override
		protected void bootstrap() {
			installAll( BuildinBundle.class );
			install( CollectionBindsModule.class );
		}

	}

	private static class CollectionBindsJustListBundle
			extends BootstrapperBundle {

		@Override
		protected void bootstrap() {
			install( BuildinBundle.LIST );
			install( CollectionBindsModule.class );
		}

	}

	private static class CollectionBindsJustSetBundle
			extends BootstrapperBundle {

		@Override
		protected void bootstrap() {
			install( BuildinBundle.SET );
			install( CollectionBindsModule.class );
		}

	}

	private final Injector injector = Bootstrap.injector( CollectionBindsBundle.class );
	private final AssertInjects ai = new AssertInjects( injector );

	@Test
	public void thatArrayTypeIsAvailableForAnyBoundType() {
		ai.assertInjects( new String[] { "foobar" }, raw( String[].class ) );
	}

	@Test
	public void thatListIsAvailableForBoundType() {
		ai.assertInjects( singletonList( "foobar" ), listTypeOf( String.class ) );
		ai.assertInjects( asList( new Integer[] { 42, 846 } ), listTypeOf( Integer.class ) );
	}

	@Test
	public void thatSetIsAvailableForBoundType() {
		ai.assertInjects( singleton( "foobar" ), setTypeOf( String.class ) );
		ai.assertInjects( new TreeSet<Integer>( asList( new Integer[] { 42, 846 } ) ),
				setTypeOf( Integer.class ) );
	}

	@Test
	public void thatCollectionIsAvailable() {
		Type<? extends Collection<?>> collectionType = collectionTypeOf( Integer.class );
		ai.assertInjectsItems( new Integer[] { 846, 42 }, collectionType );
	}

	@Test
	public void thatListAsLowerBoundIsAvailable() {
		Type<? extends List<Number>> wildcardListType = listTypeOf( Number.class ).parametizedAsLowerBounds();
		ai.assertInjectsItems( new Number[] { 846, 42, 42.0f }, wildcardListType );
	}

	@Test
	public void thatSetAsLowerBoundIsAvailable() {
		Type<? extends Set<Number>> wildcardSetType = setTypeOf( Number.class ).parametizedAsLowerBounds();
		ai.assertInjectsItems( new Number[] { 846, 42, 42.0f }, wildcardSetType );
	}

	@Test
	public void thatCollectionAsLowerBoundIsAvailable() {
		Type<? extends Collection<Number>> collectionType = collectionTypeOf( Number.class ).parametizedAsLowerBounds();
		ai.assertInjectsItems( new Number[] { 846, 42, 42.0f }, collectionType );
	}

	@Test
	public void thatListOfListsOfBoundTypesAreAvailable() {
		ai.assertInjects( singletonList( singletonList( "foobar" ) ),
				listTypeOf( listTypeOf( String.class ) ) );
	}

	@Test
	public void thatCollectionIsAvailableWhenJustSetIsInstalled() {
		Injector injector = Bootstrap.injector( CollectionBindsJustSetBundle.class );
		Type<? extends Collection<?>> collectionType = collectionTypeOf( Integer.class );
		new AssertInjects( injector ).assertInjectsItems( new Integer[] { 846, 42 }, collectionType );
	}

	@Test
	public void thatCollectionIsAvailableWhenJustListIsInstalled() {
		Injector injector = Bootstrap.injector( CollectionBindsJustListBundle.class );
		Type<? extends Collection<?>> collectionType = collectionTypeOf( Integer.class );
		new AssertInjects( injector ).assertInjectsItems( new Integer[] { 846, 42 }, collectionType );
	}

	private static <T> Type<? extends Collection<T>> collectionTypeOf( Class<T> elementType ) {
		@SuppressWarnings ( "unchecked" )
		Type<? extends Collection<T>> collectionType = (Type<? extends Collection<T>>) raw(
				Collection.class ).parametized( elementType );
		return collectionType;
	}
}
