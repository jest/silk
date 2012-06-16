package de.jbee.inject.bind;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith ( Suite.class )
@SuiteClasses ( { TestAutobindBinds.class, TestElementBinds.class, TestInstanceBinds.class,
		TestRobotLegsBinds.class, TestServiceBinds.class, TestServiceDecouplerBinds.class,
		TestSupplierBinds.class, TestTypeBinds.class } )
public class SuiteBind {

}