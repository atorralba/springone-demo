/**
 * @kind path-problem
 */

import java
import semmle.code.java.dataflow.TaintTracking
import semmle.code.java.dataflow.FlowSources
import DataFlow::PathGraph

class ExpressionParser extends Interface {
  ExpressionParser() { this.hasQualifiedName("org.springframework.expression", "ExpressionParser") }
}

class ParseExpressionParser extends MethodAccess {
  ParseExpressionParser() {
    exists(Method m |
      this.getMethod() = m and
      m.getDeclaringType().getASupertype*() instanceof ExpressionParser and
      m.hasName("parseExpression")
    )
  }
}

class SpELInjectionConfiguration extends TaintTracking::Configuration {
  SpELInjectionConfiguration() { this = "SpELInjectionConfiguration" }

  override predicate isSource(DataFlow::Node source) { source instanceof RemoteFlowSource }

  override predicate isSink(DataFlow::Node sink) {
    sink.asExpr() = any(ParseExpressionParser expParser).getArgument(0)
  }
}

from DataFlow::PathNode source, DataFlow::PathNode sink, SpELInjectionConfiguration conf
where conf.hasFlowPath(source, sink)
select sink.getNode(), source, sink, "SpEL injection from $@.", source.getNode(), "this user input"
