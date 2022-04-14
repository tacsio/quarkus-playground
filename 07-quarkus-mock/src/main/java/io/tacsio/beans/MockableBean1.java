package io.tacsio.beans;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class MockableBean1 {

  public String greet(String name) {
    return "Hello " + name;
  }

}
