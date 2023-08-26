package osgi2;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import osgi2.converter.*;
import osgi2.converter.impl.*;

public class Activator implements BundleActivator {

	   private static BundleContext context;

	   static BundleContext getContext() {
	       return context;
	   }

	   public void start(BundleContext bundleContext) throws Exception {
	       Activator.context = bundleContext;
	       
	       System.out.println("Service...");
	       
	       this.registryService();
	       
	       System.out.println("OSGi Service Started");
	   }

	   private void registryService() {
	       Converter converter = new ConverterImpl();
	       context.registerService(Converter.class, converter, null);
	   }

	   public void stop(BundleContext bundleContext) throws Exception {
	       Activator.context = null;
	       System.out.println("OSGi Service Stopped!");
	   }

	}
