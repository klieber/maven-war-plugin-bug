package org.example;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

@WebService(portName = "ExampleWebService", serviceName = "ExampleWebService")
public class ExampleWebService {

  @WebMethod
  @WebResult(name = "output")
  public ExampleOutput sayHello(@WebParam(name = "input") ExampleInput input) {
    ExampleOutput output = new ExampleOutput();
    output.setMessage("Hello " + input.getName());
    return output;
  }

}
