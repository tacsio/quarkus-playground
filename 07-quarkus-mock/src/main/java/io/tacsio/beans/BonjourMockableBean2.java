package io.tacsio.beans;

import javax.enterprise.context.ApplicationScoped;

public class BonjourMockableBean2 extends MockableBean2 {

  public String greet(String name) {
    return "Bonjour " + name;
  }
}
