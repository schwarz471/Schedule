package filter;

import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerResponse;
import com.sun.jersey.spi.container.ContainerResponseFilter;

public class HeaderFilter implements ContainerResponseFilter{

	@Override
	public ContainerResponse filter(ContainerRequest arg0,
			ContainerResponse arg1) {
		arg1.getHttpHeaders().putSingle("Access-Control-Allow-Origin", "*");
		arg1.getHttpHeaders().putSingle("Access-Control-Allow-Methods", "GET, POST");
		arg1.getHttpHeaders().putSingle("Access-Control-Allow-Headers", arg0.getHeaderValue("Access-Control-Request-Headers"));
		return arg1;
	}
}
